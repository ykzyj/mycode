package com.sunnyit.common.thread;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import com.sunnyit.common.http.HttpGetOperate;
import com.sunnyit.common.json.JsonToData;
import android.os.Handler;

/**   
* @Title: HttpJsonThread.java 
* @Package com.sunnyit.common.http 
* @Description: TODO
* @author yk
* @date 2015��8��1�� ����9:53:31 
* @version V1.0   
*/
public abstract class JsonListUtil<T> extends Thread{
	
	private String murl;
	private Handler mhandler;
	private Class<?> mclass; 
	
	/**
	* @author yk
	* @date 2015��8��3�� ����3:47:29 
	* @param content ������
	* @param url ���ʵ�ַ
	* @param cla list�����������
	* @param handler UI����
	* @param listview list�ؼ�
	* @param layoutId ���list��item��id
	 */
	public JsonListUtil(String url) {
		// TODO Auto-generated constructor stub
		this.murl=url;
		this.mhandler=new Handler();
		this.mclass=getMyClass();
	}
	
	/**
	 * http��ȡjson
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String resultStr=HttpGetOperate.getStringOfHttpGet(murl);
		if(!resultStr.equals(""))
		{
			//List<T> lt=initJsonData(resultStr);
			JsonToData jd=new JsonToData();
			final List<T> lt=jd.initJsonData(resultStr, mclass);

			mhandler.post(new Runnable() {
				public void run() {
					initShowData(lt,mclass);
				}
			});
			
		}
	}
	
	/**
	* @author yk 
	* @date 2015��8��17�� ����11:28:12 
	* @Title: getMyClass 
	* @Description: ��ȡʵ�����ķ���class����
	* @return    �趨�ļ� 
	* @return Class<T>    �������� 
	* @throws
	 */
	public Class<T> getMyClass()
	{
		Class<T> cla=null;
		cla =  (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		return cla;
	}


	/**
	* @author yk 
	* @date 2015��8��3�� ����3:04:44 
	* @Title: initShowData 
	* @Description: ��http������������ݽ��д���
	* @return void    �������� 
	* @throws
	 */
	public abstract void initShowData(List<T> datas,Class cla);

}


