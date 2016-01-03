package com.sunnyit.enforcement.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.dialog.CustomDialog;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.ClickLoadListview;
import com.sunnyit.common.view.ClickLoadListview.IClickLoadListListener;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.company.model.SimpleEnterprise;
import com.sunnyit.enforcement.data.SimpleEnterpriseNameAdapter;
import com.sunnyit.enforcement.model.DailyCheck;
import com.sunnyit.enforcement.model.SpecialCheck;
import com.sunnyit.enforcement.model.Standard_CK_Table;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**   
* @Title: WelcomeActivity.java 
* @Package com.sunnyit.system.action 
* @Description: TODO
* @author yk
* @date 2015年8月1日 上午9:47:36 
* @version V1.0   
*/
public class HiddenMounthActivity extends BaseActivity  {
	
	//private GridView gridview_company_industry;
	private ClickLoadListview mlistview;
	private TopBarView  topbar_hidden_company_mounth;
	
	private HashSet<String> mDatas_name = new HashSet<String>();
	
	private List<SimpleEnterprise> mDatas=new ArrayList<SimpleEnterprise>();
	private List<SimpleEnterprise> mAllDatas=new ArrayList<SimpleEnterprise>();
	private SimpleEnterpriseNameAdapter mAdapter;
	
	private int pangSize=10;
	private int pageCount=0;
	
	String year_str;
	String mounth_str;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hidden_mounth_company);
		
		Intent intent=getIntent();
		
		String father_str=intent.getStringExtra("father_str");
		String child_str=intent.getStringExtra("child_str");
		
		year_str=father_str.substring(0, 4);
		mounth_str=child_str.substring(0, 2);
		
		initComponent();
		
		topbar_hidden_company_mounth.setRightButVisibility(View.GONE);
		topbar_hidden_company_mounth.setTitle(father_str+child_str);
		topbar_hidden_company_mounth.setTopBarClick(new ITopBarClick() {
			
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
		
		final CustomDialog cusdialog=new CustomDialog(HiddenMounthActivity.this);
		cusdialog.setViewAndAlpha(R.layout.progressbar_rw_blue,0);
		cusdialog.setOutCancel(false);
		cusdialog.show();
		
		new Thread(new Runnable(){  
            
            public void run(){

            	String dailyCheckSqlStr=" where ck_time like '"+year_str+"-"+mounth_str+"%' ";
            	SqlOperate<DailyCheck> opetaterDailyCheck=new SqlOperate<DailyCheck>(HiddenMounthActivity.this, DailyCheck.class);
        		List<DailyCheck> ls_DailyCheck=opetaterDailyCheck.SelectEntitysByCondition(dailyCheckSqlStr);
        		opetaterDailyCheck.close();
        		
        		String specialCheckSqlStr=" where ck_time like '"+year_str+"-"+mounth_str+"%' ";
            	SqlOperate<SpecialCheck> opetaterSpecialCheck=new SqlOperate<SpecialCheck>(HiddenMounthActivity.this, SpecialCheck.class);
        		List<SpecialCheck> ls_SpecialCheck=opetaterSpecialCheck.SelectEntitysByCondition(specialCheckSqlStr);
        		opetaterSpecialCheck.close();
        		
        		String Standard_CK_TableSqlStr=" where ck_time like '"+year_str+"-"+mounth_str+"%' ";
            	SqlOperate<Standard_CK_Table> opetaterStandard_CK_Table=new SqlOperate<Standard_CK_Table>(HiddenMounthActivity.this, Standard_CK_Table.class);
        		List<Standard_CK_Table> ls_Standard_CK_Table=opetaterStandard_CK_Table.SelectEntitysByCondition(Standard_CK_TableSqlStr);
        		opetaterStandard_CK_Table.close();
        		
        		for(DailyCheck dailyCheck:ls_DailyCheck)
        		{
        			mDatas_name.add(dailyCheck.getCk_checkeddepartment());
        		}
        		
        		for(SpecialCheck specialCheck:ls_SpecialCheck)
        		{
        			mDatas_name.add(specialCheck.getCk_checkeddepartment());
        		}
        		
        		for(Standard_CK_Table standard_CK_Table:ls_Standard_CK_Table)
        		{
        			mDatas_name.add(standard_CK_Table.getCompanyName());
        		}
        		
        		for(String com_name:mDatas_name)
            	{
            		SimpleEnterprise simp=new SimpleEnterprise();
            		simp.setE_companyName(com_name);
            		mAllDatas.add(simp);
            	}
        		
        		initShowDate();
        		mAdapter= new SimpleEnterpriseNameAdapter(HiddenMounthActivity.this, mDatas, R.layout.company_industry_item);
        		
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
			if(msg.what==0)
			{
				if(mDatas.size()<pangSize)
        		{
        			mlistview.hindLoadView(false);
        		}
				mlistview.setAdapter(mAdapter);
				initlistViewOperate();
			}
		}
		
	};
	
	private boolean initShowDate() {
		// TODO Auto-generated method stub
		List<SimpleEnterprise> mDatasTemp=new ArrayList<SimpleEnterprise>();
		for(int i=pageCount*pangSize;
				(i<pageCount*pangSize+pangSize)&&(i<mAllDatas.size());i++)
		{
			if(mAllDatas.get(i)!=null)
			{
				mDatasTemp.add(mAllDatas.get(i));
			}
		}
		mDatas.addAll(mDatasTemp);
		if(mAllDatas.size()<(pageCount*pangSize+pangSize))
		{
			pageCount++;
			return false;
		}
		else
		{
			pageCount++;
			return true;
		}
	}  
	
	private void initComponent()
	{
		mlistview=(ClickLoadListview) findViewById(R.id.listview_hidden_company_mounth);
		topbar_hidden_company_mounth=(TopBarView) findViewById(R.id.topbar_hidden_company_mounth);
    }
	
	private void initlistViewOperate() {
		
		mlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				try {
					Intent intent=new Intent(HiddenMounthActivity.this, HiddenGovernListActivity.class);
					intent.putExtra("companyName", mDatas.get(arg2).getE_companyName());
					startActivity(intent);
				} catch (Exception e) {
					// TODO Auto-generated catch block
				}
			}
		});
		
		mlistview.setIClickLoadListListener(new IClickLoadListListener() {
			
			@Override
			public void onLoad(Handler handler) {
				// TODO Auto-generated method stub
				handler.post(new Runnable() {
					public void run() {
						boolean isHideFoot=initShowDate();
						mAdapter.notifyDataSetChanged();
						if(!isHideFoot)
						{
							mlistview.hindLoadView(true);
						}
					}
				});
			}
		});
		
	}
	
}


