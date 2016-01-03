package com.sunnyit.hiddencheck.action;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.company.model.SimpleEnterprise;
import com.sunnyit.hiddencheck.model.SelfCheck;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

/**   
* @Title: HiddenList.java 
* @Package com.sunnyit.hiddencheck.action 
* @Description: TODO
* @author yk
* @date 2015年9月7日 下午3:27:12 
* @version V1.0   
*/
public class HiddenSelfCheckIngActivity extends BaseActivity {
	
	private TopBarView topbar_selfcheck_add;
	
	private ToggleButton TogBtn_push_switch;
	
	private EditText sc_id,sc_checkTime,sc_checkingDepartment,sc_inspector;
	private EditText sc_checkedDepartment,sc_checkedJob;
	private EditText sc_startTime,sc_deadline;
	private EditText sc_completeTableTime,sc_completeTablePeople;
	
	private EditText sco_acceptanceDepartment,sco_acceptanceTime;
	private EditText sco_completeTablePeople,sco_completeTableTime;
	
	private ImageButton img_sc_hiddenCondition,img_sc_fixMeasure;
	private ImageButton img_sco_fixresult,img_sco_acceptopinion;
	
	private WebView sc_hiddenCondition,sc_fixMeasure;
	private WebView sco_fixresult,sco_acceptopinion;
	private WebView sc_sc_superviseOpinion;
	
	private RelativeLayout rel_sc_hiddenCondition,rel_sc_fixMeasure;
	private LinearLayout lin_sc_startTime,lin_sc_deadline;
	private RelativeLayout rel_sco_fixresult,rel_sco_acceptopinion;
	private LinearLayout lin_sco_acceptanceDepartment,lin_sco_acceptanceTime;
	private LinearLayout lin_sco_completeTablePeople,lin_sco_completeTableTime;
	
	private RelativeLayout rel_sc_superviseOpinion;
	
