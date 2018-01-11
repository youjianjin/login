package cn.desayele.care.util;

/**
 * Created by Administrator on 2017/12/13.
 */
public class RandNum {

    //获取6位数的随机数字
    public static String getRandNum6(){
        Double d=Math.random();
        return d.toString().substring(2,8);
    }
    //获取当前的timemillis
    public static Long getCurrentTM(){
        long tm=System.currentTimeMillis();
        return tm;
    }
}
