package cn.desayele.care.serviceimpl;


import cn.desayele.care.entity.TUserEntity;
import cn.desayele.care.service.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * @apiNote: 用户登录注册相关
 * @author dingfada
 * @date 2017.12.12
 */

@Service
public class UserService extends BaseServiceImpl {
	private static final Logger logger=Logger.getLogger(UserService.class);

    /**
     * @apiNote 根据手机号获取用户登录信息
     * @param gmobile
     * @return TUserEntity
     */
    public TUserEntity getTUserEntityByGM(String gmobile){
        TUserEntity t=queryEntity("select * from t_user where gmobile='"+gmobile+"'",TUserEntity.class);
        return t;
    }
    /**
     * @apiNote 根据手机号、密码获取用户登录信息
     * @param gmobile
     * @param gpassword
     * @return TUserEntity
     */
    public TUserEntity getTUserEntityByGMGP(String gmobile,String gpassword){
        TUserEntity t=queryEntity("select * from t_user where gmobile='"+gmobile+"' and gpassword='"+gpassword+"'",TUserEntity.class);
        return t;
    }
    /**
     * @apiNote 根据邮箱获取用户登录信息
     * @param gemail
     * @return TUserEntity
     */
    public TUserEntity getTUserEntityByGE(String gemail){
        TUserEntity t=queryEntity("select * from t_user where gemail='"+gemail+"'",TUserEntity.class);
        return t;
    }
    /**
     * @apiNote 注册用户
     * @param gusername
     * @param gmobile
     * @param gemail
     * @param gpassword
     */
    public void addTUserEntity(String oid,String gusername,String gmobile,String gemail,String gpassword,String glev1){
        TUserEntity te=new TUserEntity();
        te.setOid(oid);
        te.setGusername(gusername);
        te.setGmobile(gmobile);
        te.setGemail(gemail);
        te.setGpassword(gpassword);
        te.setGlev1(glev1);
        te.setGlev2("0");
        te.setGlev3("0");
        add(te);
    }
    //添加或更新用户
    public void addTUserEntity(TUserEntity tUserEntity){
        saveOrupdateSQL(tUserEntity);
    }

}
