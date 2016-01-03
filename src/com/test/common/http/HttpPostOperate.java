package com.sunnyit.common.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;

/**   
* @Title: HttpPostOperate.java 
* @Package com.sunnyit.common.http 
* @Description: TODO
* @author yk
* @date 2015年8月29日 上午9:52:05 
* @version V1.0   
*/
public class HttpPostOperate {
	public static <T> String getStringOfHttpGet(String url,T t)
	{
		HttpURLConnection conn=null;
		OutputStream out=null;
		InputStream in=null;
		try {
			URL httpUrl=new URL(url);
			conn=(HttpURLConnection) httpUrl.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("POST");
			out=conn.getOutputStream(); 
			in=conn.getInputStream();
			
			StringBuffer parameter=new StringBuffer();
			Class<?> mclass=t.getClass();
			Field[] fieldes=mclass.getDeclaredFields();
			for(Field f:fieldes)
			{
				f.setAccessible(true);
				Class<?> fc=f.getClass();
					try {
						if(fc.getSimpleName().equals(Integer.class.getSimpleName()))
						{
						parameter.append(f.getName().toString()+"="+String.valueOf(f.getInt(t)));
						}
						else if(fc.getSimpleName().equals(String.class.getSimpleName()))
						{
							parameter.append(f.getName().toString()+"="+String.valueOf(f.get(t)));
						}
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					parameter.append("&");
			}
			String parameterStr=parameter.substring(0, parameter.length()-1);
			out.write(parameterStr.getBytes());
			
			BufferedReader br=new BufferedReader(new InputStreamReader(in));
			String str;
			StringBuffer sb=new StringBuffer();
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
	}
}


