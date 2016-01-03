package com.sunnyit.common.http;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

/**   
* @Title: HttpClientGetOperate.java 
* @Package com.sunnyit.common.http 
* @Description: TODO
* @author yk
* @date 2015年8月31日 上午11:49:18 
* @version V1.0   
*/
public class HttpClientGetOperate {
	
	public static String httpClientGet(String url) 
			throws ClientProtocolException, IOException{
		String content = null;
		HttpGet httpGet=new HttpGet(url);
		HttpClient httpClient=CustomerHttpClient.getHttpClient();
		HttpResponse response=httpClient.execute(httpGet);
		if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK)
		{
			content=EntityUtils.toString(response.getEntity());
		}
		return content;
	}
	
}


