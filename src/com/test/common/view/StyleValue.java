package com.sunnyit.common.view;

import com.sunnyit.R;
import com.sunnyit.common.convert.PpConvert;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

/**   
* @Title: GetStyleValue.java 
* @Package com.sunnyit.common.view 
* @Description: TODO
* @author yk
* @date 2015年8月7日 下午2:09:15 
* @version V1.0   
*/
public class StyleValue {
	
	
	public static int getStyleValue(Context context, AttributeSet attrs, 
			int defStyle,int[] styleIds,int targetId){
		int defValue=PpConvert.convertDpToPx(context,80);
		int value = defValue;
		TypedArray a=context.getTheme().obtainStyledAttributes(attrs, R.styleable.LeftSlidingMenu,defStyle , 0);
		int mcount=a.getIndexCount();
		for(int i=0;i<mcount;i++)
		{
			int attr=a.getIndex(i);
			if(attr==targetId)
			{
				value=a.getDimensionPixelSize(attr, defValue);
				break;
			}
		}
		a.recycle();
		
		return value;
	}
}


