package com.sunnyit.hiddencheck.action;

import com.sunnyit.R;
import com.sunnyit.common.activity.ActivityCollector;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.company.action.CompanySearch;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
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
public class HiddenTabListActivity extends TabActivity {

	
	private TopBarView topbar_hidden_check;
	private TabHost tabHost;
	private RadioButton rt_OneMonthNoReport;
	private RadioButton rt_threeMonthNoReport;
	
	private String mSelfCheckStr="";
			
	private String mSelfStandCheckStr="";

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.hidden_tab_checklist);
		ActivityCollector.addActivity(this);
		
		String sc_companyId=getIntent().getStringExtra("e_Id");
		
		if(sc_companyId==null)
		{
			mSelfCheckStr="select * from SelfCheck";
			mSelfStandCheckStr="select * from SelfStandCheck";
		}
		else
		{
			mSelfCheckStr="select * from SelfCheck where sc_companyId='"+sc_companyId+"'";
			mSelfStandCheckStr="select * from SelfStandCheck where sc_companyId='"+sc_companyId+"'";
		}
		
		initComponent();
		
		topbar_hidden_check.setTitle("������Ϣ�б�");
		topbar_hidden_check.setTopBarClick(new ITopBarClick() {
			
			@Override
			public void onClickRightBut() {
				// TODO Auto-generated method stub
				Intent	intent=new Intent(HiddenTabListActivity.this, HiddenCheckAddChoiceActivity.class);
	       		startActivity(intent);
			}
			
			@Override
			public void onClickLeftBut() {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		topbar_hidden_check.setRightButText("���");
		topbar_hidden_check.setRightButVisibility(View.GONE);
		
        tabHost=this.getTabHost();
        TabHost.TabSpec spec;
        Intent intent;
        
        intent=new Intent().setClass(this, HiddenTabListDatilActivity.class);
        intent.putExtra("SqlStr", mSelfCheckStr);
        intent.putExtra("aFlag", "SelfCheck");
        spec=tabHost.newTabSpec("�ճ����").setIndicator("�ճ����").setContent(intent);
        tabHost.addTab(spec);

        intent=new Intent().setClass(this, HiddenTabListDatilActivity.class);
        intent.putExtra("SqlStr", mSelfStandCheckStr);
        intent.putExtra("aFlag", "SelfStandCheck");
        spec=tabHost.newTabSpec("���ձ�׼�Ų�").setIndicator("���ձ�׼�Ų�").setContent(intent);
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
					case R.id.rt_daily_check:
						tabHost.setCurrentTabByTag("�ճ����");
						break;
					case R.id.rt_stand_check:
						tabHost.setCurrentTabByTag("���ձ�׼�Ų�");
						break;
					default:
						break;
				}
			}
		});
        
	}
	
	private void initComponent() {
		// TODO Auto-generated method stub
		topbar_hidden_check=(TopBarView) findViewById(R.id.topbar_hidden_tab_checklist);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		ActivityCollector.removeActivity(this);
	}
	

}





