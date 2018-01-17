package cn.desayele.care.controller;


import cn.desayele.care.entity.TCompanyEntity;
import cn.desayele.care.entity.TStaffEntity;
import cn.desayele.care.entity.TUserEntity;
import cn.desayele.care.object.Loginer;
import cn.desayele.care.object.Register;
import cn.desayele.care.object.SmsSession;
import cn.desayele.care.serviceimpl.CompanyService;
import cn.desayele.care.serviceimpl.StaffService;
import cn.desayele.care.serviceimpl.UserService;
import cn.desayele.care.util.Gettime;
import cn.desayele.care.util.Password;
import cn.desayele.care.util.RandNum;
import com.aliyuncs.exceptions.ClientException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @apiNote  用户登录注册个人信息等
 * @author dingfada
 * @date 20171202
 */
@Controller
@RequestMapping("/user")
public class UserController {
	@Resource(name="userService")
	private UserService userService;
	@Resource(name="staffService")
	private StaffService staffService;
	@Resource(name="companyService")
	private CompanyService companyService;

	static Logger logger= Logger.getLogger(UserController.class);

	/**
	 * @apiNote 1个人登录2员工登录
	 * @param request
	 * @param response
     * @return
     */
	@RequestMapping("/login")
	public @ResponseBody Map checkMobile(HttpServletRequest request,HttpServletResponse response){
		Map map=new HashMap<String,String>();
		try{
			String gmobile=request.getParameter("gmobile");
			String gpassword=request.getParameter("gpassword");
			String gcode=request.getParameter("gcode");
			String gltype=request.getParameter("gltype");//1个人登录2企业登录
			if(gmobile==null || gmobile=="" || gpassword==null || gpassword=="" || gcode==null || gcode=="" || gltype==null || gltype==""){
				map.put("msg","error1");
			}else{
				String randcode=request.getSession().getAttribute("randCode").toString();
				if(randcode.equals(gcode)){
					String MD5pwd=Password.getEnPassword(gpassword);
					if(gltype.equals("1")){
						TUserEntity user=userService.getTUserEntityByGMGP(gmobile,MD5pwd);
						if(user==null || user.getOid()==null){//账号或密码错误
							map.put("msg","error2");
						}else{//将登录信息储存到session
							Loginer loginer=new Loginer();
							loginer.setOid(user.getOid());
							loginer.setGmobile(user.getGmobile());
							loginer.setGltype(gltype);
							loginer.setGlev1(user.getGlev1());
							request.getSession().setAttribute("loginer",loginer);
//							logSuc(request);
							map.put("msg","ok");
						}
					}
					if(gltype.equals("2")){
						TStaffEntity staff=staffService.getTStaffEntityByGMGP1(gmobile,MD5pwd);
						if(staff==null || staff.getOid()==null){
							map.put("msg","error2");//不存在用户
						}else{
							if(staff.getGlev2().equals("0")){
								map.put("msg","errorlock");
							}else if(staff.getGlev2().equals("1")){//将登录信息储存到session
								Loginer loginer=new Loginer();
								loginer.setOid(staff.getOid());
								loginer.setGmobile(staff.getGmobile());
								loginer.setGltype(gltype);
								loginer.setCid(staff.getCid());
								loginer.setGlev1(staff.getGlev1());
								loginer.setGlev2(staff.getGlev2());
								request.getSession().setAttribute("loginer",loginer);
								map.put("msg","ok");
							}else{
								map.put("msg","error1");
							}
						}
					}
				}else{
					map.put("msg","error1");
				}
			}
		}catch (Exception e){
			map.put("msg","error1");
		}
		return map;
	}
	/**
	 * @apiNote 检测验证码
	 * @param request
	 * @param response
     * @return
     */
	@RequestMapping("/checkRandCode")
	public @ResponseBody Map checkRandCode(HttpServletRequest request,HttpServletResponse response){
		Map map=new HashMap<String,String>();
		try{
			String userCode=request.getParameter("usercode");
			String sessCode=request.getSession().getAttribute("randCode").toString();
			if(userCode.equals(sessCode)){
				map.put("msg","ok");
			}else{
				map.put("msg","error");
			}
		}catch (Exception e){
			map.put("msg","error");
		}
		return map;
	}

