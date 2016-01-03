package com.sunnyit.menu.action;

import java.util.List;
import java.util.UUID;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.encrypt.StringToMd5;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.synchronous.model.UpInfo;
import com.sunnyit.system.action.LoginActivity;
import com.sunnyit.system.model.User;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

/**   
* @Title: NoteAddActivity.java 
* @Package com.sunnyit.menu.action 
* @Description: TODO
* @author yk
* @date 2015��8��10�� ����10:35:01 
* @version V1.0   
*/
public class UserPwUpActivity extends BaseActivity{
	
	private TopBarView topbar_pwup;
	
	private EditText edit_user_pw;
	private EditText edit_user_pw_f;
	private EditText edit_user_pw_s;
	
	private User mUser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leftmenu_user_pwup);
		
		initView();
		topbar_pwup.setTopBarClick(new TopBarView.ITopBarClick() {
			@Override
			public void onClickRightBut() {
				// TODO Auto-generated method stub
				String user_pw=edit_user_pw.getText().toString().trim();
				String user_pw_f=edit_user_pw_f.getText().toString().trim();
				String user_pw_s=edit_user_pw_s.getText().toString().trim();
				
				mUser=getCurrentUser();
				
				if(user_pw.equals(""))
				{
					Toast.makeText(UserPwUpActivity.this, "�����벻��Ϊ��", Toast.LENGTH_SHORT).show();
				}
				else if(user_pw_f.equals("")&&user_pw_s.equals(""))
				{
					Toast.makeText(UserPwUpActivity.this, "�����벻��Ϊ��", Toast.LENGTH_SHORT).show();
				}
				else if(!user_pw_f.equals(user_pw_s))
				{
					Toast.makeText(UserPwUpActivity.this, "��������������벻һ��", Toast.LENGTH_SHORT).show();
				}
				else if(user_pw_f.length()<6)
				{
					Toast.makeText(UserPwUpActivity.this, "����������볤�Ȳ���С��6", Toast.LENGTH_SHORT).show();
				}
				else
				{
					String pw_md5=StringToMd5.md5(user_pw_f).trim();
					
					SqlOperate<User> operateruser=new SqlOperate<User>(UserPwUpActivity.this, User.class);
					List<User> lu_p=operateruser.SelectEntitysByCondition(" where userName='"+mUser.getUserName().trim()+"'");
					if(lu_p.size()>0)
					{
						if(lu_p.get(0).getPassword().equals(pw_md5))
						{
							mUser.setPassword(pw_md5);
							operateruser.upContent(mUser);
							
							SqlOperate<UpInfo> opetaterUpInfo=new SqlOperate<UpInfo>(UserPwUpActivity.this, UpInfo.class);
					    	String conditionSqlStr="  where InfoId='"+mUser.getUserId()+"' ";
					    	List<UpInfo> ls_in=opetaterUpInfo.SelectEntitysByCondition(conditionSqlStr);
					    	if(ls_in.size()==0)
					    	{
					    		UpInfo info=new UpInfo();
								info.setU_Id(UUID.randomUUID().toString());
								info.setInfoTable("User");
								info.setInfoId(mUser.getUserId());
								info.setOperateType("upUserPw");
								info.setRemark("");
								opetaterUpInfo.saveContent(info);
					    	}
					    	opetaterUpInfo.close();
					    	
					    	Toast.makeText(UserPwUpActivity.this, "���ĳɹ�", Toast.LENGTH_SHORT).show();
					    	finish();
						}
						else
						{
							Toast.makeText(UserPwUpActivity.this, "������ľ���������,���������룡", Toast.LENGTH_SHORT).show();
						}
					}
					operateruser.close();
				}
			}
			
			@Override
			public void onClickLeftBut() {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	/**
     * ��ʼ������
     */
    private void initView() {
    	topbar_pwup = (TopBarView)findViewById(R.id.topbar_pwup);
    	
    	edit_user_pw = (EditText)findViewById(R.id.edit_user_pw);
    	edit_user_pw_f = (EditText)findViewById(R.id.edit_user_pw_f);
    	edit_user_pw_s = (EditText)findViewById(R.id.edit_user_pw_s);
    }
    
    private void keyInputController() {
    	InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
    	//�õ�InputMethodManager��ʵ��
    	if (imm.isActive()) {
    		//�������
    		imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
    		//�ر�����̣�����������ͬ������������л�������ر�״̬��
    	}
    }
    
}


