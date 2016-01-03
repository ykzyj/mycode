package com.sunnyit.hiddencheck.action;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.activity.ImgShowActivity;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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
public class HiddenSelfCheckAddActivity extends BaseActivity {
	
	private TopBarView topbar_selfcheck_add;
	
	private ToggleButton TogBtn_push_switch;
	
	private RelativeLayout rel_sc_fixMeasure;
	private LinearLayout lin_sc_startTime;
	private LinearLayout lin_sc_deadline;
	
	private EditText sc_id,sc_checkTime,sc_checkingDepartment,sc_inspector;
	private EditText sc_checkedDepartment,sc_checkedJob,sc_hiddenCondition;
	private EditText sc_fixMeasure,sc_startTime,sc_deadline;
	private EditText sc_completeTableTime,sc_completeTablePeople;
	
	private ImageButton img_sc_hiddenCondition,img_sc_fixMeasure;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hidden_seftcheck_add);
		
		initComponent();
		
		topbar_selfcheck_add.setTopBarClick(new ITopBarClick() {
			
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
		
		img_sc_hiddenCondition.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(HiddenSelfCheckAddActivity.this, ImgShowActivity.class);
	       		startActivity(intent);
			}
		});
		
		img_sc_fixMeasure.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(HiddenSelfCheckAddActivity.this, ImgShowActivity.class);
	       		startActivity(intent);
			}
		});
		
	}

	private void initComponent() {
		// TODO Auto-generated method stub
		topbar_selfcheck_add=(TopBarView) findViewById(R.id.topbar_selfcheck_add);
		TogBtn_push_switch=(ToggleButton) findViewById(R.id.TogBtn_push_switch);
		
		rel_sc_fixMeasure=(RelativeLayout) findViewById(R.id.rel_sc_fixMeasure);
		lin_sc_startTime=(LinearLayout) findViewById(R.id.lin_sc_startTime);
		lin_sc_deadline=(LinearLayout) findViewById(R.id.lin_sc_deadline);
		
		sc_id=(EditText) findViewById(R.id.sc_id);
		sc_checkTime=(EditText) findViewById(R.id.sc_checkTime);
		sc_checkingDepartment=(EditText) findViewById(R.id.sc_checkingDepartment);
		sc_inspector=(EditText) findViewById(R.id.sc_inspector);
		sc_checkedDepartment=(EditText) findViewById(R.id.sc_checkedDepartment);
		sc_checkedJob=(EditText) findViewById(R.id.sc_checkedJob);
		sc_hiddenCondition=(EditText) findViewById(R.id.sc_hiddenCondition);
		sc_fixMeasure=(EditText) findViewById(R.id.sc_fixMeasure);
		sc_startTime=(EditText) findViewById(R.id.sc_startTime);
		sc_deadline=(EditText) findViewById(R.id.sc_deadline);
		sc_completeTableTime=(EditText) findViewById(R.id.sc_completeTableTime);
		sc_completeTablePeople=(EditText) findViewById(R.id.sc_completeTablePeople);
		
		img_sc_hiddenCondition=(ImageButton) findViewById(R.id.img_sc_hiddenCondition);
		img_sc_fixMeasure=(ImageButton) findViewById(R.id.img_sc_fixMeasure);
		
		TogBtn_push_switch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked)
				{
					rel_sc_fixMeasure.setVisibility(View.VISIBLE);
					lin_sc_startTime.setVisibility(View.VISIBLE);
					lin_sc_deadline.setVisibility(View.VISIBLE);
				}
				else
				{
					rel_sc_fixMeasure.setVisibility(View.GONE);
					lin_sc_startTime.setVisibility(View.GONE);
					lin_sc_deadline.setVisibility(View.GONE);
				}
			}
		});
		
	}
	
}


