package com.sunnyit.law.action;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**   
* @Title: DailyCheckList.java 
* @Package com.sunnyit.hiddencheck.action 
* @Description: TODO
* @author yk
* @date 2015年9月7日 下午3:20:13 
* @version V1.0   
*/
public class StandChoiceActivity extends BaseActivity {
	
	private Button but_govern_stand,but_conpany_stand;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.law_stand_choice);
		
		initComponent();
		initButClick();
	}

	private void initComponent() {
		// TODO Auto-generated method stub
		but_govern_stand=(Button) findViewById(R.id.but_govern_stand);
		but_conpany_stand=(Button) findViewById(R.id.but_conpany_stand);
	}
	
	public void initButClick() {
		// TODO Auto-generated method stub
		but_govern_stand.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(StandChoiceActivity.this, StandListActivity.class);
				intent.putExtra("stand", "govern");
		   		startActivity(intent);
			}
		});
		
		but_conpany_stand.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(StandChoiceActivity.this, StandListActivity.class);
				intent.putExtra("stand", "company");
		   		startActivity(intent);
			}
		});
	}
	
}


