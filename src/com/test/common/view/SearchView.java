package com.sunnyit.common.view;

import com.sunnyit.R;
import com.sunnyit.common.convert.PpConvert;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

/**   
* @Title: TopBar.java 
* @Package com.sunnyit.common.view 
* @Description: TODO
* @author yk
* @date 2015年8月20日 下午3:38:20 
* @version V1.0   
*/
public class SearchView extends RelativeLayout {
	
	/**
	 * 清除button
	 */
	private Button mSearchBut;
	/**
	 * 文字输入Edit
	 */
	private EditText mEditText;
	
	/**
	 * 元素布局
	 */
	private LayoutParams mSearchEditLayout;
	private LayoutParams mSearchButLayout;
	
	/**
	* @author yk
	* @date 2015年8月20日 下午4:05:49 
	* @param context
	* @param attrs    设定文件
	* 初始化titlebar界面
	 */
	public SearchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		TypedArray ta=context.obtainStyledAttributes(attrs, R.styleable.topbar);
		
		ta.recycle();
		
		mEditText=new EditText(context);
		mSearchBut=new Button(context);
		
		mEditText=new EditText(context);
		mEditText.setMinHeight(PpConvert.dp2px(context, 15));
		mEditText.setTextSize(PpConvert.sp2px(context, 8));
		//mEditText.setFocusable(false);
		mEditText.setHint("请输入搜索内容");
		
		mSearchBut.setWidth(PpConvert.dp2px(context, 60));
		mSearchBut.setHeight(PpConvert.dp2px(context, 41));
		mSearchBut.setText("搜索");
		mSearchBut.setTextColor(Color.WHITE);
		mSearchBut.setTextSize(PpConvert.convertSpToPx(context, 10));
		mSearchBut.setBackgroundResource(R.drawable.bg_blue14);
		//mSearchBut.setBackgroundColor(Color.argb(255, 0, 172, 255));
		
		mSearchEditLayout=new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		mSearchEditLayout.setMargins(20, 10, 150, 10);
		this.addView(mEditText, mSearchEditLayout);
		
		mSearchButLayout=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		mSearchButLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
		mSearchButLayout.setMargins(20, 12, 20, 10);
		this.addView(mSearchBut, mSearchButLayout);
		
		editLongClickEvent();
		
	}

	private void editLongClickEvent() {
		
	}

}