	/**
	 * @apiNote 发送手机验证码
	 * @param request
	 * @param response
     * @return
     */
	@RequestMapping("/sendSMS")
	public @ResponseBody Map sendSMS(HttpServletRequest request,HttpServletResponse response){
		Map map=new HashMap<String,String>();
		try {
			String userun=request.getParameter("userun");
			String userval=request.getParameter("userval");
			String grtype=request.getParameter("grtype");
			if(userun==null || userun=="" || userval==null || userval=="" || grtype==null || grtype==""){
				map.put("msg","error1");
			}else{
				boolean chkmo=false;
				if(grtype.equals("1")){
					TUserEntity t=userService.getTUserEntityByGM(userun);
					if(t==null){chkmo=true;}
				}
				if(grtype.equals("2")){
					TStaffEntity t=staffService.getTStaffEntityByGM(userun,"1");
					if(t==null){chkmo=true;}
				}
				if(chkmo){
					SmsSession ss= (SmsSession) request.getSession().getAttribute("SmsSession");
					String sessCode=request.getSession().getAttribute("randCode").toString();
					if(userval.equals(sessCode)){
						//先检测session是否存在
						long cur_tm= RandNum.getCurrentTM();//获取当前tm
						String randcode=RandNum.getRandNum6();
						if(ss==null){//第一次请求
							ss=new SmsSession();
							ss.setTimeMillis(cur_tm);
							ss.setUserMo(randcode);//保存随机码到session
							ss.setUserUn(userun);//保存手机号到session换号时使用新的随机码，防止验证通过后又换填手机号
							request.getSession().setAttribute("SmsSession",ss);
							//请求发送sms
							String code=sendSMSReturnCode(userun,randcode);
							map.put("code",code);
							map.put("msg","ok");
							map.put("tm",0);
						}else{//已建立session
							String oldcode=ss.getUserMo();
							if(userun.equals(ss.getUserUn())){randcode=oldcode;}//没换号就继续使用旧随机码
							long ss_tm=ss.getTimeMillis();
							long duration=cur_tm-ss_tm;
							if(duration>60*1000){//正常请求后，重置计时器
								ss.setTimeMillis(cur_tm);
								request.getSession().setAttribute("SmsSession",ss);
								//请求发送sms
								String code=sendSMSReturnCode(userun,randcode);
								map.put("code",code);
							}else{
								//一分钟内的请求拒绝发送
							}
							map.put("msg","ok");
							map.put("tm",duration);
						}
					}else{
						map.put("msg","error2");//回调页面执行刷新验证码
					}
				}else{//手机号码已存在
					map.put("msg","error3");
				}
			}
		}catch (Exception e){
			map.put("msg","error0");
		}
		return map;
	}
	@RequestMapping("/sendSMS1")
	public @ResponseBody Map sendSMS1(HttpServletRequest request,HttpServletResponse response){
		Map map=new HashMap<String,String>();
		try {
			String userun=request.getParameter("userun");
			String userval=request.getParameter("userval");
			String grtype=request.getParameter("grtype");
			if(userun==null || userun=="" || userval==null || userval=="" || grtype==null || grtype==""){
				map.put("msg","error1");
			}else{
				boolean chkmo=false;
				if(grtype.equals("1")){
					TUserEntity t=userService.getTUserEntityByGM(userun);
					if(t==null){}else{chkmo=true;}
				}
				if(grtype.equals("2")){
					TStaffEntity t=staffService.getTStaffEntityByGM(userun,"1");
					if(t==null){}else{chkmo=true;}
				}
				if(chkmo){
					SmsSession ss= (SmsSession) request.getSession().getAttribute("SmsSession");
					String sessCode=request.getSession().getAttribute("randCode").toString();
					if(userval.equals(sessCode)){
						//先检测session是否存在
						long cur_tm= RandNum.getCurrentTM();//获取当前tm
						String randcode=RandNum.getRandNum6();
						if(ss==null){//第一次请求
							ss=new SmsSession();
							ss.setTimeMillis(cur_tm);
							ss.setUserMo(randcode);//保存随机码到session
							ss.setUserUn(userun);//保存手机号到session换号时使用新的随机码，防止验证通过后又换填手机号
							request.getSession().setAttribute("SmsSession",ss);
							//请求发送sms
							String code=sendSMSReturnCode(userun,randcode);
							map.put("code",code);
							map.put("msg","ok");
							map.put("tm",0);
						}else{//已建立session
							String oldcode=ss.getUserMo();
							if(userun.equals(ss.getUserUn())){randcode=oldcode;}//没换号就继续使用旧随机码
							long ss_tm=ss.getTimeMillis();
							long duration=cur_tm-ss_tm;
							if(duration>60*1000){//正常请求后，重置计时器
								ss.setTimeMillis(cur_tm);
								request.getSession().setAttribute("SmsSession",ss);
								//请求发送sms
								String code=sendSMSReturnCode(userun,randcode);
								map.put("code",code);
							}else{
								//一分钟内的请求拒绝发送
							}
							map.put("msg","ok");
							map.put("tm",duration);
						}
					}else{
						map.put("msg","error2");//回调页面执行刷新验证码
					}
				}else{//手机号码不存在
					map.put("msg","error3");
				}
			}
		}catch (Exception e){
			map.put("msg","error0");
		}
		return map;
	}
	public String sendSMSReturnCode(String mobilenum,String randcode) throws ClientException{
		//SendSmsResponse response= SmsHandle.sendSms(mobilenum,randcode);
		logger.info("发送短信给用户----------------"+mobilenum+"随机码："+randcode);
		System.out.println(randcode);
		//System.out.println("Code=" + response.getCode());
        //System.out.println("Message=" + response.getMessage());
        //System.out.println("RequestId=" + response.getRequestId());
        //System.out.println("BizId=" + response.getBizId());
		//return response.getCode();
		return "ok";
	}
	/**
	 * @apiNote 手机验证码
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/checkUserMo")
	public @ResponseBody Map checkUserMo(HttpServletRequest request,HttpServletResponse response){
		Map map=new HashMap<String,String>();
		try{
			String usermo=request.getParameter("usermo");
			SmsSession ss= (SmsSession) request.getSession().getAttribute("SmsSession");
			if(usermo==null || usermo==""){
				map.put("msg","error");
			}else{
				if(ss==null){
					map.put("msg","error");
				}else{
					String oldcode=ss.getUserMo();
					if(oldcode.equals(usermo)){
						map.put("msg","ok");
					}else{
						map.put("msg","error");
					}
				}
			}
		}catch (Exception e){
			map.put("msg","error");
		}
		return map;
	}
	/**
	 * @apiNote 邮箱唯一性验证
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/checkEmail")
	public @ResponseBody Map checkEmail(HttpServletRequest request,HttpServletResponse response){
		Map map=new HashMap<String,String>();
		try{
			String gemail=request.getParameter("gemail");
			TUserEntity tc=userService.getTUserEntityByGE(gemail);
			if(tc==null){
				map.put("msg","ok");
			}else{
				map.put("msg","error");
			}
		}catch (Exception e){
			map.put("msg","error");
		}
		return map;
	}
	/**
	 * @apiNote 公司名唯一性验证
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/checkCompanyName")
	public @ResponseBody Map checkCompanyName(HttpServletRequest request,HttpServletResponse response){
		Map map=new HashMap<String,String>();
		try{
			String gcompany=request.getParameter("gcompany");
			TCompanyEntity tc=companyService.getTCompanyEntityByGC(gcompany);
			if(tc==null){
				map.put("msg","ok");
			}else{
				map.put("msg","error1");
			}
		}catch (Exception e){
			map.put("msg","error");
		}
		return map;
	}
	/**
	 * @apiNote 注册用户，员工
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/register")
	public @ResponseBody Map register(HttpServletRequest request,HttpServletResponse response){
		Map map=new HashMap<String,String>();
		try{
			String gusername=request.getParameter("gusername");
			String gcompany=request.getParameter("gcompany");
			String gmobile=request.getParameter("gmobile");
			String gemail=request.getParameter("gemail");
			String gpassword=request.getParameter("gpassword");
			String grtype=request.getParameter("grtype");
			if(gmobile==null || gmobile=="" || gemail==null || gemail=="" || gpassword==null || gpassword=="" || grtype==null || grtype==""){
				map.put("msg","error0");
			}else{
				//gpassword= Password.getEnPassword(gpassword);
				if(grtype.equals("1")){//1手机号唯一性2邮箱唯一性
					TUserEntity t=userService.getTUserEntityByGM(gmobile);
					if(t==null){
						t=userService.getTUserEntityByGE(gemail);
						if(t==null){
							if(gusername==null || gusername==""){
								map.put("msg","error3");
							}else{
								String regMsg=userRegister(request,gusername,gmobile,gemail,gpassword);//存在一个漏洞，注册后是否自动登录
								map.put("msg",regMsg);
							}
						}else{map.put("msg","error2");}
					}else{map.put("msg","error1");}
				}
				else if(grtype.equals("2")){//1手机号唯一性2邮箱唯一性3公司名唯一性
					TStaffEntity t=staffService.getTStaffEntityByGM(gmobile,"1");
					if(t==null){
						t=staffService.getTStaffEntityByGE(gemail);
						if(t==null){
							TCompanyEntity tc=companyService.getTCompanyEntityByGC(gcompany);
							if(tc==null){
								String regMsg=comRegister(request,gcompany,gmobile,gemail,gpassword);//存在一个漏洞，注册后是否自动登录
								map.put("msg",regMsg);
							}else{map.put("msg","error3");}
						}else{map.put("msg","error2");}
					}else{map.put("msg","error1");}
				}
				else{
					map.put("msg","error");
				}
			}
		}catch (Exception e){
			map.put("msg","error");
		}
		return map;
	}

	//个人注册
	public String userRegister(HttpServletRequest request,String gusername,String gmobile,String gemail,String gpassword){
		String msg;
		try{
			String oid="US"+Gettime.getRandom12();
			String MD5pwd= Password.getEnPassword(gpassword);
			userService.addTUserEntity(oid,gusername,gmobile,gemail,MD5pwd,"1");
			Register r=new Register();
			r.setOid(oid);
			r.setGmobile(gmobile);
			r.setGltype("1");
			request.getSession().setAttribute("register",r);
			msg="ok";
		}catch (Exception e){
			msg="error";
		}
		return msg;
	}
	//公司注册
	public String comRegister(HttpServletRequest request,String gcompany,String gmobile,String gemail,String gpassword){
		String msg;
		try{
			String cid= "CO"+Gettime.getRandom12();
			String uid= "US"+Gettime.getRandom12();
			String MD5pwd= Password.getEnPassword(gpassword);
			companyService.addTCompanyEntity(cid,gcompany);
			staffService.addTStaffEntity(uid,gcompany,gmobile,gemail,MD5pwd,cid,"","","1","1");//这里是否做一个事件回滚？还是执行删除
			Register r=new Register();
			r.setOid(uid);
			r.setGmobile(gmobile);
			r.setGltype("2");
			request.getSession().setAttribute("register",r);
			msg="ok";
		}catch (Exception e){
			msg="error";
		}
		return msg;
	}
	////////////////////////// 重置密码 //////////////////////////
	@RequestMapping("/checkmobileexist")
	public @ResponseBody Map checkMobileExist(HttpServletRequest request,HttpServletResponse response){
		Map map=new HashMap<String,String>();
		try{
			String gmobile=request.getParameter("gmobile");
			String gltype=request.getParameter("gltype");//1个人登录2企业登录
			if(gmobile==null || gmobile=="" || gltype==null || gltype==""){
				map.put("msg","error");
			}else{
				if(gltype.equals("1")){
					TUserEntity t=userService.getTUserEntityByGM(gmobile);
					if(t==null || t.getOid()==null){//不存在用户
						map.put("msg","error0");
					}else{
						map.put("msg","ok");
					}
				}
				if(gltype.equals("2")){
					TStaffEntity t1=staffService.getTStaffEntityByGM(gmobile,"1");
					if(t1==null || t1.getOid()==null){
						map.put("msg","error0");//不存在用户或停用
					}else{
						map.put("msg","ok");
					}
				}
			}
		}catch (Exception e){
			map.put("msg","error");
		}
		return map;
	}
	@RequestMapping("/modifypassword")
	public @ResponseBody Map modifyPassword(HttpServletRequest request,HttpServletResponse response){
		Map map=new HashMap<String,String>();
		try{
			String gmobile=request.getParameter("gmobile");
			String gltype=request.getParameter("gltype");//1个人登录2企业登录
			String gmo=request.getParameter("gmo");
			String gpassword=request.getParameter("gpassword");
			String grepassword=request.getParameter("grepassword");
			if(gmobile==null || gmobile=="" || gltype==null || gltype=="" || gmo==null || gmo=="" || gpassword==null || gpassword=="" || grepassword==null || grepassword==""){
				map.put("msg","error");
			}else{
				//String sessCode=request.getSession().getAttribute("randCode").toString();
				SmsSession ss= (SmsSession) request.getSession().getAttribute("SmsSession");
				if(ss==null){
					map.put("msg","error1");
				}else{
					String smo=ss.getUserMo();
					if(smo.equals(gmo)){
						String MD5=Password.getEnPassword(gpassword);
						if(gltype.equals("1")){
							TUserEntity tue=userService.getTUserEntityByGM(gmobile);
							tue.setGpassword(MD5);
							userService.addTUserEntity(tue);
							//
							Register r=new Register();
							r.setOid(tue.getOid());
							r.setGmobile(gmobile);
							r.setGltype("1");
							request.getSession().setAttribute("register",r);
							map.put("msg","ok");
						}
						if(gltype.equals("2")){
							TStaffEntity tse=staffService.getTStaffEntityByGM(gmobile,"1");//应该不需要判断了吧
							tse.setGpassword(MD5);
							staffService.addTStaffEntity(tse);
							//
							Register r=new Register();
							r.setOid(tse.getOid());
							r.setGmobile(gmobile);
							r.setGltype("2");
							request.getSession().setAttribute("register",r);
							map.put("msg","ok");
						}
					}else{
						map.put("msg","error1");
					}
				}
			}
		}catch (Exception e){
			map.put("msg","error");
		}
		return map;
	}
	////////////////////////// 注册or登录后 //////////////////////////
	/**
	 * @apiNote 根据session中的gltype判定用户所属
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/regsuc")
	public ModelAndView regSuc(HttpServletRequest request, HttpServletResponse response){
		Register r= (Register) request.getSession().getAttribute("register");
		if(r==null){
			return new ModelAndView("redirect:/login.jsp?method=logtout");
		}else{
			if(r.getGltype().equals("1")){
				TUserEntity te=userService.getTUserEntityByGM(r.getGmobile());
				if(te==null){
					return new ModelAndView("redirect:/login.jsp?method=register");//你可能需要重新注册
				}else{
					Loginer l=new Loginer();
					l.setOid(te.getOid());
					l.setGmobile(te.getGmobile());
					l.setGltype(r.getGltype());
					l.setGlev1(te.getGlev1());
					request.getSession().setAttribute("loginer",l);
					return new ModelAndView("redirect:/pInfo.jsp");
				}
			}else if(r.getGltype().equals("2")){
				TStaffEntity ts=staffService.getTStaffEntityByGM(r.getGmobile(),"1");
				if(ts==null){
					return new ModelAndView("redirect:/login.jsp?method=register");//你可能需要重新注册
				}else{
					Loginer l=new Loginer();
					l.setOid(ts.getOid());
					l.setGmobile(ts.getGmobile());
					l.setGltype(r.getGltype());
					l.setCid(ts.getCid());
					l.setGlev1(ts.getGlev1());
					l.setGlev2(ts.getGlev2());
					request.getSession().setAttribute("loginer",l);
					if(ts.getGlev1().equals("1") || ts.getGlev1().equals("2")){
						return new ModelAndView("redirect:/cInfo.jsp");
					}else if(ts.getGlev1().equals("3")){
						return new ModelAndView("redirect:/sInfo.jsp");
					}else{
						return new ModelAndView("redirect:/login.jsp?method=register");//你可能需要重新注册，这里以后有其他类型
					}
				}
			}else{
				return new ModelAndView("redirect:/login.jsp?method=logtout");
			}
		}
	}
	@RequestMapping("/logsuc")
	public ModelAndView logSuc(HttpServletRequest request, HttpServletResponse response){
		Loginer loginer= (Loginer) request.getSession().getAttribute("loginer");
		if(loginer==null){
			return new ModelAndView("redirect:/login.jsp?method=logtout");
		}else{
			if(loginer.getGltype().equals("1")){
				return new ModelAndView("redirect:/pMain.jsp");
			}else if(loginer.getGltype().equals("2")){
				if(loginer.getGlev1().equals("1")){
					return new ModelAndView("redirect:/cMain.jsp");
				}else if(loginer.getGlev1().equals("3")){
					return new ModelAndView("redirect:/sMain.jsp");
				}else{return null;}
			}else{
				return new ModelAndView("redirect:/login.jsp?method=logtout");
			}
		}
	}
	////////////////////////// 退出 //////////////////////////
	@RequestMapping("/logout")
	public ModelAndView logOut(HttpServletRequest request, HttpServletResponse response){
		try{
			request.getSession().removeAttribute("loginer");
			request.getSession().removeAttribute("register");
			request.getSession().removeAttribute("SmsSession");
			return new ModelAndView("redirect:/login.jsp?method=logout");
		}catch (Exception e){
			return new ModelAndView("redirect:/login.jsp?method=logout");
		}
	}
}
