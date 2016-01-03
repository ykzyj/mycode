package com.sunnyit.common.services;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.http.HttpStatus;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;

public class DownService extends Service {

	public static final String DOWNPATH=
			Environment.getExternalStorageDirectory().getAbsolutePath()
			+"/anjianju/";
	
	public static final String ACTION_START="ACTION_START";
	public static final String ACTION_UPDATE="ACTION_UPDATE";
	public static final String ACTION_SETLENGTH="ACTION_SETLENGTH";
	public static final String ACTION_FINISH="ACTION_FINISH";
	
	public static final int MSG_INIT=0;
	
	private DownTask mDownTask;
	
	private String fileUrl;
	private String fileName;
	private String fileId;
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		
		fileUrl=intent.getStringExtra("fileUrl");
		fileName=intent.getStringExtra("fileName");
		fileId=intent.getStringExtra("fileId");
		new DownThread(fileUrl,fileName).start();
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	private Handler mHandler=new Handler()
	{
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MSG_INIT:
				String fileLength=(String)msg.obj;
				
				Intent intent=new Intent(DownService.ACTION_SETLENGTH);
				intent.putExtra("filelength", Long.valueOf(fileLength));
				DownService.this.sendBroadcast(intent);
				
				mDownTask=new DownTask(DownService.this, fileUrl,fileName,fileId,fileLength);
				mDownTask.downLoad();
				
				break;

			default:
				break;
			}
		};
	};
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	class DownThread extends Thread {
		
		private String mUrl;
		private String mName;
		
		public DownThread(String url,String name)
		{
			this.mUrl=url;
			this.mName=name;
		}

		@Override
		public void run() {
			HttpURLConnection conn=null;
			RandomAccessFile randomFile=null;
			try {
				//获取网络文件
				//URL url=new URL(mUrl);
				//URLEncoder.encode(mUrl, "utf-8")
				int lastState=mUrl.lastIndexOf("/");
				String fileName=mUrl.substring(lastState+1, mUrl.length());
				URL url=new URL(mUrl.substring(0, lastState+1)+URLEncoder.encode(fileName, "utf-8"));
				conn=(HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(3000);
				conn.setRequestMethod("GET");
				long length=-1;
				if(conn.getResponseCode()==HttpStatus.SC_OK)
				{
					//获取文件长度
					length=conn.getContentLength();
				}
				if(length<=0)
				{
					return;
				}
				File dir=new File(DOWNPATH);
				if(!dir.exists())
				{
					dir.mkdir();
				}
				//创建本地文件
				File file=new File(dir, mName);
				randomFile=new RandomAccessFile(file, "rwd");
				randomFile.setLength(length);
				mHandler.obtainMessage(MSG_INIT, String.valueOf(length)).sendToTarget();
			} catch (Exception e) {
			}
			finally
			{
				if(conn!=null)
				{
					conn.disconnect();
				}
				if(randomFile!=null)
				{
					try {
						randomFile.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
					}
				}
			}
		}
	}
	
}
