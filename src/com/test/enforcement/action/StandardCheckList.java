package com.sunnyit.enforcement.action;

import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.ClickLoadListview;
import com.sunnyit.common.view.ClickLoadListview.IClickLoadListListener;
import com.sunnyit.common.view.CustomFAB;
import com.sunnyit.common.view.SearchInfoView;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.enforcement.data.Standard_CK_TableShowAdapter;
import com.sunnyit.enforcement.model.Standard_CK_Table;

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
public class StandardCheckList extends Fragment {
	
	private TopBarView  topbar_com_name;
	private CustomFAB company_hidden_search;
	
	private View mView;
	
	private ClickLoadListview mlistview;
	private Context mContext;
	
	private int pangSize=10;
	private int pageCount=0;
	
	private List<Standard_CK_Table> mDatas;
	private Standard_CK_TableShowAdapter mAdapter;
	
	private String mSqlStr="";
	
	private SearchInfoView search_hidden_company_name;
	
	public StandardCheckList(Context context,String companyName) {
		// TODO Auto-generated constructor stub
		this.mContext=context;
		
		mSqlStr=" where companyName='"+companyName+"' order by ck_time desc ";
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mView=inflater.inflate(R.layout.hidden_check_list, container, false);
		initComponent();
		
		pageCount=0;
		SqlOperate<Standard_CK_Table> opetaterStandard_CK_Table=new SqlOperate<Standard_CK_Table>(mContext, Standard_CK_Table.class);
		mDatas=opetaterStandard_CK_Table.SelectOffsetEntitysByCondition(mSqlStr, pangSize, pageCount);
        pageCount=1;
        opetaterStandard_CK_Table.close();
		mAdapter= new Standard_CK_TableShowAdapter(mContext, mDatas, R.layout.en_standcheck_item);
		if(mDatas.size()<pangSize)
		{
			mlistview.hindLoadView(false);
		}
		mlistview.setAdapter(mAdapter);
		initCheckDate();
		
		return mView;
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
	
	private void initCheckDate() {
		
        mlistview.setIClickLoadListListener(new IClickLoadListListener() {
			
			@Override
			public void onLoad(Handler handler) {
				// TODO Auto-generated method stub
				SqlOperate<Standard_CK_Table> opetaterStandard_CK_TableMore=new SqlOperate<Standard_CK_Table>(mContext, Standard_CK_Table.class);
				final List<Standard_CK_Table> data=opetaterStandard_CK_TableMore.SelectOffsetEntitysByCondition(mSqlStr, pangSize, pageCount);
		        pageCount++;
		        opetaterStandard_CK_TableMore.close();
				
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
					if(data.size()<pangSize)
					{
						mlistview.hindLoadView(true);
					}
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
					lookStandCheck(arg2);
				} catch (Exception e) {
					// TODO Auto-generated catch block
				}
			}

		});
        
	}
	
	private void lookStandCheck(final int arg2) {
		Bundle bundle=new Bundle();
		bundle.putSerializable("Standard_CK_Table", mDatas.get(arg2));
		Intent intentlook=new Intent(mContext, HiddenStandCheckShowBaseActivity.class);
		intentlook.putExtras(bundle);
		startActivity(intentlook);
	}  
	
}


