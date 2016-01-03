package com.sunnyit.enforcement.action;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.activity.BigTvActivity;
import com.sunnyit.common.activity.ExpandListActivity;
import com.sunnyit.common.activity.ImgShowActivity;
import com.sunnyit.common.convert.DigitalConvert;
import com.sunnyit.common.dataOperate.dOperate;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.ChoiceEditView;
import com.sunnyit.common.view.DateEditView;
import com.sunnyit.common.view.DateTimeEditView;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.ChoiceEditView.onClickEditListener;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.company.action.CompanyInfoAddActivity;
import com.sunnyit.company.model.SimpleEnterprise;
import com.sunnyit.enforcement.model.CheckConditionItem;
import com.sunnyit.enforcement.model.DailyCheck;
import com.sunnyit.enforcement.model.Inspector;
import com.sunnyit.synchronous.model.UpInfo;
import com.sunnyit.system.model.User;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import test.action.DialogShowActivity;
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
public class HiddenDailyCheckAddActivity extends BaseActivity {
	
	private TopBarView topbar_dailycheck_add;
	
	private ToggleButton tog_dc_haveopinion;
	
	private RelativeLayout rel_ck_fixnowing;
	private RelativeLayout rel_ck_fixdeadline;
	private LinearLayout lin_ck_fixstart_time;
	private LinearLayout lin_ck_fixend_time;
	
	private EditText et_ck_id,et_ck_checkingdepartment;
	private EditText et_ck_checkPlace,et_ck_sceneresponsible,et_ck_position,et_ck_telephone;
	private EditText et_ck_scenecondition,et_ck_fixnowing,et_ck_fixdeadline;
	private EditText et_ck_checkpeople,et_ck_completechecktablepeople;
	private EditText et_ck_startime,et_ck_endtime;
	
	private DateEditView et_ck_time;
	
	private DateEditView et_ck_fixstart_time;
	private DateEditView et_ck_fixend_time,et_ck_completechecktabletime;
	
	private ChoiceEditView et_ck_checkeddepartment;
	
	private ImageButton img_max_ck_scenecondition,img_max_ck_fixnowing,img_max_ck_fixdeadline;
	
	private SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
	
	private int code;
	
	private User user;
	
