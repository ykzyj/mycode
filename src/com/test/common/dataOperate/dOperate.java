package com.sunnyit.common.dataOperate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**   
* @Title: dsad.java 
* @Package com.sunnyit.common.dataOperate 
* @Description: TODO
* @author yk
* @date 2015年10月13日 上午10:44:49 
* @version V1.0   
*/
public class dOperate {
	public static int compare_date(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }
	
	public static String getCurrentDate(String type) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(type);
        return formatter.format(currentTime);
    }
	
	public static String getCurrentDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(currentTime);
    }
	
	public static String getCurrentLongDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        return formatter.format(currentTime);
    }
	
	public static String getCurrentAllLongDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return formatter.format(currentTime);
    }
}


