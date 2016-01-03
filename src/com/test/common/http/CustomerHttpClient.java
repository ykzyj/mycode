package com.sunnyit.common.http;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
/**   
* @Title: CustomHttpClient.java 
* @Package com.sunnyit.common.http 
* @Description: TODO
* @author yk
* @date 2015��8��31�� ����10:41:18 
* @version V1.0   
*/
public class CustomerHttpClient {
	/*
	 * ���ô����ʽΪUTF-8
	 */
	private static final String CHARSET = HTTP.UTF_8;
    private static HttpClient customerHttpClient;

    private CustomerHttpClient(ClientConnectionManager conMgr,HttpParams params) {
    	customerHttpClient =new DefaultHttpClient(conMgr, params);
    }

    public static synchronized HttpClient getHttpClient() {
        if (null== customerHttpClient) {
            HttpParams params =new BasicHttpParams();
            // ����һЩ��������
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params,
                    CHARSET);
            HttpProtocolParams.setUseExpectContinue(params, true);
            HttpProtocolParams.setUserAgent(params,
                            "Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
                                    +"AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
            // ��ʱ����
            /* �����ӳ���ȡ���ӵĳ�ʱʱ�� */
            ConnManagerParams.setTimeout(params, 1000);
            /* ���ӳ�ʱ */
            HttpConnectionParams.setConnectionTimeout(params, 2000);
            /* ����ʱ */
            HttpConnectionParams.setSoTimeout(params, 300000);
            
            // ����HttpClient֧��HTTP��HTTPS����ģʽ
            SchemeRegistry schReg =new SchemeRegistry();
            schReg.register(new Scheme("http", PlainSocketFactory
                    .getSocketFactory(), 80));
            schReg.register(new Scheme("https", SSLSocketFactory
                    .getSocketFactory(), 443));
            // ʹ���̰߳�ȫ�����ӹ���������HttpClient
            ClientConnectionManager conMgr =new ThreadSafeClientConnManager(
                    params, schReg);
            new CustomerHttpClient(conMgr, params);
        }
        return customerHttpClient;
    }
}


