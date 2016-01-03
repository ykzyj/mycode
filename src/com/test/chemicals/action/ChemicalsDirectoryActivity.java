package com.sunnyit.chemicals.action;

import java.util.List;

import com.sunnyit.R;
import com.sunnyit.chemicals.data.ChemicalsDirectoryAdapter;
import com.sunnyit.chemicals.model.Chemicals;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.dialog.CustomDialog;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.ClickLoadListview;
import com.sunnyit.common.view.ClickLoadListview.IClickLoadListListener;
import com.sunnyit.common.view.CustomFAB;
import com.sunnyit.common.view.SearchInfoView;
import com.sunnyit.common.view.SearchInfoView.SearchButListener;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

/**   
* @Title: ChemicalsDirectoryActivity.java 
* @Package com.sunnyit.chemicals.action 
* @Description: TODO
* @author yk
* @date 2015年11月10日 下午2:22:11 
* @version V1.0   
*/
public class ChemicalsDirectoryActivity extends BaseActivity {
	
	private ClickLoadListview mlistview;
	private SearchInfoView search_chemical_name;
	
	private int pangSize=10;
	private int pageCount=0;
	
	private CustomFAB Chemicals_directory_search;
	private TopBarView topbar_com_name;
	
	private List<Chemicals> mDatas;
	private ChemicalsDirectoryAdapter mAdapter;
	
	private CustomDialog cusdialog;
	
	private String mSqlStr=" select * from Chemicals order by  cast(c_no as int) ";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chemicals_directory_list);
		
		initComponent();
		topbar_com_name.setTopBarClick(new ITopBarClick() {
			
			@Override
			public void onClickRightBut() {
				mlistview.showLoadView();
				
				cusdialog=new CustomDialog(ChemicalsDirectoryActivity.this);
				cusdialog.setViewAndAlpha(R.layout.progressbar_rw_blue,0);
				cusdialog.setOutCancel(false);
				cusdialog.show();
				
				new Thread(new Runnable() {
					public void run() {
						
						mSqlStr=" select * from Chemicals order by  cast(c_no as int) ";
						pageCount=0;
						SqlOperate<Chemicals> opetaterChemicals=new SqlOperate<Chemicals>(ChemicalsDirectoryActivity.this, Chemicals.class);
						mDatas=opetaterChemicals.SelectOffsetEntitysBySqlCondition(mSqlStr, pangSize, pageCount);
				        pageCount=1;
				        opetaterChemicals.close();
				        
				        mAdapter= new ChemicalsDirectoryAdapter(ChemicalsDirectoryActivity.this, mDatas, R.layout.chemical_directory_item);
		        		
		        		Message msg=Message.obtain();
		        		msg.what=0;
		        		handler.sendMessage(msg);
		        		cusdialog.dismiss();
					}
				}).start();
			}
			
			@Override
			public void onClickLeftBut() {
				// TODO Auto-generated method stub
				finish();
			}
		});
		topbar_com_name.setTitle("危险化学品目录");
		
		cusdialog=new CustomDialog(ChemicalsDirectoryActivity.this);
		cusdialog.setViewAndAlpha(R.layout.progressbar_rw_blue,0);
		cusdialog.setOutCancel(false);
		cusdialog.show();
		
		new Thread(new Runnable() {
			public void run() {
				SqlOperate<Chemicals> opetaterChemicals=new SqlOperate<Chemicals>(ChemicalsDirectoryActivity.this, Chemicals.class);
				mDatas=opetaterChemicals.SelectOffsetEntitysBySqlCondition(mSqlStr, pangSize, pageCount);
		        pageCount=1;
		        opetaterChemicals.close();
		        
		        mAdapter= new ChemicalsDirectoryAdapter(ChemicalsDirectoryActivity.this, mDatas, R.layout.chemical_directory_item);
        		
        		Message msg=Message.obtain();
        		msg.what=0;
        		handler.sendMessage(msg);
        		cusdialog.dismiss();
			}
		}).start();
		
		search_chemical_name.setSearchButListener(new SearchButListener(){
			@Override
			public void setOnSearchButClick(String content) {
				
				mlistview.showLoadView();
				
				mSqlStr=" select * from Chemicals where c_name like '%"+content+"%' or c_aliasname like '%"+content+"%' ";
				final int tempCount=pageCount;
				pageCount=0;
				
				cusdialog=new CustomDialog(ChemicalsDirectoryActivity.this);
				cusdialog.setViewAndAlpha(R.layout.progressbar_rw_blue,0);
				cusdialog.setOutCancel(false);
				cusdialog.show();
				
				new Thread(new Runnable() {
					public void run() {
						SqlOperate<Chemicals> opetaterChemicals=new SqlOperate<Chemicals>(ChemicalsDirectoryActivity.this, Chemicals.class);
						List<Chemicals> datas=opetaterChemicals.SelectOffsetEntitysBySqlCondition(mSqlStr, pangSize, pageCount);
				        opetaterChemicals.close();
				        
				        if(datas!=null&&datas.size()>0)
				        {
				        	mDatas=datas;
				        	pageCount=1;
				        	mAdapter= new ChemicalsDirectoryAdapter(ChemicalsDirectoryActivity.this, mDatas, R.layout.chemical_directory_item);
				        	Message msg=Message.obtain();
			        		msg.what=0;
			        		handler.sendMessage(msg);
				        }
				        else
				        {
				        	pageCount=tempCount;
				        	Message msg=Message.obtain();
			        		msg.what=1;
			        		handler.sendMessage(msg);
				        }
				        cusdialog.dismiss();
					}
				}).start();
			}
		});
		
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
				initDate();
			}
			else if(msg.what==1)
			{
				Toast.makeText(ChemicalsDirectoryActivity.this, "搜索结果为空", Toast.LENGTH_SHORT).show();
			}
		}
		
	};
	
	private void initDate() {
		// TODO Auto-generated method stub
		
        mlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				try {
					Chemicals chemicals=mDatas.get(arg2);
					if(chemicals!=null)
					{
						Bundle bundle = new Bundle(); 
						bundle.putSerializable("Chemicals", chemicals); 
						Intent intent=new Intent(ChemicalsDirectoryActivity.this, ChemicalsDatilShowActivity.class);
			       		intent.putExtras(bundle);
			       		startActivity(intent);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
		});
        
        mlistview.setIClickLoadListListener(new IClickLoadListListener() {
			
			@Override
			public void onLoad(Handler handler) {
				SqlOperate<Chemicals> opetaterChemicals=new SqlOperate<Chemicals>(ChemicalsDirectoryActivity.this, Chemicals.class);
				final List<Chemicals> data=opetaterChemicals.SelectOffsetEntitysBySqlCondition(mSqlStr, pangSize, pageCount);
				pageCount++;
		        opetaterChemicals.close();
				
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
	
	private void initComponent() {
		// TODO Auto-generated method stub
		mlistview=(ClickLoadListview) findViewById(R.id.ListView_hidden_check);
		Chemicals_directory_search=(CustomFAB) findViewById(R.id.company_hidden_search);
		Chemicals_directory_search.setVisibility(View.GONE);
		topbar_com_name=(TopBarView) findViewById(R.id.topbar_com_name);
		search_chemical_name=(SearchInfoView) findViewById(R.id.search_chemical_name);
	}
	
}


