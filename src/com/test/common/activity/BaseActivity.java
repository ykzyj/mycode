package com.sunnyit.common.activity;

import com.sunnyit.R;
import com.sunnyit.common.dialog.CustomDialog;
import com.sunnyit.common.dialog.CustomDialog.CancelDialogListener;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.system.model.User;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**   
* @Title: BaseActivity.java 
* @Package com.sunnyit.common.activity 
* @Description: TODO
* @author yk
* @date 2015年8月14日 下午3:31:35 
* @version V1.0   
*/
public class BaseActivity extends Activity {
	
	protected SharedPreferences sp;
	private CustomDialog cusdialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//Log.d("BaseActivity", getClass().getSimpleName());
		ActivityCollector.addActivity(this);
		sp = this.getSharedPreferences("SP", MODE_PRIVATE);
		
	}
	

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ActivityCollector.removeActivity(this);
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	
	/**
	* @author yk 
	* @date 2015年9月2日 上午9:37:30 
	* @Title: getCurrentUser 
	* @Description: 获取当前用户信息
	* @return    设定文件 
	* @return User    返回类型 
	* @throws
	 */
	protected User getCurrentUser() {
		User user=new User();
		String UserId=sp.getString("UserId", null);
		if(UserId!=null)
		{
			SqlOperate<User> uop=new SqlOperate<User>(this, User.class);
			user=uop.SelectEntityByID(UserId);
			uop.close();
		}
		return user;
	}
	
	/**
	* @author yk 
	* @date 2015年9月2日 上午11:07:02 
	* @Title: getUrl 
	* @Description: 获取url
	* @return    设定文件 
	* @return User    返回类型 
	* @throws
	 */
	protected String getBaseUrl() {
		String url=sp.getString("url", null);
		if(url==null)
		{
			setUrl();
			url=sp.getString("url", null);
		}
		if(url!=null)
		{
			url="http://"+url;
		}
		return url;
	}
	
	protected void setUrl() {
		final Context context=this;
		final CustomDialog cusdialog=new CustomDialog(this);
		cusdialog.setViewAndAlpha(R.layout.dialog_custom,0);
		cusdialog.setText(R.id.tv_dg_title, "设置url");
		String url=sp.getString("url", "");
		cusdialog.setText(R.id.et_dg_context, url);
		cusdialog.findViewById(R.id.but_dg_positiveButton).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Editor edit=sp.edit();
				edit.putString("url", cusdialog.getText(R.id.et_dg_context));
				edit.commit(); 
				cusdialog.dismiss();
				Toast.makeText(context,"url设置成功", Toast.LENGTH_LONG).show();
			}
		});
		cusdialog.findViewById(R.id.but_dg_negativeButton).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cusdialog.dismiss();
			}
		});
		cusdialog.show();
	}
}


