package com.sunnyit.menu.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.menu.model.WXMessage;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**   
* @Title: NoteAddActivity.java 
* @Package com.sunnyit.menu.action 
* @Description: TODO
* @author yk
* @date 2015年8月10日 上午10:35:01 
* @version V1.0   
*/
public class NoteAddActivity extends BaseActivity {
	
	private TopBarView topbar_pwup ;
    private TextView tv_edit_count;
    private EditText edit_node_title;
    private EditText edit_node_msg;
    WXMessage upmsg;
    
    Date currentTime = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    Boolean upflag=false;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leftmenu_nodeadd);
		
		initView();
		
		Intent intent = this.getIntent(); 
		upmsg=(WXMessage)intent.getSerializableExtra("msg");
		if(upmsg!=null)
		{
			edit_node_title.setText(upmsg.getTitle());
			edit_node_msg.setText(upmsg.getMsg());
			upflag=true;
		}
		else
		{
			edit_node_title.setText("");
			edit_node_msg.setText("");
			upflag=false;
		}
		
		topbar_pwup.setTopBarClick(new TopBarView.ITopBarClick() {
			@Override
			public void onClickRightBut() {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				NoteAddActivity.this.setResult(1, intent);
				if(upflag)
				{
					upmsg.setTitle(edit_node_title.getText().toString());
					upmsg.setMsg(edit_node_msg.getText().toString());
					upmsg.setTime(formatter.format(currentTime));
					
					SqlOperate<WXMessage> opetater=new SqlOperate<WXMessage>(NoteAddActivity.this, WXMessage.class);
					opetater.upContent(upmsg);
					opetater.close();
					Toast.makeText(NoteAddActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
					//Intent intent=new Intent(NoteAddActivity.this, NoteListActivity.class);
					//startActivity(intent);
					//finish();
				}
				else
				{
					WXMessage msg=new WXMessage();
					msg.setIcon_id(R.drawable.notepad);
					msg.setTitle(edit_node_title.getText().toString());
					msg.setMsg(edit_node_msg.getText().toString());
					msg.setTime(formatter.format(currentTime));
					
					SqlOperate<WXMessage> opetater=new SqlOperate<WXMessage>(NoteAddActivity.this, WXMessage.class);
					opetater.saveContent(msg);
					opetater.close();
					Toast.makeText(NoteAddActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
					//Intent intent=new Intent(NoteAddActivity.this, NoteListActivity.class);
					//startActivity(intent);
					//finish();
				}
				finish();
			}
			
			@Override
			public void onClickLeftBut() {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				NoteAddActivity.this.setResult(2, intent);
				finish();
			}
		});
		
        edit_node_msg.addTextChangedListener(new TextWatcher()
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
				String content = edit_node_msg.getText().toString();
				tv_edit_count.setText(content.length() + "/"
						+ "300");
			}

		});
	}
	
	/**
     * 初始化界面
     */
    private void initView() {
    	topbar_pwup = (TopBarView)findViewById(R.id.topbar_pwup);
        tv_edit_count = (TextView)findViewById(R.id.tv_edit_count);
        edit_node_title = (EditText)findViewById(R.id.edit_node_title);
        edit_node_msg = (EditText)findViewById(R.id.edit_node_msg);
    }
    
}


