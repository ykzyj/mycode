package com.sunnyit.system.action;

import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.activity.ActivityCollector;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.async.HttpClientAsyncPost;
import com.sunnyit.common.async.HttpClientAsyncPost.onHttpClientReturnListener;
import com.sunnyit.common.dialog.CustomDialog;
import com.sunnyit.common.dialog.CustomDialog.CancelDialogListener;
import com.sunnyit.common.encrypt.StringToMd5;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.system.model.User;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import test.action.DialogShowActivity;

/**   
* @Title: WelcomeActivity.java 
* @Package com.sunnyit.system.action 
* @Description: TODO
* @author yk
* @date 2015年8月1日 上午9:47:36 
* @version V1.0   
*/
public class LoginActivity extends BaseActivity  {

	private long firstTime = 0;
	
	private EditText et_Num,et_Pwd;
	private Button btn_Login;
	private CheckBox cb_pw;
	
	private Handler mHandler=new Handler(){
		public void handleMessage(Message msg) {
			int flag=msg.what;
			if(flag==0)
			{
				Toast.makeText(LoginActivity.this,"用户名和密码不能为空", Toast.LENGTH_LONG).show();
			}
			else if(flag==1)
			{
				Toast.makeText(LoginActivity.this,"密码不正确", Toast.LENGTH_LONG).show();
			}
			else if(flag==2)
			{
				User user=(User) msg.obj;
				loginOfNet(user.getUserName(), user.getPassword(), user);
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		initComponent();
		
		String userName=sp.getString("userName", "");
		String passWord=sp.getString("passWord", "");
		et_Num.setText(userName);
		et_Pwd.setText(passWord);
		
		btn_Login.setOnClickListener(new View.OnClickListener() 
        {
            @Override
            public void onClick(View view) 
            {
            	final CustomDialog cusdialog=new CustomDialog(LoginActivity.this);
        		cusdialog.setViewAndAlpha(R.layout.progressbar_rw_blue,0);
        		cusdialog.setOutCancel(false);
        		cusdialog.show();
        		
        		new Thread(new Runnable(){  
                    
                    public void run(){  
                    	final String username=et_Num.getText().toString().trim();
                    	final String password=et_Pwd.getText().toString().trim();
                    	if(username.equals("")||password.equals(""))
                    	{
                    		cusdialog.dismiss();
                    		Message msg=Message.obtain();
                    		msg.what=0;
							mHandler.sendMessage(msg);
                    	}
                    	else
                    	{
                    		User user_cur=new User();    
                    		user_cur.setUserName(username);
                    		user_cur.setPassword(password);
                    		
                    		SqlOperate<User> operateruser=new SqlOperate<User>(LoginActivity.this, User.class);
        					List<User> lu_p=operateruser.SelectEntitysByCondition(" where userName='"+username.trim().toString()+"'");
    						operateruser.close();
    						if(lu_p.size()>0)
    						{
    							loginOfLocal(username, password, lu_p,cusdialog);
    						}
    						else
    						{
    							cusdialog.dismiss();
    							Message msg=Message.obtain();
    							msg.what=2;
    							msg.obj=user_cur;
    							mHandler.sendMessage(msg);
    						}
                        	
                    	}
                    }  
                }).start();  
            
            	
            	/*Intent intent = new Intent(LoginActivity.this,MainActivity.class);
		        startActivity(intent);*/
            }

        });
		
	}
	
	private void loginOfLocal(final String username, final String password, List<User> lu_p,CustomDialog cusdialog) {
		String pw_md5=StringToMd5.md5(password).trim();
		boolean haveFlag=false;
		for(User u:lu_p)
		{
			if(pw_md5.equals(u.getPassword()))
			{
				haveFlag=true;
				Editor edit=sp.edit();
				if(cb_pw.isChecked())
				{
					edit.putString("userName", username);
					edit.putString("passWord", password);
				}
				else
				{
					edit.remove("userName");
					edit.remove("passWord");
				}
				edit.putString("UserId", u.getUserId());
				edit.commit();
				
				Intent intent = new Intent(LoginActivity.this,MainActivity.class);
		        startActivity(intent);
		        cusdialog.dismiss();
			}
		}
		if(!haveFlag)
		{
			cusdialog.dismiss();
			Message msg=Message.obtain();
			msg.what=1;
			mHandler.sendMessage(msg);
		}
	}

	private void loginOfNet(final String username, final String password, User user_cur) {
		HttpClientAsyncPost post=new HttpClientAsyncPost(LoginActivity.this, user_cur);
		String baseURL=getBaseUrl();
		if(baseURL!=null)
		{
			post.execute(baseURL+"/logon/loginOfApp");
			post.setHttpClientReturnListener(new onHttpClientReturnListener() {
				@Override
				public void returnPostExecute(boolean isSuccess, String msg, List<Object> lo) {
					// TODO Auto-generated method stub
					if(isSuccess)
					{
						User user=(User) lo.get(0);
						Editor edit=sp.edit();
						if(cb_pw.isChecked())
						{
							edit.putString("userName", username);
							edit.putString("passWord", password);
						}
						else
						{
							edit.remove("userName");
							edit.remove("passWord");
						}
						edit.putString("UserId", user.getUserId());
						edit.commit();
						
						SqlOperate<User> opetater=new SqlOperate<User>(LoginActivity.this, User.class);
						opetater.DeleteContent(user.getUserId());
						opetater.saveContent(user);
						opetater.close();
						
						Intent intent = new Intent(LoginActivity.this,MainActivity.class);
		                startActivity(intent);
					}
					else
					{
						Toast.makeText(LoginActivity.this,msg, Toast.LENGTH_LONG).show();
					}
				}
			});
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.login_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.login_setting_url:
			setUrl();
			break;
		case R.id.login_sys_quit:
			ActivityCollector.finishAll();
			break;
		default:
		}
		return true;

	}

	/**
	 * 点击两次退出系统
	 */
    @Override 
    public boolean onKeyUp(int keyCode, KeyEvent event) 
    { 
        if (keyCode == KeyEvent.KEYCODE_BACK) 
        { 
            long secondTime = System.currentTimeMillis(); 
            if (secondTime - firstTime > 2000) 
            {
            	//如果两次按键时间间隔大于2000毫秒，则不退出 
                Toast.makeText(LoginActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show(); 
                firstTime = secondTime;//更新firstTime 
                return true; 
            }
            else 
            { 
                System.exit(0);//否则退出程序 
            } 
        } 
        return super.onKeyUp(keyCode, event); 
    } 
    
    /**
    * @author yk 
    * @date 2015年8月6日 上午10:18:24 
    * @Title: initComponent 
    * @Description: 初始化用户组件 
    * @return void    返回类型 
    * @throws
     */
	private void initComponent()
	{
		et_Num=(EditText)findViewById(R.id.et_Num);
		et_Pwd=(EditText)findViewById(R.id.et_Pwd);
		btn_Login=(Button)findViewById(R.id.btn_login);
		cb_pw=(CheckBox)findViewById(R.id.cb_pw);
    }
    
	@Override
	public void onConfigurationChanged(Configuration config)
	{
		super.onConfigurationChanged(config);
	
		if (config.orientation == Configuration.ORIENTATION_PORTRAIT)
		{
			setContentView(R.layout.login); //竖屏布局
			initComponent();
			btn_Login.setOnClickListener(new View.OnClickListener() 
	        {
	            @Override
	            public void onClick(View view) 
	            {
	            	Intent intent = new Intent(LoginActivity.this,MainActivity.class);
	                startActivity(intent);
	                finish();
	            }
	        });
		}
	
		if (config.orientation == Configuration.ORIENTATION_LANDSCAPE)
		{
			setContentView(R.layout.login); //横屏布局
			initComponent();
			btn_Login.setOnClickListener(new View.OnClickListener() 
	        {
	            @Override
	            public void onClick(View view) 
	            {
	            	Intent intent = new Intent(LoginActivity.this,MainActivity.class);
	                startActivity(intent);
	                finish();
	            }
	        });
		}
	}

}


