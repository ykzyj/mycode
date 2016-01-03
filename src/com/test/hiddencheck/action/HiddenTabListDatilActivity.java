package com.sunnyit.hiddencheck.action;

import java.util.List;
import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.dialog.CustomDialog;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.SwipeLoadListView;
import com.sunnyit.common.view.SwipeLoadListView.IClickLoadListListener;
import com.sunnyit.company.action.CompanyInfoActivity;
import com.sunnyit.company.action.CompanyListActivity;
import com.sunnyit.company.model.SimpleEnterprise;
import com.sunnyit.enforcement.action.DailyReviewListActivity;
import com.sunnyit.enforcement.data.DailyCheckAdapter;
import com.sunnyit.enforcement.model.DailyCheck;
import com.sunnyit.hiddencheck.data.SelfCheckAdapter;
import com.sunnyit.hiddencheck.data.SelfStandCheckAdapter;
import com.sunnyit.hiddencheck.model.CompanyMin;
import com.sunnyit.hiddencheck.model.SelfCheck;
import com.sunnyit.hiddencheck.model.SelfStandCheck;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

/**   
* @Title: HiddenList.java 
* @Package com.sunnyit.hiddencheck.action 
* @Description: TODO
* @author yk
* @date 2015年9月7日 下午3:27:12 
* @version V1.0   
*/
public class HiddenTabListDatilActivity extends BaseActivity {

	
	private SwipeLoadListView mlistview;
	private List<SelfCheck> mDatas_Sc;
	private List<SelfStandCheck> mDatas_Ssc;
	private SelfCheckAdapter mAdapter_Sc;
	private SelfStandCheckAdapter mAdapter_Ssc;
	private int pangSize=10;
	private int pageCount=0;
	
	private String mSqlStr;
	private String aFlag;
	
