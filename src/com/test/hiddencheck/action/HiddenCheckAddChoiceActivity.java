package com.sunnyit.hiddencheck.action;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.activity.ImgShowActivity;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.law.action.StandChoiceActivity;
import com.sunnyit.law.action.StandListActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

/**   
* @Title: HiddenList.java 
* @Package com.sunnyit.hiddencheck.action 
* @Description: TODO
* @author yk
* @date 2015年9月7日 下午3:27:12 
* @version V1.0   
*/
public class HiddenCheckAddChoiceActivity extends BaseActivity {

	private TopBarView  topbar_hidden_add_choice;
	private Button but_hidden_self,but_hidden_stand;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hidden_addcheck_choice);
		
		initComponent();
		initButClick();
		
		topbar_hidden_add_choice.setTopBarClick(new ITopBarClick() {
			
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
		topbar_hidden_add_choice.setRightButVisibility(View.GONE);
	}

	private void initComponent() {
		// TODO Auto-generated method stub
		but_hidden_self=(Button) findViewById(R.id.but_hidden_self);
		but_hidden_stand=(Button) findViewById(R.id.but_hidden_stand);
		topbar_hidden_add_choice=(TopBarView ) findViewById(R.id.topbar_hidden_add_choice);
	}
	
	public void initButClick() {
		// TODO Auto-generated method stub
		but_hidden_self.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(HiddenCheckAddChoiceActivity.this, HiddenSelfCheckAddActivity.class);
				intent.putExtra("stand", "govern");
		   		startActivity(intent);
			}
		});
		
		but_hidden_stand.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(HiddenCheckAddChoiceActivity.this, HiddenStandCheckCycleActivity.class);
				intent.putExtra("stand", "company");
		   		startActivity(intent);
			}
		});
	}
	

}


