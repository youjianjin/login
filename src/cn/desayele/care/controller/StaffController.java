package cn.desayele.care.controller;

import cn.desayele.care.entity.TCompanyEntity;
import cn.desayele.care.entity.TStaffEntity;
import cn.desayele.care.entity.TStaffInfoEntity;
import cn.desayele.care.entity.TStaffProfileEntity;
import cn.desayele.care.object.Loginer;
import cn.desayele.care.serviceimpl.CompanyService;
import cn.desayele.care.serviceimpl.StaffService;
import cn.desayele.care.util.Gettime;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @apiNote  员工相关
 * @author dingfada
 * @date 20171225
 */
@Controller
@RequestMapping("/staff")
public class StaffController {
//    @Resource(name="userService")
//    private UserService userService;
    @Resource(name="staffService")
    private StaffService staffService;
    @Resource(name="companyService")
    private CompanyService companyService;

    static Logger logger= Logger.getLogger(CompanyController.class);

    /**
     * @apiNote 获取员工信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/staffinfo")
    public @ResponseBody
    Map checkRandCode(HttpServletRequest request, HttpServletResponse response){
        Map map=new HashMap<String,Object>();
        try{
            Loginer l=(Loginer)request.getSession().getAttribute("loginer");
            if(l==null){
                map.put("msg","error1");
            }else{
                String oid=l.getOid();
                TStaffEntity ts=staffService.getTStaffEntityByOID(oid);
                TStaffInfoEntity tsi=staffService.getTStaffInfoEntityByOID(ts.getSiid());
                map.put("ts",ts);
                map.put("tsi",tsi);
                map.put("msg","ok");
            }
        }catch (Exception e){
            map.put("msg","error0");
        }
        return map;
    }
    //更新员工信息
    @RequestMapping("/modifystaffinfo")
    public @ResponseBody Map modifyStaffInfo(HttpServletRequest request, HttpServletResponse response){
        Map map=new HashMap<String,Object>();
        try{
            Loginer l=(Loginer)request.getSession().getAttribute("loginer");
            String gemail=request.getParameter("gemail");
            String gjobnum=request.getParameter("gjobnum");
            String gqq=request.getParameter("gqq");
            if(l==null){
                map.put("msg","error1");
            }else{
                if(gemail==null || gemail.equals("")){
                    map.put("msg","error0");
                }else{//判断邮箱是否在用
                    TStaffEntity tsold=staffService.getTStaffEntityByGEGlev2(gemail,"1");
                    boolean isempty=false;
                    if(tsold==null){
                        isempty=true;
                    }else{
                        if(l.getOid().equals(tsold.getOid())){
                            isempty=true;
                        }
                    }
                    if(isempty){
                        TStaffEntity ts1=staffService.getTStaffEntityByOID(l.getOid());
                        ts1.setGemail(gemail);
                        if(ts1.getSiid()==null || ts1.getSiid().equals("")){//新账号
                            String siid="SI"+ Gettime.getRandom12();
                            TStaffInfoEntity tse=new TStaffInfoEntity();
                            tse.setOid(siid);
                            tse.setGjobnum(gjobnum);
                            tse.setGqq(gqq);
                            tse.setCid(l.getCid());
                            staffService.addTStaffInfoEntity(tse);
                            ts1.setSiid(siid);
                        }else{
                            TStaffInfoEntity tse=staffService.getTStaffInfoEntityByOID(ts1.getSiid());
                            tse.setGjobnum(gjobnum);
                            tse.setGqq(gqq);
                            staffService.addTStaffInfoEntity(tse);
                        }
                        staffService.addTStaffEntity(ts1);
                        map.put("msg","ok");
                    }else{
                        map.put("msg","error2");//邮箱有人用了
                    }
                }
            }
        }catch (Exception e){
            map.put("msg","error0");
        }
        return map;
    }
    //查询员工简介
    @RequestMapping("/staffprofile")
    public @ResponseBody Map staffProfile(HttpServletRequest request, HttpServletResponse response){
        Map map=new HashMap<String,Object>();
        try{
            Loginer l=(Loginer)request.getSession().getAttribute("loginer");
            if(l==null){
                map.put("msg","error1");
            }else{
                TStaffEntity ts=staffService.getTStaffEntityByOID(l.getOid());
                TCompanyEntity tc=companyService.getTCompanyEntityByOid(ts.getCid());
                TStaffInfoEntity tsi=staffService.getTStaffInfoEntityByOID(ts.getSiid());
                TStaffProfileEntity tsp=staffService.getTStaffProfileEntityBySID(ts.getOid());
                map.put("ts",ts);
                map.put("tc",tc);
                map.put("tsi",tsi);
                map.put("tsp",tsp);
                map.put("msg","ok");
            }
        }catch (Exception e){
            map.put("msg","error0");
        }
        return map;
    }
    //更新员工简介
    @RequestMapping("/modifystaffprofile")
    public @ResponseBody Map modifyStaffProfile(HttpServletRequest request, HttpServletResponse response){
        Map map=new HashMap<String,Object>();
        try{
            Loginer l=(Loginer)request.getSession().getAttribute("loginer");
            String ssign=request.getParameter("ssign");
            String sexp=request.getParameter("sexp");
            String sgoodat=request.getParameter("sgoodat");
            if(l==null){
                map.put("msg","error1");
            }else{
                TStaffProfileEntity tsp=staffService.getTStaffProfileEntityBySID(l.getOid());
                if(tsp==null){//new
                    tsp=new TStaffProfileEntity();
                    String tspid="SP"+Gettime.getRandom12();
                    tsp.setOid(tspid);
                    tsp.setSsign(ssign);
                    tsp.setSexp(sexp);
                    tsp.setSgoodat(sgoodat);
                    tsp.setSid(l.getOid());
                }else{//modify
                    tsp.setSsign(ssign);
                    tsp.setSexp(sexp);
                    tsp.setSgoodat(sgoodat);
                }
                staffService.addTStaffProfileEntity(tsp);
                map.put("msg","ok");
            }
        }catch (Exception e){
            map.put("msg","error0");
        }
        return map;
    }
}
