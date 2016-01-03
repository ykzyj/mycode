package com.sunnyit.filetransfer.action;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**   
* @Title: HiddenList.java 
* @Package com.sunnyit.hiddencheck.action 
* @Description: TODO
* @author yk
* @date 2015年9月7日 下午3:27:12 
* @version V1.0   
*/
public class FileTransferTabActivity extends BaseActivity {
	
	private Button but_tf_send;
	private Button but_tf_havesend;
	private Button but_tf_waitreceive;
	private Button but_tf_havereceive;
	private Button but_tf_receivehistory;
	
	private TopBarView topbar_file_tf;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.file_transfer_tab_choice);
		
		initComponent();
		
		initButClick();
		
		topbar_file_tf.setTopBarClick(new ITopBarClick() {
			
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

	private void initButClick() {
		but_tf_send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(FileTransferTabActivity.this, FileSendActivity.class);
				startActivity(intent);
			}
		});
		but_tf_havesend.setOnClickListener(new OnClickListener() {
					
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(FileTransferTabActivity.this, HaveSendActivity.class);
				startActivity(intent);
			}
		});
		but_tf_waitreceive.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(FileTransferTabActivity.this, WaitReceiceActivity.class);
				startActivity(intent);
			}
		});
		but_tf_havereceive.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(FileTransferTabActivity.this, HaveReceiveActivity.class);
				startActivity(intent);
			}
		});
		but_tf_receivehistory.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(FileTransferTabActivity.this, FileHistoryActivity.class);
				startActivity(intent);
			}
		});
	}

	private void initComponent() {
		but_tf_send=(Button) findViewById(R.id.but_tf_send);
		but_tf_havesend=(Button) findViewById(R.id.but_tf_havesend);
		but_tf_waitreceive=(Button) findViewById(R.id.but_tf_waitreceive);
		but_tf_havereceive=(Button) findViewById(R.id.but_tf_havereceive);
		but_tf_receivehistory=(Button) findViewById(R.id.but_tf_receivehistory);
		
		topbar_file_tf=(TopBarView) findViewById(R.id.topbar_file_tf);
	}
}