	private SelfCheck sc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hidden_seftcheck_ing);
		
		sc = (SelfCheck) getIntent().getSerializableExtra("SelfCheck"); 
		
		initComponent();
		
		topbar_selfcheck_add.setTopBarClick(new ITopBarClick() {
			
			@Override
			public void onClickRightBut() {
				// TODO Auto-generated method stub
	       		Bundle bundle = new Bundle(); 
				bundle.putSerializable("SelfCheck", sc); 
				Intent intent=new Intent(HiddenSelfCheckIngActivity.this, HiddenSelfCheckSuperviseActivity.class);
	       		intent.putExtras(bundle);
	       		startActivity(intent);
			}
			
			@Override
			public void onClickLeftBut() {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		topbar_selfcheck_add.setTitle("检查信息");
		topbar_selfcheck_add.setRightButText("督促");
		
	}

	private void initComponent() {
		// TODO Auto-generated method stub
		topbar_selfcheck_add=(TopBarView) findViewById(R.id.topbar_selfcheck_add);
		
		sc_id=(EditText) findViewById(R.id.sc_id);
		sc_checkTime=(EditText) findViewById(R.id.sc_checkTime);
		sc_checkingDepartment=(EditText) findViewById(R.id.sc_checkingDepartment);
		sc_inspector=(EditText) findViewById(R.id.sc_inspector);
		sc_checkedDepartment=(EditText) findViewById(R.id.sc_checkedDepartment);
		sc_checkedJob=(EditText) findViewById(R.id.sc_checkedJob);
		sc_startTime=(EditText) findViewById(R.id.sc_startTime);
		sc_deadline=(EditText) findViewById(R.id.sc_deadline);
		sc_completeTableTime=(EditText) findViewById(R.id.sc_completeTableTime);
		sc_completeTablePeople=(EditText) findViewById(R.id.sc_completeTablePeople);
		
		img_sc_hiddenCondition=(ImageButton) findViewById(R.id.img_sc_hiddenCondition);
		img_sc_fixMeasure=(ImageButton) findViewById(R.id.img_sc_fixMeasure);
		img_sco_fixresult=(ImageButton) findViewById(R.id.img_sco_fixresult);
		img_sco_acceptopinion=(ImageButton) findViewById(R.id.img_sco_acceptopinion);
		
		sc_hiddenCondition=(WebView) findViewById(R.id.sc_hiddenCondition);
		sc_fixMeasure=(WebView) findViewById(R.id.sc_fixMeasure);
		sco_fixresult=(WebView) findViewById(R.id.sco_fixresult);
		sco_acceptopinion=(WebView) findViewById(R.id.sco_acceptopinion);
		sc_sc_superviseOpinion=(WebView) findViewById(R.id.sc_sc_superviseOpinion);
		
		sco_acceptanceDepartment=(EditText) findViewById(R.id.sco_acceptanceDepartment);
		sco_acceptanceTime=(EditText) findViewById(R.id.sco_acceptanceTime);
		sco_completeTablePeople=(EditText) findViewById(R.id.sco_completeTablePeople);
		sco_completeTableTime=(EditText) findViewById(R.id.sco_completeTableTime);
		
		rel_sc_hiddenCondition=(RelativeLayout) findViewById(R.id.rel_sc_hiddenCondition);
		rel_sc_fixMeasure=(RelativeLayout) findViewById(R.id.rel_sc_fixMeasure);
		lin_sc_startTime=(LinearLayout) findViewById(R.id.lin_sc_startTime);
		lin_sc_deadline=(LinearLayout) findViewById(R.id.lin_sc_deadline);
		rel_sco_fixresult=(RelativeLayout) findViewById(R.id.rel_sco_fixresult);
		rel_sco_acceptopinion=(RelativeLayout) findViewById(R.id.rel_sco_acceptopinion);
		lin_sco_acceptanceDepartment=(LinearLayout) findViewById(R.id.lin_sco_acceptanceDepartment);
		lin_sco_acceptanceTime=(LinearLayout) findViewById(R.id.lin_sco_acceptanceTime);
		lin_sco_completeTablePeople=(LinearLayout) findViewById(R.id.lin_sco_completeTablePeople);
		lin_sco_completeTableTime=(LinearLayout) findViewById(R.id.lin_sco_completeTableTime);
		
		rel_sc_superviseOpinion=(RelativeLayout) findViewById(R.id.rel_sc_superviseOpinion);
		
		switch (sc.getSc_state()) {
		case "ING":
			rel_sco_fixresult.setVisibility(View.GONE);
			rel_sco_acceptopinion.setVisibility(View.GONE);
			lin_sco_acceptanceDepartment.setVisibility(View.GONE);
			lin_sco_acceptanceTime.setVisibility(View.GONE);
			lin_sco_completeTablePeople.setVisibility(View.GONE);
			lin_sco_completeTableTime.setVisibility(View.GONE);
			
			sc_id.setText(sc.getSc_id());
			sc_checkTime.setText(sc.getSc_checkTime());
			sc_checkingDepartment.setText(sc.getSc_checkingDepartment());
			sc_inspector.setText(sc.getSc_inspector());
			sc_checkedDepartment.setText(sc.getSc_checkedDepartment());
			sc_checkedJob.setText(sc.getSc_checkedJob());
			
			sc_hiddenCondition.getSettings().setDefaultTextEncodingName("UTF -8");//设置默认为utf-8  
			sc_hiddenCondition.loadData(sc.getSc_hiddenCondition(), "text/html; charset=UTF-8", null);//这种写法可以正确解码  
			sc_fixMeasure.getSettings().setDefaultTextEncodingName("UTF -8");//设置默认为utf-8  
			sc_fixMeasure.loadData(sc.getSc_fixMeasure(), "text/html; charset=UTF-8", null);//这种写法可以正确解码  
			
			sc_startTime.setText(sc.getSc_startTime());
			sc_deadline.setText(sc.getSc_deadline());
			sc_completeTableTime.setText(sc.getSc_completeTableTime());
			sc_completeTablePeople.setText(sc.getSc_completeTablePeople());
			
			if(!sc.getSc_superviseOpinion().equals(""))
			{
				rel_sc_superviseOpinion.setVisibility(View.VISIBLE);
				sc_sc_superviseOpinion.getSettings().setDefaultTextEncodingName("UTF -8");//设置默认为utf-8  
				sc_sc_superviseOpinion.loadData(sc.getSc_superviseOpinion(), "text/html; charset=UTF-8", null);//这种写法可以正确解码  
			}
			
			break;
		case "NONEED":
			topbar_selfcheck_add.setRightButVisibility(View.GONE);
			
			rel_sc_fixMeasure.setVisibility(View.GONE);
			lin_sc_startTime.setVisibility(View.GONE);
			lin_sc_deadline.setVisibility(View.GONE);
			rel_sco_fixresult.setVisibility(View.GONE);
			rel_sco_acceptopinion.setVisibility(View.GONE);
			lin_sco_acceptanceDepartment.setVisibility(View.GONE);
			lin_sco_acceptanceTime.setVisibility(View.GONE);
			lin_sco_completeTablePeople.setVisibility(View.GONE);
			lin_sco_completeTableTime.setVisibility(View.GONE);
			
			sc_id.setText(sc.getSc_id());
			sc_checkTime.setText(sc.getSc_checkTime());
			sc_checkingDepartment.setText(sc.getSc_checkingDepartment());
			sc_inspector.setText(sc.getSc_inspector());
			sc_checkedDepartment.setText(sc.getSc_checkedDepartment());
			sc_checkedJob.setText(sc.getSc_checkedJob());
			
			sc_hiddenCondition.getSettings().setDefaultTextEncodingName("UTF -8");//设置默认为utf-8  
			sc_hiddenCondition.loadData(sc.getSc_hiddenCondition(), "text/html; charset=UTF-8", null);//这种写法可以正确解码  
			
			sc_completeTableTime.setText(sc.getSc_completeTableTime());
			sc_completeTablePeople.setText(sc.getSc_completeTablePeople());
			break;
		case "FINISH":
			topbar_selfcheck_add.setRightButVisibility(View.GONE);
			
			sc_id.setText(sc.getSc_id());
			sc_checkTime.setText(sc.getSc_checkTime());
			sc_checkingDepartment.setText(sc.getSc_checkingDepartment());
			sc_inspector.setText(sc.getSc_inspector());
			sc_checkedDepartment.setText(sc.getSc_checkedDepartment());
			sc_checkedJob.setText(sc.getSc_checkedJob());
			
			sc_hiddenCondition.getSettings().setDefaultTextEncodingName("UTF -8");//设置默认为utf-8  
			sc_hiddenCondition.loadData(sc.getSc_hiddenCondition(), "text/html; charset=UTF-8", null);//这种写法可以正确解码  
			sc_fixMeasure.getSettings().setDefaultTextEncodingName("UTF -8");//设置默认为utf-8  
			sc_fixMeasure.loadData(sc.getSc_fixMeasure(), "text/html; charset=UTF-8", null);//这种写法可以正确解码  
			
			sc_startTime.setText(sc.getSc_startTime());
			sc_deadline.setText(sc.getSc_deadline());
			sc_completeTableTime.setText(sc.getSc_completeTableTime());
			sc_completeTablePeople.setText(sc.getSc_completeTablePeople());
			
			sco_fixresult.getSettings().setDefaultTextEncodingName("UTF -8");//设置默认为utf-8  
			sco_fixresult.loadData(sc.getSco_fixresult(), "text/html; charset=UTF-8", null);//这种写法可以正确解码  
			sco_acceptopinion.getSettings().setDefaultTextEncodingName("UTF -8");//设置默认为utf-8  
			sco_acceptopinion.loadData(sc.getSco_acceptopinion(), "text/html; charset=UTF-8", null);//这种写法可以正确解码  
			
			sco_acceptanceDepartment.setText(sc.getSco_acceptanceDepartment());
			sco_acceptanceTime.setText(sc.getSco_acceptanceTime());
			sco_completeTablePeople.setText(sc.getSco_completeTablePeople());
			sco_completeTableTime.setText(sc.getSco_completeTableTime());
			
			break;
		default:
			break;
		}

		
	}
	
}


