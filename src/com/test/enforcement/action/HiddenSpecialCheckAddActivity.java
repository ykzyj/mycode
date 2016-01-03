package com.sunnyit.enforcement.action;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.activity.BigTvActivity;
import com.sunnyit.common.convert.DigitalConvert;
import com.sunnyit.common.dataOperate.dOperate;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.ChoiceEditView;
import com.sunnyit.common.view.ChoiceEditView.onClickEditListener;
import com.sunnyit.common.view.DateEditView;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.enforcement.model.CheckConditionItem;
import com.sunnyit.enforcement.model.Inspector;
import com.sunnyit.enforcement.model.SpecialCheck;
import com.sunnyit.synchronous.model.UpInfo;
import com.sunnyit.system.model.User;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

/**   
* @Title: HiddenList.java 
* @Package com.sunnyit.hiddencheck.action 
* @Description: TODO
* @author yk
* @date 2015年9月7日 下午3:27:12 
* @version V1.0   
*/
public class HiddenSpecialCheckAddActivity extends BaseActivity {

	
	private TopBarView topbar_specialcheck_add;
	
	private ToggleButton tog_dc_haveopinion;
	
	private RelativeLayout rel_ck_fixnowing;
	private RelativeLayout rel_ck_fixdeadline;
	private LinearLayout lin_ck_fixstart_time;
	private LinearLayout lin_ck_fixend_time;
	
	private EditText et_ck_id,et_ck_specialcheckname,et_ck_checkingdepartment;
	private EditText et_ck_checkgroupid,et_ck_leader,et_ck_position;
	private EditText et_ck_sceneresponsible,et_ck_duty,et_ck_telephone;
	
	private EditText et_ck_scenecondition,et_ck_fixnowing,et_ck_fixdeadline;
	private EditText et_ck_completechecktablepeople,et_ck_site;
	private EditText et_ck_startime,et_ck_endtime;
	
	private ChoiceEditView et_ck_expert,et_ck_inspter;
	private ChoiceEditView et_ck_checkeddepartment;
	
	private DateEditView et_ck_time,et_ck_fixstart_time;
	private DateEditView et_ck_fixend_time,et_ck_completechecktabletime;
	
	private ImageButton img_max_ck_scenecondition,img_max_ck_fixnowing,img_max_ck_fixdeadline;
	
	private List<Inspector> ls_expter=new ArrayList<>();
	private List<Inspector> ls_checker=new ArrayList<>();
	
	private SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
	/*private int code;*/
	
	private User user;
	