	private List<CheckConditionItem> ls_ckConItem;
	private int fixnowingCount=0;
	private int fixdeadlineCount=0;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hidden_dailycheck_add);
		
		ls_ckConItem=new ArrayList<CheckConditionItem>();
		
		initComponent();
		
		user=getCurrentUser();
		
		et_ck_checkingdepartment.setText(user.getDepartmentName());
		et_ck_checkingdepartment.setEnabled(false);
		
		code=sp.getInt(df.format(new Date()).toString(), 0);
		if(code==0)
		{
			code++;
			Editor edit=sp.edit();
			edit.putInt(df.format(new Date()).toString(), code);
			edit.commit(); 
		}
		else
		{
			code++;
		}
		
		et_ck_id.setText(df.format(new Date()).toString()+"000"+String.valueOf(code));
		
		et_ck_checkpeople.setText(sp.getString("dc_ck_checkpeople", ""));
		et_ck_completechecktablepeople.setText(sp.getString("dc_ck_completechecktablepeople", ""));
		
		topbar_dailycheck_add.setTopBarClick(new ITopBarClick() {
			
			@Override
			public void onClickRightBut() {
				// TODO Auto-generated method stub
				addDailyCheckInfo();
			}
			
			@Override
			public void onClickLeftBut() {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		bigTvClick();
		
	}
	
	private void addDailyCheckInfo() {
		// TODO Auto-generated method stub
		
		String ck_id=et_ck_id.getText().toString().trim();
		String ck_checkingdepartment=et_ck_checkingdepartment.getText().toString().trim();
		String ck_checkeddepartment=et_ck_checkeddepartment.getText().toString().trim();
		String ck_checkPlace=et_ck_checkPlace.getText().toString().trim();
		String ck_sceneresponsible=et_ck_sceneresponsible.getText().toString().trim();
		String ck_position=et_ck_position.getText().toString().trim();
		String ck_telephone=et_ck_telephone.getText().toString().trim();
		String ck_scenecondition=et_ck_scenecondition.getText().toString().trim();
		String ck_fixnowing=et_ck_fixnowing.getText().toString().trim();
		String ck_fixdeadline=et_ck_fixdeadline.getText().toString().trim();
		String ck_checkpeople=et_ck_checkpeople.getText().toString().trim();
		String ck_completechecktablepeople=et_ck_completechecktablepeople.getText().toString().trim();
		String ck_time=et_ck_time.getText().toString().trim();
		String ck_fixstart_time=et_ck_fixstart_time.getText().toString().trim();
		String ck_fixend_time=et_ck_fixend_time.getText().toString().trim();
		String ck_completechecktabletime=et_ck_completechecktabletime.getText().toString().trim();
		
		String ck_startime=et_ck_startime.getText().toString().trim();
		String ck_endtime=et_ck_endtime.getText().toString().trim();
		
		if(ck_checkeddepartment.equals(""))
		{
			Toast.makeText(HiddenDailyCheckAddActivity.this, "被检查企业不能为空", Toast.LENGTH_SHORT).show();
		}
		else if(ck_completechecktabletime.equals(""))
		{
			Toast.makeText(HiddenDailyCheckAddActivity.this, "填表时间不能为空", Toast.LENGTH_SHORT).show();
		}
		else if(ck_completechecktablepeople.equals(""))
		{
			Toast.makeText(HiddenDailyCheckAddActivity.this, "填表人不能为空", Toast.LENGTH_SHORT).show();
		}
		else
		{
			DailyCheck dc=new DailyCheck();
			dc.setC_id(UUID.randomUUID().toString());
			dc.setCk_id(ck_id);
			dc.setCk_checkingdepartment(ck_checkingdepartment);
			dc.setCk_checkeddepartment(ck_checkeddepartment);
			dc.setCk_checkPlace(ck_checkPlace);
			dc.setCk_sceneresponsible(ck_sceneresponsible);
			dc.setCk_position(ck_position);
			dc.setCk_telephone(ck_telephone);
			dc.setCk_scenecondition(ck_scenecondition);
			dc.setCk_telephone(ck_telephone);
			dc.setCk_scenecondition(ck_scenecondition);
			if(tog_dc_haveopinion.isChecked())
			{
				dc.setHaveopinion("有");
				dc.setCk_fixnowing(ck_fixnowing);
				dc.setCk_fixdeadline(ck_fixdeadline);
				dc.setCk_fixstart_time(ck_fixstart_time);
				dc.setCk_fixend_time(ck_fixend_time);
				dc.setCk_state("NOTYET");
			}
			else
			{
				dc.setHaveopinion("无");
				dc.setCk_state("NONEED");
				dc.setCk_fixstart_time("");
				dc.setCk_fixend_time("");
			}
			
			dc.setCk_checkpeople(ck_checkpeople);
			dc.setCk_completechecktablepeople(ck_completechecktablepeople);
			dc.setCk_completechecktabletime(ck_completechecktabletime);
			dc.setCk_time(ck_time);
			dc.setCk_userId(user.getUserId());
			dc.setCk_startime(ck_startime);
			dc.setCk_endtime(ck_endtime);
			
			SqlOperate<DailyCheck> opetaterDailyCheck=new SqlOperate<DailyCheck>(HiddenDailyCheckAddActivity.this, DailyCheck.class);
			opetaterDailyCheck.saveContent(dc);
			opetaterDailyCheck.close();
			
			UpInfo info=new UpInfo();
			info.setU_Id(UUID.randomUUID().toString());
			info.setInfoTable("DailyCheck");
			info.setInfoId(dc.getC_id());
			info.setOperateType("add");
			info.setRemark("");
			
	    	SqlOperate<UpInfo> opetaterUpInfo=new SqlOperate<UpInfo>(HiddenDailyCheckAddActivity.this, UpInfo.class);
	    	opetaterUpInfo.saveContent(info);
	    	opetaterUpInfo.close();
	    	
	    	for(CheckConditionItem checkConditionItem:ls_ckConItem)
	    	{
	    		checkConditionItem.setCheckCkId(dc.getC_id());
	    		SqlOperate<CheckConditionItem> opetatercheckConditionItem=new 
	    				SqlOperate<CheckConditionItem>(HiddenDailyCheckAddActivity.this, CheckConditionItem.class);
	    		opetatercheckConditionItem.saveContent(checkConditionItem);
	    		opetatercheckConditionItem.close();
	    	}
	    	
	    	Editor edit=sp.edit();
			edit.putString("dc_ck_checkpeople", ck_checkpeople);
			edit.putString("dc_ck_completechecktablepeople", ck_completechecktablepeople);
			edit.commit(); 
			
			Toast.makeText(HiddenDailyCheckAddActivity.this, "本地保存成功", Toast.LENGTH_SHORT).show();
			code++;
			
			/*if(dc.getCk_state().equals("ING"))
			{
				Intent intent=new Intent(HiddenDailyCheckAddActivity.this, DailyReviewListActivity.class);
				intent.putExtra("activityFlag", 0);
	       		startActivity(intent);
			}
			else
			{
				Intent intent=new Intent(HiddenDailyCheckAddActivity.this, DailyReviewListActivity.class);
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
				Intent intent=new Intent(HiddenDailyCheckAddActivity.this, BigTvActivity.class);
				intent.putExtra("writecontent", et_ck_scenecondition.getText().toString());
				intent.putExtra("title", "现场检查情况");
	       		startActivityForResult(intent, 1);
			}
		});
		
		img_max_ck_fixnowing.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(HiddenDailyCheckAddActivity.this, BigTvActivity.class);
				intent.putExtra("writecontent", et_ck_fixnowing.getText().toString());
				intent.putExtra("title", "立即整改信息");
	       		startActivityForResult(intent, 2);
			}
		});
		
		img_max_ck_fixdeadline.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(HiddenDailyCheckAddActivity.this, BigTvActivity.class);
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
	        	break;
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
	    	default:
	    		break;
		}
     }
	}

	private void initComponent() {
		// TODO Auto-generated method stub
		topbar_dailycheck_add=(TopBarView) findViewById(R.id.topbar_dailycheck_add);
		tog_dc_haveopinion=(ToggleButton) findViewById(R.id.tog_dc_haveopinion);
		
		rel_ck_fixnowing=(RelativeLayout) findViewById(R.id.rel_ck_fixnowing);
		rel_ck_fixdeadline = (RelativeLayout) findViewById(R.id.rel_ck_fixdeadline);
		lin_ck_fixstart_time=(LinearLayout) findViewById(R.id.lin_ck_fixstart_time);
		lin_ck_fixend_time=(LinearLayout) findViewById(R.id.lin_ck_fixend_time);
		
		et_ck_id=(EditText) findViewById(R.id.et_ck_id);
		
		et_ck_startime=(EditText) findViewById(R.id.et_ck_startime);
		et_ck_endtime=(EditText) findViewById(R.id.et_ck_endtime);
		
		et_ck_checkingdepartment=(EditText) findViewById(R.id.et_ck_checkingdepartment);
		
		et_ck_checkPlace=(EditText) findViewById(R.id.et_ck_checkPlace);
		et_ck_sceneresponsible=(EditText) findViewById(R.id.et_ck_sceneresponsible);
		et_ck_position=(EditText) findViewById(R.id.et_ck_position);
		et_ck_telephone=(EditText) findViewById(R.id.et_ck_telephone);
		et_ck_scenecondition=(EditText) findViewById(R.id.et_ck_scenecondition);
		et_ck_fixnowing=(EditText) findViewById(R.id.et_ck_fixnowing);
		et_ck_fixdeadline=(EditText) findViewById(R.id.et_ck_fixdeadline);
		et_ck_checkpeople=(EditText) findViewById(R.id.et_ck_checkpeople);
		et_ck_completechecktablepeople=(EditText) findViewById(R.id.et_ck_completechecktablepeople);
		
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
		
		Calendar cal = Calendar.getInstance();
		final int hour = cal.get(Calendar.HOUR_OF_DAY);
		final int minute = cal.get(Calendar.MINUTE);
		
		et_ck_startime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TimePickerDialog timePickerDialogS = new TimePickerDialog(HiddenDailyCheckAddActivity.this, new TimePickerDialog.OnTimeSetListener()
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

				TimePickerDialog timePickerDialogE = new TimePickerDialog(HiddenDailyCheckAddActivity.this, new TimePickerDialog.OnTimeSetListener()
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
		
		et_ck_checkeddepartment=(ChoiceEditView) findViewById(R.id.et_ck_checkeddepartment);
		
		et_ck_checkeddepartment.setOnClickEditListener(new onClickEditListener() {
			
			@Override
			public void onClickEdit() {
				// TODO Auto-generated method stub
				Intent intent=new Intent(HiddenDailyCheckAddActivity.this, HiddenCompanyChoiceActivity.class);
	       		startActivityForResult(intent, 4);
			}
		});
		
		et_ck_scenecondition.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(HiddenDailyCheckAddActivity.this, HiddenCheckConditionAddActivity.class);
				intent.putExtra("ls_ckConItem", (Serializable)ls_ckConItem);
				startActivityForResult(intent,5);
			}
		});
		
		et_ck_fixnowing.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(ls_ckConItem.size()==0)
				{
					Toast.makeText(HiddenDailyCheckAddActivity.this, "请先填写检查情况", Toast.LENGTH_SHORT).show();
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
			        AlertDialog.Builder builder=new AlertDialog.Builder(HiddenDailyCheckAddActivity.this);  //先得到构造器  
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
					Toast.makeText(HiddenDailyCheckAddActivity.this, "请先填写检查情况", Toast.LENGTH_SHORT).show();
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
			        AlertDialog.Builder builder=new AlertDialog.Builder(HiddenDailyCheckAddActivity.this);  //先得到构造器  
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


