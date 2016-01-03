package com.sunnyit.enforcement.action;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.enforcement.model.Standard_CK_Table;

import android.content.Intent;
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
public class HiddenStandCheckShowBaseActivity extends BaseActivity {

	
	private TopBarView topbar_en_standcheck_show;
	
	private EditText et_ck_companyName,et_ck_companyaddress;
	private EditText et_ck_representative,et_ck_phone,et_ck_site;
	private EditText et_ck_dept,et_ck_people,et_ck_time;
	
	private EditText et_ck_startime,et_ck_endtime;
	
	private Button but_standcheck_item_show;
	private Standard_CK_Table standard_CK_Table;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.en_standcheck_showbase);
		
		initComponent();
		
		Intent intent=getIntent();
		standard_CK_Table=(Standard_CK_Table) intent.getSerializableExtra("Standard_CK_Table");
		
		/*String condition=" where e_companyName='"+standard_CK_Table.getCompanyName()+"' ";
		SqlOperate<SimpleEnterprise> operaterSimpleEnterprise=new SqlOperate<SimpleEnterprise>(HiddenStandCheckShowBaseActivity.this, SimpleEnterprise.class);
		final List<SimpleEnterprise> mdatas=operaterSimpleEnterprise.SelectEntitysByCondition(condition);
		operaterSimpleEnterprise.close();
		
		if(mdatas!=null&&mdatas.size()>0)
		{
			et_ck_companyName.setText(mdatas.get(0).getE_companyName());
			et_ck_companyaddress.setText(mdatas.get(0).getE_companyAddress());
			et_ck_representative.setText(mdatas.get(0).getE_legal_representative());
			et_ck_phone.setText(mdatas.get(0).getE_contact_phone());
		}*/
		
		et_ck_companyName.setText(standard_CK_Table.getCompanyName());
		et_ck_companyaddress.setText(standard_CK_Table.getCk_enterpriseaddress());
		et_ck_representative.setText(standard_CK_Table.getCk_legal_representative());
		et_ck_phone.setText(standard_CK_Table.getCk_telphone());
		
		et_ck_site.setText(standard_CK_Table.getCk_site());
		et_ck_dept.setText(standard_CK_Table.getCk_dept());
		et_ck_people.setText(standard_CK_Table.getCk_people());
		et_ck_time.setText(standard_CK_Table.getCk_time());
		
		et_ck_startime.setText(standard_CK_Table.getCk_startime());
		et_ck_endtime.setText(standard_CK_Table.getCk_endtime());
		
		topbar_en_standcheck_show.setTopBarClick(new ITopBarClick() {
			
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
				Intent intentlook=new Intent(HiddenStandCheckShowBaseActivity.this, HiddenStandCheckShowDatilActivity.class);
				intentlook.putExtra("Ck_id", standard_CK_Table.getCk_id());
		   		startActivity(intentlook);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch(resultCode){
		case 1:
			 Intent intent = new Intent();
             intent.putExtra("Ck_ID", (String)data.getStringExtra("Ck_ID"));
             setResult(3, intent);
			finish();
			break;
		case 2:
			Intent intent2 = new Intent();
            setResult(4, intent2);
			finish();
			break;
    	default:
    		break;
		}
	}
	
	private void initComponent() {
		// TODO Auto-generated method stub
		topbar_en_standcheck_show=(TopBarView) findViewById(R.id.topbar_en_standcheck_show);
		
		et_ck_companyName=(EditText) findViewById(R.id.et_ck_companyName);
		et_ck_companyaddress=(EditText) findViewById(R.id.et_ck_companyaddress);
		et_ck_representative=(EditText) findViewById(R.id.et_ck_representative);
		et_ck_phone=(EditText) findViewById(R.id.et_ck_phone);
		et_ck_site=(EditText) findViewById(R.id.et_ck_site);
		et_ck_dept=(EditText) findViewById(R.id.et_ck_dept);
		et_ck_time=(EditText) findViewById(R.id.et_ck_time);
		et_ck_startime=(EditText) findViewById(R.id.et_ck_startime);
		et_ck_endtime=(EditText) findViewById(R.id.et_ck_endtime);
		et_ck_people=(EditText) findViewById(R.id.et_ck_people);
		
		but_standcheck_item_show=(Button) findViewById(R.id.but_standcheck_item_show);
	}
	
}


