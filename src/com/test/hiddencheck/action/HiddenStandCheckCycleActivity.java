package com.sunnyit.hiddencheck.action;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.activity.ImgShowActivity;
import com.sunnyit.common.view.ChoiceEditView;
import com.sunnyit.common.view.ChoiceEditView.onChoiceEditItemListener;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**   
* @Title: HiddenList.java 
* @Package com.sunnyit.hiddencheck.action 
* @Description: TODO
* @author yk
* @date 2015年9月7日 下午3:27:12 
* @version V1.0   
*/
public class HiddenStandCheckCycleActivity extends BaseActivity {
	
	private TopBarView topbar_standcheck_cycle;
	
	private RadioGroup ra_sc_circle;
	private RadioButton rb_all_year,rb_every_year,rb_every_halfyear;
	private RadioButton rb_every_quarter,rb_every_monthly,rb_every_week;
	private RadioButton rb_other;
	
	private EditText sc_idd,sc_checkTime;
	private EditText sc_checkPerson,sc_checkDept;
	private ChoiceEditView sc_hf_name;
	
	private String find_type;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hidden_standcheck_cycle);
		
		initComponent();
		
		topbar_standcheck_cycle.setTopBarClick(new ITopBarClick() {
			
			@Override
			public void onClickRightBut() {
				// TODO Auto-generated method stub
				Intent intent=new Intent(HiddenStandCheckCycleActivity.this, HiddenStandCheckaddActivity.class);
	       		startActivity(intent);
			}
			
			@Override
			public void onClickLeftBut() {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		sc_hf_name.setDialogTitle("标准选择");
		sc_hf_name.setChoiceEditItem(new onChoiceEditItemListener() {
			
			@Override
			public String[] onSetChoiceEditItem() {
				// TODO Auto-generated method stub
				String items[]={"标准一","标准二","标准三"};  
				return items;
			}
		});
		
	}

	private void initComponent() {
		// TODO Auto-generated method stub
		topbar_standcheck_cycle=(TopBarView) findViewById(R.id.topbar_standcheck_cycle);
		
		ra_sc_circle=(RadioGroup) findViewById(R.id.ra_sc_circle);
		rb_all_year=(RadioButton) findViewById(R.id.rb_all_year);
		rb_every_year=(RadioButton) findViewById(R.id.rb_every_year);
		rb_every_halfyear=(RadioButton) findViewById(R.id.rb_every_halfyear);
		rb_every_quarter=(RadioButton) findViewById(R.id.rb_every_quarter);
		rb_every_monthly=(RadioButton) findViewById(R.id.rb_every_monthly);
		rb_every_week=(RadioButton) findViewById(R.id.rb_every_week);
		rb_other=(RadioButton) findViewById(R.id.rb_other);
		
		sc_hf_name=(ChoiceEditView) findViewById(R.id.sc_hf_name);
		
		rb_all_year.setTextColor(Color.parseColor("#28aae1"));
		ra_sc_circle.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				 	int radioButtonId = group.getCheckedRadioButtonId();
	                RadioButton rb = (RadioButton)HiddenStandCheckCycleActivity.this.findViewById(radioButtonId);
	                find_type=rb.getText().toString();
	                
	                rb_all_year.setTextColor(Color.parseColor("#FF7D899D"));
	                rb_every_year.setTextColor(Color.parseColor("#FF7D899D"));
	                rb_every_halfyear.setTextColor(Color.parseColor("#FF7D899D"));
	                rb_every_quarter.setTextColor(Color.parseColor("#FF7D899D"));
	                rb_every_monthly.setTextColor(Color.parseColor("#FF7D899D"));
	                rb_every_week.setTextColor(Color.parseColor("#FF7D899D"));
	                rb_other.setTextColor(Color.parseColor("#FF7D899D"));
	                
	                rb.setTextColor(Color.parseColor("#28aae1")); 
			}
		});
	}
	
}


