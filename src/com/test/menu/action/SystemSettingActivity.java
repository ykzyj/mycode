package com.sunnyit.menu.action;

import com.sunnyit.R;
import com.sunnyit.common.activity.ActivityCollector;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.cache.DataCleanManager;
import com.sunnyit.common.dialog.CustomDialog;
import com.sunnyit.common.network.NetworkDetector;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.system.action.MainActivity;

import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/**   
* @Title: SystemSettingActivity.java 
* @Package com.sunnyit.menu.action 
* @Description: TODO
* @author yk
* @date 2015年8月14日 上午9:43:09 
* @version V1.0   
*/
public class SystemSettingActivity extends BaseActivity {
	
	private TopBarView topbar_pwup ;
	
	private RelativeLayout relative_url_steeing;
	private RelativeLayout relative_clear_cache;
	private RelativeLayout relative_push_time;
	
	private TextView clear_cache_count;
	
	private ToggleButton TogBtn_push_switch;
	private ToggleButton TogBtn_pattern_switch;
	private ToggleButton TogBtn_pattern_wifi_switch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leftmenu_syssetting);
		initView();
		topbar_pwup.setTopBarClick(new TopBarView.ITopBarClick() {
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
		
		relative_url_steeing.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setUrl();
			}
		});
		
		try {
			clear_cache_count.setText(DataCleanManager.getTotalCacheSize(getApplicationContext()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		relative_clear_cache.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			 	final CustomDialog cusdialog=new CustomDialog(SystemSettingActivity.this);
				cusdialog.setViewAndAlpha(R.layout.dialog_custom,0);
				cusdialog.setText(R.id.tv_dg_title, "提示");
				cusdialog.setText(R.id.tv_dg_context, "是否确认清除当前缓存?");
				cusdialog.findViewById(R.id.but_dg_positiveButton).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						DataCleanManager.clearAllCache(getApplicationContext());
						try {
							clear_cache_count.setText(DataCleanManager.getTotalCacheSize(getApplicationContext()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						cusdialog.dismiss();
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
		});
		
		relative_push_time.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(SystemSettingActivity.this, "完善中，请稍候……", Toast.LENGTH_SHORT).show();
			}
		});
		
		String wifi=sp.getString("wifi", null);
		if(wifi.equals("yes"))
		{
			TogBtn_pattern_wifi_switch.setChecked(true);
		}
		else
		{
			TogBtn_pattern_wifi_switch.setChecked(false);
		}
		TogBtn_pattern_wifi_switch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Editor edit=sp.edit();
				if(isChecked)
				{
					edit.putString("wifi", "yes");
					Toast.makeText(SystemSettingActivity.this, "当前为Wifi模式", Toast.LENGTH_SHORT).show();
				}
				else
				{
					edit.putString("wifi", "no");
					String  networType=NetworkDetector.getCurrentNetType(SystemSettingActivity.this);
					if (networType == "null") 
					{
						Toast.makeText(SystemSettingActivity.this, "当前为普通网络模式,但当前网络连接为空，建议切换wifi模式", Toast.LENGTH_LONG).show();
					} 
					else if (networType == "2g") 
					{
						Toast.makeText(SystemSettingActivity.this, "当前为普通网络模式,但当前网络连接为2G，建议切换wifi模式", Toast.LENGTH_LONG).show();
					}
					else
					{
						Toast.makeText(SystemSettingActivity.this, "当前为普通网络模式", Toast.LENGTH_SHORT).show();
					}
				}
				edit.commit(); 
			}
		});
		
		TogBtn_push_switch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked)
				{
					Toast.makeText(SystemSettingActivity.this, "允许向客户端推送信息", Toast.LENGTH_SHORT).show();
					relative_push_time.setEnabled(true);
				}
				else
				{
					Toast.makeText(SystemSettingActivity.this, "禁止向客户端推送信息", Toast.LENGTH_SHORT).show();
					relative_push_time.setEnabled(false);
				}
			}
		});
		
		TogBtn_pattern_switch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked)
				{
					Toast.makeText(SystemSettingActivity.this, "开启夜间模式", Toast.LENGTH_SHORT).show();
				}
				else
				{
					Toast.makeText(SystemSettingActivity.this, "关闭夜间模式", Toast.LENGTH_SHORT).show();
				}
			}
		});
        
	}
	/**
     * 初始化界面
     */
    private void initView() {
    	topbar_pwup = (TopBarView)findViewById(R.id.topbar_pwup);
    	
    	relative_url_steeing = (RelativeLayout)findViewById(R.id.relative_url_steeing);
    	relative_clear_cache = (RelativeLayout)findViewById(R.id.relative_clear_cache);
    	relative_push_time = (RelativeLayout)findViewById(R.id.relative_push_time);
    	
    	clear_cache_count = (TextView)findViewById(R.id.clear_cache_count);
    	
    	TogBtn_push_switch = (ToggleButton)findViewById(R.id.TogBtn_push_switch);
    	TogBtn_pattern_switch = (ToggleButton)findViewById(R.id.TogBtn_pattern_switch);
    	TogBtn_pattern_wifi_switch = (ToggleButton)findViewById(R.id.TogBtn_pattern_wifi_switch);
    }
}


