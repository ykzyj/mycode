package com.sunnyit.enforcement.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.DateEditView;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.company.model.SimpleEnterprise;
import com.sunnyit.enforcement.model.Standard_CK_Table;
import com.sunnyit.system.model.User;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TimePicker;

/**   
* @Title: HiddenList.java 
* @Package com.sunnyit.hiddencheck.action 
* @Description: TODO
* @author yk
* @date 2015年9月7日 下午3:27:12 
* @version V1.0   
*/
public class HiddenStandCheckAddActivity extends BaseActivity {
	
	private TopBarView topbar_en_standcheck_add;
	
	private EditText et_ck_companyName,et_ck_companyaddress;
	private EditText et_ck_representative,et_ck_phone,et_ck_site;
	private EditText et_ck_dept,et_ck_people;
	
	private EditText et_ck_startime,et_ck_endtime;
	
	private DateEditView et_ck_time;
	
	Date currentTime = new Date();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	String dateString = formatter.format(currentTime);
	
	private String checkCompanyName;
	private String tableid;
	private String tableName;
	
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hidden_enbase_standcheck_add);
		
		initComponent();
		
		et_ck_time.setText(dateString);
		
		Intent intent=getIntent();
		checkCompanyName=intent.getStringExtra("checkCompanyName");
		tableid=intent.getStringExtra("tableid");
		tableName=intent.getStringExtra("tableName");
		
		user=getCurrentUser();
		et_ck_dept.setText(user.getDepartmentName());
		
		String condition=" where e_companyName='"+checkCompanyName+"' ";
		SqlOperate<SimpleEnterprise> operaterSimpleEnterprise=new SqlOperate<SimpleEnterprise>(HiddenStandCheckAddActivity.this, SimpleEnterprise.class);
		final List<SimpleEnterprise> mdatas=operaterSimpleEnterprise.SelectEntitysByCondition(condition);
		operaterSimpleEnterprise.close();
		
		if(mdatas!=null&&mdatas.size()>0)
		{
			et_ck_companyName.setText(mdatas.get(0).getE_companyName());
			et_ck_companyaddress.setText(mdatas.get(0).getE_companyAddress());
			et_ck_representative.setText(mdatas.get(0).getE_legal_representative());
			et_ck_phone.setText(mdatas.get(0).getE_contact_phone());
		}
		
		et_ck_site.setText(sp.getString("sc_ck_site", ""));
		et_ck_people.setText(sp.getString("sc_ck_people", ""));
		
		topbar_en_standcheck_add.setTopBarClick(new ITopBarClick() {
			
			@Override
			public void onClickRightBut() {
				// TODO Auto-generated method stub
				
				Standard_CK_Table standTable=new Standard_CK_Table();
				standTable.setCk_id(UUID.randomUUID().toString());
				standTable.setCk_site(et_ck_site.getText().toString().trim());
				standTable.setCompanyName(mdatas.get(0).getE_companyName());
				standTable.setCompanyId(mdatas.get(0).getE_Id());
				standTable.setCk_people(et_ck_people.getText().toString().trim());
				standTable.setCk_dept(user.getDepartmentName());
				standTable.setCk_deptId(user.getDepartmentId());
				standTable.setCk_time(et_ck_time.getText().toString().trim());
				standTable.setCk_name(tableName);
				standTable.setCk_userId(user.getUserId());
				
				standTable.setCk_startime(et_ck_startime.getText().toString().trim());
				standTable.setCk_endtime(et_ck_endtime.getText().toString().trim());
				
				standTable.setCk_legal_representative(et_ck_representative.getText().toString().trim());
				standTable.setCk_telphone(et_ck_phone.getText().toString().trim());
				standTable.setCk_enterpriseaddress(et_ck_companyaddress.getText().toString());
				
				Editor edit=sp.edit();
				edit.putString("sc_ck_site", et_ck_site.getText().toString().trim());
				edit.putString("sc_ck_people", et_ck_people.getText().toString().trim());
				edit.commit(); 
				
				Bundle bundle = new Bundle(); 
				bundle.putSerializable("Standard_CK_Table", standTable); 
				Intent intent=new Intent(HiddenStandCheckAddActivity.this, HiddenStandDatilAddActivity.class);
	       		intent.putExtras(bundle);
	       		intent.putExtra("tableid", tableid);
	       		startActivityForResult(intent, 0);
				
			}
			
			@Override
			public void onClickLeftBut() {
				// TODO Auto-generated method stub
				finish();
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
		topbar_en_standcheck_add=(TopBarView) findViewById(R.id.topbar_en_standcheck_add);
		
		et_ck_companyName=(EditText) findViewById(R.id.et_ck_companyName);
		et_ck_companyaddress=(EditText) findViewById(R.id.et_ck_companyaddress);
		et_ck_representative=(EditText) findViewById(R.id.et_ck_representative);
		et_ck_phone=(EditText) findViewById(R.id.et_ck_phone);
		et_ck_site=(EditText) findViewById(R.id.et_ck_site);
		et_ck_dept=(EditText) findViewById(R.id.et_ck_dept);
		et_ck_time=(DateEditView) findViewById(R.id.et_ck_time);
		et_ck_people=(EditText) findViewById(R.id.et_ck_people);
		
		et_ck_startime=(EditText) findViewById(R.id.et_ck_startime);
		et_ck_endtime=(EditText) findViewById(R.id.et_ck_endtime);
		
		Calendar cal = Calendar.getInstance();
		final int hour = cal.get(Calendar.HOUR_OF_DAY);
		final int minute = cal.get(Calendar.MINUTE);
		
		et_ck_startime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				TimePickerDialog timePickerDialogE = new TimePickerDialog(HiddenStandCheckAddActivity.this, new TimePickerDialog.OnTimeSetListener()
                {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
                    {
                    	 StringBuffer sb_etime=new StringBuffer();
                 		if(hourOfDay<10)
 						{
                 			if(minute<10)
     						{
                 				sb_etime.append("0"+hourOfDay + ":0"+ minute);
     						}
     						else
     						{
     							sb_etime.append("0"+hourOfDay + ":"+ minute);
     						}
 						}
 						else
 						{
 							if(minute<10)
     						{
 								sb_etime.append(hourOfDay + ":0"+ minute);
     						}
     						else
     						{
     							sb_etime.append(hourOfDay + ":"+ minute);
     						}
 						}
                 		et_ck_startime.setText(sb_etime);
 					
                    }
                }, hour, minute, true);
				timePickerDialogE.show();
			
			
			}
		});;
		et_ck_endtime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				TimePickerDialog timePickerDialogE = new TimePickerDialog(HiddenStandCheckAddActivity.this, new TimePickerDialog.OnTimeSetListener()
                {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
                    {
                    	 StringBuffer sb_etime=new StringBuffer();
                 		if(hourOfDay<10)
 						{
                 			if(minute<10)
     						{
                 				sb_etime.append("0"+hourOfDay + ":0"+ minute);
     						}
     						else
     						{
     							sb_etime.append("0"+hourOfDay + ":"+ minute);
     						}
 						}
 						else
 						{
 							if(minute<10)
     						{
 								sb_etime.append(hourOfDay + ":0"+ minute);
     						}
     						else
     						{
     							sb_etime.append(hourOfDay + ":"+ minute);
     						}
 						}
                 		et_ck_endtime.setText(sb_etime);
 					
                    }
                }, hour, minute, true);
				timePickerDialogE.show();
			
			
			}
		});
	}
	
}


