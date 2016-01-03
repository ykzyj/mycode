package com.sunnyit.enforcement.action;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.enforcement.model.DailyCheck;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**   
* @Title: HiddenList.java 
* @Package com.sunnyit.hiddencheck.action 
* @Description: TODO
* @author yk
* @date 2015年9月7日 下午3:27:12 
* @version V1.0   
*/
public class HiddenDailyCheckShowActivity extends BaseActivity {
	
	private TopBarView topbar_dailycheck_show;
	
	private EditText et_ck_time,et_ck_checkingdepartment,et_ck_checkeddepartment;
	private EditText et_ck_checkPlace,et_ck_sceneresponsible,et_ck_position;
	private EditText et_ck_telephone,et_ck_scenecondition,et_ck_fixnowing;
	private EditText et_ck_fixdeadline,et_ck_fixstart_time,et_ck_fixend_time;
	private EditText et_ck_checkpeople,et_ck_completechecktabletime,et_ck_completechecktablepeople;
	private EditText et_ck_fixMeasure,et_ck_fixCondition,et_ck_acceptopinion;
	private EditText et_ck_acceptanceDept,et_ck_acceptanceTime,et_ck_reviewcondition;
	private EditText et_ck_reviewopinion,et_ck_reviewdepartment,et_ck_reviewtime;
	private EditText et_ck_reviewprople;
	
	private EditText et_ck_startime,et_ck_endtime;
	
	private RelativeLayout rel_ck_fixnowing,rel_ck_fixdeadline,rel_ck_fixMeasure;
	private RelativeLayout rel_ck_fixCondition,rel_ck_reviewcondition,rel_ck_reviewopinion;
	private LinearLayout lin_ck_fixstart_time,lin_ck_fixend_time,lin_ck_acceptopinion;
	private LinearLayout lin_ck_acceptanceDept,lin_ck_acceptanceTime,lin_ck_reviewdepartment;
	private LinearLayout lin_ck_reviewtime,lin_ck_reviewprople;
	
