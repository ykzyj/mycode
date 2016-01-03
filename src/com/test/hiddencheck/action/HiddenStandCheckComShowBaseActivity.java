package com.sunnyit.hiddencheck.action;

import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.company.model.SimpleEnterprise;
import com.sunnyit.enforcement.model.Standard_CK_Table;
import com.sunnyit.hiddencheck.model.SelfStandCheck;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**   
* @Title: HiddenList.java 
* @Package com.sunnyit.hiddencheck.action 
* @Description: TODO
* @author yk
* @date 2015年9月7日 下午3:27:12 
* @version V1.0   
*/
public class HiddenStandCheckComShowBaseActivity extends BaseActivity {

	
	private TopBarView topbar_hidden_standcheck_show;
	
	private EditText et_ck_companyName,et_ck_companyaddress;
	private EditText et_ck_representative,et_ck_phone,et_sc_checkTime;
	private EditText et_sc_checkDept,et_sc_checkPerson,et_sc_deadline;
	private EditText et_sc_state;
	
	private Button but_standcheck_item_show;
	private SelfStandCheck mSelfStandCheck;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hidden_standcheck_showbase);
		
		initComponent();
		
		Intent intent=getIntent();
		mSelfStandCheck=(SelfStandCheck) intent.getSerializableExtra("SelfStandCheck");
		
		String condition=" where e_companyName='"+mSelfStandCheck.getSc_companyName()+"' ";
		SqlOperate<SimpleEnterprise> operaterSimpleEnterprise=new SqlOperate<SimpleEnterprise>(HiddenStandCheckComShowBaseActivity.this, SimpleEnterprise.class);
		final List<SimpleEnterprise> mdatas=operaterSimpleEnterprise.SelectEntitysByCondition(condition);
		operaterSimpleEnterprise.close();
		
		if(mdatas!=null&&mdatas.size()>0)
		{
			et_ck_companyName.setText(mdatas.get(0).getE_companyName());
			et_ck_companyaddress.setText(mdatas.get(0).getE_companyAddress());
			et_ck_representative.setText(mdatas.get(0).getE_legal_representative());
			et_ck_phone.setText(mdatas.get(0).getE_contact_phone());
		}
		
		et_sc_checkTime.setText(mSelfStandCheck.getSc_checkTime());
		et_sc_checkDept.setText(mSelfStandCheck.getSc_checkDept());
		et_sc_checkPerson.setText(mSelfStandCheck.getSc_checkPerson());
		
		if(mSelfStandCheck.getSc_state().equals("dealing")||
				mSelfStandCheck.getSc_state().equals("noyet")||
				mSelfStandCheck.getSc_state().equals("ing")||
				mSelfStandCheck.getSc_state().equals("finish"))
		{
			if(mSelfStandCheck.getSc_deadline()!=null&&
					!mSelfStandCheck.getSc_deadline().equals(""))
			{
				et_sc_deadline.setText(mSelfStandCheck.getSc_deadline());
			}
			else
			{
				et_sc_deadline.setText("立即整改");
			}
		}
		else if(mSelfStandCheck.getSc_state().equals("noneed"))
		{
			et_sc_deadline.setText("无");
		}
		else
		{
			et_sc_deadline.setText("未添加");
		}
		
		switch (mSelfStandCheck.getSc_state()) {
		case "checking":
			et_sc_state.setText("检查中");
			break;
		case "undeal":
			et_sc_state.setText("未处理");
			break;
		case "dealing":
			et_sc_state.setText("处理中");
			break;
		case "noyet":
			et_sc_state.setText("未整改");
			break;
		case "ing":
			et_sc_state.setText("整改中");
			break;
		case "finish":
			et_sc_state.setText("已整改");
			break;
		case "noneed":
			et_sc_state.setText("无需整改");
			break;
		default:
			et_sc_state.setText("未知状态");
			break;
		}
		
		topbar_hidden_standcheck_show.setTopBarClick(new ITopBarClick() {
			
			@Override
			public void onClickRightBut() {
				
			}
			
			@Override
			public void onClickLeftBut() {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		but_standcheck_item_show.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentlook=new Intent(HiddenStandCheckComShowBaseActivity.this, HiddenStandCheckComShowDatilActivity.class);
				intentlook.putExtra("id", mSelfStandCheck.getSc_uuid());
		   		startActivity(intentlook);
			}
		});
	}
	
	private void initComponent() {
		// TODO Auto-generated method stub
		topbar_hidden_standcheck_show=(TopBarView) findViewById(R.id.topbar_hidden_standcheck_show);
		
		et_ck_companyName=(EditText) findViewById(R.id.et_ck_companyName);
		et_ck_companyaddress=(EditText) findViewById(R.id.et_ck_companyaddress);
		et_ck_representative=(EditText) findViewById(R.id.et_ck_representative);
		et_ck_phone=(EditText) findViewById(R.id.et_ck_phone);
		et_sc_checkTime=(EditText) findViewById(R.id.et_sc_checkTime);
		et_sc_checkDept=(EditText) findViewById(R.id.et_sc_checkDept);
		et_sc_checkPerson=(EditText) findViewById(R.id.et_sc_checkPerson);
		et_sc_deadline=(EditText) findViewById(R.id.et_sc_deadline);
		et_sc_state=(EditText) findViewById(R.id.et_sc_state);
		
		but_standcheck_item_show=(Button) findViewById(R.id.but_standcheck_item_show);
	}
	
}


