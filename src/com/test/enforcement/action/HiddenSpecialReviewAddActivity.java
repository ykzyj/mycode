package com.sunnyit.enforcement.action;

import java.util.List;
import java.util.UUID;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.dataOperate.dOperate;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.DateEditView;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.enforcement.model.DailyCheck;
import com.sunnyit.enforcement.model.SpecialCheck;
import com.sunnyit.synchronous.model.UpInfo;
import com.sunnyit.system.model.User;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import test.action.DialogShowActivity;

/**   
* @Title: HiddenList.java 
* @Package com.sunnyit.hiddencheck.action 
* @Description: TODO
* @author yk
* @date 2015年9月7日 下午3:27:12 
* @version V1.0   
*/
public class HiddenSpecialReviewAddActivity extends BaseActivity {
	
	private TopBarView topbar_dailycheck_review_add;
	
	private EditText et_ck_reviewcondition,et_ck_reviewdepartment;
	private EditText et_ck_reviewprople,et_ck_reviewopinion;
	private EditText et_ck_completereviewtablepeople;
	
	private DateEditView et_ck_completereviewtabletime,et_ck_reviewtime;
	
	private SpecialCheck mSpecialCheck;
	
	private User mUser;
	
	private int index;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hidden_review_add);
		
		mSpecialCheck=(SpecialCheck) getIntent().getSerializableExtra("SpecialCheck");
		index=getIntent().getIntExtra("index", 0);
		
		mUser=getCurrentUser();
		
		initComponent();
		
		topbar_dailycheck_review_add.setTopBarClick(new ITopBarClick() {
			
			@Override
			public void onClickRightBut() {	
				
				 String ck_reviewcondition=et_ck_reviewcondition.getText().toString().trim();           				//现场复查情况
				 String ck_reviewopinion=et_ck_reviewopinion.getText().toString().trim();             					//复查意见
				 String ck_reviewdepartment=et_ck_reviewdepartment.getText().toString().trim();          				//复查部门
				 String ck_reviewtime=et_ck_reviewtime.getText().toString().trim();                						//复查时间
				 String ck_reviewprople=et_ck_reviewprople.getText().toString().trim();              					//复查人员
				 String ck_completereviewtablepeople=et_ck_completereviewtablepeople.getText().toString().trim(); 		//填表人
				 String ck_completereviewtabletime=et_ck_completereviewtabletime.getText().toString().trim();   		//填表时间
				 
				 if("".equals(ck_reviewcondition))
				 {
					 Toast.makeText(HiddenSpecialReviewAddActivity.this, "现场复查情况不能为空", Toast.LENGTH_SHORT).show();  
				 }
				 else if("".equals(ck_reviewcondition))
				 {
					 Toast.makeText(HiddenSpecialReviewAddActivity.this, "复查意见不能为空", Toast.LENGTH_SHORT).show();  
				 }
				 else
				 {
					 mSpecialCheck.setCk_reviewcondition(ck_reviewcondition);
					 mSpecialCheck.setCk_reviewopinion(ck_reviewopinion);
					 mSpecialCheck.setCk_reviewdepartment(ck_reviewdepartment);
					 mSpecialCheck.setCk_reviewtime(ck_reviewtime);
					 mSpecialCheck.setCk_reviewprople(ck_reviewprople);
					 mSpecialCheck.setCk_completereviewtablepeople(ck_completereviewtablepeople);
					 mSpecialCheck.setCk_completereviewtabletime(ck_completereviewtabletime);
					 
					 AlertDialog.Builder builder=new AlertDialog.Builder(HiddenSpecialReviewAddActivity.this);  //先得到构造器  
				        builder.setTitle("提示"); //设置标题  
				        builder.setMessage("复查是否合格?"); //设置内容  
				        builder.setPositiveButton("合格", new DialogInterface.OnClickListener() { //设置确定按钮  
				            @Override  
				            public void onClick(DialogInterface dialog, int which) {  
				                dialog.dismiss(); //关闭dialog  
				                
				                AlertDialog.Builder builder=new AlertDialog.Builder(HiddenSpecialReviewAddActivity.this);  //先得到构造器  
						        builder.setTitle("提示"); //设置标题  
						        builder.setMessage("是否销号?"); //设置内容  
						        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() { //设置确定按钮  
						            @Override  
						            public void onClick(DialogInterface dialog, int which) {  
						                dialog.dismiss(); //关闭dialog  
						                
						                //DESTORY----销号
						                mSpecialCheck.setCk_state("DESTORY");
						                reviewOperate();
						                
						            }

						        });  
						        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮  
						            @Override  
						            public void onClick(DialogInterface dialog, int which) {  
						                dialog.dismiss();  
						                
						                //REVIEWED----已复查
						                mSpecialCheck.setCk_state("REVIEWED");
						                reviewOperate();
						            }  
						        });  
						        //参数都设置完成了，创建并显示出来  
						        builder.create().show(); 
						        
				            }  
				        });  
				        builder.setNegativeButton("不合格", new DialogInterface.OnClickListener() { //设置取消按钮  
				            @Override  
				            public void onClick(DialogInterface dialog, int which) {  
				                dialog.dismiss();  
				                
				                AlertDialog.Builder builder=new AlertDialog.Builder(HiddenSpecialReviewAddActivity.this);  //先得到构造器  
						        builder.setTitle("提示"); //设置标题  
						        builder.setMessage("是否直接立案?"); //设置内容  
						        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() { //设置确定按钮  
						            @Override  
						            public void onClick(DialogInterface dialog, int which) {  
						                dialog.dismiss(); //关闭dialog  
						                
						              //RECORDED----已立案
						                mSpecialCheck.setCk_state("RECORDED");
						                reviewOperate();
						                
						            }  
						        });  
						        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮  
						            @Override  
						            public void onClick(DialogInterface dialog, int which) {  
						                dialog.dismiss();  
						                
						              //REVIEWING----复查中
						                mSpecialCheck.setCk_state("REVIEWING");
						                reviewOperate();
						                
						            }  
						        });  
						        //参数都设置完成了，创建并显示出来  
						        builder.create().show(); 
						        
				            }  
				        });  
				        //参数都设置完成了，创建并显示出来  
				        builder.create().show(); 
				 }
			}
			
			@Override
			public void onClickLeftBut() {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		topbar_dailycheck_review_add.setTitle("专项检查复查信息添加");
		
	}
	
	private void reviewOperate() {

		String sqlStr=" update SpecialCheck set ck_reviewcondition='"+mSpecialCheck.getCk_reviewcondition()+"' ,ck_reviewopinion='"+mSpecialCheck.getCk_reviewopinion()+"'"
				+ " , ck_reviewdepartment='"+mSpecialCheck.getCk_reviewdepartment()+"' , ck_reviewtime='"+mSpecialCheck.getCk_reviewtime()+"' "
						+ " , ck_reviewprople='"+mSpecialCheck.getCk_reviewprople()+"' , ck_completereviewtablepeople='"+mSpecialCheck.getCk_completereviewtablepeople()+"' "
								+ " , ck_completereviewtabletime='"+mSpecialCheck.getCk_completechecktabletime()+"' , ck_state='"+mSpecialCheck.getCk_state()+"' "
				+ " where c_id='"+mSpecialCheck.getC_id()+"' ";
		SqlOperate<DailyCheck> opetaterDailyCheck=new SqlOperate<DailyCheck>(HiddenSpecialReviewAddActivity.this, DailyCheck.class);
		opetaterDailyCheck.execMySQL(sqlStr);
        opetaterDailyCheck.close();
		
		SqlOperate<UpInfo> opetaterUpInfo=new SqlOperate<UpInfo>(HiddenSpecialReviewAddActivity.this, UpInfo.class);
		String conditionSqlStr="  where InfoId='"+mSpecialCheck.getC_id()+"' ";
    	List<UpInfo> ls_in=opetaterUpInfo.SelectEntitysByCondition(conditionSqlStr);
    	if(ls_in.size()==0)
    	{

    		UpInfo info=new UpInfo();
    		info.setU_Id(UUID.randomUUID().toString());
    		info.setInfoTable("SpecialCheck");
    		info.setInfoId(mSpecialCheck.getC_id());
    		info.setOperateType("addreview");
    		info.setRemark("");
    		opetaterUpInfo.saveContent(info);
    	}
    	opetaterUpInfo.close();
		
		//本地保存
    	Toast.makeText(HiddenSpecialReviewAddActivity.this, "数据本地保存成功！", Toast.LENGTH_SHORT).show();
    	Intent intent = new Intent();
        intent.putExtra("ck_state", mSpecialCheck.getCk_state());
        intent.putExtra("index", index);
        setResult(3, intent);
		finish();
	}  
	
	private void initComponent() {
		// TODO Auto-generated method stub
		topbar_dailycheck_review_add=(TopBarView) findViewById(R.id.topbar_dailycheck_review_add);
		
		et_ck_reviewcondition=(EditText) findViewById(R.id.et_ck_reviewcondition);
		et_ck_reviewdepartment=(EditText) findViewById(R.id.et_ck_reviewdepartment);
		et_ck_reviewprople=(EditText) findViewById(R.id.et_ck_reviewprople);
		et_ck_reviewopinion=(EditText) findViewById(R.id.et_ck_reviewopinion);
		et_ck_completereviewtablepeople=(EditText) findViewById(R.id.et_ck_completereviewtablepeople);
		
		et_ck_reviewtime=(DateEditView) findViewById(R.id.et_ck_reviewtime);
		et_ck_completereviewtabletime=(DateEditView) findViewById(R.id.et_ck_completereviewtabletime);
		
		et_ck_reviewdepartment.setText(mUser.getDepartmentName()); 
		et_ck_reviewtime.setText(dOperate.getCurrentDate());
		et_ck_completereviewtabletime.setText(dOperate.getCurrentDate());
	}
	
}


