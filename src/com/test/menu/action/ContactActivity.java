package com.sunnyit.menu.action;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.view.TopBarView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ContactActivity extends BaseActivity 
{
	private TopBarView topbar_pwup;
	
	RelativeLayout relative_company_phone_one,relative_company_phone_two;
	RelativeLayout relative_company_url,relative_company_name;
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leftmenu_contact); 
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
        
        //电话一
        relative_company_phone_one.setOnClickListener(new View.OnClickListener() 
        {
        	@Override
        	public void onClick(View view) 
        	{
        		Intent intent = new Intent();
    		    intent.setAction("android.intent.action.DIAL");
    		    intent.setData(Uri.parse("tel:02988330557"));
    		    startActivity(intent);
        	}
    	});
        
        //电话二
        relative_company_phone_two.setOnClickListener(new View.OnClickListener() 
        {
        	@Override
        	public void onClick(View view) 
        	{
        		Intent intent = new Intent();
    		    intent.setAction("android.intent.action.DIAL");
    		    intent.setData(Uri.parse("tel:02988330605"));
    		    startActivity(intent);
        	}
    	});
        
        //网站
        relative_company_url.setOnClickListener(new View.OnClickListener() 
        {
        	@Override
        	public void onClick(View view) 
        	{
        		Intent intent = new Intent();        
                intent.setAction("android.intent.action.VIEW");    
                Uri content_url = Uri.parse("http://www.sunnyit.com");   
                intent.setData(content_url);  
                startActivity(intent);
        	}
    	});
        
        //简介
        relative_company_name.setOnClickListener(new View.OnClickListener() 
        {
        	@Override
        	public void onClick(View view) 
        	{
        		Intent intent = new Intent(ContactActivity.this,IntroductionActivity.class);        
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
		
		relative_company_name=(RelativeLayout)findViewById(R.id.relative_company_name);
		relative_company_phone_one=(RelativeLayout)findViewById(R.id.relative_company_phone_one);
		relative_company_phone_two=(RelativeLayout)findViewById(R.id.relative_company_phone_two);
		relative_company_url=(RelativeLayout)findViewById(R.id.relative_company_url);
    }
	
}