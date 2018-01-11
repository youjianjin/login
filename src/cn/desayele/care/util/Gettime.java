package cn.desayele.care.util;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Gettime {

	public static String getTS(){
		DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str=dateFormat.format(new Date());
		return str;
	}
	public static String getGainTS(){
		DateFormat dateFormat=new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		String str=dateFormat.format(new Date());
		return str;
	}
	public static String getLastUpdate() {
		//14位字符
		DateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
		String str=dateFormat.format(new Date());
		return str;
	}
	public static String getDATETS(){
		DateFormat dateFormat=new SimpleDateFormat("yyyy-MM");
		String str=dateFormat.format(new Date());
		return str;
	}
	public static String getRandom(){
		Date date=new Date();
		SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMddHHmmss");
		double rand=Math.random();
		String randString =sdf.format(date)+String .valueOf(rand).substring(2,12);
		return randString;
	}
	//获得yyyyMMdd+xxxx
	public static String getRandom12(){
		Date date=new Date();
		SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMddHHmmss");
		double rand=Math.random();
		String randString =sdf.format(date).substring(0,8)+String .valueOf(rand).substring(2,6);
		return randString;
	}
	 public static Date getDateLong() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(0);
		Date currentTime_2 = formatter.parse(dateString, pos);
		return currentTime_2;

	}

}
