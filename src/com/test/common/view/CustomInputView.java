package com.sunnyit.common.view;

import java.io.Serializable;

import com.sunnyit.R;
import com.sunnyit.common.convert.PpConvert;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
public class CustomInputView extends RelativeLayout implements Serializable {
	
	private static int tvid=100;
	
	/**
	 * TextView
	 */
	private TextView mTextView;
	/**
	 * ImageButton
	 */
	private LinearLayout mLinearLayout;
	private ImageView mFirstImageView;
	private ImageView mSecondImageView;
	/**
	 * mEditText
	 */
	private EditText mEditText;
	
	
	/**
	 * 三元素布局
	 */
	private LayoutParams tvParams;
	private LayoutParams layoutParams;
	private LinearLayout.LayoutParams firstIvParams;
	private LinearLayout.LayoutParams secondIvParams;
	private LayoutParams editParams;
	
	private String input_title;
	private Drawable input_fimg_src;
	private Drawable input_simg_src;
	private String input_hint;
	
	private ButtonOnClickListener mButtonOnClickListener;
	
	public CustomInputView(Context context) {
		this(context,null);
	}

	public CustomInputView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		getAttrs(context, attrs);
		
		initComponent(context);
		
		initButClick(context);
		
	}

	/**
	* @author yk 
	* @date 2015年11月27日 上午11:44:06 
	* @Title: initButClick 
	* @Description: button点击事件初始化
	* @param context    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private void initButClick(Context context) {
		// TODO Auto-generated method stub
		mFirstImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mButtonOnClickListener!=null)
				{
					mButtonOnClickListener.onFirstButtonOnClick();
				}
			}
		});
		
		mSecondImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mButtonOnClickListener!=null)
				{
					mButtonOnClickListener.onSecondButtonOnClick();
				}
			}
		});
	}

	/**
	* @author yk 
	* @date 2015年11月27日 上午11:28:12 
	* @Title: initComponent 
	* @Description: 控件初始化
	* @param context    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private void initComponent(Context context) {
		
		mTextView=new TextView(context);
		mLinearLayout=new LinearLayout(context);
		mFirstImageView=new ImageView(context);
		mSecondImageView=new ImageView(context);
		mEditText=new EditText(context);
		
		mTextView.setText(input_title);
		mTextView.setTextSize(PpConvert.sp2px(context, 10));
		mTextView.setTextColor(0xff2197db);
		mTextView.setId(tvid);
		tvid++;
		mTextView.setPadding(PpConvert.dp2px(context, 10), 0, 0, 0);
		tvParams=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		tvParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
		tvParams.setMargins(0, PpConvert.dp2px(context, 5), 0, 0); 
		addView(mTextView, tvParams);
		
		mLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
		layoutParams=new LayoutParams(PpConvert.dp2px(context, 50), LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
		layoutParams.addRule(RelativeLayout.ALIGN_TOP, mTextView.getId());
		layoutParams.setMargins(0, 0, PpConvert.dp2px(context, 5), 0); 
		addView(mLinearLayout, layoutParams);
		
		mFirstImageView.setBackground(input_fimg_src);
		firstIvParams=new LinearLayout.LayoutParams(PpConvert.dp2px(context, 22), PpConvert.dp2px(context, 22));
		firstIvParams.setMargins(0, 0, PpConvert.dp2px(context, 5), 0);
		mLinearLayout.addView(mFirstImageView, firstIvParams);
		
		mSecondImageView.setBackground(input_simg_src);
		secondIvParams=new LinearLayout.LayoutParams(PpConvert.dp2px(context, 22), PpConvert.dp2px(context, 22));
		mLinearLayout.addView(mSecondImageView, secondIvParams);
		
		mEditText.setHint(input_hint);
		mEditText.setGravity(Gravity.TOP);
		mEditText.setGravity(Gravity.LEFT);
		mEditText.setMinLines(4);
		mEditText.setTextSize(PpConvert.sp2px(context, 9));
		editParams=new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		editParams.addRule(RelativeLayout.BELOW, mTextView.getId());
		addView(mEditText, editParams);
		
		setBackgroundResource(R.drawable.login_input);
	}

	/**
	* @author yk 
	* @date 2015年11月27日 上午11:28:31 
	* @Title: getAttrs 
	* @Description: 属性获取
	* @param context
	* @param attrs    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private void getAttrs(Context context, AttributeSet attrs) {
		TypedArray ta=context.obtainStyledAttributes(attrs, R.styleable.inputView);
		input_title=ta.getString(R.styleable.inputView_input_title);
		input_fimg_src=ta.getDrawable(R.styleable.inputView_input_fimg_src);
		input_simg_src=ta.getDrawable(R.styleable.inputView_input_simg_src);
		input_hint=ta.getString(R.styleable.inputView_input_hint);
		ta.recycle();
	}
	
	/**
	* @author yk 
	* @date 2015年11月27日 上午11:14:20 
	* @Title: setInputTitle 
	* @Description: 设置标题
	* @param title    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void setInputTitle(String title) {
		mTextView.setText(title);
	}
	
	/**
	* @author yk 
	* @date 2015年11月27日 上午11:14:59 
	* @Title: setFirstButBackBrougd 
	* @Description: 设置第一个按钮的背景图片
	* @param dwId    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void setFirstButBackBrougd(int dwId)
	{
		mFirstImageView.setBackgroundResource(dwId);
	}
	
	/**
	* @author yk 
	* @date 2015年11月27日 上午11:15:15 
	* @Title: setSecondButBackBrougd 
	* @Description: 设置第二个按钮的背景图片
	* @param dwId    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void setSecondButBackBrougd(int dwId)
	{
		mSecondImageView.setBackgroundResource(dwId);
	}
	
	/**
	* @author yk 
	* @date 2015年11月27日 上午11:22:37 
	* @Title: setHintText 
	* @Description: 设置inputview的HintTitle
	* @param hintStr    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void setHintText(String hintStr) {
		mEditText.setHint(hintStr);
	}
	
	/**
	* @author yk 
	* @date 2015年11月27日 下午2:25:47 
	* @Title: getInputViewText 
	* @Description: 获取edit内容
	* @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public String getInputViewText()
	{
		return mEditText.getText().toString();
	}
	
	/**
	* @author yk 
	* @date 2015年11月27日 下午4:34:54 
	* @Title: setInputViewText 
	* @Description: 设置InputViewText 
	* @param text    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void setInputViewText(String text)
	{
		mEditText.setText(text);
	}
	
	public void setInputViewTextSize(float f)
	{
		mEditText.setTextSize(f);
	}
	
	/**
	* @author yk 
	* @date 2015年11月28日 上午11:25:07 
	* @Title: setFirstButtonVisible 
	* @Description: firstbutton可见性
	* @param visible    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void setFirstButtonVisible(int visible)
	{
		mFirstImageView.setVisibility(visible);
	}
	
	/**
	* @author yk 
	* @date 2015年11月28日 上午11:28:25 
	* @Title: setSecondButtonVisible 
	* @Description: secondbutton可见性
	* @param visible    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void setSecondButtonVisible(int visible)
	{
		mSecondImageView.setVisibility(visible);
	}
	
	/**
	* @author yk 
	* @date 2015年11月28日 上午11:30:01 
	* @Title: setInputEditEnable 
	* @Description: edit输入可用性
	* @param enable    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void setInputEditEnable(boolean enable)
	{
		mEditText.setEnabled(enable);
	}
	
	/**
	* @author yk 
	* @date 2015年11月27日 上午11:43:48 
	* @Title: setButtonOnClickListener 
	* @Description: 回调设置
	* @param buttonOnClickListener    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void setButtonOnClickListener(ButtonOnClickListener buttonOnClickListener) { 
		this.mButtonOnClickListener=buttonOnClickListener;
	}
	
	/*
	 * button回调接口
	 */
	public interface ButtonOnClickListener
	{
		/*
		 * 第一个按钮点击事件
		 */
		public void onFirstButtonOnClick();
		/*
		 * 第二个按钮点击事件
		 */
		public void onSecondButtonOnClick();
	}
}


