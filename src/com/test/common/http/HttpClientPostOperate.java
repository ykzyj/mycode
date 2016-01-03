package com.sunnyit.common.http;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.util.EntityUtils;

import com.sunnyit.common.http.CustomMultiPartEntity.ProgressListener;

/**   
* @Title: HttpClientGetOperate.java 
* @Package com.sunnyit.common.http 
* @Description: TODO
* @author yk
* @date 2015年8月31日 上午11:49:18 
* @version V1.0   
*/
public class HttpClientPostOperate {
	
	public static final String DEFAULT_ENCODING = "UTF-8";
	
	public static <T> String httpClientPost(String url,T t,List<File> lf,ProgressListener listener) 
			throws ClientProtocolException, IOException{
		String content = null;
		HttpPost httpPost=new HttpPost(url);
		HttpClient httpClient=CustomerHttpClient.getHttpClient();
		CustomMultiPartEntity entity=new CustomMultiPartEntity();
		entity.setProgressListener(listener);
		Class<?> mclass=t.getClass();
		Field[] fieldes=mclass.getDeclaredFields();
		for(Field f:fieldes)
		{
			if(f.getGenericType()!=null)
			{
				StringBody strBody = null;
				f.setAccessible(true);
				try {
					if(f.getGenericType().toString().equals("class java.lang.Integer")||
							f.getGenericType().toString().equals("int"))
					{
						strBody = new StringBody(URLEncoder.encode(String.valueOf(f.getInt(t)), DEFAULT_ENCODING));
					}
					else
					{
						strBody = new StringBody(URLEncoder.encode(String.valueOf(f.get(t)), DEFAULT_ENCODING));
					}
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				entity.addPart(f.getName(),strBody);
			}
		}
		if(lf!=null)
		{
			for(int i=0; i<lf.size(); i++){
	        	ContentBody body = new FileBody(lf.get(i));	
	        	entity.addPart("file",body);
	        }
		}
		httpPost.setEntity(entity);
		HttpResponse response=httpClient.execute(httpPost);
		if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK)
		{
			content=EntityUtils.toString(response.getEntity());
		}
		return content;
	}
	
	public static <T> String httpClientPost(String url,T t,List<File> lf) 
			throws ClientProtocolException, IOException{
		String content = null;
		HttpPost httpPost=new HttpPost(url);
		HttpClient httpClient=CustomerHttpClient.getHttpClient();
		CustomMultiPartEntity entity=new CustomMultiPartEntity();
		Class<?> mclass=t.getClass();
		Field[] fieldes=mclass.getDeclaredFields();
		for(Field f:fieldes)
		{
			if(f.getGenericType()!=null)
			{
				StringBody strBody = null;
				f.setAccessible(true);
				try {
					if(f.getGenericType().toString().equals("class java.lang.Integer")||
							f.getGenericType().toString().equals("int"))
					{
						strBody = new StringBody(URLEncoder.encode(String.valueOf(f.getInt(t)), DEFAULT_ENCODING));
					}
					else
					{
						strBody = new StringBody(URLEncoder.encode(String.valueOf(f.get(t)), DEFAULT_ENCODING));
					}
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				entity.addPart(f.getName(),strBody);
			}
		}
		if(lf!=null)
		{
			for(int i=0; i<lf.size(); i++){
	        	ContentBody body = new FileBody(lf.get(i));	
	        	entity.addPart("file",body);
	        }
		}
		httpPost.setEntity(entity);
		HttpResponse response=httpClient.execute(httpPost);
		if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK)
		{
			content=EntityUtils.toString(response.getEntity());
		}
		return content;
	}
	
	public static <T> String httpClientPost(String url,T t) 
			throws ClientProtocolException, IOException{
		String content = null;
		HttpPost httpPost=new HttpPost(url);
		HttpClient httpClient=CustomerHttpClient.getHttpClient();
		CustomMultiPartEntity entity=new CustomMultiPartEntity();
		Class<?> mclass=t.getClass();
		Field[] fieldes=mclass.getDeclaredFields();
		for(Field f:fieldes)
		{
			if(f.getGenericType()!=null)
			{
				StringBody strBody = null;
				f.setAccessible(true);
				try {
					if(f.getGenericType().toString().equals("class java.lang.Integer")||
							f.getGenericType().toString().equals("int"))
					{
						strBody = new StringBody(URLEncoder.encode(String.valueOf(f.getInt(t)), DEFAULT_ENCODING));
					}
					else
					{
						strBody = new StringBody(URLEncoder.encode(String.valueOf(f.get(t)), DEFAULT_ENCODING));
					}
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				entity.addPart(f.getName(),strBody);
			}
		}
		httpPost.setEntity(entity);
		HttpResponse response=httpClient.execute(httpPost);
		if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK)
		{
			content=EntityUtils.toString(response.getEntity());
		}
		return content;
	}
}


