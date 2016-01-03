package com.sunnyit.law.action;

import java.util.ArrayList;
import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.dialog.CustomDialog;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.ClickLoadListview;
import com.sunnyit.common.view.CustomFAB;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.ClickLoadListview.IClickLoadListListener;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.law.data.HiddenStandardFileAdapter;
import com.sunnyit.law.model.HiddenStandardFile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.AdapterView.OnItemClickListener;

/**   
* @Title: CompanySearch.java 
* @Package com.sunnyit.company.action 
* @Description: TODO
* @author yk
* @date 2015年9月17日 下午4:44:10 
* @version V1.0   
*/
public class StandSearchFileActivity extends BaseActivity  {

	
	private TopBarView topbar_law_info;
	private CustomFAB law_stand_search;
	
	private ClickLoadListview mlistview;
	private List<HiddenStandardFile> mdatas;
	
	private HiddenStandardFileAdapter mAdapter;
	
	private int pangSize=10;
	private int pageCount=0;
	
	private String conditionStr;
	
	private String activityflag;
	private String searchInfo;
	private String searchtitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.law_stand_list);
		
		initComponent();
		
		activityflag = getIntent().getStringExtra("activityflag");
		searchInfo = getIntent().getStringExtra("searchInfo");
		searchtitle = getIntent().getStringExtra("searchtitle");
		
		topbar_law_info.setTopBarClick(new ITopBarClick() {
			
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
		
		initDate();
		topbar_law_info.setTitle("\""+searchtitle+"\""+"检索结果");
		law_stand_search.setVisibility(View.GONE);
	}

	private void initComponent() {
		// TODO Auto-generated method stub
		topbar_law_info=(TopBarView) findViewById(R.id.topbar_law_info);
		mlistview=(ClickLoadListview) findViewById(R.id.ListView_law_info);
		law_stand_search=(CustomFAB) findViewById(R.id.law_stand_search);
	}
	
	private void initDate() {
		// TODO Auto-generated method stub
		
		conditionStr=" Select * from HiddenStandardFile where hf_id in "
				+ " (select distinct c_rootId from ExcelCell where c_content  like '%"+searchInfo+"%' "
				+ " and c_title<6 and c_rootId in  "
				+ "(select hf_id from HiddenStandardFile where hf_userObj='"+activityflag+"'))";
		
		mdatas=new ArrayList<HiddenStandardFile>();
		SqlOperate<HiddenStandardFile> operaterHiddenStandardFile=new SqlOperate<HiddenStandardFile>(StandSearchFileActivity.this, HiddenStandardFile.class);
		mdatas=operaterHiddenStandardFile.SelectOffsetEntitysBySqlCondition(conditionStr, pangSize, pageCount);
		pageCount++;
		operaterHiddenStandardFile.close();
		mAdapter=new HiddenStandardFileAdapter(this, mdatas, R.layout.company_industry_item);
		if(mdatas.size()<pangSize)
		{
			if(mdatas.size()==0)
			{
				mlistview.searchNullHindLoadView();
			}
			else
			{
				mlistview.hindLoadView(true);
			}
		}
		
		mlistview.setAdapter(mAdapter);
		mlistview.setIClickLoadListListener(new IClickLoadListListener() {
			
			@Override
			public void onLoad(Handler handler) {
				// TODO Auto-generated method stub
				SqlOperate<HiddenStandardFile> opetaterMore=new SqlOperate<HiddenStandardFile>(StandSearchFileActivity.this, HiddenStandardFile.class);
				final List<HiddenStandardFile> datas=opetaterMore.SelectOffsetEntitysByCondition(conditionStr,pangSize,pageCount);
				pageCount++;
				opetaterMore.close();
				mdatas.addAll(datas);
				
				if(datas!=null&&datas.size()>0)
		        {
					handler.post(new Runnable() {
						public void run() {
							mdatas.addAll(datas);
							mAdapter.notifyDataSetChanged();
							if(datas.size()<pangSize)
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
		mlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				try {
					if(mdatas.get(arg2)!=null)
					{
						Intent intent=new Intent(StandSearchFileActivity.this, StandShowActivity.class);
						intent.putExtra("searchInfo", searchInfo);
						intent.putExtra("searchtitle", mdatas.get(arg2).getHf_name());
						intent.putExtra("c_rootid", mdatas.get(arg2).getHf_id());
						startActivity(intent);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
				}
			}
		});
	}
	
}


