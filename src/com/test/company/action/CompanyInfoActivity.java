package com.sunnyit.company.action;

import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.company.model.SimpleEnterprise;
import com.sunnyit.hiddencheck.action.HiddenTabListActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

/**   
* @Title: WelcomeActivity.java 
* @Package com.sunnyit.system.action 
* @Description: TODO
* @author yk
* @date 2015年8月1日 上午9:47:36 
* @version V1.0   
*/
public class CompanyInfoActivity extends BaseActivity  {
	
	private TopBarView topbar_com_industry;
	private TextView tv_e_companyName,tv_e_companyAddress,tv_e_managerlayer;
	private TextView tv_e_departmentName,tv_e_localName,tv_e_legal_representative;
	private TextView tv_e_contact_phone,tv_belongIndustry,tv_mangementMethod;
	private TextView tv_e_businessCode,tv_e_safe_person_phone;
	private LinearLayout Layout_contact_phone,Layout_safe_person_phone;
	
	private TextView tv_e_companyProperty,tv_e_safe_person;
	
	private SimpleEnterprise simpleEnterprise;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.company_info);
		
		
		simpleEnterprise = (SimpleEnterprise) getIntent().getSerializableExtra("SimpleEnterprise"); 
		
		if(simpleEnterprise==null)
		{
			String companyName=getIntent().getStringExtra("companyName");
			SqlOperate<SimpleEnterprise> opetaterCompanyName=new SqlOperate<SimpleEnterprise>(this, SimpleEnterprise.class);
			List<SimpleEnterprise> data=opetaterCompanyName.SelectEntitysByCondition(" where e_companyName='"+companyName+"' ");
			opetaterCompanyName.close();
			if(data.size()>0)
			{
				simpleEnterprise=data.get(0);
			}
		}
		
		initView();
		initShow(simpleEnterprise);
		
		topbar_com_industry.setTopBarClick(new TopBarView.ITopBarClick() {
			@Override
			public void onClickRightBut() {
				// TODO Auto-generated method stub
				/*Bundle bundle = new Bundle(); 
				bundle.putSerializable("SimpleEnterprise", simpleEnterprise); */
				Intent intent=new Intent(CompanyInfoActivity.this, HiddenTabListActivity.class);
	       		/*intent.putExtras(bundle);*/
				intent.putExtra("e_Id", simpleEnterprise.getE_Id());
	       		startActivity(intent);
			}
			
			@Override
			public void onClickLeftBut() {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
	}


	private void initShow(SimpleEnterprise simpleEnterprise) {
		// TODO Auto-generated method stub
		tv_e_companyName.setText(simpleEnterprise.getE_companyName());
		tv_e_companyAddress.setText(simpleEnterprise.getE_companyAddress());
		tv_e_managerlayer.setText(simpleEnterprise.getE_managerlayer());
		if(simpleEnterprise.getE_departmentName().equals("other"))
		{
			tv_e_departmentName.setText("其他");
		}
		else
		{
			tv_e_departmentName.setText(simpleEnterprise.getE_departmentName());
		}
		tv_e_localName.setText(simpleEnterprise.getE_localName());
		tv_e_legal_representative.setText(simpleEnterprise.getE_legal_representative());
		tv_e_contact_phone.setText(simpleEnterprise.getE_contact_phone());
		tv_belongIndustry.setText(simpleEnterprise.getE_belongIndustry());
		tv_mangementMethod.setText(simpleEnterprise.getE_mangementMethod());
		tv_e_businessCode.setText(simpleEnterprise.getE_businessCode());
		tv_e_safe_person_phone.setText(simpleEnterprise.getE_safe_person_phone());
		tv_e_safe_person.setText(simpleEnterprise.getE_safe_person());
		
		tv_e_companyProperty.setText(simpleEnterprise.getE_companyProperty());
	}


	private void initView() {
		topbar_com_industry = (TopBarView)findViewById(R.id.topbar_company_info);
		
		Layout_contact_phone = (LinearLayout)findViewById(R.id.Layout_contact_phone);
		Layout_safe_person_phone = (LinearLayout)findViewById(R.id.Layout_safe_person_phone);
		
		tv_e_companyName = (TextView)findViewById(R.id.tv_e_companyName);
		tv_e_companyAddress = (TextView)findViewById(R.id.tv_e_companyAddress);
		tv_e_managerlayer = (TextView)findViewById(R.id.tv_e_managerlayer);
		tv_e_departmentName = (TextView)findViewById(R.id.tv_e_departmentName);
		tv_e_localName = (TextView)findViewById(R.id.tv_e_localName);
		tv_e_legal_representative = (TextView)findViewById(R.id.tv_e_legal_representative);
		tv_e_contact_phone = (TextView)findViewById(R.id.tv_e_contact_phone);
		tv_belongIndustry = (TextView)findViewById(R.id.tv_belongIndustry);
		tv_mangementMethod = (TextView)findViewById(R.id.tv_mangementMethod);
		tv_e_businessCode = (TextView)findViewById(R.id.tv_e_businessCode);
		tv_e_safe_person_phone = (TextView)findViewById(R.id.tv_e_safe_person_phone);
		tv_e_safe_person = (TextView)findViewById(R.id.tv_e_safe_person);
		
		tv_e_companyProperty = (TextView)findViewById(R.id.tv_e_companyProperty);
		
		Layout_contact_phone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
    		    intent.setAction("android.intent.action.DIAL");
    		    intent.setData(Uri.parse("tel:"+tv_e_contact_phone.getText().toString()));
    		    startActivity(intent);
			}
		});
		
		Layout_safe_person_phone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
    		    intent.setAction("android.intent.action.DIAL");
    		    intent.setData(Uri.parse("tel:"+tv_e_safe_person_phone.getText().toString()));
    		    startActivity(intent);
			}
		});
	}
	
}


