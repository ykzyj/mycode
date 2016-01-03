package com.sunnyit.common.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

/**   
* @Title: ActivityCollector.java 
* @Package com.sunnyit.common.activity 
* @Description: TODO
* @author yk
* @date 2015年8月14日 下午2:45:28 
* @version V1.0   
*/
public class ActivityCollector { 
	/**
	 * activity集合
	 */
	public static List<Activity> activities=new ArrayList<Activity>();
	
	public static void addActivity(Activity activity) {
		activities.add(activity);
	}

	public static void removeActivity(Activity activity) {
		activities.remove(activity);
	}

	public static void finishAll() {
		for (Activity activity : activities) {
			if (!activity.isFinishing()) {
				activity.finish();
			}
		}
	}

}


