package com.sunnyit.enforcement.action;

import java.util.ArrayList;
import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.activity.ActivityCollector;
import com.sunnyit.common.view.PageViewTab;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.Window;

/**   
* @Title: HiddenList.java 
* @Package com.sunnyit.hiddencheck.action 
* @Description: TODO
* @author yk
* @date 2015年9月7日 下午3:27:12 
* @version V1.0   
*/
public class HiddenGovernChoiceActivity extends FragmentActivity {
	
	private ViewPager mViewPager;
	private TopBarView topbar_hidden_check;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.hidden_check);
		ActivityCollector.addActivity(this);
		
		initComponent();
		
		YearMounthCheck yearMounthCheck=new YearMounthCheck(this);
		ReviewCheckChoice reviewCheckChoice=new ReviewCheckChoice(this);
		/*StandardGovernCheckList standardCheckList=new StandardGovernCheckList(this);
		SpecialCheckList specialCheckList=new SpecialCheckList(this);*/
		List<Fragment> datas=new ArrayList<Fragment>();
		datas.add(yearMounthCheck);
		datas.add(reviewCheckChoice);
		
		FragmentManager mFragmentManager= getSupportFragmentManager();
		new PageViewTab(this, mFragmentManager, 
				mViewPager, datas, 
				new String[]{"安全检查","隐患复查"});
		
		topbar_hidden_check.setTopBarClick(new ITopBarClick() {
			
			@Override
			public void onClickRightBut() {
				// TODO Auto-generated method stub
				Intent intent=new Intent(HiddenGovernChoiceActivity.this, HiddenGovernAddChoiceActivity.class);
	       		startActivity(intent);
			}
			
			@Override
			public void onClickLeftBut() {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
	}

	private void initComponent() {
		// TODO Auto-generated method stub
		mViewPager=(ViewPager) findViewById(R.id.viewpager_hidden_check);
		topbar_hidden_check=(TopBarView) findViewById(R.id.topbar_hidden_check);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		ActivityCollector.removeActivity(this);
	}
	
}





