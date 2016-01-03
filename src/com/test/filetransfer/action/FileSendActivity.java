package com.sunnyit.filetransfer.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.dataOperate.dOperate;
import com.sunnyit.common.dialog.CustomDialog;
import com.sunnyit.common.file.FileUtils;
import com.sunnyit.common.http.HttpClientPostOperate;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.filetransfer.model.TransportFile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
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
public class FileSendActivity extends BaseActivity {
	
	private TopBarView topbar_file_tf_send;
	
	private EditText et_receiver;
	private EditText et_title;
	private EditText et_annexName;
	private EditText et_content;
	
	public static final int FILE_SELECT_CODE=1151;
	
	private CustomDialog cusdialog;
	
	private List<TransportFile>  mLs_tf;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.file_transfer_send);
		
		initComponent();
		
		topbar_file_tf_send.setTopBarClick(new ITopBarClick() {
			
			@Override
			public void onClickRightBut() {
				if(et_receiver.getText().toString().trim().equals(""))
				{
					Toast.makeText(FileSendActivity.this, "收件人不能为空", Toast.LENGTH_SHORT).show();
				}
				else
				{
					cusdialog=new CustomDialog(FileSendActivity.this);
					cusdialog.setViewAndAlpha(R.layout.progressbar_rw_blue,0);
					cusdialog.setOutCancel(false);
					cusdialog.show();
					
					new Thread(new Runnable() {
						public void run() {
							
							String annexNameStr=et_annexName.getText().toString();
							List<File> ls_file=new ArrayList<File>();
							
							for(int i=0;i<mLs_tf.size();i++)
							{
								mLs_tf.get(i).setId(UUID.randomUUID().toString());
								mLs_tf.get(i).setSender(getCurrentUser().getRealName());
								mLs_tf.get(i).setSenderId(getCurrentUser().getUserId());
								mLs_tf.get(i).setTitle(et_title.getText().toString());
								mLs_tf.get(i).setContent(et_content.getText().toString());
								mLs_tf.get(i).setSendTime(dOperate.getCurrentAllLongDate());
								mLs_tf.get(i).setState("0");
								
								int lastKey=annexNameStr.lastIndexOf("/");
								String filename=annexNameStr.substring(lastKey+1, annexNameStr.length());
								
								if(et_annexName.getText().toString().equals(""))
								{
									mLs_tf.get(i).setAnnexName("");
									mLs_tf.get(i).setAnnexPath("/static/upload/transportFile/");
								}
								else
								{
									mLs_tf.get(i).setAnnexName(filename);
									mLs_tf.get(i).setAnnexPath("/static/upload/transportFile/");
									
								}
								
							}
							String saveUrl=getBaseUrl()+"/appWebSave/saveTransportFile";
							boolean okFlag=true;
							for(int i=0;i<mLs_tf.size();i++)
							{
								if(i==0)
								{
									File file=new File(annexNameStr);
									ls_file.add(file);
									try {
										HttpClientPostOperate.httpClientPost(saveUrl, mLs_tf.get(i), ls_file);
									} catch (IOException e) {
										Message msg=Message.obtain();
						        		msg.what=1;
						        		handler.sendMessage(msg);
						        		okFlag=false;
						        		break;
									}
								}
								else
								{
									try {
										HttpClientPostOperate.httpClientPost(saveUrl, mLs_tf.get(i));
									} catch (IOException e) {
										Message msg=Message.obtain();
						        		msg.what=1;
						        		handler.sendMessage(msg);
						        		okFlag=false;
						        		break;
									}
								}
								
							}
							
							if(okFlag)
							{
								Message msg=Message.obtain();
				        		msg.what=0;
				        		handler.sendMessage(msg);
							}
							
							cusdialog.dismiss();
						}
					}).start();
				}
			}
			
			@Override
			public void onClickLeftBut() {
				finish();
			}
		});
		
		et_annexName.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showFileChooser();
			}
		});
		
		et_receiver.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(FileSendActivity.this, ReceiverActivity.class);
				startActivityForResult(intent, 10);
			}
		});
		
	}
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if(msg.what==0)
			{
				//Toast.makeText(FileSendActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
				AlertDialog.Builder builder=new AlertDialog.Builder(FileSendActivity.this);  //先得到构造器  
				builder.setTitle("提示"); //设置标题  
		        builder.setMessage("文件发送成功"); //设置内容  
		        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮  
		            @Override  
		            public void onClick(DialogInterface dialog, int which) {  
		                dialog.dismiss(); //关闭dialog  
		                finish();
		            }  
		        });  
		        builder.create().show(); 
			}
			else if(msg.what==1)
			{
				//Toast.makeText(FileSendActivity.this, "发送失败，请检查您的当前网络连接是否可用", Toast.LENGTH_SHORT).show();
				AlertDialog.Builder builder=new AlertDialog.Builder(FileSendActivity.this);  //先得到构造器  
				builder.setTitle("提示"); //设置标题  
		        builder.setMessage("发送失败，请检查当前网络连接情况"); //设置内容  
		        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮  
		            @Override  
		            public void onClick(DialogInterface dialog, int which) {  
		                dialog.dismiss(); //关闭dialog  
		            }  
		        });  
		        builder.create().show(); 
			}
		}
		
	};
	
	private void initComponent() {
		et_receiver=(EditText) findViewById(R.id.et_receiver);
		et_title=(EditText) findViewById(R.id.et_title);
		et_annexName=(EditText) findViewById(R.id.et_annexName);
		et_content=(EditText) findViewById(R.id.et_content);
		
		topbar_file_tf_send=(TopBarView) findViewById(R.id.topbar_file_tf_send);
	}
	
	
	private void showFileChooser() {
	    Intent intent = new Intent(Intent.ACTION_GET_CONTENT); 
	    intent.setType("*/*"); 
	    intent.addCategory(Intent.CATEGORY_OPENABLE);
	 
	    try {
	        startActivityForResult( Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_CODE);
	    } catch (android.content.ActivityNotFoundException ex) {
	        Toast.makeText(this, "Please install a File Manager.",  Toast.LENGTH_SHORT).show();
	    }
	}
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)  {
	    switch (requestCode) {
	        case FILE_SELECT_CODE:      
		        if (resultCode == RESULT_OK) {  
		            // Get the Uri of the selected file 
		            Uri uri = data.getData();
		            String path = FileUtils.getPath(this, uri);
		            //Toast.makeText(this, path,  Toast.LENGTH_SHORT).show();
		            et_annexName.setText(path);
		        }   
		        break;
	        case 10:      
	        	//Toast.makeText(FileSendActivity.this, String.valueOf(resultCode), Toast.LENGTH_SHORT).show();
	        	if(data!=null)
	        	{
	        		mLs_tf = (List<TransportFile>)data.getSerializableExtra("ls_tf"); 
	        		String sbShow = (String)data.getStringExtra("sbShow");  
	        		if(sbShow!=null)
	        		{
	        			et_receiver.setText(sbShow);
	        		}
	        	}
	        break;
	    }
	    super.onActivityResult(requestCode, resultCode, data);
	}
	
}


