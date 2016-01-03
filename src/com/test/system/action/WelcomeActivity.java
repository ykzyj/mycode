package com.sunnyit.system.action;

import java.util.Timer;
import java.util.TimerTask;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;

/**   
* @Title: WelcomeActivity.java 
* @Package com.sunnyit.system.action 
* @Description: TODO
* @author yk
* @date 2015年8月1日 上午9:47:36 
* @version V1.0   
*/
public class WelcomeActivity extends BaseActivity  {

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		
		String wifi=sp.getString("wifi", null);
		if(wifi==null)
		{
			Editor edit=sp.edit();
			edit.putString("wifi", "yes");
			edit.commit(); 
		}
		
		//三秒后跳转到登录界面
		final Intent it = new Intent(this, LoginActivity.class); 
		Timer timer = new Timer();  
		TimerTask task = new TimerTask() 
		{   
			@Override   
			public void run() 
			{   
				startActivity(it);   
				finish();
			}  
		};
		timer.schedule(task, 1000*3); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onConfigurationChanged(Configuration config)
	{
		super.onConfigurationChanged(config);
	
		if (config.orientation == Configuration.ORIENTATION_PORTRAIT)
		{
			setContentView(R.layout.welcome); //布局
		}
	
		if (config.orientation == Configuration.ORIENTATION_LANDSCAPE)
		{
			setContentView(R.layout.welcome); //布局
		}
	}

}


