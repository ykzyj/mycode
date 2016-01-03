package com.sunnyit.common.view;

import java.util.ArrayList;
import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.convert.PpConvert;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**   
* @Title: TopBar.java 
* @Package com.sunnyit.common.view 
* @Description: TODO
* @author yk
* @date 2015年8月20日 下午3:38:20 
* @version V1.0   
*/
public class TabDialogView extends LinearLayout {
	
	/*
	 * 标题栏
	 */
	private LinearLayout mTitleLinearLayout;
	private List<TextView> ls_tx_title;
	
	/*
	 * 分割线
	 */
	private LinearLayout mDividerLinearLayoutOne;
	private LinearLayout mDividerLinearLayoutTwo;
	
	/*
	 * 内容区域
	 */
	private ScrollView mContentScrollView;
	private LinearLayout mContentLinearLayout;
	private List<TextView> ls_tx_content;
	
	/*
	 * 按钮
	 */
	private LinearLayout mButLinearLayout;
	
	private Context mContext;
	
	private int tvCount=0;
	private int butCount=0;
	
	
	public TabDialogView(Context context) {
		this(context,null);
	}

	/**
	* @author yk
	* @date 2015年11月25日 上午10:04:38 
	* @param context
	* @param attrs    设定文件
	 */
	public TabDialogView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		this.mContext=context;
		
		ls_tx_title=new ArrayList<TextView>();
		ls_tx_content=new ArrayList<TextView>();
		
		/*
		 * 主区域
		 */
		setOrientation(LinearLayout.VERTICAL);
		setMinimumWidth(PpConvert.dp2px(context, 300));
		
		/*
		 * title区域
		 */
		mTitleLinearLayout=new LinearLayout(context);
		LinearLayout.LayoutParams title_lin_lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, PpConvert.convertDpToPx(context, 50)); 
		mTitleLinearLayout.setLayoutParams(title_lin_lp);   
		mTitleLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
		this.addView(mTitleLinearLayout);
		
		
		/*
		 * 分割线
		 */
		mDividerLinearLayoutOne=new LinearLayout(context);
		LinearLayout.LayoutParams dividerone_lin_lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, PpConvert.dp2px(context, 1));
		mDividerLinearLayoutOne.setBackgroundColor(0xffbababa);
		mDividerLinearLayoutOne.setOrientation(LinearLayout.HORIZONTAL);
		this.addView(mDividerLinearLayoutOne, dividerone_lin_lp);
		
		/*
		 * 内容区域
		 */
		mContentScrollView=new ScrollView(context);
		LinearLayout.LayoutParams scroll_lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);
		this.addView(mContentScrollView, scroll_lp);
		
		mContentLinearLayout=new LinearLayout(context);
		LinearLayout.LayoutParams content_lin_lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT); 
		mContentLinearLayout.setLayoutParams(content_lin_lp);   
		mContentLinearLayout.setOrientation(LinearLayout.VERTICAL);
		mContentLinearLayout.setBackgroundResource(R.drawable.white14);
		mContentScrollView.addView(mContentLinearLayout);
		
		/*
		 * 分割线
		 */
		mDividerLinearLayoutTwo=new LinearLayout(context);
		LinearLayout.LayoutParams dividertwo_lin_lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, PpConvert.dp2px(context, 1));
		mDividerLinearLayoutTwo.setBackgroundColor(0xffbababa);
		mDividerLinearLayoutTwo.setOrientation(LinearLayout.HORIZONTAL);
		this.addView(mDividerLinearLayoutTwo, dividertwo_lin_lp);
		
		/*
		 * but区域
		 */
		mButLinearLayout=new LinearLayout(context);
		LinearLayout.LayoutParams but_lin_lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, PpConvert.convertDpToPx(context, 50)); 
		but_lin_lp.setMargins(0, 0, 0, 70); 
		mButLinearLayout.setLayoutParams(but_lin_lp);   
		mButLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
		mButLinearLayout.setGravity(Gravity.CENTER);
		addView(mButLinearLayout);
	}
	
	public void addContent(String title,String content) {
		
		if(content!=null)
		{
			final TextView mTitleTv=new TextView(mContext);
			TextView mContentTv=new TextView(mContext);
			
			mTitleTv.setText(title);
			mTitleTv.setTag(tvCount);
			mTitleTv.setTextSize(PpConvert.sp2px(mContext, 10));
			mTitleTv.setGravity(Gravity.CENTER);
			LinearLayout.LayoutParams title_lp=new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
			if(ls_tx_title.size()==0)
			{
				mTitleTv.setTextColor(0xffffffff);
				mTitleTv.setBackgroundResource(R.drawable.bg_blue14);
			}
			else
			{
				mTitleTv.setTextColor(0xff6c6c6c);
				mTitleTv.setBackgroundResource(R.drawable.white14);
				
				LinearLayout mTitleDivider=new LinearLayout(mContext);
				LinearLayout.LayoutParams title_lin_lp=new LinearLayout.LayoutParams(PpConvert.dp2px(mContext, 1),ViewGroup.LayoutParams.MATCH_PARENT);
				mTitleDivider.setBackgroundColor(0xffbababa);
				mTitleDivider.setOrientation(LinearLayout.VERTICAL);
				mTitleLinearLayout.addView(mTitleDivider, title_lin_lp);
			}
			
			mTitleTv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					for(int i=0;i<ls_tx_title.size();i++)
					{
						if(ls_tx_title.get(i)==mTitleTv)
						{
							ls_tx_title.get(i).setBackgroundResource(R.drawable.bg_blue14);
							ls_tx_title.get(i).setTextColor(0xffffffff);
							ls_tx_content.get(i).setVisibility(View.VISIBLE);
						}
						else
						{
							ls_tx_title.get(i).setBackgroundResource(R.drawable.white14);
							ls_tx_title.get(i).setTextColor(0xff6c6c6c);
							ls_tx_content.get(i).setVisibility(View.GONE);
						}
					}
				}
			});
			
			mTitleLinearLayout.addView(mTitleTv, title_lp);
			ls_tx_title.add(mTitleTv);
			
			
			mContentTv.setText(content);
			mContentTv.setTextColor(0xff6c6c6c);
			mContentTv.setTextSize(PpConvert.sp2px(mContext, 9));
			LinearLayout.LayoutParams content_lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
			if(ls_tx_content.size()==0)
			{
				mContentTv.setVisibility(View.VISIBLE);
			}
			else
			{
				mContentTv.setVisibility(View.GONE);
			}
			
			mContentLinearLayout.addView(mContentTv, content_lp);
			ls_tx_content.add(mContentTv);
			
			tvCount++;
		}
	}
	
	
	public Button addBut(String butText) {
		Button but=new Button(mContext);
		but.setText(butText);
		but.setTextColor(0xff6c6c6c);
		but.setTextSize(PpConvert.sp2px(mContext, 9));
		but.setBackgroundResource(R.drawable.rel_tree_bg);
		LinearLayout.LayoutParams but_lp=new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
		
		if(butCount!=0)
		{
			LinearLayout mButDivider=new LinearLayout(mContext);
			LinearLayout.LayoutParams but_lin_lp=new LinearLayout.LayoutParams(PpConvert.dp2px(mContext, 1),ViewGroup.LayoutParams.MATCH_PARENT);
			mButDivider.setBackgroundColor(0xffbababa);
			mButDivider.setOrientation(LinearLayout.VERTICAL);
			mButLinearLayout.addView(mButDivider, but_lin_lp);
		}
		
		mButLinearLayout.addView(but, but_lp);
		
		butCount++;
		return but;
	}
	

}


