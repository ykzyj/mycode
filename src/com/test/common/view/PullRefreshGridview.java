package com.sunnyit.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.sunnyit.R;

public class PullRefreshGridview extends GridView {

	LinearLayout mLinearLayout;
	
	public PullRefreshGridview(Context context) {
		this(context,null);
		// TODO Auto-generated constructor stub
	}
	
	public PullRefreshGridview(Context context, AttributeSet attrs) {
		this(context, attrs,0);
		// TODO Auto-generated constructor stub
	}
	
	public PullRefreshGridview(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		
		initView(context);
	}

	private void initView(Context context) {
		// TODO Auto-generated method stub
		LayoutInflater mLayoutInflater=LayoutInflater.from(context);
		View mView=mLayoutInflater.inflate(R.layout.common_search, null);
		mLinearLayout=(LinearLayout) mView.findViewById(R.id.lin_search);
		
	}

}