	private List<CheckConditionItem> ls_ckConItem;
	private int fixnowingCount=0;
	private int fixdeadlineCount=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hidden_specialcheck_add);
		
		ls_ckConItem=new ArrayList<CheckConditionItem>();
		
		initComponent();
		
		user=getCurrentUser();
		
		et_ck_checkingdepartment.setText(user.getDepartmentName());
		et_ck_checkingdepartment.setEnabled(false);
		
		et_ck_specialcheckname.setText(sp.getString("ck_specialcheckname", ""));
		et_ck_checkgroupid.setText(sp.getString("ck_checkgroupid", ""));
		et_ck_leader.setText(sp.getString("ck_leader", ""));
		et_ck_position.setText(sp.getString("ck_position", ""));
		et_ck_site.setText(sp.getString("ck_site", ""));
		et_ck_completechecktablepeople.setText(sp.getString("ck_completechecktablepeople", ""));
		
		et_ck_expert.setText(sp.getString("ck_in_x_n_p", ""));
		et_ck_inspter.setText(sp.getString("ck_in_i_n_p", ""));
		
		String[] strs_xn=sp.getString("ck_in_xn", "").split(",");
		String[] strs_xp=sp.getString("ck_in_xp", "").split(",");
		String[] strs_in=sp.getString("ck_in_in", "").split(",");
		String[] strs_ip=sp.getString("ck_in_ip", "").split(",");
		
		for(int i=0;i<strs_xn.length;i++)
		{
			Inspector in=new Inspector();
			in.setCheckid(UUID.randomUUID().toString());
			in.setInspectorname(strs_xn[i]);
			in.setInspectorduty(strs_xp[i]);
			in.setInspectortype("专家");
			ls_expter.add(in);
		}
		
		for(int i=0;i<strs_in.length;i++)
		{
			Inspector in=new Inspector();
			in.setCheckid(UUID.randomUUID().toString());
			in.setInspectorname(strs_in[i]);
			in.setInspectorduty(strs_ip[i]);
			in.setInspectortype("检查人员");
			ls_checker.add(in);
		}
		
		/*ls_expter=new ArrayList<Inspector>();
		ls_checker=new ArrayList<Inspector>();*/
		
		et_ck_id.setText(UUID.randomUUID().toString());
		
		topbar_specialcheck_add.setTopBarClick(new ITopBarClick() {
			
			@Override
			public void onClickRightBut() {
				// TODO Auto-generated method stub
				addSpecialCheckInfo();
			}
			
			@Override
			public void onClickLeftBut() {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		bigTvClick();
		
	}
	
	private void addSpecialCheckInfo() {
		// TODO Auto-generated method stub
		String ck_id=et_ck_id.getText().toString().trim();
		String ck_specialcheckname=et_ck_specialcheckname.getText().toString().trim();
		String ck_checkingdepartment=et_ck_checkingdepartment.getText().toString().trim();
		String ck_checkgroupid=et_ck_checkgroupid.getText().toString().trim();
		String ck_site=et_ck_site.getText().toString().trim();
		String ck_leader=et_ck_leader.getText().toString().trim();
		String ck_position=et_ck_position.getText().toString().trim();
		String ck_checkeddepartment=et_ck_checkeddepartment.getText().toString().trim();
		String ck_sceneresponsible=et_ck_sceneresponsible.getText().toString().trim();
		String ck_duty=et_ck_duty.getText().toString().trim();
		String ck_telephone=et_ck_telephone.getText().toString().trim();
		
		String ck_scenecondition=et_ck_scenecondition.getText().toString().trim();
		String ck_fixnowing=et_ck_fixnowing.getText().toString().trim();
		String ck_fixdeadline=et_ck_fixdeadline.getText().toString().trim();
		String ck_completechecktablepeople=et_ck_completechecktablepeople.getText().toString().trim();
		
		String ck_startime=et_ck_startime.getText().toString().trim();
		String ck_endtime=et_ck_endtime.getText().toString().trim();
		
		String ck_time=et_ck_time.getText().toString().trim();
		String ck_fixstart_time=et_ck_fixstart_time.getText().toString().trim();
		String ck_fixend_time=et_ck_fixend_time.getText().toString().trim();
		String ck_completechecktabletime=et_ck_completechecktabletime.getText().toString().trim();
		
		if(ck_checkeddepartment.equals(""))
		{
			Toast.makeText(HiddenSpecialCheckAddActivity.this, "被检查企业不能为空", Toast.LENGTH_SHORT).show();
		}
		else if(ck_completechecktabletime.equals(""))
		{
			Toast.makeText(HiddenSpecialCheckAddActivity.this, "填表时间不能为空", Toast.LENGTH_SHORT).show();
		}
		else if(ck_completechecktablepeople.equals(""))
		{
			Toast.makeText(HiddenSpecialCheckAddActivity.this, "填表人不能为空", Toast.LENGTH_SHORT).show();
		}
		else
		{
			Editor edit=sp.edit();
			edit.putString("ck_specialcheckname", ck_specialcheckname);
			edit.putString("ck_checkgroupid", ck_checkgroupid);
			edit.putString("ck_site", ck_site);
			edit.putString("ck_leader", ck_leader);
			edit.putString("ck_position", ck_position);
			edit.putString("ck_completechecktablepeople", ck_completechecktablepeople);
			
			edit.putString("ck_in_x_n_p", et_ck_expert.getText().toString());
			edit.putString("ck_in_i_n_p", et_ck_inspter.getText().toString());
			
			StringBuffer sb_in_xn=new StringBuffer();
			StringBuffer sb_in_xp=new StringBuffer();
			StringBuffer sb_in_in=new StringBuffer();
			StringBuffer sb_in_ip=new StringBuffer();
			for(Inspector xp:ls_expter)
			{
				sb_in_xn.append(xp.getInspectorname()+",");
				sb_in_xp.append(xp.getInspectorduty()+",");
			}
			
			for(Inspector ck:ls_checker)
			{
				sb_in_in.append(ck.getInspectorname()+",");
				sb_in_ip.append(ck.getInspectorduty()+",");
			}
			edit.putString("ck_in_xn", sb_in_xn.substring(0, sb_in_xn.length()-1));
			edit.putString("ck_in_xp", sb_in_xp.substring(0, sb_in_xp.length()-1));
			edit.putString("ck_in_in", sb_in_in.substring(0, sb_in_in.length()-1));
			edit.putString("ck_in_ip", sb_in_ip.substring(0, sb_in_ip.length()-1));
			edit.commit(); 
			
			
			SpecialCheck sc=new SpecialCheck();
			sc.setC_id(UUID.randomUUID().toString());
			sc.setCk_id(sc.getC_id());
			sc.setCk_specialcheckname(ck_specialcheckname);
			sc.setCk_checkingdepartment(ck_checkingdepartment);
			sc.setCk_checkgroupid(ck_checkgroupid);
			sc.setCk_site(ck_site);
			sc.setCk_leader(ck_leader);
			sc.setCk_position(ck_position);
			sc.setCk_checkeddepartment(ck_checkeddepartment);
			sc.setCk_sceneresponsible(ck_sceneresponsible);
			sc.setCk_duty(ck_duty);
			sc.setCk_telephone(ck_telephone);
			sc.setCk_scenecondition(ck_scenecondition);
			sc.setCk_time(ck_time);
			if(tog_dc_haveopinion.isChecked())
			{
				sc.setHaveopinion("有");
				sc.setCk_fixnowing(ck_fixnowing);
				sc.setCk_fixdeadline(ck_fixdeadline);
				sc.setCk_fixstart_time(ck_fixstart_time);
				sc.setCk_fixend_time(ck_fixend_time);
				sc.setCk_state("NOYET");
			}
			else
			{
				sc.setHaveopinion("无");
				sc.setCk_state("NONEED");
			}
			
			sc.setCk_completechecktablepeople(ck_completechecktablepeople);
			sc.setCk_completechecktabletime(ck_completechecktabletime);
			sc.setCk_userId(user.getUserId());
			sc.setCk_startime(ck_startime);
			sc.setCk_endtime(ck_endtime);
			
			SqlOperate<SpecialCheck> opetaterSpecialCheck=new 
					SqlOperate<SpecialCheck>(HiddenSpecialCheckAddActivity.this, SpecialCheck.class);
			opetaterSpecialCheck.saveContent(sc);
			opetaterSpecialCheck.close();
			
			for(Inspector xp:ls_expter)
			{
				//xp.setCk_id(sc.getCk_id());
				xp.setCk_id(sc.getC_id());
				SqlOperate<Inspector> opetaterInspectorX=new 
						SqlOperate<Inspector>(HiddenSpecialCheckAddActivity.this, Inspector.class);
				opetaterInspectorX.saveContent(xp);
				opetaterInspectorX.close();
			}
			
			for(Inspector ck:ls_checker)
			{
				//ck.setCk_id(sc.getCk_id());
				ck.setCk_id(sc.getC_id());
				SqlOperate<Inspector> opetaterInspectorC=new 
						SqlOperate<Inspector>(HiddenSpecialCheckAddActivity.this, Inspector.class);
				opetaterInspectorC.saveContent(ck);
				opetaterInspectorC.close();
			}
			
			UpInfo info=new UpInfo();
			info.setU_Id(UUID.randomUUID().toString());
			info.setInfoTable("SpecialCheck");
			info.setInfoId(sc.getC_id());
			info.setOperateType("add");
			info.setRemark("");
			
			for(CheckConditionItem checkConditionItem:ls_ckConItem)
	    	{
				checkConditionItem.setCheckCkId(sc.getC_id());
	    		SqlOperate<CheckConditionItem> opetatercheckConditionItem=new 
	    				SqlOperate<CheckConditionItem>(HiddenSpecialCheckAddActivity.this, CheckConditionItem.class);
	    		opetatercheckConditionItem.saveContent(checkConditionItem);
	    		opetatercheckConditionItem.close();
	    	}
			
	    	SqlOperate<UpInfo> opetaterUpInfo=new SqlOperate<UpInfo>(HiddenSpecialCheckAddActivity.this, UpInfo.class);
	    	opetaterUpInfo.saveContent(info);
	    	opetaterUpInfo.close();
			
			Toast.makeText(HiddenSpecialCheckAddActivity.this, "本地保存成功", Toast.LENGTH_SHORT).show();
			
			/*if(sc.getCk_state().equals("ING"))
			{
				Intent intent=new Intent(HiddenSpecialCheckAddActivity.this, SpecialReviewListActivity.class);
				intent.putExtra("activityFlag", 0);
	       		startActivity(intent);
			}
			else
			{
				Intent intent=new Intent(HiddenSpecialCheckAddActivity.this, SpecialReviewListActivity.class);
				intent.putExtra("activityFlag", 1);
	       		startActivity(intent);
			}*/
			
			Intent intent = new Intent();
            setResult(0, intent);
			finish();
		}
	}

	private void bigTvClick() {
		img_max_ck_scenecondition.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(HiddenSpecialCheckAddActivity.this, BigTvActivity.class);
				intent.putExtra("writecontent", et_ck_scenecondition.getText().toString());
				intent.putExtra("title", "现场检查情况");
	       		startActivityForResult(intent, 1);
			}
		});
		
		img_max_ck_fixnowing.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(HiddenSpecialCheckAddActivity.this, BigTvActivity.class);
				intent.putExtra("writecontent", et_ck_fixnowing.getText().toString());
				intent.putExtra("title", "立即整改信息");
	       		startActivityForResult(intent, 2);
			}
		});
		
		img_max_ck_fixdeadline.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(HiddenSpecialCheckAddActivity.this, BigTvActivity.class);
				intent.putExtra("writecontent", et_ck_fixdeadline.getText().toString());
				intent.putExtra("title", "限期整改情况");
	       		startActivityForResult(intent, 3);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(data!=null)
		{
			switch(requestCode){
	        case 1:
	        	et_ck_scenecondition.setText(data.getStringExtra("writecontent"));
	        	break;
	        case 2:
	        	et_ck_fixnowing.setText(data.getStringExtra("writecontent"));
	        	break;
	        case 3:
	        	et_ck_fixdeadline.setText(data.getStringExtra("writecontent"));
	        case 4:
	        	et_ck_checkeddepartment.setText(data.getStringExtra("companyName"));
	        	break;
	        case 5:
	        	fixnowingCount=0;
	        	fixdeadlineCount=0;
	        	
	        	StringBuffer fixnowingStr=new StringBuffer();
	        	StringBuffer fixdeadlineStr=new StringBuffer();
	        	
	        	ls_ckConItem = (List<CheckConditionItem>)data.getSerializableExtra("ls_ckConItem");
	        	StringBuffer sb_condition=new StringBuffer();
	        	for(int i=0;i<ls_ckConItem.size()-1;i++)
	        	{
	        		sb_condition.append(DigitalConvert.digital2China(i+1));
	        		sb_condition.append("、");
	        		sb_condition.append(ls_ckConItem.get(i).getCheckContent());
	        		sb_condition.append("\r\n");
	        		if(ls_ckConItem.get(i).getCheckItemState().equals("1"))
	        		{
	        			fixnowingCount++;
	        			fixnowingStr.append("第");
	        			fixnowingStr.append(DigitalConvert.digital2China(Integer.valueOf(ls_ckConItem.get(i).getCheckNo())));
	        			fixnowingStr.append("项");
	        			fixnowingStr.append(",");
	        		}
	        		else if(ls_ckConItem.get(i).getCheckItemState().equals("2"))
	        		{
	        			fixdeadlineCount++;
	        			fixdeadlineStr.append("第");
	        			fixdeadlineStr.append(DigitalConvert.digital2China(Integer.valueOf(ls_ckConItem.get(i).getCheckNo())));
	        			fixdeadlineStr.append("项");
	        			fixdeadlineStr.append(",");
	        		}
	        	}
	        	sb_condition.append(DigitalConvert.digital2China(ls_ckConItem.size()));
	        	sb_condition.append("、");
        		sb_condition.append(ls_ckConItem.get(ls_ckConItem.size()-1).getCheckContent());
        		if("1".equals(ls_ckConItem.get(ls_ckConItem.size()-1).getCheckItemState()))
        		{
        			fixnowingCount++;
        			fixnowingStr.append("第");
        			fixnowingStr.append(DigitalConvert.digital2China(Integer.valueOf(ls_ckConItem.get(ls_ckConItem.size()-1).getCheckNo())));
        			fixnowingStr.append("项");
        			fixnowingStr.append(",");
        		}
        		else if("2".equals(ls_ckConItem.get(ls_ckConItem.size()-1).getCheckItemState()))
        		{
        			fixdeadlineCount++;
        			fixdeadlineStr.append("第");
        			fixdeadlineStr.append(DigitalConvert.digital2China(Integer.valueOf(ls_ckConItem.get(ls_ckConItem.size()-1).getCheckNo())));
        			fixdeadlineStr.append("项");
        			fixdeadlineStr.append(",");
        		}
        		et_ck_scenecondition.setText(sb_condition.toString());
        		if(fixnowingStr.length()>0)
        		{
        			et_ck_fixnowing.setText(fixnowingStr.subSequence(0, fixnowingStr.length()-1));
        		}
        		if(fixdeadlineStr.length()>0)
        		{
        			et_ck_fixdeadline.setText(fixdeadlineStr.subSequence(0, fixdeadlineStr.length()-1));
        		}
	        	break;
	        case 10:
	        	List<Inspector> listx = (List<Inspector>)data.getSerializableExtra("ls_Inspector");  
	        	if(listx!=null)
	        	{
	        		ls_expter=listx;
	        		StringBuffer sx = new StringBuffer();
	        		for(Inspector in:listx)
	        		{
	        			sx.append(in.getInspectorname());
	        			sx.append("(");
	        			sx.append(in.getInspectorduty());
	        			sx.append(")");
	        			sx.append(",");
	        		}
	        		String sxStr=sx.substring(0, sx.length()-1);
	        		et_ck_expert.setText(sxStr);
	        	}
	        	break;
	        case 11:
	        	List<Inspector> listc = (List<Inspector>)data.getSerializableExtra("ls_Inspector");  
	        	if(listc!=null)
	        	{
	        		ls_checker=listc;
	        		StringBuffer sx = new StringBuffer();
	        		for(Inspector in:listc)
	        		{
	        			sx.append(in.getInspectorname());
	        			sx.append("(");
	        			sx.append(in.getInspectorduty());
	        			sx.append(")");
	        			sx.append(",");
	        		}
	        		String sxStr=sx.substring(0, sx.length()-1);
	        		et_ck_inspter.setText(sxStr);
	        	}
	        	break;
	    	default:
	    		break;
		}
     }
	}

	private void initComponent() {
		// TODO Auto-generated method stub
		topbar_specialcheck_add=(TopBarView) findViewById(R.id.topbar_specialcheck_add);
		tog_dc_haveopinion=(ToggleButton) findViewById(R.id.tog_dc_haveopinion);
		
		rel_ck_fixnowing=(RelativeLayout) findViewById(R.id.rel_ck_fixnowing);
		rel_ck_fixdeadline = (RelativeLayout) findViewById(R.id.rel_ck_fixdeadline);
		lin_ck_fixstart_time=(LinearLayout) findViewById(R.id.lin_ck_fixstart_time);
		lin_ck_fixend_time=(LinearLayout) findViewById(R.id.lin_ck_fixend_time);
		
		et_ck_id=(EditText) findViewById(R.id.et_ck_id);
		
		et_ck_startime=(EditText) findViewById(R.id.et_ck_startime);
		et_ck_endtime=(EditText) findViewById(R.id.et_ck_endtime);
		
		et_ck_specialcheckname=(EditText) findViewById(R.id.et_ck_specialcheckname);
		et_ck_checkingdepartment=(EditText) findViewById(R.id.et_ck_checkingdepartment);
		et_ck_checkgroupid=(EditText) findViewById(R.id.et_ck_checkgroupid);
		et_ck_leader=(EditText) findViewById(R.id.et_ck_leader);
		et_ck_position=(EditText) findViewById(R.id.et_ck_position);
		et_ck_sceneresponsible=(EditText) findViewById(R.id.et_ck_sceneresponsible);
		et_ck_duty=(EditText) findViewById(R.id.et_ck_duty);
		et_ck_telephone=(EditText) findViewById(R.id.et_ck_telephone);
		et_ck_site=(EditText) findViewById(R.id.et_ck_site);
		
		et_ck_scenecondition=(EditText) findViewById(R.id.et_ck_scenecondition);
		et_ck_fixnowing=(EditText) findViewById(R.id.et_ck_fixnowing);
		et_ck_fixdeadline=(EditText) findViewById(R.id.et_ck_fixdeadline);
		et_ck_completechecktablepeople=(EditText) findViewById(R.id.et_ck_completechecktablepeople);
		
		et_ck_expert=(ChoiceEditView) findViewById(R.id.et_ck_expert);
		et_ck_inspter=(ChoiceEditView) findViewById(R.id.et_ck_inspter);
		
		et_ck_completechecktabletime=(DateEditView) findViewById(R.id.et_ck_completechecktabletime);
		et_ck_completechecktabletime.setText(dOperate.getCurrentDate());
		et_ck_time=(DateEditView) findViewById(R.id.et_ck_time);
		et_ck_time.setText(dOperate.getCurrentDate());
		et_ck_fixstart_time=(DateEditView) findViewById(R.id.et_ck_fixstart_time);
		et_ck_fixend_time=(DateEditView) findViewById(R.id.et_ck_fixend_time);
		
		img_max_ck_scenecondition=(ImageButton) findViewById(R.id.img_max_ck_scenecondition);
		img_max_ck_scenecondition.setVisibility(View.GONE);
		img_max_ck_fixnowing=(ImageButton) findViewById(R.id.img_max_ck_fixnowing);
		img_max_ck_fixnowing.setVisibility(View.GONE);
		img_max_ck_fixdeadline=(ImageButton) findViewById(R.id.img_max_ck_fixdeadline);
		img_max_ck_fixdeadline.setVisibility(View.GONE);
		
		et_ck_checkeddepartment=(ChoiceEditView) findViewById(R.id.et_ck_checkeddepartment);
		
		Calendar cal = Calendar.getInstance();
		final int hour = cal.get(Calendar.HOUR_OF_DAY);
		final int minute = cal.get(Calendar.MINUTE);
		et_ck_startime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TimePickerDialog timePickerDialogS = new TimePickerDialog(HiddenSpecialCheckAddActivity.this, new TimePickerDialog.OnTimeSetListener()
                {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
                    {
                    	StringBuffer sb_stime=new StringBuffer();
                    	if(hourOfDay<10)
						{
                			if(minute<10)
    						{
                				sb_stime.append("0"+hourOfDay + ":0"+ minute);
    						}
    						else
    						{
    							sb_stime.append("0"+hourOfDay + ":"+ minute);
    						}
						}
						else
						{
							if(minute<10)
    						{
                				sb_stime.append(hourOfDay + ":0"+ minute);
    						}
    						else
    						{
    							sb_stime.append(hourOfDay + ":"+ minute);
    						}
						}
                		et_ck_startime.setText(sb_stime);
                    }
                }, hour, minute, true);
				timePickerDialogS.show();
			}
		});
		et_ck_endtime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				TimePickerDialog timePickerDialogE = new TimePickerDialog(HiddenSpecialCheckAddActivity.this, new TimePickerDialog.OnTimeSetListener()
                {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
                    {
                    	 StringBuffer sb_etime=new StringBuffer();
                 		if(hourOfDay<10)
 						{
                 			if(minute<10)
     						{
                 				sb_etime.append("0"+hourOfDay + ":0"+ minute);
     						}
     						else
     						{
     							sb_etime.append("0"+hourOfDay + ":"+ minute);
     						}
 						}
 						else
 						{
 							if(minute<10)
     						{
 								sb_etime.append(hourOfDay + ":0"+ minute);
     						}
     						else
     						{
     							sb_etime.append(hourOfDay + ":"+ minute);
     						}
 						}
                 		et_ck_endtime.setText(sb_etime);
 					
                    }
                }, hour, minute, true);
				timePickerDialogE.show();
			
			}
		});
		
		et_ck_scenecondition.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(HiddenSpecialCheckAddActivity.this, HiddenCheckConditionAddActivity.class);
				intent.putExtra("ls_ckConItem", (Serializable)ls_ckConItem);
				startActivityForResult(intent,5);
			}
		});
		
		et_ck_fixnowing.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				if(ls_ckConItem.size()==0)
				{
					Toast.makeText(HiddenSpecialCheckAddActivity.this, "请先填写检查情况", Toast.LENGTH_SHORT).show();
				}
				else
				{
					String[] items = new String[ls_ckConItem.size()-fixdeadlineCount];
					final String[] itemIDs = new String[ls_ckConItem.size()-fixdeadlineCount];
					final boolean[] items_check = new boolean[ls_ckConItem.size()-fixdeadlineCount];
					int m=0;
					for(int i=0;i<ls_ckConItem.size();i++)
		        	{
		        		if(!ls_ckConItem.get(i).getCheckItemState().equals("2"))
		        		{
		        			StringBuffer sb_condition=new StringBuffer();
		        			sb_condition.append(ls_ckConItem.get(i).getCheckNo());
			        		sb_condition.append("、");
			        		sb_condition.append(ls_ckConItem.get(i).getCheckContent());
			        		items[m]=sb_condition.toString();
			        		itemIDs[m]=ls_ckConItem.get(i).getCheckNo();
			        		if(ls_ckConItem.get(i).getCheckItemState().equals("1"))
			        		{
			        			items_check[m]=true;
			        		}
			        		else
			        		{
			        			items_check[m]=false;
			        		}
			        		m++;
		        		}
		        	}
			        AlertDialog.Builder builder=new AlertDialog.Builder(HiddenSpecialCheckAddActivity.this);  //先得到构造器  
			        builder.setTitle("立即整改选取"); //设置标题  
			        builder.setMultiChoiceItems(items,items_check,new DialogInterface.OnMultiChoiceClickListener() {  
			            @Override  
			            public void onClick(DialogInterface dialog, int which, boolean isChecked) {  
			            	String no=itemIDs[which];
			            	for(int i=0;i<ls_ckConItem.size();i++)
				        	{
				        		if(ls_ckConItem.get(i).getCheckNo().equals(no))
				        		{
				        			if(isChecked)
				        			{
				        				ls_ckConItem.get(i).setCheckItemState("1");
				        				fixnowingCount++;
				        			}
				        			else
				        			{
				        				ls_ckConItem.get(i).setCheckItemState("0");
				        				fixnowingCount--;
				        			}
				        			break;
				        		}
				        	}
			            }  
			        });  
			        builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {  
			            @Override  
			            public void onClick(DialogInterface dialog, int which) {  
			            	StringBuffer sb_checked=new StringBuffer();
			            	for(int i=0;i<ls_ckConItem.size();i++)
				        	{
				        		if(ls_ckConItem.get(i).getCheckItemState().equals("1"))
				        		{
				        			sb_checked.append("第");
				        			sb_checked.append(DigitalConvert.digital2China(Integer.valueOf(ls_ckConItem.get(i).getCheckNo())));
			            			sb_checked.append("项");
			            			sb_checked.append(",");
				        		}
				        	}
			            	if(sb_checked.length()>0)
			            	{
			            		et_ck_fixnowing.setText(sb_checked.subSequence(0, sb_checked.length()-1));
			            	}
			            	else
			            	{
			            		et_ck_fixnowing.setText("");
			            	}
			                dialog.dismiss();  
			            }  
			        });  
			        builder.create().show();  
				}
			}
		});

		et_ck_fixdeadline.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {


				if(ls_ckConItem.size()==0)
				{
					Toast.makeText(HiddenSpecialCheckAddActivity.this, "请先填写检查情况", Toast.LENGTH_SHORT).show();
				}
				else
				{
					String[] items = new String[ls_ckConItem.size()-fixnowingCount];
					final String[] itemIDs = new String[ls_ckConItem.size()-fixnowingCount];
					final boolean[] items_check = new boolean[ls_ckConItem.size()-fixnowingCount];
					int m=0;
					for(int i=0;i<ls_ckConItem.size();i++)
		        	{
		        		if(!ls_ckConItem.get(i).getCheckItemState().equals("1"))
		        		{
		        			StringBuffer sb_condition=new StringBuffer();
		        			sb_condition.append(ls_ckConItem.get(i).getCheckNo());
			        		sb_condition.append("、");
			        		sb_condition.append(ls_ckConItem.get(i).getCheckContent());
			        		items[m]=sb_condition.toString();
			        		itemIDs[m]=ls_ckConItem.get(i).getCheckNo();
			        		if(ls_ckConItem.get(i).getCheckItemState().equals("2"))
			        		{
			        			items_check[m]=true;
			        		}
			        		else
			        		{
			        			items_check[m]=false;
			        		}
			        		m++;
		        		}
		        	}
			        AlertDialog.Builder builder=new AlertDialog.Builder(HiddenSpecialCheckAddActivity.this);  //先得到构造器  
			        builder.setTitle("立即整改选取"); //设置标题  
			        builder.setMultiChoiceItems(items,items_check,new DialogInterface.OnMultiChoiceClickListener() {  
			            @Override  
			            public void onClick(DialogInterface dialog, int which, boolean isChecked) {  
			            	String no=itemIDs[which];
			            	for(int i=0;i<ls_ckConItem.size();i++)
				        	{
				        		if(ls_ckConItem.get(i).getCheckNo().equals(no))
				        		{
				        			if(isChecked)
				        			{
				        				ls_ckConItem.get(i).setCheckItemState("2");
				        				fixdeadlineCount++;
				        			}
				        			else
				        			{
				        				ls_ckConItem.get(i).setCheckItemState("0");
				        				fixdeadlineCount--;
				        			}
				        			break;
				        		}
				        	}
			            }  
			        });  
			        builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {  
			            @Override  
			            public void onClick(DialogInterface dialog, int which) {  
			            	StringBuffer sb_checked=new StringBuffer();
			            	for(int i=0;i<ls_ckConItem.size();i++)
				        	{
				        		if(ls_ckConItem.get(i).getCheckItemState().equals("2"))
				        		{
				        			sb_checked.append("第");
			            			sb_checked.append(DigitalConvert.digital2China(Integer.valueOf(ls_ckConItem.get(i).getCheckNo())));
			            			sb_checked.append("项");
			            			sb_checked.append(",");
				        		}
				        	}
			            	if(sb_checked.length()>0)
			            	{
			            		et_ck_fixdeadline.setText(sb_checked.subSequence(0, sb_checked.length()-1));
			            	}
			            	else
			            	{
			            		et_ck_fixdeadline.setText("");
			            	}
			                dialog.dismiss();  
			            }  
			        });  
			        builder.create().show();  
				}
			}
		});
		
		et_ck_checkeddepartment.setOnClickEditListener(new onClickEditListener() {
			
			@Override
			public void onClickEdit() {
				// TODO Auto-generated method stub
				Intent intent=new Intent(HiddenSpecialCheckAddActivity.this, HiddenCompanyChoiceActivity.class);
	       		startActivityForResult(intent, 4);
			}
		});
		
		et_ck_expert.setOnClickEditListener(new onClickEditListener() {
			
			@Override
			public void onClickEdit() {
				// TODO Auto-generated method stub
				
				ArrayList<Inspector> List_xp = new ArrayList<Inspector>();  
				for(Inspector in:ls_expter)
				{
					List_xp.add(in);
				}
				
				Intent intent=new Intent(HiddenSpecialCheckAddActivity.this, HiddenInspterAddActivity.class);
				intent.putExtra("insptertype", "专家");
				intent.putExtra("insptertitle", "检查专家信息");
				intent.putExtra("infoList",List_xp);  
	       		startActivityForResult(intent, 10);
			}
		});

		et_ck_inspter.setOnClickEditListener(new onClickEditListener() {
			
			@Override
			public void onClickEdit() {
				// TODO Auto-generated method stub
				
				ArrayList<Inspector> List_ck = new ArrayList<Inspector>();  
				for(Inspector in:ls_checker)
				{
					List_ck.add(in);
				}
				
				Intent intent=new Intent(HiddenSpecialCheckAddActivity.this, HiddenInspterAddActivity.class);
				intent.putExtra("insptertype", "检查人员");
				intent.putExtra("insptertitle", "检查人员信息");
				intent.putExtra("infoList",List_ck); 
		   		startActivityForResult(intent, 11);
			}
		});
		
		tog_dc_haveopinion.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked)
				{
					rel_ck_fixnowing.setVisibility(View.VISIBLE);
					rel_ck_fixdeadline.setVisibility(View.VISIBLE);
					lin_ck_fixstart_time.setVisibility(View.VISIBLE);
					lin_ck_fixend_time.setVisibility(View.VISIBLE);
				}
				else
				{
					rel_ck_fixnowing.setVisibility(View.GONE);
					rel_ck_fixdeadline.setVisibility(View.GONE);
					lin_ck_fixstart_time.setVisibility(View.GONE);
					lin_ck_fixend_time.setVisibility(View.GONE);
				}
			}
		});
		
	}
}


