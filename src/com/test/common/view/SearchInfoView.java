package com.sunnyit.common.view;

import com.sunnyit.R;
import com.sunnyit.common.convert.PpConvert;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import jxl.format.Orientation;
import android.widget.RelativeLayout.LayoutParams;

/**   
* @Title: TopBar.java 
* @Package com.sunnyit.common.view 
* @Description: TODO
* @author yk
* @date 2015��8��20�� ����3:38:20 
* @version V1.0   
*/
public class SearchInfoView extends RelativeLayout {
	
	/**
	 * left_secrch��
	 */
	private ImageView mImageView;
	/**
	 * middle_search��
	 */
	private EditText mSearchEdit;
	private LinearLayout mLinearLayout;
	/**
	 * right_search��
	 */
	private Button mSearchBut;
	
	/**
	 * Ԫ�ز���
	 */
	private LayoutParams mSearchLeft;
	private LayoutParams mSearchMiddle;
	private LayoutParams mSearchRight;
	
	private String mHintText="���������Ϣ";
	private String mButText="����";
	private int mButTextColor=Color.WHITE;
	
	private SearchButListener mSearchButListener;
	private SearchTextListener mSearchTextListener;
	
	/**
	* @author yk
	* @date 2015��11��12�� ����11:15:02 
	* @param context
	* @param attrs    �趨�ļ�
	 */
	public SearchInfoView(final Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		TypedArray ta=context.obtainStyledAttributes(attrs, R.styleable.searchView);
		mHintText=ta.getString(R.styleable.searchView_search_hint);
		mButText=ta.getString(R.styleable.searchView_search_but_text);
		mButTextColor=ta.getColor(R.styleable.searchView_search_but_text_color, Color.WHITE);
		ta.recycle();
		
		initShow(context);
		
		mSearchBut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mSearchButListener!=null)
				{
					if(mSearchEdit.getText().toString().trim().equals(""))
					{
						Toast.makeText(context, "�������ݲ���Ϊ��", Toast.LENGTH_SHORT).show();
					}
					else
					{
						mSearchButListener.setOnSearchButClick(mSearchEdit.getText().toString());
					}
				}
			}
		});
		
		mSearchEdit.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				String key=mSearchEdit.getText().toString().trim();
				if(mSearchTextListener!=null)
				{
					mSearchTextListener.setOnSearchTextChanged(key);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	private void initShow(Context context) {
		mImageView=new ImageView(context);
		mSearchEdit=new EditText(context);
		mLinearLayout=new LinearLayout(context);
		mSearchBut=new Button(context);
		
		mImageView.setBackgroundResource(R.drawable.search_blue_left);
		
		mSearchEdit.setHeight(PpConvert.dp2px(context, 40));
		mSearchEdit.setTextSize(PpConvert.dp2px(context, 9));
		mSearchEdit.setBackgroundColor(Color.parseColor("#00ffffff"));
		mSearchEdit.setHint(mHintText);
		
		mLinearLayout.setBackgroundResource(R.drawable.search_blue_middle);
		mLinearLayout.setGravity(Gravity.CENTER_VERTICAL);
		mLinearLayout.setOrientation(LinearLayout.VERTICAL);
		mLinearLayout.addView(mSearchEdit);
		
		mSearchBut.setText(mButText);
		mSearchBut.setTextSize(PpConvert.dp2px(context, 10));
		mSearchBut.setTextColor(mButTextColor);
		mSearchBut.setBackgroundResource(R.drawable.search_blue_right);
		
		mSearchLeft=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
		mSearchLeft.setMargins(PpConvert.dp2px(context, 3), 0, 0, 0);
		mSearchLeft.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
		this.addView(mImageView, mSearchLeft);
		
		mSearchMiddle=new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		mSearchMiddle.setMargins(PpConvert.dp2px(context, 26), 0, PpConvert.dp2px(context, 60), 0);
		this.addView(mLinearLayout, mSearchMiddle);
		
		mSearchRight=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
		mSearchRight.setMargins(PpConvert.dp2px(context, 0), 0, 10, 0);
		mSearchRight.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
		this.addView(mSearchBut, mSearchRight);
	}
	
	public String getSearchText() {
		return mSearchEdit.getText().toString().trim();
	}
	
	public void setSearchButListener(SearchButListener searchButListener) {
		mSearchButListener=searchButListener;
	}
	
	public void setSearchTextListener(SearchTextListener searchTextListener) {
		mSearchTextListener=searchTextListener;
	}
	
	public void setSearchText(String content) {
		mSearchEdit.setText(content);
	}
	
	/*
	 * ������ť�¼�
	 */
	public interface SearchButListener{
		/*
		 * ������ť����¼�
		 */
		public void setOnSearchButClick(String content);
	}
	
	/*
	 * text�ı��¼�
	 */
	public interface SearchTextListener{
		/*
		 * edit����Ϣchanged�¼�
		 */
		public void setOnSearchTextChanged(String key);
	}
	
}


