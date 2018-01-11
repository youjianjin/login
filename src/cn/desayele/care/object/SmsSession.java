package cn.desayele.care.object;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/13.
 */
public class SmsSession implements Serializable{

    private String userUn;//用户账号即手机号
    private String userMo;//发送的随机码6位
    private long timeMillis;//上次发送的时间戳

    public String getUserUn() {
        return userUn;
    }

    public void setUserUn(String userUn) {
        this.userUn = userUn;
    }

    public String getUserMo() {
        return userMo;
    }

    public void setUserMo(String userMo) {
        this.userMo = userMo;
    }

    public long getTimeMillis() {
        return timeMillis;
    }

    public void setTimeMillis(long timeMillis) {
        this.timeMillis = timeMillis;
    }
}
