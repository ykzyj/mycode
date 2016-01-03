package com.sunnyit.menu.action;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.view.TopBarView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FeedbackActivity extends BaseActivity 
{
	private TopBarView topbar_pwup ;
	private TextView tv_fd_count;
	
	private EditText et_infor;
	private EditText tv_fd_pn;
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leftmenu_feedback); 
		initComponent();
		
        et_infor.addTextChangedListener(new TextWatcher()
        {
			@Override
			public void afterTextChanged(Editable s) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String content = et_infor.getText().toString();
				tv_fd_count.setText(content.length() + "/"
						+ "300");
			}

		});
        
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
        
	}
  
	
    /**
	 * 初始化用户组件
	 */
	private void initComponent()
	{
		topbar_pwup = (TopBarView)findViewById(R.id.topbar_pwup);
		
		et_infor=(EditText)findViewById(R.id.et_infor);
		tv_fd_pn=(EditText)findViewById(R.id.tv_fd_pn);
		tv_fd_count=(TextView)findViewById(R.id.tv_fd_count);
    }
}
