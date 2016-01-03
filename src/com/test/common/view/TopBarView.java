package com.sunnyit.common.view;

import com.sunnyit.R;
import com.sunnyit.common.convert.PpConvert;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**   
* @Title: TopBar.java 
* @Package com.sunnyit.common.view 
* @Description: TODO
* @author yk
* @date 2015年8月20日 下午3:38:20 
* @version V1.0   
*/
public class TopBarView extends RelativeLayout {
	/**
	 * 左button
	 */
	private Button mLeftBut;
	/**
	 * 右button
	 */
	private Button mRightBut;
	/**
	 * title
	 */
	private TextView mTitleText;
	
	/**
	 * title的界面设置属性
	 */
	private String titletext; 
	private int titletextcolor;
	private int titletextsize;
	
	/**
	 * leftbut的设置属性
	 */
	private String lefttext;
	private int lefttextcolor;
	private int lefttextsize;
	private Drawable leftbackground;
	
	/**
	 * leftbut的设置属性
	 */
	private String righttext;
	private int righttextcolor;
	private int righttextsize;
	private Drawable rightbackground;
	
	/**
	 * 三元素布局
	 */
	private LayoutParams leftLayout;
	private LayoutParams rightLayout;
	private LayoutParams titleLayout;
	
	/**
	 * 接口
	 */
	private ITopBarClick mITopBarClick;

	/**
	* @author yk
	* @date 2015年8月20日 下午4:05:49 
	* @param context
	* @param attrs    设定文件
	* 初始化titlebar界面
	 */
	public TopBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		TypedArray ta=context.obtainStyledAttributes(attrs, R.styleable.topbar);
		
		titletext=ta.getString(R.styleable.topbar_titletext);
		titletextcolor=ta.getColor(R.styleable.topbar_titletextcolor, Color.BLACK);
		titletextsize=ta.getDimensionPixelSize(R.styleable.topbar_titletextsize, 12);
		
		lefttext=ta.getString(R.styleable.topbar_lefttext);
		lefttextcolor=ta.getColor(R.styleable.topbar_lefttextcolor, Color.BLACK);
		lefttextsize=ta.getDimensionPixelSize(R.styleable.topbar_lefttextsize, 12);
		leftbackground=ta.getDrawable(R.styleable.topbar_leftbackground);
		
		righttext=ta.getString(R.styleable.topbar_righttext);
		righttextcolor=ta.getColor(R.styleable.topbar_rightcolor, Color.BLACK);
		righttextsize=ta.getDimensionPixelSize(R.styleable.topbar_rightsize, 12);
		rightbackground=ta.getDrawable(R.styleable.topbar_rightbackground);
		
		ta.recycle();
		
		mTitleText=new TextView(context);
		mLeftBut=new Button(context);
		mRightBut=new Button(context);
		
		mTitleText.setText(titletext);
		mTitleText.setTextColor(titletextcolor);
		mTitleText.setTextSize(PpConvert.px2dp(context,titletextsize));
		mTitleText.setGravity(Gravity.CENTER);
		mTitleText.setEllipsize(TruncateAt.MARQUEE);
		mTitleText.setSingleLine(true);
		mTitleText.setSelected(true);
		
		mLeftBut.setText(lefttext);
		mLeftBut.setTextColor(lefttextcolor);
		mLeftBut.setTextSize(PpConvert.px2dp(context,lefttextsize));
		mLeftBut.setBackgroundDrawable(leftbackground);
		
		mRightBut.setText(righttext);
		mRightBut.setTextColor(righttextcolor);
		mRightBut.setTextSize(PpConvert.px2dp(context,righttextsize));
		mRightBut.setBackgroundDrawable(rightbackground);
		
		titleLayout=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
		titleLayout.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);
		titleLayout.setMargins(130, 0, 130, 0); 
		addView(mTitleText, titleLayout);
		
		leftLayout=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		leftLayout.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
		leftLayout.addRule(RelativeLayout.CENTER_VERTICAL);
		leftLayout.setMargins(10, 0, 0, 0);
		addView(mLeftBut, leftLayout);
		
		rightLayout=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		rightLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,TRUE);
		rightLayout.addRule(RelativeLayout.CENTER_VERTICAL);
		rightLayout.setMargins(0, 0, 20, 0);
		addView(mRightBut,rightLayout);
		
		mLeftBut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mITopBarClick!=null)
				{
					mITopBarClick.onClickLeftBut();
				}
			}
		});
		
		mRightBut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mITopBarClick!=null)
				{
					mITopBarClick.onClickRightBut();
				}
			}
		});
	}
	
	/**
	* @author yk 
	* @date 2015年9月11日 下午4:45:34 
	* @Title: setTitle 
	* @Description: 设置title
	* @param title    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void setTitle(String title) {
		this.mTitleText.setText(title);
	}
	
	public void setRightButText(String text) {
		this.mRightBut.setText(text);
	}
	
	/**
	* @author yk 
	* @date 2015年8月21日 下午1:39:38 
	* @Title: setTopBarClick 
	* @Description: 设置接口回调
	* @param topBarClick    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void setTopBarClick(ITopBarClick topBarClick) {
		this.mITopBarClick=topBarClick;
	}
	
	/*
	 * 左but可见性
	 */
	public void setLeftButVisibility(int visibility) {
		this.mLeftBut.setVisibility(visibility);
	}
	
	/*
	 * 右but可见性
	 */
	public void setRightButVisibility(int visibility) {
		this.mRightBut.setVisibility(visibility);
	}
	
	/**
	 * @author yk
	 *topbar点击事件
	 */
	public interface ITopBarClick
	{
		public void onClickLeftBut();
		public void onClickRightBut();
	}

}


