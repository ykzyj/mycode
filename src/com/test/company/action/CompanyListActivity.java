package com.sunnyit.company.action;

import java.util.ArrayList;
import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.ClickLoadListview;
import com.sunnyit.common.view.ClickLoadListview.IClickLoadListListener;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.company.data.EnterpriseAdapter;
import com.sunnyit.company.model.SimpleEnterprise;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**   
* @Title: WelcomeActivity.java 
* @Package com.sunnyit.system.action 
* @Description: TODO
* @author yk
* @date 2015年8月1日 上午9:47:36 
* @version V1.0   
*/
public class CompanyListActivity extends BaseActivity  {
	
	private TopBarView topbar_com_industry;
	private ClickLoadListview mlistview;
	private List<SimpleEnterprise> mdatas;
	
	private EnterpriseAdapter mAdapter;
	
	private int img;
	private String industryName;
	private String conditionStr;//搜索条件
	
	private int pangSize=10;
	private int pageCount=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.company_list);
		
		Intent intent=getIntent();
		img=intent.getIntExtra("industry", R.drawable.an72);
		industryName=intent.getStringExtra("industryName");
		
		conditionStr=intent.getStringExtra("conditionStr");
		
		initView();
		
		if(conditionStr==null)
		{
			conditionStr=" where e_belongIndustry like '%"+industryName+"%' order by e_Id";
			topbar_com_industry.setTitle(industryName+"企业信息列表");
		}
		else
		{
			img=-1;
			topbar_com_industry.setTitle("搜索企业信息列表");
		}
		
		initDate();
		
		topbar_com_industry.setTopBarClick(new TopBarView.ITopBarClick() {
			@Override
			public void onClickRightBut() {
				// TODO Auto-generated method stub
				Intent intent=new Intent(CompanyListActivity.this, CompanyInfoAddActivity.class);
				intent.putExtra("industryName", industryName);
	       		startActivityForResult(intent, 0);
			}
			
			@Override
			public void onClickLeftBut() {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		initList();
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 0:
			
			if(data!=null)
			{
				SimpleEnterprise simp=(SimpleEnterprise) data.getSerializableExtra("SimpleEnterprise");
				if(simp!=null)
				{
					mdatas.add(0, simp);
					mAdapter.notifyDataSetChanged();
				}
			}
			
			break;

		default:
			break;
		}
	}

	private void initList() {
		mlistview.setAdapter(mAdapter);
		mlistview.setIClickLoadListListener(new IClickLoadListListener() {
			
			@Override
			public void onLoad(Handler handler) {
				// TODO Auto-generated method stub
				//Toast.makeText(CompanyListActivity.this, "99999", Toast.LENGTH_SHORT).show();
				SqlOperate<SimpleEnterprise> opetater=new SqlOperate<SimpleEnterprise>(CompanyListActivity.this, SimpleEnterprise.class);
				//lse=opetater.SelectEntitysByCondition(" where e_belongIndustry='"+industryName+"'");
				final List<SimpleEnterprise> datas=opetater.SelectOffsetEntitysByCondition(conditionStr,pangSize,pageCount);
				pageCount++;
				opetater.close();
				
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
						SimpleEnterprise sime=mdatas.get(arg2);
						Bundle bundle = new Bundle(); 
						bundle.putSerializable("SimpleEnterprise", sime); 
						Intent intent=new Intent(CompanyListActivity.this, CompanyInfoActivity.class);
						intent.putExtras(bundle);
						startActivity(intent);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
				}
			}
		});
	}
	
	private void initDate() {
		// TODO Auto-generated method stub
		mdatas=new ArrayList<SimpleEnterprise>();
		SqlOperate<SimpleEnterprise> opetater=new SqlOperate<SimpleEnterprise>(CompanyListActivity.this, SimpleEnterprise.class);
		//lse=opetater.SelectEntitysByCondition(" where e_belongIndustry='"+industryName+"'");
		mdatas=opetater.SelectOffsetEntitysByCondition(conditionStr,pangSize,pageCount);
		pageCount++;
		opetater.close();
		mAdapter=new EnterpriseAdapter(this, mdatas, R.layout.company_list_item,img);
		if(mdatas.size()<pangSize)
		{
			mlistview.hindLoadView(true);
		}
	}

	private void initView() {
		topbar_com_industry = (TopBarView)findViewById(R.id.topbar_com_industry);
		mlistview=(ClickLoadListview) findViewById(R.id.ListView_company_info);
	}
	
}


