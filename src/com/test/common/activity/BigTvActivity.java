package com.sunnyit.common.activity;

import com.sunnyit.R;
import com.sunnyit.common.view.TopBarView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

/**   
* @Title: WelcomeActivity.java 
* @Package com.sunnyit.system.action 
* @Description: TODO
* @author yk
* @date 2015年8月1日 上午9:47:36 
* @version V1.0   
*/
public class BigTvActivity extends BaseActivity  {
	
	private TopBarView topbar_common_tv;
	private EditText et_common;
	
	private String writecontent;
	private String title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_tv_show);
		
		Intent intent=getIntent();
		writecontent=intent.getStringExtra("writecontent");
		title=intent.getStringExtra("title");
		
		initView();
		
		topbar_common_tv.setTopBarClick(new TopBarView.ITopBarClick() {
			@Override
			public void onClickRightBut() {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("writecontent", et_common.getText().toString());
                setResult(0, intent);
				finish();
			}
			
			@Override
			public void onClickLeftBut() {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		topbar_common_tv.setTitle(title);
		et_common.setText(writecontent); 
	}

	private void initView() {
		topbar_common_tv = (TopBarView)findViewById(R.id.topbar_common_tv);
		et_common = (EditText)findViewById(R.id.et_common);
	}
	
}