	private DailyCheck mDailyCheck;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hidden_dailycheck_show);
		
		initComponent();
		
		mDailyCheck=(DailyCheck) getIntent().getSerializableExtra("DailyCheck");
		
		switch (mDailyCheck.getCk_state()) {
		case "CKING":
			rel_ck_fixMeasure.setVisibility(View.GONE);
			rel_ck_fixCondition.setVisibility(View.GONE);
			lin_ck_acceptopinion.setVisibility(View.GONE);
			lin_ck_acceptanceDept.setVisibility(View.GONE);
			lin_ck_acceptanceTime.setVisibility(View.GONE);
			rel_ck_reviewcondition.setVisibility(View.GONE);
			rel_ck_reviewopinion.setVisibility(View.GONE);
			lin_ck_reviewdepartment.setVisibility(View.GONE);
			lin_ck_reviewtime.setVisibility(View.GONE);
			lin_ck_reviewprople.setVisibility(View.GONE);
			break;
		case "NONEED":
			rel_ck_fixnowing.setVisibility(View.GONE);
			rel_ck_fixdeadline.setVisibility(View.GONE);
			lin_ck_fixstart_time.setVisibility(View.GONE);
			lin_ck_fixend_time.setVisibility(View.GONE);
			
			rel_ck_fixMeasure.setVisibility(View.GONE);
			rel_ck_fixCondition.setVisibility(View.GONE);
			lin_ck_acceptopinion.setVisibility(View.GONE);
			lin_ck_acceptanceDept.setVisibility(View.GONE);
			lin_ck_acceptanceTime.setVisibility(View.GONE);
			rel_ck_reviewcondition.setVisibility(View.GONE);
			rel_ck_reviewopinion.setVisibility(View.GONE);
			lin_ck_reviewdepartment.setVisibility(View.GONE);
			lin_ck_reviewtime.setVisibility(View.GONE);
			lin_ck_reviewprople.setVisibility(View.GONE);
			break;
		case "NOTYET":
			rel_ck_fixMeasure.setVisibility(View.GONE);
			rel_ck_fixCondition.setVisibility(View.GONE);
			lin_ck_acceptopinion.setVisibility(View.GONE);
			lin_ck_acceptanceDept.setVisibility(View.GONE);
			lin_ck_acceptanceTime.setVisibility(View.GONE);
			rel_ck_reviewcondition.setVisibility(View.GONE);
			rel_ck_reviewopinion.setVisibility(View.GONE);
			lin_ck_reviewdepartment.setVisibility(View.GONE);
			lin_ck_reviewtime.setVisibility(View.GONE);
			lin_ck_reviewprople.setVisibility(View.GONE);
			break;
		case "ING":
			rel_ck_fixCondition.setVisibility(View.GONE);
			lin_ck_acceptopinion.setVisibility(View.GONE);
			lin_ck_acceptanceDept.setVisibility(View.GONE);
			lin_ck_acceptanceTime.setVisibility(View.GONE);
			rel_ck_reviewcondition.setVisibility(View.GONE);
			rel_ck_reviewopinion.setVisibility(View.GONE);
			lin_ck_reviewdepartment.setVisibility(View.GONE);
			lin_ck_reviewtime.setVisibility(View.GONE);
			lin_ck_reviewprople.setVisibility(View.GONE);
			break;
		case "FINISH":
			rel_ck_reviewcondition.setVisibility(View.GONE);
			rel_ck_reviewopinion.setVisibility(View.GONE);
			lin_ck_reviewdepartment.setVisibility(View.GONE);
			lin_ck_reviewtime.setVisibility(View.GONE);
			lin_ck_reviewprople.setVisibility(View.GONE);
			break;
		case "REVIEWING":
			break;
		case "REVIEWED":
			break;
		case "RECORDED":
			break;
		case "DESTORY":
			break;
		default:
			break;
		}
		
		initDataShow();
		
		topbar_dailycheck_show.setTopBarClick(new ITopBarClick() {
			
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
		
	}
	
	private void initDataShow() {
		et_ck_time.setText(mDailyCheck.getCk_time());;
		et_ck_checkingdepartment.setText(mDailyCheck.getCk_checkingdepartment());
		et_ck_checkeddepartment.setText(mDailyCheck.getCk_checkeddepartment());
		et_ck_checkPlace.setText(mDailyCheck.getCk_checkPlace());
		et_ck_sceneresponsible.setText(mDailyCheck.getCk_sceneresponsible());
		et_ck_position.setText(mDailyCheck.getCk_position());
		et_ck_telephone.setText(mDailyCheck.getCk_telephone());
		et_ck_scenecondition.setText(mDailyCheck.getCk_scenecondition());
		et_ck_fixnowing.setText(mDailyCheck.getCk_fixnowing());
		et_ck_fixdeadline.setText(mDailyCheck.getCk_fixdeadline());
		et_ck_fixstart_time.setText(mDailyCheck.getCk_fixstart_time());
		et_ck_fixend_time.setText(mDailyCheck.getCk_fixend_time());
		et_ck_checkpeople.setText(mDailyCheck.getCk_checkpeople());
		et_ck_completechecktabletime.setText(mDailyCheck.getCk_completechecktablepeople());
		et_ck_completechecktablepeople.setText(mDailyCheck.getCk_completechecktabletime());
		et_ck_fixMeasure.setText(mDailyCheck.getCk_fixMeasure());
		et_ck_fixCondition.setText(mDailyCheck.getCk_fixCondition());
		
		et_ck_startime.setText(mDailyCheck.getCk_startime());
		et_ck_endtime.setText(mDailyCheck.getCk_endtime());
		
		if(mDailyCheck.getCk_acceptopinion()!=null&&
				mDailyCheck.getCk_acceptopinion()!="")
		{
			if(mDailyCheck.getCk_acceptopinion().equals("是"))
			{
				et_ck_acceptopinion.setText("合格");
			}
			else if(mDailyCheck.getCk_acceptopinion().equals("否"))
			{
				et_ck_acceptopinion.setText("不合格");
			}
			
		}
		
		et_ck_acceptanceDept.setText(mDailyCheck.getCk_acceptanceDept());
		et_ck_acceptanceTime.setText(mDailyCheck.getCk_acceptanceTime());
		et_ck_reviewcondition.setText(mDailyCheck.getCk_reviewcondition());
		et_ck_reviewopinion.setText(mDailyCheck.getCk_reviewopinion());
		et_ck_reviewdepartment.setText(mDailyCheck.getCk_reviewdepartment());
		et_ck_reviewtime.setText(mDailyCheck.getCk_reviewtime());
		et_ck_reviewprople.setText(mDailyCheck.getCk_reviewprople());
	}

	private void initComponent() {
		topbar_dailycheck_show=(TopBarView) findViewById(R.id.topbar_dailycheck_show);
		
		et_ck_startime=(EditText) findViewById(R.id.et_ck_startime);
		et_ck_endtime=(EditText) findViewById(R.id.et_ck_endtime);
		
		et_ck_time=(EditText) findViewById(R.id.et_ck_time);
		et_ck_checkingdepartment=(EditText) findViewById(R.id.et_ck_checkingdepartment);
		et_ck_checkeddepartment=(EditText) findViewById(R.id.et_ck_checkeddepartment);
		et_ck_checkPlace=(EditText) findViewById(R.id.et_ck_checkPlace);
		et_ck_sceneresponsible=(EditText) findViewById(R.id.et_ck_sceneresponsible);
		et_ck_position=(EditText) findViewById(R.id.et_ck_position);
		et_ck_telephone=(EditText) findViewById(R.id.et_ck_telephone);
		et_ck_scenecondition=(EditText) findViewById(R.id.et_ck_scenecondition);
		et_ck_fixnowing=(EditText) findViewById(R.id.et_ck_fixnowing);
		et_ck_fixdeadline=(EditText) findViewById(R.id.et_ck_fixdeadline);
		et_ck_fixstart_time=(EditText) findViewById(R.id.et_ck_fixstart_time);
		et_ck_fixend_time=(EditText) findViewById(R.id.et_ck_fixend_time);
		et_ck_checkpeople=(EditText) findViewById(R.id.et_ck_checkpeople);
		et_ck_completechecktabletime=(EditText) findViewById(R.id.et_ck_completechecktabletime);
		et_ck_completechecktablepeople=(EditText) findViewById(R.id.et_ck_completechecktablepeople);
		et_ck_fixMeasure=(EditText) findViewById(R.id.et_ck_fixMeasure);
		et_ck_fixCondition=(EditText) findViewById(R.id.et_ck_fixCondition);
		et_ck_acceptopinion=(EditText) findViewById(R.id.et_ck_acceptopinion);
		et_ck_acceptanceDept=(EditText) findViewById(R.id.et_ck_acceptanceDept);
		et_ck_acceptanceTime=(EditText) findViewById(R.id.et_ck_acceptanceTime);
		et_ck_reviewcondition=(EditText) findViewById(R.id.et_ck_reviewcondition);
		et_ck_reviewopinion=(EditText) findViewById(R.id.et_ck_reviewopinion);
		et_ck_reviewdepartment=(EditText) findViewById(R.id.et_ck_reviewdepartment);
		et_ck_reviewtime=(EditText) findViewById(R.id.et_ck_reviewtime);
		et_ck_reviewprople=(EditText) findViewById(R.id.et_ck_reviewprople);
		
		rel_ck_fixnowing=(RelativeLayout) findViewById(R.id.rel_ck_fixnowing);
		rel_ck_fixdeadline=(RelativeLayout) findViewById(R.id.rel_ck_fixdeadline);
		rel_ck_fixMeasure=(RelativeLayout) findViewById(R.id.rel_ck_fixMeasure);
		rel_ck_fixCondition=(RelativeLayout) findViewById(R.id.rel_ck_fixCondition);
		rel_ck_reviewcondition=(RelativeLayout) findViewById(R.id.rel_ck_reviewcondition);
		rel_ck_reviewopinion=(RelativeLayout) findViewById(R.id.rel_ck_reviewopinion);
		lin_ck_fixstart_time=(LinearLayout) findViewById(R.id.lin_ck_fixstart_time);
		lin_ck_fixend_time=(LinearLayout) findViewById(R.id.lin_ck_fixend_time);
		lin_ck_acceptopinion=(LinearLayout) findViewById(R.id.lin_ck_acceptopinion);
		lin_ck_acceptanceDept=(LinearLayout) findViewById(R.id.lin_ck_acceptanceDept);
		lin_ck_acceptanceTime=(LinearLayout) findViewById(R.id.lin_ck_acceptanceTime);
		lin_ck_reviewdepartment=(LinearLayout) findViewById(R.id.lin_ck_reviewdepartment);
		lin_ck_reviewtime=(LinearLayout) findViewById(R.id.lin_ck_reviewtime);
		lin_ck_reviewprople=(LinearLayout) findViewById(R.id.lin_ck_reviewprople);
		
	}
	
}


