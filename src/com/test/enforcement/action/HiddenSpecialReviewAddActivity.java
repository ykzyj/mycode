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
* @date 2015��9��7�� ����3:27:12 
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
				
				 String ck_reviewcondition=et_ck_reviewcondition.getText().toString().trim();           				//�ֳ��������
				 String ck_reviewopinion=et_ck_reviewopinion.getText().toString().trim();             					//�������
				 String ck_reviewdepartment=et_ck_reviewdepartment.getText().toString().trim();          				//���鲿��
				 String ck_reviewtime=et_ck_reviewtime.getText().toString().trim();                						//����ʱ��
				 String ck_reviewprople=et_ck_reviewprople.getText().toString().trim();              					//������Ա
				 String ck_completereviewtablepeople=et_ck_completereviewtablepeople.getText().toString().trim(); 		//�����
				 String ck_completereviewtabletime=et_ck_completereviewtabletime.getText().toString().trim();   		//���ʱ��
				 
				 if("".equals(ck_reviewcondition))
				 {
					 Toast.makeText(HiddenSpecialReviewAddActivity.this, "�ֳ������������Ϊ��", Toast.LENGTH_SHORT).show();  
				 }
				 else if("".equals(ck_reviewcondition))
				 {
					 Toast.makeText(HiddenSpecialReviewAddActivity.this, "�����������Ϊ��", Toast.LENGTH_SHORT).show();  
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
					 
					 AlertDialog.Builder builder=new AlertDialog.Builder(HiddenSpecialReviewAddActivity.this);  //�ȵõ�������  
				        builder.setTitle("��ʾ"); //���ñ���  
				        builder.setMessage("�����Ƿ�ϸ�?"); //��������  
				        builder.setPositiveButton("�ϸ�", new DialogInterface.OnClickListener() { //����ȷ����ť  
				            @Override  
				            public void onClick(DialogInterface dialog, int which) {  
				                dialog.dismiss(); //�ر�dialog  
				                
				                AlertDialog.Builder builder=new AlertDialog.Builder(HiddenSpecialReviewAddActivity.this);  //�ȵõ�������  
						        builder.setTitle("��ʾ"); //���ñ���  
						        builder.setMessage("�Ƿ�����?"); //��������  
						        builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() { //����ȷ����ť  
						            @Override  
						            public void onClick(DialogInterface dialog, int which) {  
						                dialog.dismiss(); //�ر�dialog  
						                
						                //DESTORY----����
						                mSpecialCheck.setCk_state("DESTORY");
						                reviewOperate();
						                
						            }

						        });  
						        builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() { //����ȡ����ť  
						            @Override  
						            public void onClick(DialogInterface dialog, int which) {  
						                dialog.dismiss();  
						                
						                //REVIEWED----�Ѹ���
						                mSpecialCheck.setCk_state("REVIEWED");
						                reviewOperate();
						            }  
						        });  
						        //��������������ˣ���������ʾ����  
						        builder.create().show(); 
						        
				            }  
				        });  
				        builder.setNegativeButton("���ϸ�", new DialogInterface.OnClickListener() { //����ȡ����ť  
				            @Override  
				            public void onClick(DialogInterface dialog, int which) {  
				                dialog.dismiss();  
				                
				                AlertDialog.Builder builder=new AlertDialog.Builder(HiddenSpecialReviewAddActivity.this);  //�ȵõ�������  
						        builder.setTitle("��ʾ"); //���ñ���  
						        builder.setMessage("�Ƿ�ֱ������?"); //��������  
						        builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() { //����ȷ����ť  
						            @Override  
						            public void onClick(DialogInterface dialog, int which) {  
						                dialog.dismiss(); //�ر�dialog  
						                
						              //RECORDED----������
						                mSpecialCheck.setCk_state("RECORDED");
						                reviewOperate();
						                
						            }  
						        });  
						        builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() { //����ȡ����ť  
						            @Override  
						            public void onClick(DialogInterface dialog, int which) {  
						                dialog.dismiss();  
						                
						              //REVIEWING----������
						                mSpecialCheck.setCk_state("REVIEWING");
						                reviewOperate();
						                
						            }  
						        });  
						        //��������������ˣ���������ʾ����  
						        builder.create().show(); 
						        
				            }  
				        });  
				        //��������������ˣ���������ʾ����  
				        builder.create().show(); 
				 }
			}
			
			@Override
			public void onClickLeftBut() {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		topbar_dailycheck_review_add.setTitle("ר���鸴����Ϣ���");
		
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
		
		//���ر���
    	Toast.makeText(HiddenSpecialReviewAddActivity.this, "���ݱ��ر���ɹ���", Toast.LENGTH_SHORT).show();
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


