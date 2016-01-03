package com.sunnyit.law.action;

import java.util.ArrayList;
import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.ClickLoadListview;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.ClickLoadListview.IClickLoadListListener;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.company.action.CompanyInfoActivity;
import com.sunnyit.company.action.CompanyListActivity;
import com.sunnyit.company.data.EnterpriseAdapter;
import com.sunnyit.company.model.SimpleEnterprise;
import com.sunnyit.law.data.PublishInfoAdapter;
import com.sunnyit.law.model.PublishInfo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

/**   
* @Title: HiddenList.java 
* @Package com.sunnyit.hiddencheck.action 
* @Description: TODO
* @author yk
* @date 2015年9月7日 下午3:27:12 
* @version V1.0   
*/
public class PublicInfoActivity extends BaseActivity {
	
	private TopBarView topbar_law_info;
	
	private ClickLoadListview mlistview;
	private List<PublishInfo> mdatas;
	
	private PublishInfoAdapter mAdapter;
	
	private int img;
	private String title;
	private String content;

	private int pangSize=10;
	private int pageCount=0;
	
	private String conditionStr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.law_list);
		
		initComponent();
		
		Intent intent=getIntent();
		img=intent.getIntExtra("law", R.drawable.country_law150);
		title=intent.getStringExtra("title");
		content=intent.getStringExtra("content");
		
		switch (img) {
		case R.drawable.country_law150:
			conditionStr=" where keyword='法律' ";
			topbar_law_info.setTitle("法律信息列表");
			break;
		case R.drawable.industry_law150:
			conditionStr=" where keyword='行政法规' ";
			topbar_law_info.setTitle("行政法规信息列表");
			break;
		case R.drawable.department_law150:
			conditionStr=" where keyword='部门规章' ";
			topbar_law_info.setTitle("部门规章信息列表");
			break;
		case R.drawable.local_law150:
			conditionStr=" where keyword='地方性法规' ";
			topbar_law_info.setTitle("地方性法规信息列表");
			break;
		case R.drawable.file_law150:
			conditionStr=" where keyword='规范性文件' ";
			topbar_law_info.setTitle("规范性文件信息列表");
			break;
		case R.drawable.industry_stand150:
			conditionStr=" where keyword='行业标准' ";
			topbar_law_info.setTitle("行业标准信息列表");
			break;
		default:
			conditionStr=" where 1=1 ";
			break;
		}
		
		if(title!=null&&content!=null)
		{
			if(title.equals("标题"))
			{
				conditionStr=conditionStr+" and title like '%"+content+"%' ";
			}
			else
			{
				conditionStr=conditionStr+" and content='%"+content+"%' ";
			}
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
		
	}

	private void initComponent() {
		// TODO Auto-generated method stub
		topbar_law_info=(TopBarView) findViewById(R.id.topbar_law_info);
		mlistview=(ClickLoadListview) findViewById(R.id.ListView_law_info);
	}
	
	private void initDate() {
		// TODO Auto-generated method stub
		mdatas=new ArrayList<PublishInfo>();
		SqlOperate<PublishInfo> operaterPublishInfo=new SqlOperate<PublishInfo>(PublicInfoActivity.this, PublishInfo.class);
		mdatas=operaterPublishInfo.SelectOffsetEntitysByCondition(conditionStr,pangSize,pageCount);
		pageCount++;
		operaterPublishInfo.close();
		mAdapter=new PublishInfoAdapter(this, mdatas, R.layout.law_list_item);
		if(mdatas.size()<pangSize)
		{
			mlistview.hindLoadView(true);
		}
		
		mlistview.setAdapter(mAdapter);
		mlistview.setIClickLoadListListener(new IClickLoadListListener() {
			
			@Override
			public void onLoad(Handler handler) {
				// TODO Auto-generated method stub
				SqlOperate<PublishInfo> opetaterMore=new SqlOperate<PublishInfo>(PublicInfoActivity.this, PublishInfo.class);
				final List<PublishInfo> datas=opetaterMore.SelectOffsetEntitysByCondition(conditionStr,pangSize,pageCount);
				pageCount++;
				opetaterMore.close();
				
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
						PublishInfo info=mdatas.get(arg2);
						Bundle bundle = new Bundle(); 
						bundle.putSerializable("PublishInfo", info); 
						Intent intent=new Intent(PublicInfoActivity.this, PublicInfoShowActivity.class);
						intent.putExtras(bundle);
						startActivity(intent);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
				}
			}
		});
	}
	
}


