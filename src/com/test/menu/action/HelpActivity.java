package com.sunnyit.menu.action;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.view.TopBarView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HelpActivity extends BaseActivity 
{
	private TopBarView topbar_pwup ;
	
	RelativeLayout relative_new_user,relative_question;
	RelativeLayout relative_user_return,relative_technical_support;
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leftmenu_syshelp); 
		
		initComponent();
		
		topbar_pwup.setTopBarClick(new TopBarView.ITopBarClick() {
			@Override
			public void onClickRightBut() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onClickLeftBut() {
				// TODO Auto-generated method stub
				finish();
			}
		});
        
        relative_new_user.setOnClickListener(new View.OnClickListener() 
        {
        	@Override
        	public void onClick(View view) 
        	{
        	}
    	});
        
        relative_question.setOnClickListener(new View.OnClickListener() 
        {
        	@Override
        	public void onClick(View view) 
        	{
        	}
    	});
        
        relative_user_return.setOnClickListener(new View.OnClickListener() 
        {
        	@Override
        	public void onClick(View view) 
        	{
        		Intent intent=new Intent(HelpActivity.this, FeedbackActivity.class);
        		startActivity(intent);
        	}
    	});
        
        relative_technical_support.setOnClickListener(new View.OnClickListener() 
        {
        	@Override
        	public void onClick(View view) 
        	{
        		Intent intent=new Intent(HelpActivity.this, ContactActivity.class);
        		startActivity(intent);
        	}
    	});
	}
	
    /**
	 * 初始化用户组件
	 */
	private void initComponent()
	{
		topbar_pwup = (TopBarView)findViewById(R.id.topbar_pwup);
		
		relative_new_user=(RelativeLayout)findViewById(R.id.relative_new_user);
		relative_question=(RelativeLayout)findViewById(R.id.relative_question);
		relative_user_return=(RelativeLayout)findViewById(R.id.relative_user_return);
		relative_technical_support=(RelativeLayout)findViewById(R.id.relative_technical_support);
    }
	
}