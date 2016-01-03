package com.sunnyit.common.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpStatus;

import com.sunnyit.common.sqlite.SqlOperate;

import android.content.Context;
import android.content.Intent;

public class DownTask {
	private Context mContext;
	private Long mFinished=0L;
	
	private String mfileUrl;
	private String mfileName;
	private String mfilelength;
	private String mfileId;
	
	public DownTask(Context context,String fileUrl,String fileName,String fileId,String filelength)
	{
		this.mContext=context;
		this.mfileUrl=fileUrl;
		this.mfileName=fileName;
		this.mfilelength=filelength;
		this.mfileId=fileId;
	}
	
	public void downLoad() {
		new DownLoadThread(mfileUrl,mfileName,mfilelength).start();
	}
	
	class DownLoadThread extends Thread
	{
		private String mUrl;
		private String mName;
		private String mlength;
		
		public DownLoadThread(String url,String name,String length)
		{
			this.mUrl=url;
			this.mName=name;
			this.mlength=length;
		}
		
		@Override
		public void run() {
			
			HttpURLConnection conn=null;
			RandomAccessFile raf=null;
			InputStream in=null;
			try {
				int lastState=mUrl.lastIndexOf("/");
				String fileName=mUrl.substring(lastState+1, mUrl.length());
				URL url=new URL(mUrl.substring(0, lastState+1)+URLEncoder.encode(fileName, "utf-8"));
				conn=(HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(5000);
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Range", "bytes=0-"+mlength);
				//文件写入位置
				File fiel=new File(DownService.DOWNPATH,mName);
				raf=new RandomAccessFile(fiel, "rwd");
				raf.seek(0);
				//开始下载
				Intent intent=new Intent(DownService.ACTION_UPDATE);
				mFinished=0L;
				long lastTime=System.currentTimeMillis();
				long loadCount=0L;
				if(conn.getResponseCode()==HttpStatus.SC_OK)
				{
					in=conn.getInputStream();
					byte[] buffer=new byte[1024*8];
					int len=-1;
					while((len=in.read(buffer))!=-1)
					{
						raf.write(buffer,0,len);
						//进度
						mFinished+=len;
						loadCount+=len;
						if((System.currentTimeMillis()-lastTime)>500)
						{
							int temp=(int) (mFinished*100/Long.valueOf(mlength));
							intent.putExtra("Finished",String.valueOf(temp));
							intent.putExtra("speed", loadCount*2/1024.00);
							intent.putExtra("filelength", Long.valueOf(mlength));
							intent.putExtra("Fileid", mfileId);
							mContext.sendBroadcast(intent);
							lastTime=System.currentTimeMillis();
							loadCount=0;
						}
					}
					intent.putExtra("Finished", String.valueOf(100));
					intent.putExtra("Fileid", mfileId);
					intent.putExtra("speed", 0.00);
					mContext.sendBroadcast(intent);
				}
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				try {
					if(raf!=null)
					{
						raf.close();
					}
					if(conn!=null)
					{
						conn.disconnect();
					}
					if(in!=null)
					{
						in.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}
	
}
