package com.sunnyit.law.action;

import java.util.ArrayList;
import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.ClickLoadListview;
import com.sunnyit.common.view.ClickLoadListview.IClickLoadListListener;
import com.sunnyit.common.view.CustomFAB;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.company.action.CompanyIndustryActivity;
import com.sunnyit.company.action.CompanySearch;
import com.sunnyit.law.data.HiddenStandardFileAdapter;
import com.sunnyit.law.model.HiddenStandardFile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**   
* @Title: HiddenList.java 
* @Package com.sunnyit.hiddencheck.action 
* @Description: TODO
* @author yk
* @date 2015年9月7日 下午3:27:12 
* @version V1.0   
*/
public class StandListActivity extends BaseActivity {
	
	private TopBarView topbar_law_info;
	private CustomFAB law_stand_search;
	
	private ClickLoadListview mlistview;
	private List<HiddenStandardFile> mdatas;
	
	private HiddenStandardFileAdapter mAdapter;
	
	private String stand;

	private int pangSize=10;
	private int pageCount=0;
	
	private String conditionStr;
	
	private int activityFlag; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.law_stand_list);
		
		initComponent();
		
		Intent intent=getIntent();
		stand=intent.getStringExtra("stand");
		
		switch (stand) {
		case "govern":
			conditionStr=" where hf_userObj='1' ";
			topbar_law_info.setTitle("执法检查标准信息列表");
			activityFlag=1;
			break;
		case "company":
			conditionStr=" where hf_userObj='0' ";
			topbar_law_info.setTitle("隐患排查标准信息列表");
			activityFlag=0;
			break;
		default:
			conditionStr=" where 1=1 ";
			break;
		}
		
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
		
		law_stand_search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				StandSearch.searchStandInfo(StandListActivity.this,activityFlag);
			}
		});
		
	}

	private void initComponent() {
		// TODO Auto-generated method stub
		topbar_law_info=(TopBarView) findViewById(R.id.topbar_law_info);
		mlistview=(ClickLoadListview) findViewById(R.id.ListView_law_info);
		law_stand_search=(CustomFAB) findViewById(R.id.law_stand_search);
	}
	
	private void initDate() {
		// TODO Auto-generated method stub
		mdatas=new ArrayList<HiddenStandardFile>();
		SqlOperate<HiddenStandardFile> operaterHiddenStandardFile=new SqlOperate<HiddenStandardFile>(StandListActivity.this, HiddenStandardFile.class);
		mdatas=operaterHiddenStandardFile.SelectOffsetEntitysByCondition(conditionStr,pangSize,pageCount);
		pageCount++;
		operaterHiddenStandardFile.close();
		mAdapter=new HiddenStandardFileAdapter(this, mdatas, R.layout.company_industry_item);
		if(mdatas.size()<pangSize)
		{
			mlistview.hindLoadView(true);
		}
		
		mlistview.setAdapter(mAdapter);
		mlistview.setIClickLoadListListener(new IClickLoadListListener() {
			
			@Override
			public void onLoad(Handler handler) {
				// TODO Auto-generated method stub
				SqlOperate<HiddenStandardFile> opetaterMore=new SqlOperate<HiddenStandardFile>(StandListActivity.this, HiddenStandardFile.class);
				final List<HiddenStandardFile> data=opetaterMore.SelectOffsetEntitysByCondition(conditionStr,pangSize,pageCount);
				pageCount++;
				opetaterMore.close();
				
				if(data!=null&&data.size()>0)
		        {
					handler.post(new Runnable() {
						public void run() {
							mdatas.addAll(data);
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
		mlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				try {
					if(arg2<mdatas.size())
					{
						if(mdatas.get(arg2)!=null)
						{
							HiddenStandardFile info=mdatas.get(arg2);
							Bundle bundle = new Bundle(); 
							bundle.putSerializable("HiddenStandardFile", info); 
							Intent intent=new Intent(StandListActivity.this, StandShowActivity.class);
					   		intent.putExtras(bundle);
					   		startActivity(intent);
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
				}
			}
		});
	}
	
}


