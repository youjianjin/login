package cn.desayele.care.serviceimpl;


import cn.desayele.care.entity.TStaffEntity;
import cn.desayele.care.entity.TStaffInfoEntity;
import cn.desayele.care.entity.TStaffProfileEntity;
import cn.desayele.care.service.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * @apiNote: 员工登录注册相关
 * @author dingfada
 * @date 2017.12.12
 */

@Service
public class StaffService extends BaseServiceImpl {
	private static final Logger logger=Logger.getLogger(StaffService.class);

    /**
     * @apiNote 根据手机号获取员工登录信息
     * @return TStaffEntity
     */
    public TStaffEntity getTStaffEntityByGM(String gmobile,String glev2){
        TStaffEntity t=queryEntity("select * from t_staff where gmobile='"+gmobile+"' and glev2='"+glev2+"'",TStaffEntity.class);
        return t;
    }
    /**
     * @apiNote 根据手机号、密码获取员工登录信息
     * @return TStaffEntity
     */
    public TStaffEntity getTStaffEntityByGMGP(String gmobile, String gpassword,String glev2){
        TStaffEntity t=queryEntity("select * from t_staff where gmobile='"+gmobile+"' and gpassword='"+gpassword+"' and glev2='"+glev2+"'",TStaffEntity.class);
        return t;
    }
    public TStaffEntity getTStaffEntityByGMGP1(String gmobile, String gpassword){
        TStaffEntity t=queryEntity("select * from t_staff where gmobile='"+gmobile+"' and gpassword='"+gpassword+"'",TStaffEntity.class);
        return t;
    }
    /**
     * @apiNote 根据oid获取员工登录信息
     * @return TStaffEntity
     */
    public TStaffEntity getTStaffEntityByOID(String goid){
        TStaffEntity t=queryEntity("select * from t_staff where oid='"+goid+"'",TStaffEntity.class);
        return t;
    }
    /**
     * @apiNote 根据oid获取员工信息
     * @return TStaffInfoEntity
     */
    public TStaffInfoEntity getTStaffInfoEntityByOID(String goid){
        TStaffInfoEntity t=queryEntity("select * from t_staffinfo where oid='"+goid+"'",TStaffInfoEntity.class);
        return t;
    }
    /**
     * @apiNote 根据oid获取员工简介
     * @return TStaffProfileEntity
     */
    public TStaffProfileEntity getTStaffProfileEntityBySID(String sid){
        TStaffProfileEntity t=queryEntity("select * from t_staffprofile where sid='"+sid+"'",TStaffProfileEntity.class);
        return t;
    }
    /**
     * @apiNote 根据邮箱获取员工登录信息
     * @param gemail
     * @return TUserEntity
     */
    public TStaffEntity getTStaffEntityByGE(String gemail){
        TStaffEntity t=queryEntity("select * from t_staff where gemail='"+gemail+"'",TStaffEntity.class);
        return t;
    }
    public TStaffEntity getTStaffEntityByGEGlev2(String gemail,String glev2){
        TStaffEntity t=queryEntity("select * from t_staff where gemail='"+gemail+"' and glev2='"+glev2+"'",TStaffEntity.class);
        return t;
    }

    /**
     * @apiNote 注册3个表
     * @param cid
     * @param gcompany
     * @param gmobile
     * @param gemail
     * @param gpassword
     */
    public void addTStaffEntity(String oid,String gcompany,String gmobile,String gemail,String gpassword,String cid,String did,String siid,String glev1,String glev2){
        TStaffEntity tse=new TStaffEntity();
        tse.setOid(oid);
        tse.setGusername(gcompany);//公司的用户名就是公司名字
        tse.setGmobile(gmobile);
        tse.setGemail(gemail);
        tse.setGpassword(gpassword);
        tse.setCid(cid);//关联公司表
        tse.setDid(did);
        tse.setSiid(siid);
        tse.setGlev1(glev1);
        tse.setGlev2(glev2);
        tse.setGlev3("0");
        add(tse);
    }
    //添加或更新员工
    public void addTStaffEntity(TStaffEntity tStaffEntity){
        saveOrupdateSQL(tStaffEntity);
    }
    public void addTStaffInfoEntity(String oid,String gposition,String gjobnum,String cid){
        TStaffInfoEntity tsie=new TStaffInfoEntity();
        tsie.setOid(oid);
        tsie.setGposition(gposition);
        tsie.setGjobnum(gjobnum);
        tsie.setCid(cid);
        add(tsie);
    }
    //更新员工信息
    public void addTStaffInfoEntity(TStaffInfoEntity tStaffInfoEntity){
        saveOrupdateSQL(tStaffInfoEntity);
    }
    //更新员工简介
    public void addTStaffProfileEntity(TStaffProfileEntity tStaffProfileEntity){
        saveOrupdateSQL(tStaffProfileEntity);
    }
    //添加/更新员工信息
    public void modifyStaff(TStaffEntity ts){
        saveOrupdateSQL(ts);
    }
    //更新职位和工号：以后可能有很多字段在这里
    public void modifyStaffInfoPoJn(String siid,String gpostion,String gjobnum){
        executeupdate("update t_staffinfo set gposition='"+gpostion+"',gjobnum='"+gjobnum+"' where oid='"+siid+"'");
    }
    //去掉某公司管理员
    public void deleteGlev1Is2(String cid,String before,String end){
        executeupdate("update t_staff set glev1='"+end+"' where glev1='"+before+"' and cid='"+cid+"'");
    }
    //添加某人为管理员
    public void addGlev1Is3(String oid,String end){
        executeupdate("update t_staff set glev1='"+end+"' where oid='"+oid+"'");
    }
}
