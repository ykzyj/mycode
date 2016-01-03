package com.sunnyit.filetransfer.action;

import java.io.File;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.convert.PpConvert;
import com.sunnyit.common.dialog.CustomDialog;
import com.sunnyit.common.services.DownService;
import com.sunnyit.common.view.CustomInputView;
import com.sunnyit.common.view.CustomInputView.ButtonOnClickListener;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.filetransfer.model.TransportFile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Toast;

/**   
* @Title: DailyCheckList.java 
* @Package com.sunnyit.hiddencheck.action 
* @Description: TODO
* @author yk
* @date 2015年9月7日 下午3:20:13 
* @version V1.0   
*/
public class TransportFileShowActivity extends BaseActivity {

	private TopBarView topbar_file_tf_show;
	
	private EditText et_sender;
	private EditText et_sendTime;
	private EditText et_receiver;
	private EditText et_receiveTime;
	private EditText et_title;
	private CustomInputView et_annexName;
	private WebView wv_content;
	
	private CustomDialog cusdialogt;
	
	private TransportFile mTransportFile;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.file_transfer_show);
		
		mTransportFile=(TransportFile) getIntent().getSerializableExtra("TransportFile");
		
		initComponent();
		
		et_sender.setText(mTransportFile.getSender());
		et_sendTime.setText(mTransportFile.getSendTime());
		et_receiver.setText(mTransportFile.getReceiver());
		if(mTransportFile.getState().equals("0"))
		{
			et_receiveTime.setText("待接收");
		}
		else
		{
			et_receiveTime.setText(mTransportFile.getReceiveTime());
		}
		//et_receiveTime.setText(transportFile.getReceiveTime());
		et_title.setText(mTransportFile.getTitle());
		if(mTransportFile.getAnnexName().equals(""))
		{
			et_annexName.setVisibility(View.GONE);
		}
		else
		{
			et_annexName.setInputViewText(mTransportFile.getAnnexName());
			et_annexName.setInputViewTextSize(PpConvert.sp2px(TransportFileShowActivity.this, 9));
			et_annexName.setInputEditEnable(false);
		}
		wv_content.getSettings().setDefaultTextEncodingName("UTF -8");//设置默认为utf-8  
		wv_content.loadData(mTransportFile.getContent(), "text/html; charset=UTF-8", null);//这种写法可以正确解码  
		
		topbar_file_tf_show.setTopBarClick(new ITopBarClick() {
			
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
		topbar_file_tf_show.setRightButVisibility(View.GONE);
		
		et_annexName.setButtonOnClickListener(new ButtonOnClickListener() {
			
			@Override
			public void onSecondButtonOnClick() {
				if(!mTransportFile.getAnnexName().equals(""))
				{
					int lastState=mTransportFile.getAnnexName().lastIndexOf(".");
					String type=mTransportFile.getAnnexName().substring(lastState+1, mTransportFile.getAnnexName().length());
					if(type.equals("jpg")||
							type.equals("jpeg")||
							type.equals("gif")||
							type.equals("png")||
							type.equals("bmp"))
					{
						Intent intent = new Intent(Intent.ACTION_VIEW);
						File imgFile=new File(DownService.DOWNPATH+mTransportFile.getAnnexName());
						intent.setDataAndType(Uri.fromFile(imgFile), "image/*");
						startActivity(intent);
					}
					else if(type.equals("pdf")||
							type.equals("doc")||
							type.equals("xls")||
							type.equals("ppt")||
							type.equals("docx")||
							type.equals("xlsx")||
							type.equals("pptx"))
					{
						Intent intent = new Intent();
						intent.setAction("android.intent.action.VIEW"); 
					    intent.addCategory("android.intent.category.DEFAULT");
						String fileMimeType = "application/msword";
						File docFile=new File(DownService.DOWNPATH+mTransportFile.getAnnexName());
						intent.setDataAndType(Uri.fromFile(docFile), fileMimeType);
						startActivity(intent);
					}
					else
					{
						Intent intent = new Intent();
						intent.setAction(android.content.Intent.ACTION_VIEW);
						File file = new File(DownService.DOWNPATH);
						intent.setDataAndType(Uri.fromFile(file), "video/*");
						startActivity(intent);
					}
				}
			}
			
			@Override
			public void onFirstButtonOnClick() {

				cusdialogt=new CustomDialog(TransportFileShowActivity.this);
				cusdialogt.setViewAndAlpha(R.layout.dialog_custom,0);
				cusdialogt.initProgressBar(R.id.id_progressbarh);
				cusdialogt.setOutCancel(false);
				cusdialogt.setText(R.id.tv_dg_title, "数据上传中");
				cusdialogt.findViewById(R.id.rel_cb_divider).setVisibility(View.GONE);
				cusdialogt.findViewById(R.id.lin_but_divider).setVisibility(View.GONE);
				cusdialogt.show();
				
				Intent intent=new Intent(TransportFileShowActivity.this, DownService.class);
				intent.putExtra("fileUrl", getBaseUrl()+ mTransportFile.getAnnexPath());
				intent.putExtra("fileName", mTransportFile.getAnnexName());
				intent.putExtra("fileId", mTransportFile.getId());
				startService(intent);
				
			}
		});
		
		IntentFilter mIntentFilter=new IntentFilter();
		mIntentFilter.addAction(DownService.ACTION_UPDATE);
		mIntentFilter.addAction(DownService.ACTION_SETLENGTH);
		//mIntentFilter.addAction(DownService.ACTION_FINISH);
		registerReceiver(mReceiver, mIntentFilter);
		
	}
	
	BroadcastReceiver mReceiver=new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent.getAction().equals(DownService.ACTION_UPDATE))
			{
				String Fileid=intent.getStringExtra("Fileid");
				String Finished=intent.getStringExtra("Finished");
				if(Fileid.equals(mTransportFile.getId()))
				{
					cusdialogt.setProgress(Integer.valueOf(Finished));
				}
				if(Finished.equals("100"))
				{
					cusdialogt.dismiss();
					Toast.makeText(TransportFileShowActivity.this, "下载完成", Toast.LENGTH_SHORT).show();
				}
			}
			else if(intent.getAction().equals(DownService.ACTION_SETLENGTH))
			{
				
			}
		}
	};

	
	private void initComponent() {
		topbar_file_tf_show=(TopBarView) findViewById(R.id.topbar_file_tf_show);
		
		et_sender=(EditText) findViewById(R.id.et_sender);
		et_sendTime=(EditText) findViewById(R.id.et_sendTime);
		et_receiver=(EditText) findViewById(R.id.et_receiver);
		et_receiveTime=(EditText) findViewById(R.id.et_receiveTime);
		et_title=(EditText) findViewById(R.id.et_title);
		et_annexName=(CustomInputView) findViewById(R.id.civ_annexName);
		wv_content=(WebView) findViewById(R.id.wv_content);
		
		//rel_annexName=(RelativeLayout) findViewById(R.id.rel_annexName);
	}
	
}


