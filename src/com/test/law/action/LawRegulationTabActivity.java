package com.sunnyit.law.action;

import com.sunnyit.R;
import com.sunnyit.common.activity.ActivityCollector;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.hiddencheck.action.HiddenTabListDatilActivity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

/**   
* @Title: HiddenList.java 
* @Package com.sunnyit.hiddencheck.action 
* @Description: TODO
* @author yk
* @date 2015年9月7日 下午3:27:12 
* @version V1.0   
*/
public class LawRegulationTabActivity extends TabActivity {

	
	private TopBarView topbar_lawr_tab;
	private TabHost tabHost;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.law_tab_choice);
		ActivityCollector.addActivity(this);
		
		initComponent();
		
		topbar_lawr_tab.setTitle("法律与标准");
		topbar_lawr_tab.setTopBarClick(new ITopBarClick() {
			
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
		
		topbar_lawr_tab.setRightButVisibility(View.GONE);
		
        tabHost=this.getTabHost();
        TabHost.TabSpec spec;
        Intent intent;
        
        intent=new Intent().setClass(this, LawChoiceActivity.class);
        spec=tabHost.newTabSpec("法律法规").setIndicator("法律法规").setContent(intent);
        tabHost.addTab(spec);

        intent=new Intent().setClass(this, StandChoiceActivity.class);
        spec=tabHost.newTabSpec("检查标准").setIndicator("检查标准").setContent(intent);
        tabHost.addTab(spec);
        
        tabHost.setCurrentTab(0);
        
        RadioGroup radioGroup=(RadioGroup) this.findViewById(R.id.lr_tab_group);
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() 
        {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) 
			{
				switch (checkedId) 
				{
					case R.id.rt_lr_law:
						tabHost.setCurrentTabByTag("法律法规");
						break;
					case R.id.rt_lr_regular:
						tabHost.setCurrentTabByTag("检查标准");
						break;
					default:
						break;
				}
			}
		});
        
	}
	
	private void initComponent() {
		// TODO Auto-generated method stub
		topbar_lawr_tab=(TopBarView) findViewById(R.id.topbar_lawr_tab);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		ActivityCollector.removeActivity(this);
	}
	

}


