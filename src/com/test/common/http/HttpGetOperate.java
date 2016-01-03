package com.sunnyit.common.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.Environment;

/**   
* @Title: GetResponse.java 
* @Package com.sunnyit.common.http 
* @Description: TODO
* @author yk
* @date 2015年8月17日 下午2:14:38 
* @version V1.0   
*/
public class HttpGetOperate{
	public static String getStringOfHttpGet(String url)
	{
		HttpURLConnection conn = null;
		BufferedReader br = null;
		InputStream in = null;
		try {
			URL httpurl=new URL(url);
			conn=(HttpURLConnection)httpurl.openConnection();
			conn.setReadTimeout(5000);
			conn.setRequestMethod("GET");
			in=conn.getInputStream();
			String str;
			StringBuffer sb=new StringBuffer();
			br=new BufferedReader(new InputStreamReader(in));
			while((str=br.readLine())!=null)
			{
				sb.append(str);
			}
			return sb.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		finally {
			if(conn!=null)
			{
				conn.disconnect();
			}
			try {
				if(in!=null)
				{
					in.close();
				}
				if(br!=null)
				{ 
					br.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	* @author yk 
	* @date 2015年8月29日 上午9:37:38 
	* @Title: getFileOfHttpGet 
	* @Description: 获取文件并写入，返回文件的写入地址
	* @param url
	* @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String getFileOfHttpGet(String url)
	{
		URL httpUrl;
		HttpURLConnection conn=null;
		InputStream in = null;
		FileOutputStream fileOut = null;
		try {
			httpUrl = new URL(url);
			conn=(HttpURLConnection)httpUrl.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			in=conn.getInputStream();
			
			File downFile = null;
			
			if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
			{
				String fileName=String.valueOf(System.currentTimeMillis());
				File parent=Environment.getExternalStorageDirectory();
				downFile=new File(parent, fileName);
				fileOut=new FileOutputStream(downFile);
			}
			
			int len=0;
			byte[] bt=new byte[4*1024];
			if(fileOut!=null)
			{
				while((len=in.read(bt))!=-1)
				{
					fileOut.write(bt, 0, len);
				}
			}
			
			if(downFile!=null)
			{
				return downFile.getAbsolutePath().toString();
			}
			else
			{
				return "";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		finally {
			if(conn!=null)
			{
				conn.disconnect();
			}
			try {
				if(in!=null)
				{
					in.close();
				}
				if(fileOut!=null)
				{ 
					fileOut.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}


