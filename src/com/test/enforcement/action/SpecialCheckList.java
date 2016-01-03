package com.sunnyit.enforcement.action;

import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.ClickLoadListview;
import com.sunnyit.common.view.ClickLoadListview.IClickLoadListListener;
import com.sunnyit.common.view.CustomFAB;
import com.sunnyit.common.view.SearchInfoView;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.enforcement.data.SpecialCheckShowAdapter;
import com.sunnyit.enforcement.model.SpecialCheck;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
public class SpecialCheckList extends Fragment {
	
	private TopBarView  topbar_com_name;
	private CustomFAB company_hidden_search;
	
	private View mView;
	
	private ClickLoadListview mlistview;
	private Context mContext;
	
	private int pangSize=10;
	private int pageCount=0;
	
	private List<SpecialCheck> mDatas;
	private SpecialCheckShowAdapter mAdapter;
	
	private SearchInfoView search_hidden_company_name;
	
	private String mSqlStr=" select * from SpecialCheck ";
	
	public SpecialCheckList(Context context,String companyName) {
		// TODO Auto-generated constructor stub
		this.mContext=context;
		
		mSqlStr=mSqlStr+" where ck_checkeddepartment='"+companyName+"' order by ck_time desc ";
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mView=inflater.inflate(R.layout.hidden_check_list, container, false);
		
		initComponent();
		
		pageCount=0;
		SqlOperate<SpecialCheck> opetaterSpecialCheck=new SqlOperate<SpecialCheck>(mContext, SpecialCheck.class);
		mDatas=opetaterSpecialCheck.SelectOffsetEntitysBySqlCondition(mSqlStr, pangSize, pageCount);
        pageCount=1;
        opetaterSpecialCheck.close();
		mAdapter= new SpecialCheckShowAdapter(mContext, mDatas, R.layout.en_specialcheck_item);
		if(mDatas.size()<pangSize)
		{
			mlistview.hindLoadView(false);
		}
		mlistview.setAdapter(mAdapter);
		initCheckDate();
		
		return mView;
	}
	
	private void initCheckDate() {
        
        mlistview.setIClickLoadListListener(new IClickLoadListListener() {
			
			@Override
			public void onLoad(Handler handler) {
				// TODO Auto-generated method stub
				SqlOperate<SpecialCheck> opetaterSpecialCheck=new SqlOperate<SpecialCheck>(mContext, SpecialCheck.class);
				final List<SpecialCheck> data=opetaterSpecialCheck.SelectOffsetEntitysBySqlCondition(mSqlStr, pangSize, pageCount);
		        pageCount++;
		        opetaterSpecialCheck.close();
				
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
				try {
					lookSpecialCheck(arg2);
				} catch (Exception e) {
					// TODO Auto-generated catch block
				}
			}
		});
        
	}
	
	private void lookSpecialCheck(final int arg2) {
		Bundle bundle=new Bundle();
		bundle.putSerializable("SpecialCheck", mDatas.get(arg2));
		Intent intent=new Intent(mContext,HiddenSpecialCheckShowActivity.class);
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


