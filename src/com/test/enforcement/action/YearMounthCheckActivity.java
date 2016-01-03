package com.sunnyit.enforcement.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.view.CustomFAB;
import com.sunnyit.company.action.CompanyIndustryActivity;
import com.sunnyit.company.action.CompanySearch;
import com.sunnyit.enforcement.data.ExAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

/**   
* @Title: DailyCheckList.java 
* @Package com.sunnyit.hiddencheck.action 
* @Description: TODO
* @author yk
* @date 2015年9月7日 下午3:20:13 
* @version V1.0   
*/
public class YearMounthCheckActivity extends BaseActivity {

	private ExpandableListView exlist_govern_ym;
	private CustomFAB en_company_search;
	
	private List<BasicNameValuePair> fatherList;
	private List<List<BasicNameValuePair>> childList;
	
	private ExAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.en_check_yearmounth);
		
		fatherList = new ArrayList<BasicNameValuePair>();
		childList = new ArrayList<List<BasicNameValuePair>>();
		
		initComponent();
		
		initExpandableListView();
		
	}
	
	private void initExpandableListView()
	{
		Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
		int year = c.get(Calendar.YEAR); 
		while(year>2014)
		{
			fatherList.add(new BasicNameValuePair("father", year+"年"));
			List<BasicNameValuePair> cList_yt = new ArrayList<BasicNameValuePair>();
			cList_yt.add(new BasicNameValuePair("child", "01月被检查企业"));
			cList_yt.add(new BasicNameValuePair("child", "02月被检查企业"));
			cList_yt.add(new BasicNameValuePair("child", "03月被检查企业"));
			cList_yt.add(new BasicNameValuePair("child", "04月被检查企业"));
			cList_yt.add(new BasicNameValuePair("child", "05月被检查企业"));
			cList_yt.add(new BasicNameValuePair("child", "06月被检查企业"));
			cList_yt.add(new BasicNameValuePair("child", "07月被检查企业"));
			cList_yt.add(new BasicNameValuePair("child", "08月被检查企业"));
			cList_yt.add(new BasicNameValuePair("child", "09月被检查企业"));
			cList_yt.add(new BasicNameValuePair("child", "10被月检查企业"));
			cList_yt.add(new BasicNameValuePair("child", "11被月检查企业"));
			cList_yt.add(new BasicNameValuePair("child", "12被月检查企业"));
			childList.add(cList_yt);
			
			year--;
		}
		
		if(c.get(Calendar.YEAR)>2014)
		{
			mAdapter=new ExAdapter(YearMounthCheckActivity.this, fatherList, childList);
			exlist_govern_ym.setAdapter(mAdapter);
			exlist_govern_ym.setGroupIndicator(null);
			exlist_govern_ym.setDivider(null);
			exlist_govern_ym.expandGroup(0);  
			
			exlist_govern_ym.setOnChildClickListener(new OnChildClickListener() 
			{	     
			    @Override
			    public boolean onChildClick(ExpandableListView parent, View v,
			            int groupPosition, int childPosition, long id) 
			    {
			    	String father_str=fatherList.get(groupPosition).getValue();
			    	String child_str=childList.get(groupPosition).get(childPosition).getValue();
			    	
			    	Intent intent=new Intent(YearMounthCheckActivity.this, HiddenMounthActivity.class);
			    	intent.putExtra("father_str", father_str);
			    	intent.putExtra("child_str", child_str);
		       		startActivity(intent);
		    	
			        return true;
			    }
			});
			
			en_company_search.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					CompanySearch.searchCompanyInfo(YearMounthCheckActivity.this,2);
				}
			});
		}
    }
	
	private void initComponent() {
		// TODO Auto-generated method stub
		exlist_govern_ym=(ExpandableListView) findViewById(R.id.exlist_govern_ym);
		en_company_search=(CustomFAB) findViewById(R.id.en_company_search);
		
		en_company_search.setVisibility(View.GONE);
	}
	

}


