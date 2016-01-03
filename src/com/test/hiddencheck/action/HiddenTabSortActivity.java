package com.sunnyit.hiddencheck.action;

import com.sunnyit.R;
import com.sunnyit.common.activity.ActivityCollector;
import com.sunnyit.common.async.HiddenDataOfUserGetAsync;
import com.sunnyit.common.async.HiddenDataOfUserGetAsync.EndOperateListener;
import com.sunnyit.common.view.SearchInfoView;
import com.sunnyit.common.view.SearchInfoView.SearchTextListener;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.company.action.CompanySearch;

import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

/**   
* @Title: HiddenList.java 
* @Package com.sunnyit.hiddencheck.action 
* @Description: TODO
* @author yk
* @date 2015��9��7�� ����3:27:12 
* @version V1.0   
*/
public class HiddenTabSortActivity extends TabActivity {

	
	private TopBarView topbar_hidden_check;
	private String mOneMonthStr="select e_Id,e_companyName from SimpleEnterprise "+
	" where e_Id not in "+
		"(select sc_companyId from SelfCheck "
		+ " where sc_checkTime>date('now', '-1 month') UNION "+
		" select sc_companyId from SelfStandCheck "
		+ " where sc_checkTime>date('now', '-1 month') )";
	
	private String mRectificationStr="select e_Id,e_companyName from SimpleEnterprise "+
			" where e_Id in "+
				"(select sc_companyId from SelfCheck"
				+ " where sc_deadline<date('now') and sc_state='ING' UNION "+
				" select sc_companyId from SelfStandCheck "
				+ "where sc_deadline<date('now') and (sc_state='ing' or sc_state='noyet' ))";

	private String mThreeMonthStr="select e_Id,e_companyName from SimpleEnterprise "+
			" where e_Id not in "+
				"(select sc_companyId from SelfCheck"
				+ " where sc_checkTime>date('now', '-3 month') UNION "+
				" select sc_companyId from SelfStandCheck "
				+ "where sc_checkTime>date('now', '-3 month'))";
	
	private TabHost tabHost;
	private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.hidden_tab_check);
		ActivityCollector.addActivity(this);
		
		sp = this.getSharedPreferences("SP", MODE_PRIVATE);
		
		initComponent();
		/*
		HiddenDatilSort OneMonthNoReport=new HiddenDatilSort(this,mOneMonthStr);
		HiddenDatilSort totRectification=new HiddenDatilSort(this,mRectificationStr);
		HiddenDatilSort threeMonthNoReport=new HiddenDatilSort(this,mThreeMonthStr);
		List<Fragment> datas=new ArrayList<Fragment>();
		datas.add(OneMonthNoReport);
		datas.add(threeMonthNoReport);
		datas.add(totRectification);
		
		FragmentManager mFragmentManager= getSupportFragmentManager();
		new PageViewTab(this, mFragmentManager, 
				mViewPager, datas, 
				new String[]{"����δ�ϱ�","����3��0����","����δ����"});*/
		
		topbar_hidden_check.setTopBarClick(new ITopBarClick() {
			
			@Override
			public void onClickRightBut() {
				// TODO Auto-generated method stub
				/*Intent intent=new Intent(HiddenTabSortActivity.this, HiddenTabListActivity.class);
	       		startActivity(intent);*/
				SynchronousHiddenCheckdata();
				/*Intent intent=new Intent(HiddenTabSortActivity.this, HiddenTabSortActivity.class);
	       		startActivity(intent);
	       		finish();*/
			}
			
			@Override
			public void onClickLeftBut() {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		//topbar_hidden_check.setRightButText("ȫ��");
		topbar_hidden_check.setRightButText("ͬ��");
		
        tabHost=this.getTabHost();
        TabHost.TabSpec spec;
        Intent intent;
        
        intent=new Intent().setClass(this, HiddenTabSortDatilActivity.class);
        intent.putExtra("sql", mOneMonthStr);
        spec=tabHost.newTabSpec("����δ�ϱ�").setIndicator("����δ�ϱ�").setContent(intent);
        tabHost.addTab(spec);

        intent=new Intent().setClass(this, HiddenTabSortDatilActivity.class);
        intent.putExtra("sql", mThreeMonthStr);
        spec=tabHost.newTabSpec("����3��0����").setIndicator("����3��0����").setContent(intent);
        tabHost.addTab(spec);
        
        intent=new Intent().setClass(this,HiddenTabSortDatilActivity.class);
        intent.putExtra("sql", mRectificationStr);
        spec=tabHost.newTabSpec("����δ����").setIndicator("����δ����").setContent(intent);
        tabHost.addTab(spec);
        
        tabHost.setCurrentTab(0);
        
        RadioGroup radioGroup=(RadioGroup) this.findViewById(R.id.hidden_tab_group);
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() 
        {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) 
			{
				switch (checkedId) 
				{
					case R.id.rt_OneMonthNoReport:
						tabHost.setCurrentTabByTag("����δ�ϱ�");
						break;
					case R.id.rt_threeMonthNoReport:
						tabHost.setCurrentTabByTag("����3��0����");
						break;
					case R.id.rt_toRectification:
						tabHost.setCurrentTabByTag("����δ����");
						break;
					default:
						break;
				}
			}
		});
        
	}
	
	private void SynchronousHiddenCheckdata() {
		// TODO Auto-generated method stub
		HiddenDataOfUserGetAsync hiddenDataOfUserGetAsync=new HiddenDataOfUserGetAsync(HiddenTabSortActivity.this);
		String SelfCheckUrl=getBaseUrl()+"/appWebGet/getAllSelfCheck";
		String SelfStandCheckUrl=getBaseUrl()+"/appWebGet/getAllSelfStandCheck";
		String StandcheckDetailUrl=getBaseUrl()+"/appWebGet/getAllStandCheckDetail";
		hiddenDataOfUserGetAsync.execute(SelfCheckUrl,SelfStandCheckUrl,StandcheckDetailUrl);
		hiddenDataOfUserGetAsync.setEndOperateListener(new EndOperateListener() {
			
			@Override
			public void setEndOperate() {
				// TODO Auto-generated method stub
				Intent intent=new Intent(HiddenTabSortActivity.this, HiddenTabSortActivity.class);
	       		startActivity(intent);
	       		finish();
			}
		});
	}
	
	protected String getBaseUrl() {
		String url=sp.getString("url", null);
		if(url!=null)
		{
			url="http://"+url;
		}
		return url;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch(resultCode){
        case 1:
        	CompanySearch.et_search_localName.setText(data.getStringExtra("localname"));
        	break;
        case 2:
        	CompanySearch.et_search_departmentName.setText(data.getStringExtra("depname"));
        	break;
        case 3:
        	break;
     }
	}

	private void initComponent() {
		// TODO Auto-generated method stub
		topbar_hidden_check=(TopBarView) findViewById(R.id.topbar_hidden_tab_check);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		ActivityCollector.removeActivity(this);
	}
	

}


