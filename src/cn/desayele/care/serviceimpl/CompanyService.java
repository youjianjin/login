package cn.desayele.care.serviceimpl;


import cn.desayele.care.entity.*;
import cn.desayele.care.service.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @apiNote: 公司相关
 * @author dingfada
 * @date 2017.12.12
 */

@Service
public class CompanyService extends BaseServiceImpl {
	private static final Logger logger=Logger.getLogger(CompanyService.class);

    /**
     * @apiNote 公司id获取公司
     * @param oid
     * @return TCompanyEntity
     */
    public TCompanyEntity getTCompanyEntityByOid(String oid){
        TCompanyEntity t=queryEntity("select * from t_company where oid='"+oid+"'",TCompanyEntity.class);
        return t;
    }
    /**
     * @apiNote 根据公司名获取公司
     * @param gcompany
     * @return TUserEntity
     */
    public TCompanyEntity getTCompanyEntityByGC(String gcompany){
        TCompanyEntity t=queryEntity("select * from t_company where gcompany='"+gcompany+"'",TCompanyEntity.class);
        return t;
    }
    //更新公司
    public void modifyCompany(TCompanyEntity te){
        saveOrupdateSQL(te);
    }
    /*******************************************t_companyinfo********************************************/
    /**
     * @apiNote 公司id获取公司信息
     * @param cid
     * @return TCompanyInfoEntity
     */
    public TCompanyInfoEntity getTCompanyInfoEntityByCid(String cid){
        TCompanyInfoEntity t=queryEntity("select * from t_companyinfo where cid='"+cid+"'",TCompanyInfoEntity.class);
        return t;
    }
    /**
     * @apiNote 注册3个表
     * @param oid
     * @param gcompany
     *
     */
    public void addTCompanyEntity(String oid,String gcompany){
        TCompanyEntity tce=new TCompanyEntity();
        tce.setOid(oid);
        tce.setGcompany(gcompany);
        add(tce);
    }
    //更新公司信息
    public void modifyCompanyInfo(TCompanyInfoEntity ti){
        saveOrupdateSQL(ti);
    }
    //获取公司员工列表(非超级管理员)
    public List getTSDSIListByCid(String cid,String not){
        String sql="select a.oid,b.oid did,b.gdept,a.siid,a.glev2,a.gusername,a.gmobile,c.gposition,c.gjobnum from " +
                "(select * from t_dept where cid='"+cid+"') b left join " +
                "(select * from t_staff where cid='"+cid+"' and glev1!='"+not+"') a on a.did=b.oid left join " +
                "(select * from t_staffinfo where cid='"+cid+"') c on a.siid=c.oid order by gdept,did,oid";
        List list=queryList(sql);
        return list;
    }
    public List getSeTStaffListByCid(String cid,String glev1,String glev2){
        String sql="select oid,gusername,glev1 from t_staff where cid='"+cid+"' and glev1!='"+glev1+"' and glev2='"+glev2+"'";
        List list=queryList(sql);
        return list;
    }
    //添加/更新部门
    public void addTDeptEntity(TDeptEntity tde){
        saveOrupdateSQL(tde);
    }
    //获取某公司客户列表
    public List getComClientByCid(String cid){
        String sql="select a.clientid,b.gcompany,c.gcity,c.gaddress,c.gmould from " +
                "(select cid,clientid from t_client where cid='"+cid+"') a inner join " +
                "(select oid,gcompany from t_company) b on a.clientid=b.oid left join " +
                "(select gcity,gaddress,gmould,cid from t_companyinfo) c on a.clientid=c.cid";
        List list=queryList(sql);
        return list;
    }
    public List getComSupplyByCid(String cid){
        String sql="select a.supplyid,b.gcompany,c.gcity,c.gaddress,c.gmould from " +
                "(select cid,supplyid from t_supply where cid='"+cid+"') a inner join " +
                "(select oid,gcompany from t_company) b on a.supplyid=b.oid left join " +
                "(select gcity,gaddress,gmould,cid from t_companyinfo) c on a.supplyid=c.cid";
        List list=queryList(sql);
        return list;
    }
    //模糊搜索公司名字
    public List getComListByNameLike(String name){
        List list=queryList("select oid,gcompany from t_company where gcompany like '%"+name+"%'");
        return list;
    }
    //添加一个客户
    public void addclient(String cid,String clid){
        TClientEntity tc=new TClientEntity();
        tc.setCid(cid);
        tc.setClientid(clid);
        add(tc);
    }
    public void addsupply(String cid,String suid){
        TSupplyEntity ts=new TSupplyEntity();
        ts.setCid(cid);
        ts.setSupplyid(suid);
        add(ts);
    }
}
