package com.sunnyit.enforcement.action;

import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.ChoiceEditView;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.enforcement.model.Inspector;
import com.sunnyit.enforcement.model.SpecialCheck;

import android.graphics.Color;
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
public class HiddenSpecialCheckShowActivity extends BaseActivity {

	
	private TopBarView topbar_specialcheck_show;
	
	private EditText et_ck_time,et_ck_specialcheckname,et_ck_checkingdepartment;
	private EditText et_ck_checkgroupid,et_ck_site,et_ck_leader;
	private EditText et_ck_position,et_ck_checkeddepartment,et_ck_sceneresponsible;
	private EditText et_ck_duty,et_ck_telephone,et_ck_scenecondition;
	private EditText et_ck_fixnowing,et_ck_fixdeadline,et_ck_fixstart_time;
	private EditText et_ck_fixend_time,et_ck_completechecktabletime,et_ck_completechecktablepeople;
	private EditText et_ck_repairmethod,et_ck_repaircondition,et_ck_repairconditionTime;
	private EditText et_ck_repairconditionPerson,et_ck_reviewcondition,et_ck_reviewopinion;
	private EditText et_ck_reviewdepartment,et_ck_reviewtime,et_ck_reviewprople;
	private EditText et_ck_completereviewtablepeople,et_ck_completereviewtabletime;
	
	private EditText et_ck_expert,et_ck_inspter;
	
	private EditText et_ck_startime,et_ck_endtime;
	
	private RelativeLayout rel_ck_fixnowing,rel_ck_fixdeadline,rel_ck_repairmethod;
	private RelativeLayout rel_ck_repaircondition,rel_ck_reviewcondition,rel_ck_reviewopinion;
	
	private LinearLayout lin_ck_fixstart_time,lin_ck_fixend_time,lin_ck_repairconditionTime;
	private LinearLayout lin_ck_repairconditionPerson,lin_ck_reviewdepartment,lin_ck_reviewtime;
	private LinearLayout lin_ck_reviewprople,lin_ck_completereviewtablepeople,lin_ck_completereviewtabletime;
	
