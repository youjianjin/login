package cn.desayele.care.object;

import java.io.Serializable;

/**
 * Created by dingfada on 2017/12/15.
 * 注册后防止用户跑路又回来，所以不帮他登录，而是暂时储存在这里
 */
public class Register implements Serializable{

    private String oid;//注册后的oid
    private String gmobile;//注册手机号
    private String gltype;//注册方式：1个人2企业

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
}
