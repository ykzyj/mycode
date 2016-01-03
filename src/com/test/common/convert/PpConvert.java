package com.sunnyit.common.convert;

import android.content.Context;
import android.util.TypedValue;

/**   
* @Title: SpToDp.java 
* @Package com.sunnyit.common.convert 
* @Description: TODO
* @author yk
* @date 2015年8月7日 上午9:53:57 
* @version V1.0   
*/
public class PpConvert {
	
	/**
	* @author yk 
	* @date 2015年8月7日 上午10:05:20 
	* @Title: convertDpToPx 
	* @Description: 把dp转化为px，即把dp值转化为像素
	* @param dp
	* @param context
	* @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	public static int convertDpToPx(Context context,int dp)
	{
		int mpx;
		mpx=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				context.getResources().getDisplayMetrics());
		return mpx;
	}
	
	/**
	* @author yk 
	* @date 2015年8月7日 上午10:05:05 
	* @Title: convertSpToPx 
	* @Description: 把sp转化为px，即把sp值转化为像素
	* @param sp
	* @param context
	* @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	public static int convertSpToPx(Context context,int sp)
	{
		int mpx;
		mpx=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, 
				context.getResources().getDisplayMetrics());
		return mpx;
	}
	
	/**
	* @author yk 
	* @date 2015年8月21日 上午9:54:11 
	* @Title: dip2px 
	* @Description: TODO
	* @param context
	* @param dipValue
	* @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	public static int dp2px(Context context, float dipValue)
	{
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int)(dipValue * scale +0.5f); 
	}
	
	/**
	* @author yk 
	* @date 2015年8月21日 上午9:54:15 
	* @Title: px2dip 
	* @Description: TODO
	* @param context
	* @param pxValue
	* @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	public static int px2dp(Context context, float pxValue)
	{
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int)(pxValue / scale +0.5f); 
	}


	/**
	* @author yk 
	* @date 2015年8月26日 上午10:37:41 
	* @Title: sp2px 
	* @Description: TODO
	* @param context
	* @param spValue
	* @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	public static int sp2px(Context context, float spValue)  
	{  
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,  
				spValue, context.getResources().getDisplayMetrics());  
	}  
}


