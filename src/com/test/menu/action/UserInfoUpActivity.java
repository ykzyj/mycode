package com.sunnyit.menu.action;

import java.util.List;
import java.util.UUID;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.enforcement.action.HiddenStandCheckListActivity;
import com.sunnyit.menu.model.WXMessage;
import com.sunnyit.synchronous.model.UpInfo;
import com.sunnyit.system.model.User;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

/**   
* @Title: NoteAddActivity.java 
* @Package com.sunnyit.menu.action 
* @Description: TODO
* @author yk
* @date 2015年8月10日 上午10:35:01 
* @version V1.0   
*/
public class UserInfoUpActivity extends BaseActivity {
	
	private TopBarView topbar_pwup;
	
	private EditText edit_user_name;
	private EditText edit_real_name;
	private EditText edit_user_phone;
	private EditText edit_user_email;
	
	private User mUser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leftmenu_user_infoup);
		
		initView();
		
		mUser=getCurrentUser();
		
		edit_user_name.setText(mUser.getUserName());
		edit_real_name.setText(mUser.getRealName());
		edit_user_phone.setText(mUser.getTelephone());
		edit_user_email.setText(mUser.getEmail());
		
		topbar_pwup.setTopBarClick(new TopBarView.ITopBarClick() {
			@Override
			public void onClickRightBut() {
				// TODO Auto-generated method stub
				String UserName=edit_user_name.getText().toString().trim();
				String RealName=edit_real_name.getText().toString().trim();
				String Telephone=edit_user_phone.getText().toString().trim();
				String Email=edit_user_email.getText().toString().trim();
				
				if(UserName.equals(""))
				{
					Toast.makeText(UserInfoUpActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
				}
				else if(RealName.equals(""))
				{
					Toast.makeText(UserInfoUpActivity.this, "真实名不能为空", Toast.LENGTH_SHORT).show();
				}
				else if(Telephone.equals(""))
				{
					Toast.makeText(UserInfoUpActivity.this, "电话不能为空", Toast.LENGTH_SHORT).show();
				}
				else if(Email.equals(""))
				{
					Toast.makeText(UserInfoUpActivity.this, "邮箱不能为空", Toast.LENGTH_SHORT).show();
				}
				else 
				{
					mUser.setUserName(UserName);
					mUser.setRealName(RealName);
					mUser.setTelephone(Telephone);
					mUser.setEmail(Email);
					
					SqlOperate<User> opetaterUser=new SqlOperate<User>(UserInfoUpActivity.this, User.class);
					opetaterUser.upContent(mUser);
					opetaterUser.close();
					
					SqlOperate<UpInfo> opetaterUpInfo=new SqlOperate<UpInfo>(UserInfoUpActivity.this, UpInfo.class);
			    	String conditionSqlStr="  where InfoId='"+mUser.getUserId()+"' ";
			    	List<UpInfo> ls_in=opetaterUpInfo.SelectEntitysByCondition(conditionSqlStr);
			    	if(ls_in.size()==0)
			    	{
			    		UpInfo info=new UpInfo();
						info.setU_Id(UUID.randomUUID().toString());
						info.setInfoTable("User");
						info.setInfoId(mUser.getUserId());
						info.setOperateType("upUserInfo");
						info.setRemark("");
						opetaterUpInfo.saveContent(info);
			    	}
			    	opetaterUpInfo.close();
			    	
			    	Toast.makeText(UserInfoUpActivity.this, "更改成功", Toast.LENGTH_SHORT).show();
			    	finish();
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
     * 初始化界面
     */
    private void initView() {
    	topbar_pwup = (TopBarView)findViewById(R.id.topbar_pwup);
    	
    	edit_user_name = (EditText)findViewById(R.id.edit_user_name);
    	edit_real_name = (EditText)findViewById(R.id.edit_real_name);
    	edit_user_phone = (EditText)findViewById(R.id.edit_user_phone);
    	edit_user_email = (EditText)findViewById(R.id.edit_user_email);
    }
}

