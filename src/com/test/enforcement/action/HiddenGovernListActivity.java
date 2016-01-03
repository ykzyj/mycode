package com.sunnyit.enforcement.action;

import java.util.ArrayList;
import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.activity.ActivityCollector;
import com.sunnyit.common.view.CustomFAB;
import com.sunnyit.common.view.PageViewTab;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;

/**   
* @Title: HiddenList.java 
* @Package com.sunnyit.hiddencheck.action 
* @Description: TODO
* @author yk
* @date 2015年9月7日 下午3:27:12 
* @version V1.0   
*/
public class HiddenGovernListActivity extends FragmentActivity {
	
	private ViewPager mViewPager;
	private TopBarView topbar_hidden_check;
	private CustomFAB company_hidden_search;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.hidden_check);
		ActivityCollector.addActivity(this);
		
		initComponent();
		
		String companyName=getIntent().getStringExtra("companyName");
		
		DailyCheckList dailyCheckList=new DailyCheckList(this,companyName);
		StandardCheckList standardCheckList=new StandardCheckList(this,companyName);
		SpecialCheckList specialCheckList=new SpecialCheckList(this,companyName);
		List<Fragment> datas=new ArrayList<Fragment>();
		datas.add(dailyCheckList);
		datas.add(standardCheckList);
		datas.add(specialCheckList);
		
		FragmentManager mFragmentManager= getSupportFragmentManager();
		new PageViewTab(this, mFragmentManager, 
				mViewPager, datas, 
				new String[]{"日常隐患检查","对照标准检查","专项检查"});
		
		topbar_hidden_check.setTopBarClick(new ITopBarClick() {
			
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
		topbar_hidden_check.setRightButVisibility(View.GONE);
		topbar_hidden_check.setTitle("企业执法检查信息列表");
	}

	private void initComponent() {
		// TODO Auto-generated method stub
		mViewPager=(ViewPager) findViewById(R.id.viewpager_hidden_check);
		topbar_hidden_check=(TopBarView) findViewById(R.id.topbar_hidden_check);
		company_hidden_search=(CustomFAB) findViewById(R.id.company_hidden_search);
		company_hidden_search.setVisibility(View.GONE);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		ActivityCollector.removeActivity(this);
	}
	
}


