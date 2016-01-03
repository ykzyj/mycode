package com.sunnyit.enforcement.action;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.view.CustomFAB;
import com.sunnyit.law.action.LawChoiceActivity;
import com.sunnyit.law.action.LawSearch;

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
public class ReviewCheckChoiceActivity extends BaseActivity {
	
	private Button but_daily_check;
	private Button but_stand_check;
	private Button but_special_check;

	private Button but_daily_allcheck;
	private Button but_stand_allcheck;
	private Button but_special_allcheck;
	
	private CustomFAB en_check_search;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.en_review_choice);
		
		initComponent();
		
		initButClick();
	}
	
	private void initButClick() {
		// TODO Auto-generated method stub
		but_daily_check.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(ReviewCheckChoiceActivity.this, DailyReviewListActivity.class);
				intent.putExtra("activityFlag", 0);
	       		startActivity(intent);
			}
		});
		
		but_stand_check.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(ReviewCheckChoiceActivity.this, HiddenStandCheckListActivity.class);
				intent.putExtra("activityFlag", 0);
	       		startActivity(intent);
			}
		});

		but_special_check.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(ReviewCheckChoiceActivity.this, SpecialReviewListActivity.class);
				intent.putExtra("activityFlag", 0);
	       		startActivity(intent);
			}
		});
		but_daily_allcheck.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(ReviewCheckChoiceActivity.this, DailyReviewListActivity.class);
				intent.putExtra("activityFlag", 1);
	       		startActivity(intent);
			}
		});
		but_stand_allcheck.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(ReviewCheckChoiceActivity.this, HiddenStandCheckListActivity.class);
				intent.putExtra("activityFlag", 1);
	       		startActivity(intent);
				
			}
		});
		but_special_allcheck.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(ReviewCheckChoiceActivity.this, SpecialReviewListActivity.class);
				intent.putExtra("activityFlag", 1);
	       		startActivity(intent);
			}
		});
		en_check_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EnCheckSearch.searchStandInfo(ReviewCheckChoiceActivity.this);
			}
		});
	}

	private void initComponent() {
		// TODO Auto-generated method stub
		but_daily_check=(Button) findViewById(R.id.but_daily_check);
		but_stand_check=(Button) findViewById(R.id.but_stand_check);
		but_special_check=(Button) findViewById(R.id.but_special_check);
		
		but_daily_allcheck=(Button) findViewById(R.id.but_daily_allcheck);
		but_stand_allcheck=(Button) findViewById(R.id.but_stand_allcheck);
		but_special_allcheck=(Button) findViewById(R.id.but_special_allcheck);
		
		en_check_search=(CustomFAB) findViewById(R.id.en_check_search);
		
		/*but_stand_check.setVisibility(View.GONE);
		but_stand_allcheck.setVisibility(View.GONE);*/
	}
	
}


