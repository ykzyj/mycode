package com.sunnyit.hiddencheck.action;

import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.ClickLoadListview;
import com.sunnyit.common.view.ClickLoadListview.IClickLoadListListener;
import com.sunnyit.common.view.CustomFAB;
import com.sunnyit.common.view.SearchInfoView;
import com.sunnyit.common.view.SearchInfoView.SearchButListener;
import com.sunnyit.common.view.SearchInfoView.SearchTextListener;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.company.action.CompanyInfoActivity;
import com.sunnyit.company.action.CompanySearch;
import com.sunnyit.hiddencheck.data.CompanyNameAdapter;
import com.sunnyit.hiddencheck.model.CompanyMin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

/**   
* @Title: OneMonthNoReport.java 
* @Package com.sunnyit.hiddencheck.action 
* @Description: TODO
* @author yk
* @date 2015年9月14日 上午11:21:27 
* @version V1.0   
*/
public class HiddenTabSortDatilActivity extends BaseActivity {
	
	private ClickLoadListview mlistview;
	private List<CompanyMin> mDatas;
	private CompanyNameAdapter mAdapter;
	private int pangSize=10;
	private int pageCount=0;
	
	private String mSqlStr;
	
	private CustomFAB  company_hidden_search;
	private TopBarView   topbar_com_name;
	
	private SearchInfoView search_hidden_company_name;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hidden_check_list);
		
		Intent intent=getIntent();
		mSqlStr=intent.getStringExtra("sql");
		String remind=intent.getStringExtra("remind");
		
		initComponent();
		if(remind==null)
		{
			topbar_com_name.setVisibility(View.GONE);
		}
		else
		{
			topbar_com_name.setTitle("企业自查超期企业");
			topbar_com_name.setRightButVisibility(View.GONE);
			topbar_com_name.setTopBarClick(new ITopBarClick() {
				
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
		}
		
		company_hidden_search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CompanySearch.searchCompanyInfo(HiddenTabSortDatilActivity.this,1);
			}
		});
		
		search_hidden_company_name.setSearchTextListener(new SearchTextListener() {
			@Override
			public void setOnSearchTextChanged(String key) {
				pageCount=0;
				mlistview.showLoadView();
				if(key.equals(""))
				{
					initDate(mSqlStr);
				}
				else
				{
					StringBuffer sb=new StringBuffer();
					sb.append(" select * from ( ");
					sb.append(mSqlStr);
					sb.append(" ) where e_companyName like '%");
					sb.append(key);
					sb.append("%'");
					initDate(sb.toString());
				}
			}
		});
		
		search_hidden_company_name.setSearchButListener(new SearchButListener() {
			@Override
			public void setOnSearchButClick(String content) {
				pageCount=0;
				mlistview.showLoadView();
				StringBuffer condition=new StringBuffer();
				condition.append("'");
				for(int i=0;i<content.length();i++)
				{
					condition.append("%");
					condition.append(content.charAt(i));
				}
				condition.append("%'");
				StringBuffer sb=new StringBuffer();
				sb.append(" select * from ( ");
				sb.append(mSqlStr);
				sb.append(" ) where e_companyName like ");
				sb.append(condition);
				initDate(sb.toString());
			}
		});
		
		initDate(mSqlStr);
	}

	
	private void initComponent() {
		// TODO Auto-generated method stub
		mlistview=(ClickLoadListview) findViewById(R.id.ListView_hidden_check);
		company_hidden_search=(CustomFAB) findViewById(R.id.company_hidden_search);
		topbar_com_name=(TopBarView) findViewById(R.id.topbar_com_name);
		search_hidden_company_name=(SearchInfoView) findViewById(R.id.search_hidden_company_name);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case 1:
			pageCount=0;
			String searchTxt=search_hidden_company_name.getSearchText();
			if(searchTxt.equals(""))
			{
				initDate(mSqlStr);
			}
			else
			{
				StringBuffer sb=new StringBuffer();
				sb.append(" select * from ( ");
				sb.append(mSqlStr);
				sb.append(" ) where e_companyName like '%");
				sb.append(searchTxt);
				sb.append("%'");
				initDate(sb.toString());
			}
			break;

		default:
			break;
		}
	}
	
	private void initDate(final String sqlStr) {
		// TODO Auto-generated method stub
        SqlOperate<CompanyMin> opetaterCompanyName=new SqlOperate<CompanyMin>(this, CompanyMin.class);
        mDatas=opetaterCompanyName.SelectOffsetEntitysBySqlCondition(sqlStr, pangSize, pageCount);
        pageCount=1;
		opetaterCompanyName.close();
		
		if(mDatas.size()<pangSize)
		{
			mlistview.hindLoadView(false);
		}
		
		mAdapter= new CompanyNameAdapter(this, mDatas, R.layout.company_industry_item);
        
        mlistview.setAdapter(mAdapter);
        mlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				if(arg2<mDatas.size())
				{
					CompanyMin companyMin=mDatas.get(arg2);
					Intent	intent=new Intent(HiddenTabSortDatilActivity.this, HiddenTabListActivity.class);
					intent.putExtra("e_Id", companyMin.getE_Id());
					//startActivity(intent);
					startActivityForResult(intent, 1);
				}
			}
		});
        
        mlistview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				if(arg2<mDatas.size())
				{
					CompanyMin companyName=mDatas.get(arg2);
					Intent	intent=new Intent(HiddenTabSortDatilActivity.this, CompanyInfoActivity.class);
					intent.putExtra("companyName", companyName.getE_companyName());
					startActivity(intent);
				}
				return true;
			}
		});
        
        mlistview.setIClickLoadListListener(new IClickLoadListListener() {
			
			@Override
			public void onLoad(Handler handler) {
				// TODO Auto-generated method stub
				SqlOperate<CompanyMin> opetaterCompanyName=new SqlOperate<CompanyMin>(HiddenTabSortDatilActivity.this, CompanyMin.class);
				final List<CompanyMin> data=opetaterCompanyName.SelectOffsetEntitysBySqlCondition(sqlStr, pangSize, pageCount);
		        pageCount++;
				opetaterCompanyName.close();
				
				if(data!=null&&data.size()>0)
		        {
					handler.post(new Runnable() {
						public void run() {
							mDatas.addAll(data);
							mAdapter.notifyDataSetChanged();
							if(data.size()<pangSize)
							{
								mlistview.hindLoadView(true);
							}
						}
					});
		        }
		        else
		        {
		        	mlistview.hindLoadView(true);
		        }
			}
		});
	}
	
}