	private SpecialCheck mSpecialCheck;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hidden_specialcheck_show);
		
		initComponent();
		
		mSpecialCheck=(SpecialCheck) getIntent().getSerializableExtra("SpecialCheck");
		
		switch (mSpecialCheck.getCk_state()) {
		case "CKING":
			
			rel_ck_repairmethod.setVisibility(View.GONE);
			rel_ck_repaircondition.setVisibility(View.GONE);
			lin_ck_repairconditionTime.setVisibility(View.GONE);
			lin_ck_repairconditionPerson.setVisibility(View.GONE);
			
			rel_ck_reviewcondition.setVisibility(View.GONE);
			rel_ck_reviewopinion.setVisibility(View.GONE);
			lin_ck_reviewdepartment.setVisibility(View.GONE);
			lin_ck_reviewtime.setVisibility(View.GONE);
			lin_ck_reviewprople.setVisibility(View.GONE);
			lin_ck_completereviewtablepeople.setVisibility(View.GONE);
			lin_ck_completereviewtabletime.setVisibility(View.GONE);
			break;
		case "NONEED":
			rel_ck_fixnowing.setVisibility(View.GONE);
			rel_ck_fixdeadline.setVisibility(View.GONE);
			lin_ck_fixstart_time.setVisibility(View.GONE);
			lin_ck_fixend_time.setVisibility(View.GONE);
			
			rel_ck_repairmethod.setVisibility(View.GONE);
			rel_ck_repaircondition.setVisibility(View.GONE);
			lin_ck_repairconditionTime.setVisibility(View.GONE);
			lin_ck_repairconditionPerson.setVisibility(View.GONE);
			
			rel_ck_reviewcondition.setVisibility(View.GONE);
			rel_ck_reviewopinion.setVisibility(View.GONE);
			lin_ck_reviewdepartment.setVisibility(View.GONE);
			lin_ck_reviewtime.setVisibility(View.GONE);
			lin_ck_reviewprople.setVisibility(View.GONE);
			lin_ck_completereviewtablepeople.setVisibility(View.GONE);
			lin_ck_completereviewtabletime.setVisibility(View.GONE);
			
			break;
		case "NOYET":
			rel_ck_repairmethod.setVisibility(View.GONE);
			rel_ck_repaircondition.setVisibility(View.GONE);
			lin_ck_repairconditionTime.setVisibility(View.GONE);
			lin_ck_repairconditionPerson.setVisibility(View.GONE);
			
			rel_ck_reviewcondition.setVisibility(View.GONE);
			rel_ck_reviewopinion.setVisibility(View.GONE);
			lin_ck_reviewdepartment.setVisibility(View.GONE);
			lin_ck_reviewtime.setVisibility(View.GONE);
			lin_ck_reviewprople.setVisibility(View.GONE);
			lin_ck_completereviewtablepeople.setVisibility(View.GONE);
			lin_ck_completereviewtabletime.setVisibility(View.GONE);
			break;
		case "ING":
			rel_ck_repaircondition.setVisibility(View.GONE);
			lin_ck_repairconditionTime.setVisibility(View.GONE);
			lin_ck_repairconditionPerson.setVisibility(View.GONE);
			
			rel_ck_reviewcondition.setVisibility(View.GONE);
			rel_ck_reviewopinion.setVisibility(View.GONE);
			lin_ck_reviewdepartment.setVisibility(View.GONE);
			lin_ck_reviewtime.setVisibility(View.GONE);
			lin_ck_reviewprople.setVisibility(View.GONE);
			lin_ck_completereviewtablepeople.setVisibility(View.GONE);
			lin_ck_completereviewtabletime.setVisibility(View.GONE);
			break;
		case "FINISH":
			rel_ck_reviewcondition.setVisibility(View.GONE);
			rel_ck_reviewopinion.setVisibility(View.GONE);
			lin_ck_reviewdepartment.setVisibility(View.GONE);
			lin_ck_reviewtime.setVisibility(View.GONE);
			lin_ck_reviewprople.setVisibility(View.GONE);
			lin_ck_completereviewtablepeople.setVisibility(View.GONE);
			lin_ck_completereviewtabletime.setVisibility(View.GONE);
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
		
		topbar_specialcheck_show.setTopBarClick(new ITopBarClick() {
			
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
		
		topbar_specialcheck_show.setRightButVisibility(View.GONE);
		
	}
	
	private void initDataShow() {
		
		et_ck_startime.setText(mSpecialCheck.getCk_startime());
		et_ck_endtime.setText(mSpecialCheck.getCk_endtime());
		
		et_ck_time.setText(mSpecialCheck.getCk_time());
		et_ck_specialcheckname.setText(mSpecialCheck.getCk_specialcheckname());
		et_ck_checkingdepartment.setText(mSpecialCheck.getCk_checkingdepartment());
		et_ck_checkgroupid.setText(mSpecialCheck.getCk_checkgroupid());
		et_ck_site.setText(mSpecialCheck.getCk_site());
		et_ck_leader.setText(mSpecialCheck.getCk_leader());
		et_ck_position.setText(mSpecialCheck.getCk_position());
		et_ck_checkeddepartment.setText(mSpecialCheck.getCk_checkeddepartment());
		et_ck_sceneresponsible.setText(mSpecialCheck.getCk_sceneresponsible());
		et_ck_duty.setText(mSpecialCheck.getCk_duty());
		et_ck_telephone.setText(mSpecialCheck.getCk_telephone());
		et_ck_scenecondition.setText(mSpecialCheck.getCk_scenecondition());
		et_ck_fixnowing.setText(mSpecialCheck.getCk_fixnowing());
		et_ck_fixdeadline.setText(mSpecialCheck.getCk_fixdeadline());
		et_ck_fixstart_time.setText(mSpecialCheck.getCk_fixstart_time());
		et_ck_fixend_time.setText(mSpecialCheck.getCk_fixend_time());
		et_ck_completechecktabletime.setText(mSpecialCheck.getCk_completechecktabletime());
		et_ck_completechecktablepeople.setText(mSpecialCheck.getCk_completechecktablepeople());
		et_ck_repairmethod.setText(mSpecialCheck.getCk_repairmethod());
		et_ck_repaircondition.setText(mSpecialCheck.getCk_repaircondition());
		et_ck_repairconditionTime.setText(mSpecialCheck.getCk_repairconditionTime());
		et_ck_repairconditionPerson.setText(mSpecialCheck.getCk_repairconditionPerson());
		et_ck_reviewcondition.setText(mSpecialCheck.getCk_reviewcondition());
		et_ck_reviewopinion.setText(mSpecialCheck.getCk_reviewopinion());
		et_ck_reviewdepartment.setText(mSpecialCheck.getCk_reviewdepartment());
		et_ck_reviewtime.setText(mSpecialCheck.getCk_reviewtime());
		et_ck_reviewprople.setText(mSpecialCheck.getCk_reviewprople());
		et_ck_completereviewtablepeople.setText(mSpecialCheck.getCk_completereviewtablepeople());
		et_ck_completereviewtabletime.setText(mSpecialCheck.getCk_completereviewtabletime());
		
		String exSqlStr=" where inspectortype='专家' and ck_id='"+mSpecialCheck.getC_id()+"' ";
		SqlOperate<Inspector> opetaterInspectorX=new SqlOperate<Inspector>(HiddenSpecialCheckShowActivity.this, Inspector.class);
		List<Inspector> ls_in_ex=opetaterInspectorX.SelectEntitysByCondition(exSqlStr);
		opetaterInspectorX.close();
		
		StringBuffer sb_ex=new StringBuffer();
		for(Inspector in_ex:ls_in_ex)
		{
			sb_ex.append(in_ex.getInspectorname());
			sb_ex.append("(");
			sb_ex.append(in_ex.getInspectorduty());
			sb_ex.append(")");
			sb_ex.append(",");
		}
		if(ls_in_ex.size()>0)
		{
			String str_ex=sb_ex.substring(0, sb_ex.length()-1);
			et_ck_expert.setText(str_ex);
		}
		
		String ckSqlStr=" where inspectortype='检查人员' and ck_id='"+mSpecialCheck.getC_id()+"' ";
		SqlOperate<Inspector> opetaterInspectorC=new SqlOperate<Inspector>(HiddenSpecialCheckShowActivity.this, Inspector.class);
		List<Inspector> ls_in_ck=opetaterInspectorC.SelectEntitysByCondition(ckSqlStr);
		opetaterInspectorC.close();
		
		StringBuffer sb_ck=new StringBuffer();
		for(Inspector in_ck:ls_in_ck)
		{
			sb_ck.append(in_ck.getInspectorname());
			sb_ck.append("(");
			sb_ck.append(in_ck.getInspectorduty());
			sb_ck.append(")");
			sb_ck.append(",");
		}
		if(ls_in_ck.size()>0)
		{
			String str_ck=sb_ck.substring(0, sb_ck.length()-1);
			et_ck_inspter.setText(str_ck);
		}
		
	}

	private void initComponent() {
		topbar_specialcheck_show=(TopBarView) findViewById(R.id.topbar_specialcheck_show);
		
		et_ck_startime=(EditText) findViewById(R.id.et_ck_startime);
		et_ck_endtime=(EditText) findViewById(R.id.et_ck_endtime);
		
		et_ck_time=(EditText) findViewById(R.id.et_ck_time);
		et_ck_specialcheckname=(EditText) findViewById(R.id.et_ck_specialcheckname);
		et_ck_checkingdepartment=(EditText) findViewById(R.id.et_ck_checkingdepartment);
		et_ck_checkgroupid=(EditText) findViewById(R.id.et_ck_checkgroupid);
		et_ck_site=(EditText) findViewById(R.id.et_ck_site);
		et_ck_leader=(EditText) findViewById(R.id.et_ck_leader);
		et_ck_position=(EditText) findViewById(R.id.et_ck_position);
		et_ck_checkeddepartment=(EditText) findViewById(R.id.et_ck_checkeddepartment);
		et_ck_sceneresponsible=(EditText) findViewById(R.id.et_ck_sceneresponsible);
		et_ck_duty=(EditText) findViewById(R.id.et_ck_duty);
		et_ck_telephone=(EditText) findViewById(R.id.et_ck_telephone);
		et_ck_scenecondition=(EditText) findViewById(R.id.et_ck_scenecondition);
		et_ck_fixnowing=(EditText) findViewById(R.id.et_ck_fixnowing);
		et_ck_fixdeadline=(EditText) findViewById(R.id.et_ck_fixdeadline);
		et_ck_fixstart_time=(EditText) findViewById(R.id.et_ck_fixstart_time);
		et_ck_fixend_time=(EditText) findViewById(R.id.et_ck_fixend_time);
		et_ck_completechecktabletime=(EditText) findViewById(R.id.et_ck_completechecktabletime);
		et_ck_completechecktablepeople=(EditText) findViewById(R.id.et_ck_completechecktablepeople);
		et_ck_repairmethod=(EditText) findViewById(R.id.et_ck_repairmethod);
		et_ck_repaircondition=(EditText) findViewById(R.id.et_ck_repaircondition);
		et_ck_repairconditionTime=(EditText) findViewById(R.id.et_ck_repairconditionTime);
		et_ck_repairconditionPerson=(EditText) findViewById(R.id.et_ck_repairconditionPerson);
		et_ck_reviewcondition=(EditText) findViewById(R.id.et_ck_reviewcondition);
		et_ck_reviewopinion=(EditText) findViewById(R.id.et_ck_reviewopinion);
		et_ck_reviewdepartment=(EditText) findViewById(R.id.et_ck_reviewdepartment);
		et_ck_reviewtime=(EditText) findViewById(R.id.et_ck_reviewtime);
		et_ck_reviewprople=(EditText) findViewById(R.id.et_ck_reviewprople);
		et_ck_completereviewtablepeople=(EditText) findViewById(R.id.et_ck_completereviewtablepeople);
		et_ck_completereviewtabletime=(EditText) findViewById(R.id.et_ck_completereviewtabletime);
		
		et_ck_expert=(EditText) findViewById(R.id.et_ck_expert);
		et_ck_inspter=(EditText) findViewById(R.id.et_ck_inspter);
		
		rel_ck_fixnowing=(RelativeLayout) findViewById(R.id.rel_ck_fixnowing);
		rel_ck_fixdeadline=(RelativeLayout) findViewById(R.id.rel_ck_fixdeadline);
		rel_ck_repairmethod=(RelativeLayout) findViewById(R.id.rel_ck_repairmethod);
		rel_ck_repaircondition=(RelativeLayout) findViewById(R.id.rel_ck_repaircondition);
		rel_ck_reviewcondition=(RelativeLayout) findViewById(R.id.rel_ck_reviewcondition);
		rel_ck_reviewopinion=(RelativeLayout) findViewById(R.id.rel_ck_reviewopinion);
		
		lin_ck_fixstart_time=(LinearLayout) findViewById(R.id.lin_ck_fixstart_time);
		lin_ck_fixend_time=(LinearLayout) findViewById(R.id.lin_ck_fixend_time);
		lin_ck_repairconditionTime=(LinearLayout) findViewById(R.id.lin_ck_repairconditionTime);
		lin_ck_repairconditionPerson=(LinearLayout) findViewById(R.id.lin_ck_repairconditionPerson);
		lin_ck_reviewdepartment=(LinearLayout) findViewById(R.id.lin_ck_reviewdepartment);
		lin_ck_reviewtime=(LinearLayout) findViewById(R.id.lin_ck_reviewtime);
		lin_ck_reviewprople=(LinearLayout) findViewById(R.id.lin_ck_reviewprople);
		lin_ck_completereviewtablepeople=(LinearLayout) findViewById(R.id.lin_ck_completereviewtablepeople);
		lin_ck_completereviewtabletime=(LinearLayout) findViewById(R.id.lin_ck_completereviewtabletime);
		
	}

}


