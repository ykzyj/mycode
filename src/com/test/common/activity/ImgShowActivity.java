package com.sunnyit.common.activity;

import java.util.ArrayList;
import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.company.data.EnterpriseAdapter;
import com.sunnyit.company.model.SimpleEnterprise;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import test.data.MyAdapter;
import test.model.test_info;

/**   
* @Title: WelcomeActivity.java 
* @Package com.sunnyit.system.action 
* @Description: TODO
* @author yk
* @date 2015年8月1日 上午9:47:36 
* @version V1.0   
*/
public class ImgShowActivity extends BaseActivity  {
	
	private TopBarView topbar_common_img_show;
	
	private int img;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_img_show);
		
		Intent intent=getIntent();
		img=intent.getIntExtra("industry", R.drawable.an72);
		
		initView();
		
		topbar_common_img_show.setTopBarClick(new TopBarView.ITopBarClick() {
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
	}

	private void initView() {
		topbar_common_img_show = (TopBarView)findViewById(R.id.topbar_common_img_show);
	}
	
}