	private CustomDialog cusdialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hidden_sscheck_list);
		
		Intent intent=getIntent();
		mSqlStr=intent.getStringExtra("SqlStr");
		aFlag=intent.getStringExtra("aFlag");
		
		initComponent();
		
		cusdialog=new CustomDialog(HiddenTabListDatilActivity.this);
		cusdialog.setViewAndAlpha(R.layout.progressbar_rw_blue,0);
		cusdialog.setOutCancel(false);
		cusdialog.show();
		
		new Thread(new Runnable() {
			public void run() {
				
				if(aFlag.equals("SelfCheck"))
				{
					initSelfCheckDate();
				}
				else
				{
					initSelfStandCheckDate();
				}
				
        		Message msg=Message.obtain();
        		msg.what=0;
        		handler.sendMessage(msg);
        		cusdialog.dismiss();
			}
		}).start();
		
	}
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(aFlag.equals("SelfCheck"))
			{
				if(mDatas_Sc.size()<pangSize)
				{
					mlistview.hindLoadView(false);
				}
				mlistview.setAdapter(mAdapter_Sc);
			}
			else
			{
				if(mDatas_Ssc.size()<pangSize)
				{
					mlistview.hindLoadView(false);
				}
				mlistview.setAdapter(mAdapter_Ssc);
			}
		}
		
	};

	
	private void initSelfCheckDate() {
		// TODO Auto-generated method stub
		SqlOperate<SelfCheck> opetaterSelfCheck=new SqlOperate<SelfCheck>(this, SelfCheck.class);
		mDatas_Sc=opetaterSelfCheck.SelectOffsetEntitysBySqlCondition(mSqlStr, pangSize, pageCount);
        pageCount=1;
        opetaterSelfCheck.close();
		
		mAdapter_Sc= new SelfCheckAdapter(this, mDatas_Sc, R.layout.hidden_check_item,mlistview.getRightViewWidth());
        
        mlistview.setIClickLoadListListener(new IClickLoadListListener() {
			
			@Override
			public void onLoad(Handler handler) {
				// TODO Auto-generated method stub
				SqlOperate<SelfCheck> opetaterSelfCheck=new SqlOperate<SelfCheck>(HiddenTabListDatilActivity.this, SelfCheck.class);
				final List<SelfCheck> data=opetaterSelfCheck.SelectOffsetEntitysBySqlCondition(mSqlStr, pangSize, pageCount);
		        pageCount++;
		        opetaterSelfCheck.close();
				
				if(data!=null&&data.size()>0)
		        {
					handler.post(new Runnable() {
						public void run() {
							mDatas_Sc.addAll(data);
							mAdapter_Sc.notifyDataSetChanged();
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
					SelfCheck sc=mDatas_Sc.get(arg2);
					if(sc!=null)
					{
						Bundle bundle = new Bundle(); 
						bundle.putSerializable("SelfCheck", sc); 
						Intent intent=new Intent(HiddenTabListDatilActivity.this, HiddenSelfCheckIngActivity.class);
						intent.putExtras(bundle);
						startActivity(intent);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
				}
			}
		});
        
        mAdapter_Sc.setOnRightItemClickListener(new SelfCheckAdapter.onRightItemClickListener() {
			
			@Override
			public void onRightItemClick(View v, SelfCheck t) {
				// TODO Auto-generated method stub
				if(t.getSc_state().equals("ING"))
				{
					Bundle bundle = new Bundle(); 
					bundle.putSerializable("SelfCheck", t); 
					Intent intent=new Intent(HiddenTabListDatilActivity.this, HiddenSelfCheckSuperviseActivity.class);
		       		intent.putExtras(bundle);
		       		startActivity(intent);
				}
				else
				{
					Toast.makeText(HiddenTabListDatilActivity.this, "当前状态不能进行督促", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}


	private void initSelfStandCheckDate() {
		// TODO Auto-generated method stub
		SqlOperate<SelfStandCheck> opetaterSelfStandCheck=new SqlOperate<SelfStandCheck>(this, SelfStandCheck.class);
		mDatas_Ssc=opetaterSelfStandCheck.SelectOffsetEntitysBySqlCondition(mSqlStr, pangSize, pageCount);
        pageCount=1;
        opetaterSelfStandCheck.close();
		
		mAdapter_Ssc= new SelfStandCheckAdapter(this, mDatas_Ssc, R.layout.hidden_check_item,mlistview.getRightViewWidth());
        
        mlistview.setIClickLoadListListener(new IClickLoadListListener() {
			
			@Override
			public void onLoad(Handler handler) {
				// TODO Auto-generated method stub
				SqlOperate<SelfStandCheck> opetaterSelfStandCheck=new SqlOperate<SelfStandCheck>(HiddenTabListDatilActivity.this, SelfStandCheck.class);
				final List<SelfStandCheck> data=opetaterSelfStandCheck.SelectOffsetEntitysBySqlCondition(mSqlStr, pangSize, pageCount);
		        pageCount++;
		        opetaterSelfStandCheck.close();
				
		        if(data!=null&&data.size()>0)
		        {
					handler.post(new Runnable() {
						public void run() {
							mDatas_Ssc.addAll(data);
							mAdapter_Ssc.notifyDataSetChanged();
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
				SelfStandCheck sc=mDatas_Ssc.get(arg2);
				if(sc!=null)
				{
					Bundle bundle = new Bundle(); 
					bundle.putSerializable("SelfStandCheck", sc); 
					Intent intent=new Intent(HiddenTabListDatilActivity.this, HiddenStandCheckComShowBaseActivity.class);
		       		intent.putExtras(bundle);
		       		startActivity(intent);
				}
			}
		});
        
        mAdapter_Ssc.setOnRightItemClickListener(new SelfStandCheckAdapter.onRightItemClickListener() {
			
			@Override
			public void onRightItemClick(View v, SelfStandCheck t) {
				// TODO Auto-generated method stub
				if(t.getSc_state().equals("noyet")||t.getSc_state().equals("ing"))
				{
					Bundle bundle = new Bundle(); 
					bundle.putSerializable("SelfStandCheck", t); 
					Intent intent=new Intent(HiddenTabListDatilActivity.this, HiddenStandCheckComSuperviseActivity.class);
		       		intent.putExtras(bundle);
		       		startActivity(intent);
				}
				else
				{
					Toast.makeText(HiddenTabListDatilActivity.this, "当前状态不能进行督促", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	private void initComponent() {
		// TODO Auto-generated method stub
		mlistview=(SwipeLoadListView) findViewById(R.id.listView_hidden_sscheck);
	}
	
}





