package com.sunnyit.enforcement.action;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**   
* @Title: HiddenList.java 
* @Package com.sunnyit.hiddencheck.action 
* @Description: TODO
* @author yk
* @date 2015年9月7日 下午3:27:12 
* @version V1.0   
*/
public class HiddenGovernAddChoiceActivity extends BaseActivity {
	
	private TopBarView topbar_encheck_add;
	
	private Button but_daily_check;
	private Button but_stand_check;
	private Button but_special_check;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.en_checkadd_choice);
		
		initComponent();
		
		initButClick();
		
		topbar_encheck_add.setTopBarClick(new ITopBarClick() {
			
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

	private void initButClick() {
		// TODO Auto-generated method stub
		but_daily_check.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(HiddenGovernAddChoiceActivity.this,HiddenDailyCheckAddActivity.class);
				startActivity(intent);
			}
		});
		but_stand_check.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(HiddenGovernAddChoiceActivity.this,StandCheckTableActivity.class);
				startActivity(intent);
			}
		});
		but_special_check.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(HiddenGovernAddChoiceActivity.this,HiddenSpecialCheckAddActivity.class);
				startActivity(intent);
			}
		});
	}

	private void initComponent() {
		// TODO Auto-generated method stub
		topbar_encheck_add=(TopBarView) findViewById(R.id.topbar_encheck_add);
		
		but_daily_check=(Button) findViewById(R.id.but_daily_check);
		but_stand_check=(Button) findViewById(R.id.but_stand_check);
		but_special_check=(Button) findViewById(R.id.but_special_check);
		
	}
	
	
}





