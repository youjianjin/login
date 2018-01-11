package cn.desayele.care.controller;


import cn.desayele.care.entity.*;
import cn.desayele.care.object.Loginer;
import cn.desayele.care.serviceimpl.CompanyService;
import cn.desayele.care.serviceimpl.StaffService;
import cn.desayele.care.serviceimpl.UserService;
import cn.desayele.care.util.Gettime;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @apiNote  公司相关
 * @author dingfada
 * @date 20171216
 */
@Controller
@RequestMapping("/company")
public class CompanyController {
	@Resource(name="userService")
	private UserService userService;
	@Resource(name="staffService")
	private StaffService staffService;
	@Resource(name="companyService")
	private CompanyService companyService;

	static Logger logger= Logger.getLogger(CompanyController.class);

	/**
	 * @apiNote 获取公司信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/companyinfo")
	public @ResponseBody Map checkRandCode(HttpServletRequest request, HttpServletResponse response){
		Map map=new HashMap<String,Object>();
		try{
			Loginer l=(Loginer)request.getSession().getAttribute("loginer");
			if(l==null){
				map.put("msg","error1");
			}else{
				String gmobile=l.getGmobile();
				String cid=l.getCid();
				TStaffEntity ts=staffService.getTStaffEntityByGM(gmobile,"1");
				TCompanyEntity tc=companyService.getTCompanyEntityByOid(cid);
				TCompanyInfoEntity ti=companyService.getTCompanyInfoEntityByCid(cid);
				map.put("table1",ts);
				map.put("table2",tc);
				map.put("table3",ti);
				map.put("msg","ok");
			}
		}catch (Exception e){
			map.put("msg","error0");
		}
		return map;
	}
	/**
	 * @apiNote 更新公司信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/modifycompany")
	public @ResponseBody Map modifycompany(HttpServletRequest request, HttpServletResponse response){
		Map map=new HashMap<String,Object>();
		try{
			Loginer l=(Loginer)request.getSession().getAttribute("loginer");
			if(l==null){
				map.put("msg","error1");
			}else{
				String l3=request.getParameter("l3");//contact
				String l4=request.getParameter("l4");//city
				String l5=request.getParameter("l5");//address
				String l6=request.getParameter("l6");//info
				String l7=request.getParameter("l7");//mould
				String ciid=request.getParameter("ciid");
				if(l3==null || l4==null || l5==null || l6==null || l7==null){
					map.put("msg","error0");
				}else{
					TCompanyInfoEntity tie=new TCompanyInfoEntity();
					if(ciid==null || ciid==""){
						String cinfoid="CI"+ Gettime.getRandom12();
						tie.setOid(cinfoid);
					}else{
						tie.setOid(ciid);
					}
					tie.setGcontact(l3);
					tie.setGcity(l4);
					tie.setGaddress(l5);
					tie.setGinfo(l6);
					tie.setGmould(l7);
					tie.setCid(l.getCid());
					companyService.modifyCompanyInfo(tie);
					map.put("msg","ok");
				}
			}
		}catch (Exception e){
			map.put("msg","error0");
		}
		return map;
	}
	//获取公司员工列表(非超级管理员)
	@RequestMapping("/stafflist")
	public @ResponseBody Map staffList(HttpServletRequest request, HttpServletResponse response){
		Map map=new HashMap<String,List>();
		try{
			Loginer l=(Loginer)request.getSession().getAttribute("loginer");
			if(l==null){
				map.put("msg","error1");
			}else{
				String cid=l.getCid();
				List list=companyService.getTSDSIListByCid(cid,"1");
				map.put("list",list);
				map.put("msg","ok");
			}
		}catch (Exception e){
			map.put("msg","error0");
		}
		return map;
	}
	@RequestMapping("/securestafflist")
	public @ResponseBody Map securestafflist(HttpServletRequest request, HttpServletResponse response){
		Map map=new HashMap<String,List>();
		try{
			Loginer l=(Loginer)request.getSession().getAttribute("loginer");
			if(l==null){
				map.put("msg","error1");
			}else{
				String cid=l.getCid();
				List list=companyService.getSeTStaffListByCid(cid,"1","1");
				map.put("list",list);
				map.put("msg","ok");
			}
		}catch (Exception e){
			map.put("msg","error0");
		}
		return map;
	}
	//添加部门
	@RequestMapping("/adddept")
	public @ResponseBody Map addDept(HttpServletRequest request, HttpServletResponse response){
		Map map=new HashMap<String,Object>();
		try{
			Loginer l=(Loginer)request.getSession().getAttribute("loginer");
			String deptname=request.getParameter("deptname");
			if(l==null){
				map.put("msg","error1");
			}else{
				if(deptname==null || deptname==""){
					map.put("msg","error0");
				}else{
					TDeptEntity tde=new TDeptEntity();
					String did="DE"+Gettime.getRandom12();
					tde.setOid(did);
					tde.setGdept(deptname);
					tde.setCid(l.getCid());
					companyService.addTDeptEntity(tde);
					map.put("msg","ok");
				}
			}
		}catch (Exception e){
			map.put("msg","error0");
		}
		return map;
	}
	//修改部门
	@RequestMapping("/modifydept")
	public @ResponseBody Map modifydept(HttpServletRequest request, HttpServletResponse response){
		Map map=new HashMap<String,Object>();
		try{
			Loginer l=(Loginer)request.getSession().getAttribute("loginer");
			String deptid=request.getParameter("deptid");
			String deptname=request.getParameter("deptname");
			if(l==null){
				map.put("msg","error1");
			}else{
				if(deptid==null || deptid=="" || deptname==null || deptname==""){
					map.put("msg","error0");
				}else{
					TDeptEntity tde=new TDeptEntity();
					tde.setOid(deptid);
					tde.setGdept(deptname);
					tde.setCid(l.getCid());
					companyService.addTDeptEntity(tde);
					map.put("msg","ok");
				}
			}
		}catch (Exception e){
			map.put("msg","error0");
		}
		return map;
	}
	//添加员工
	@RequestMapping("/addstaff")
	public @ResponseBody Map addstaff(HttpServletRequest request, HttpServletResponse response){
		Map map=new HashMap<String,Object>();
		try{
			Loginer l=(Loginer)request.getSession().getAttribute("loginer");
			String sname=request.getParameter("sname");
			String smobile=request.getParameter("smobile");
			String sdept=request.getParameter("sdept");
			String sposition=request.getParameter("sposition");
			String sjobnum=request.getParameter("sjobnum");
			if(l==null){
				map.put("msg","error1");
			}else{
				if(sname==null || sname=="" || smobile==null || smobile=="" || sdept==null || sdept==""){
					map.put("msg","error0");
				}else{
					TStaffEntity tse=staffService.getTStaffEntityByGM(smobile,"1");//1查询是否有正在使用的手机。
					if(tse==null){
						String rand=Gettime.getRandom12();
						String oid="US"+rand;
						String siid="SI"+rand;
						staffService.addTStaffEntity(oid,sname,smobile,"","",l.getCid(),sdept,siid,"3","1");
						staffService.addTStaffInfoEntity(siid,sposition,sjobnum,l.getCid());
						map.put("msg","ok");
					}else{
						map.put("msg","error2");//已有在使用的手机了
					}
				}
			}
		}catch (Exception e){
			map.put("msg","error0");
		}
		return map;
	}
	//修改员工
	@RequestMapping("/modifystaff")
	public @ResponseBody Map modifystaff(HttpServletRequest request, HttpServletResponse response){
		Map map=new HashMap<String,Object>();
		try{
			Loginer l=(Loginer)request.getSession().getAttribute("loginer");
			String soid=request.getParameter("soid");
			String sdept=request.getParameter("sdept");
			String sposition=request.getParameter("sposition");
			String sjobnum=request.getParameter("sjobnum");
			if(l==null){
				map.put("msg","error1");
			}else{
				if(soid==null || soid=="" || sdept==null || sdept==""){
					map.put("msg","error0");
				}else{
					TStaffEntity tse=staffService.getTStaffEntityByOID(soid);
					if(tse==null){
						map.put("msg","error3");//没有该用户
					}else{
						tse.setDid(sdept);
						staffService.modifyStaff(tse);
						if(tse.getSiid()==null || tse.getSiid().equals("")){
							String newsiid="SI"+Gettime.getRandom12();
							staffService.addTStaffInfoEntity(newsiid,sposition,sjobnum,l.getCid());
							map.put("msg","ok");
						}else{
							staffService.modifyStaffInfoPoJn(tse.getSiid(),sposition,sjobnum);
							map.put("msg","ok");
						}
					}
				}
			}
		}catch (Exception e){
			map.put("msg","error0");
		}
		return map;
	}
	//停用员工
	@RequestMapping("/lockstaff")
	public @ResponseBody Map lockstaff(HttpServletRequest request, HttpServletResponse response){
		Map map=new HashMap<String,Object>();
		try{
			Loginer l=(Loginer)request.getSession().getAttribute("loginer");
			String soid=request.getParameter("soid");
			if(l==null){
				map.put("msg","error1");
			}else{
				if(soid==null || soid==""){
					map.put("msg","error0");
				}else{
					TStaffEntity tse=staffService.getTStaffEntityByOID(soid);
					if(tse==null){
						map.put("msg","error3");//没有该用户
					}else{
						if(tse.getGlev2().equals("0")){//那就要看看是否有在用的
							TStaffEntity newtse=staffService.getTStaffEntityByGM(tse.getGmobile(),"1");
							if(newtse==null){
								tse.setGlev2("1");
								staffService.modifyStaff(tse);
								map.put("msg","ok");
							}else{
								map.put("msg","error2");//没有该用户
							}
						}else if(tse.getGlev2().equals("1")){//执行停用，收回管理员权限
							tse.setGlev2("0");
							tse.setGlev1("3");//普通员工
							staffService.modifyStaff(tse);
							map.put("msg","ok");
						}else{
							map.put("msg","error0");
						}
					}
				}
			}
		}catch (Exception e){
			map.put("msg","error0");
		}
		return map;
	}
	//更换管理员
	@RequestMapping("/modifystafflev")
	public @ResponseBody Map modifystaffLev(HttpServletRequest request, HttpServletResponse response){
		Map map=new HashMap<String,Object>();
		try{
			Loginer l=(Loginer)request.getSession().getAttribute("loginer");
			String soid=request.getParameter("soid");
			if(l==null){
				map.put("msg","error1");
			}else{
				if(soid==null || soid==""){
					map.put("msg","error0");
				}else{
					if(soid.equals("0")){//去掉管理员
						staffService.deleteGlev1Is2(l.getCid(),"2","3");
						map.put("msg","ok");
					}else{//更换管理员
						staffService.deleteGlev1Is2(l.getCid(),"2","3");
						staffService.addGlev1Is3(soid,"2");
						map.put("msg","ok");
					}
				}
			}
		}catch (Exception e){
			map.put("msg","error0");
		}
		return map;
	}
	//客户列表
	@RequestMapping("/clientlist")
	public @ResponseBody Map clientList(HttpServletRequest request, HttpServletResponse response){
		Map map=new HashMap<String,List>();
		try{
			Loginer l=(Loginer)request.getSession().getAttribute("loginer");
			if(l==null){
				map.put("msg","error1");
			}else{
				List list=companyService.getComClientByCid(l.getCid());
				map.put("list",list);
				map.put("msg","ok");
			}
		}catch (Exception e){
			map.put("msg","error0");
		}
		return map;
	}
	//供应商列表
	@RequestMapping("/supplylist")
	public @ResponseBody Map supplyList(HttpServletRequest request, HttpServletResponse response){
		Map map=new HashMap<String,List>();
		try{
			Loginer l=(Loginer)request.getSession().getAttribute("loginer");
			if(l==null){
				map.put("msg","error1");
			}else{
				List list=companyService.getComSupplyByCid(l.getCid());
				map.put("list",list);
				map.put("msg","ok");
			}
		}catch (Exception e){
			map.put("msg","error0");
		}
		return map;
	}
	//搜索公司
	@RequestMapping("/searchcomlist")
	public @ResponseBody Map searchComList(HttpServletRequest request, HttpServletResponse response){
		Map map=new HashMap<String,List>();
		try{
			Loginer l=(Loginer)request.getSession().getAttribute("loginer");
			String namelike=request.getParameter("namelike");
			if(l==null){
				map.put("msg","error1");
			}else{
				if(namelike==null || namelike==""){
					map.put("msg","error0");
				}else{
					List list=companyService.getComListByNameLike(namelike);
					map.put("list",list);
					map.put("msg","ok");
				}
			}
		}catch (Exception e){
			map.put("msg","error0");
		}
		return map;
	}
	//添加客户
	@RequestMapping("/addclient")
	public @ResponseBody Map addClient(HttpServletRequest request, HttpServletResponse response){
		Map map=new HashMap<String,List>();
		try{
			Loginer l=(Loginer)request.getSession().getAttribute("loginer");
			String clid=request.getParameter("clid");
			if(l==null){
				map.put("msg","error1");
			}else{
				if(clid==null || clid==""){
					map.put("msg","error0");
				}else{
					companyService.addclient(l.getCid(),clid);
					map.put("msg","ok");
				}
			}
		}catch (Exception e){
			map.put("msg","error0");
		}
		return map;
	}
	//添加供应商
	@RequestMapping("/addsupply")
	public @ResponseBody Map addSupply(HttpServletRequest request, HttpServletResponse response){
		Map map=new HashMap<String,List>();
		try{
			Loginer l=(Loginer)request.getSession().getAttribute("loginer");
			String suid=request.getParameter("suid");
			if(l==null){
				map.put("msg","error1");
			}else{
				if(suid==null || suid==""){
					map.put("msg","error0");
				}else{
					companyService.addsupply(l.getCid(),suid);
					map.put("msg","ok");
				}
			}
		}catch (Exception e){
			map.put("msg","error0");
		}
		return map;
	}
}
