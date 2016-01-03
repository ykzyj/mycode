package com.sunnyit.common.network;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**   
* @Title: NetworkDetector.java 
* @Package com.sunnyit.common.network 
* @Description: TODO
* @author yk
* @date 2015��9��16�� ����2:21:53 
* @version V1.0   
*/
public class NetworkDetector {
	/**
	* @author yk 
	* @date 2015��9��16�� ����2:23:15 
	* @Title: detect 
	* @Description: �жϵ�ǰ���绷���Ƿ����
	* @param act
	* @return    �趨�ļ� 
	* @return boolean    �������� 
	* @throws
	 */
	public static boolean detect(Activity act) 
	{    
        
       ConnectivityManager manager = (ConnectivityManager) act    
              .getApplicationContext().getSystemService(    
                     Context.CONNECTIVITY_SERVICE);    
       if (manager == null) {    
           return false;    
       }    
          
       NetworkInfo networkinfo = manager.getActiveNetworkInfo();    
          
       if (networkinfo == null || !networkinfo.isAvailable()) {    
           return false;    
       }    
     
       return true;    
    }   
	
	
	/**
	* @author yk 
	* @date 2015��9��16�� ����2:38:41 
	* @Title: getCurrentNetType 
	* @Description: ��������
	* @param context
	* @return    �趨�ļ� 
	* @return String    �������� 
	* @throws
	 */
	public static String getCurrentNetType(Context context) 
	{
		String type = "";
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if (info == null) 
		{
			type = "null";
		} 
		else if (info.getType() == ConnectivityManager.TYPE_WIFI) 
		{
			type = "wifi";
		} 
		else if (info.getType() == ConnectivityManager.TYPE_MOBILE) 
		{
			int subType = info.getSubtype();
			if (subType == TelephonyManager.NETWORK_TYPE_CDMA || subType == TelephonyManager.NETWORK_TYPE_GPRS
					|| subType == TelephonyManager.NETWORK_TYPE_EDGE) 
			{
				type = "2g";
			} 
			else if (subType == TelephonyManager.NETWORK_TYPE_UMTS || subType == TelephonyManager.NETWORK_TYPE_HSDPA
					|| subType == TelephonyManager.NETWORK_TYPE_EVDO_A || subType == TelephonyManager.NETWORK_TYPE_EVDO_0
					|| subType == TelephonyManager.NETWORK_TYPE_EVDO_B) 
			{
				type = "3g";
			} 
			else if (subType == TelephonyManager.NETWORK_TYPE_LTE) 
			{
				type = "4g";
			}
		}
		return type;
	}
}


