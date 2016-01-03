package com.sunnyit.law.action;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.view.CustomFAB;

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
public class LawChoiceActivity extends BaseActivity {
	
	private Button but_law_all,but_law_regulation;
	private Button but_law_administration,but_law_department;
	private Button but_law_local,but_law_document;
	private Button but_law_standard;
	
	private CustomFAB law_stand_search;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.law_regulation_choice);
		
		initComponent();
		initButClick();
	}
	
	private void initComponent() {
		// TODO Auto-generated method stub
		but_law_all=(Button) findViewById(R.id.but_law_all);
		but_law_regulation=(Button) findViewById(R.id.but_law_regulation);
		but_law_administration=(Button) findViewById(R.id.but_law_administration);
		but_law_department=(Button) findViewById(R.id.but_law_department);
		but_law_local=(Button) findViewById(R.id.but_law_local);
		but_law_document=(Button) findViewById(R.id.but_law_document);
		but_law_standard=(Button) findViewById(R.id.but_law_standard);
		
		law_stand_search=(CustomFAB) findViewById(R.id.law_stand_search);
	}

	public void initButClick() {
		
		law_stand_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LawSearch.searchStandInfo(LawChoiceActivity.this);
			}
		});
		
		but_law_all.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(LawChoiceActivity.this, PublicInfoActivity.class);
				intent.putExtra("law", R.drawable.about_our);
		   		startActivity(intent);
			}
		});
		
		but_law_regulation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(LawChoiceActivity.this, PublicInfoActivity.class);
				intent.putExtra("law", R.drawable.country_law150);
		   		startActivity(intent);
			}
		});
		
		but_law_administration.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(LawChoiceActivity.this, PublicInfoActivity.class);
				intent.putExtra("law", R.drawable.industry_law150);
		   		startActivity(intent);
			}
		});
		
		but_law_department.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(LawChoiceActivity.this, PublicInfoActivity.class);
				intent.putExtra("law", R.drawable.department_law150);
		   		startActivity(intent);
			}
		});
		
		but_law_local.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(LawChoiceActivity.this, PublicInfoActivity.class);
				intent.putExtra("law", R.drawable.local_law150);
		   		startActivity(intent);
			}
		});
		
		but_law_document.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(LawChoiceActivity.this, PublicInfoActivity.class);
				intent.putExtra("law", R.drawable.file_law150);
		   		startActivity(intent);
			}
		});
		
		but_law_standard.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(LawChoiceActivity.this, PublicInfoActivity.class);
				intent.putExtra("law", R.drawable.industry_stand150);
		   		startActivity(intent);
			}
		});
		
	}
	
}


