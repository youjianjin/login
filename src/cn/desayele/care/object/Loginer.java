package cn.desayele.care.object;

import java.io.Serializable;

/**
 * Created by ding on 2017/12/12.
 * 存放登录信息到session
 */
public class Loginer implements Serializable{


    private String oid;
    private String gmobile;
    private String gltype;//登录方式：1个人2企业
    private String cid;//关联公司id
    private String glev1;//1超管2管3员工
    private String glev2;//0停用1启用
    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getGmobile() {
        return gmobile;
    }

    public void setGmobile(String gmobile) {
        this.gmobile = gmobile;
    }

    public String getGltype() {
        return gltype;
    }

    public void setGltype(String gltype) {
        this.gltype = gltype;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getGlev1() {
        return glev1;
    }

    public void setGlev1(String glev1) {
        this.glev1 = glev1;
    }

    public String getGlev2() {
        return glev2;
    }

    public void setGlev2(String glev2) {
        this.glev2 = glev2;
    }
}
