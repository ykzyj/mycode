package com.sunnyit.hiddencheck.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.async.HttpClientAsyncPost;
import com.sunnyit.common.async.HttpClientAsyncPost.onHttpClientReturnListener;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.company.model.SimpleEnterprise;
import com.sunnyit.hiddencheck.model.SelfStandCheck;
import com.sunnyit.synchronous.model.UpInfo;
import com.sunnyit.system.model.User;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

/**   
* @Title: HiddenList.java 
* @Package com.sunnyit.hiddencheck.action 
* @Description: TODO
* @author yk
* @date 2015年9月7日 下午3:27:12 
* @version V1.0   
*/
public class HiddenStandCheckComSuperviseActivity extends BaseActivity {
	
	private TopBarView topbar_selfstandcheck_supervise;
	
	private LinearLayout lin_ck_phone;
	
	private EditText et_ck_companyName,et_ck_companyaddress;
	private EditText et_ck_representative,et_ck_phone,et_sc_checkTime;
	private EditText et_sc_checkDept,et_sc_checkPerson,et_sc_superviseOpinion;
	
	private SelfStandCheck mSelfStandCheck;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hidden_seftstandcheck_supervise);
		
		initComponent();
		
		Intent intent=getIntent();
		mSelfStandCheck=(SelfStandCheck) intent.getSerializableExtra("SelfStandCheck");
		
		String condition=" where e_companyName='"+mSelfStandCheck.getSc_companyName()+"' ";
		SqlOperate<SimpleEnterprise> operaterSimpleEnterprise=new SqlOperate<SimpleEnterprise>(HiddenStandCheckComSuperviseActivity.this, SimpleEnterprise.class);
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
		
		topbar_selfstandcheck_supervise.setTopBarClick(new ITopBarClick() {
			
			@Override
			public void onClickRightBut() {
				//提交督促信息
				if(et_sc_superviseOpinion.getText().toString().equals(""))
				{
					Toast.makeText(HiddenStandCheckComSuperviseActivity.this, "督促信息不能为空！", Toast.LENGTH_SHORT).show();
				}
				else
				{
					User user=getCurrentUser();
					
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
					
					mSelfStandCheck.setSc_superviseOpinion(et_sc_superviseOpinion.getText().toString());
					mSelfStandCheck.setSc_superviseDept(user.getDepartmentName());
					mSelfStandCheck.setSc_superviseTime(df.format(new Date()));
					
					SqlOperate<SelfStandCheck> opetaterSelfStandCheck=new SqlOperate<SelfStandCheck>(HiddenStandCheckComSuperviseActivity.this, SelfStandCheck.class);
					opetaterSelfStandCheck.upContent(mSelfStandCheck);
					opetaterSelfStandCheck.close();
					
					String wifi=sp.getString("wifi", null);
					if(wifi.equals("yes"))
					{
						//本地保存
						UpInfo info=new UpInfo();
						info.setU_Id(UUID.randomUUID().toString());
						info.setInfoTable("SelfStandCheck");
						info.setInfoId(mSelfStandCheck.getSc_uuid());
						info.setOperateType("update");
						info.setRemark("");
						
				    	SqlOperate<UpInfo> opetaterUpInfo=new SqlOperate<UpInfo>(HiddenStandCheckComSuperviseActivity.this, UpInfo.class);
				    	opetaterUpInfo.saveContent(info);
				    	opetaterUpInfo.close();
						
						Toast.makeText(HiddenStandCheckComSuperviseActivity.this, "本地保存成功", Toast.LENGTH_SHORT).show();
				    	finish();
					}
					else
					{
						//直接上传
						String addEnUrl=getBaseUrl()+"/appWebSave/upSuperviseOfSelfStandCheck";
						
						HttpClientAsyncPost post=new HttpClientAsyncPost(HiddenStandCheckComSuperviseActivity.this, mSelfStandCheck);
			        	post.execute(addEnUrl);
			        	post.setHttpClientReturnListener(new onHttpClientReturnListener() {
							
							@Override
							public void returnPostExecute(boolean isSuccess, String msg, List<Object> lo) {
								// TODO Auto-generated method stub
								Toast.makeText(HiddenStandCheckComSuperviseActivity.this, msg, Toast.LENGTH_SHORT).show();
								if(isSuccess)
								{
									finish();
								}
							}
						});
					}
				}
			
				
			}
			
			@Override
			public void onClickLeftBut() {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		lin_ck_phone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
    		    intent.setAction("android.intent.action.DIAL");
    		    intent.setData(Uri.parse("tel:"+et_ck_phone.getText().toString()));
    		    startActivity(intent);
			}
		});
	}
	
	private void initComponent() {
		// TODO Auto-generated method stub
		topbar_selfstandcheck_supervise=(TopBarView) findViewById(R.id.topbar_selfstandcheck_supervise);
		
		et_ck_companyName=(EditText) findViewById(R.id.et_ck_companyName);
		et_ck_companyaddress=(EditText) findViewById(R.id.et_ck_companyaddress);
		et_ck_representative=(EditText) findViewById(R.id.et_ck_representative);
		et_ck_phone=(EditText) findViewById(R.id.et_ck_phone);
		et_sc_checkTime=(EditText) findViewById(R.id.et_sc_checkTime);
		et_sc_checkDept=(EditText) findViewById(R.id.et_sc_checkDept);
		et_sc_checkPerson=(EditText) findViewById(R.id.et_sc_checkPerson);
		et_sc_superviseOpinion=(EditText) findViewById(R.id.et_sc_superviseOpinion);
		
		lin_ck_phone=(LinearLayout) findViewById(R.id.lin_ck_phone);
	}
	
}


