package com.sunnyit.enforcement.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.dialog.CustomDialog;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.ClickLoadListview;
import com.sunnyit.common.view.ClickLoadListview.IClickLoadListListener;
import com.sunnyit.common.view.CustomFAB;
import com.sunnyit.common.view.SearchInfoView;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.enforcement.data.DailyCheckShowAdapter;
import com.sunnyit.enforcement.model.DailyCheck;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**   
* @Title: DailyCheckList.java 
* @Package com.sunnyit.hiddencheck.action 
* @Description: TODO
* @author yk
* @date 2015年9月7日 下午3:20:13 
* @version V1.0   
*/
public class DailyCheckList extends Fragment {

	private TopBarView  topbar_com_name;
	private CustomFAB company_hidden_search;
	
	private View mView;
	
	private ClickLoadListview mlistview;
	private Context mContext;
	
	private int pangSize=10;
	private int pageCount=0;
	
	private List<DailyCheck> mDatas;
	private DailyCheckShowAdapter mAdapter;
	
	private boolean isFirst=false;
	
	private SearchInfoView search_hidden_company_name;
	
	private String mSqlStr=" select * from DailyCheck ";
	
	public DailyCheckList(Context context,String companyName) {
		// TODO Auto-generated constructor stub
		this.mContext=context;
		
		mSqlStr=mSqlStr+" where ck_checkeddepartment='"+companyName+"' order by ck_time desc ";
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mView=inflater.inflate(R.layout.hidden_check_list, container, false);
		
		initComponent();
		
		if(!isFirst)
		{
			final CustomDialog cusdialog=new CustomDialog(mContext);
			cusdialog.setViewAndAlpha(R.layout.progressbar_rw_blue,0);
			cusdialog.setOutCancel(false);
			cusdialog.show();
			
			new Thread(new Runnable(){  
	            
	            public void run(){

	            	SqlOperate<DailyCheck> opetaterDailyCheck=new SqlOperate<DailyCheck>(mContext, DailyCheck.class);
	        		mDatas=opetaterDailyCheck.SelectOffsetEntitysBySqlCondition(mSqlStr, pangSize, pageCount);
	                pageCount=1;
	                opetaterDailyCheck.close();
	        		mAdapter= new DailyCheckShowAdapter(mContext, mDatas, R.layout.en_dailycheck_item);
	                
	        		try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	        		
	        		Message msg=Message.obtain();
	        		msg.what=0;
	        		handler.sendMessage(msg);
	        		
	        		cusdialog.dismiss();
	        		
	        		isFirst=true;
	        		
	            }  
	        }).start();
		}
		else
		{
			pageCount=0;
			SqlOperate<DailyCheck> opetaterDailyCheck=new SqlOperate<DailyCheck>(mContext, DailyCheck.class);
    		mDatas=opetaterDailyCheck.SelectOffsetEntitysBySqlCondition(mSqlStr, pangSize, pageCount);
            pageCount=1;
            opetaterDailyCheck.close();
    		mAdapter= new DailyCheckShowAdapter(mContext, mDatas, R.layout.en_dailycheck_item);
    		if(mDatas.size()<pangSize)
			{
				mlistview.hindLoadView(false);
			}
			mlistview.setAdapter(mAdapter);
			initSelfCheckDate();
		}
		
		
		return mView;
	}
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.what==0)
			{
				if(mDatas.size()<pangSize)
				{
					mlistview.hindLoadView(false);
				}
				mlistview.setAdapter(mAdapter);
				initSelfCheckDate();
			}
		}
		
	};
	
	private void initSelfCheckDate() {
        
        mlistview.setIClickLoadListListener(new IClickLoadListListener() {
			
			@Override
			public void onLoad(Handler handler) {
				// TODO Auto-generated method stub
				SqlOperate<DailyCheck> opetaterDailyCheck=new SqlOperate<DailyCheck>(mContext, DailyCheck.class);
				final List<DailyCheck> data=opetaterDailyCheck.SelectOffsetEntitysBySqlCondition(mSqlStr, pangSize, pageCount);
		        pageCount++;
		        opetaterDailyCheck.close();
				
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
        
        mlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
				// TODO Auto-generated method stub
				try {
					lookDailyCheck(arg2);
				} catch (Exception e) {
					// TODO Auto-generated catch block
				}
			}

		});
	}
	
	private void lookDailyCheck(final int arg2) {
		Bundle bundle=new Bundle();
		bundle.putSerializable("DailyCheck", mDatas.get(arg2));
		Intent intent=new Intent(mContext,HiddenDailyCheckShowActivity.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}  
	
	private void initComponent() {
		// TODO Auto-generated method stub
		mlistview=(ClickLoadListview) mView.findViewById(R.id.ListView_hidden_check);
		topbar_com_name=(TopBarView) mView.findViewById(R.id.topbar_com_name);
		company_hidden_search=(CustomFAB) mView.findViewById(R.id.company_hidden_search);
		topbar_com_name.setVisibility(View.GONE);
		company_hidden_search.setVisibility(View.GONE);
		search_hidden_company_name=(SearchInfoView) mView.findViewById(R.id.search_hidden_company_name);
		search_hidden_company_name.setVisibility(View.GONE);
	}
	
}


