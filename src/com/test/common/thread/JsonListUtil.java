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
* @date 2015年8月1日 上午9:53:31 
* @version V1.0   
*/
public abstract class JsonListUtil<T> extends Thread{
	
	private String murl;
	private Handler mhandler;
	private Class<?> mclass; 
	
	/**
	* @author yk
	* @date 2015年8月3日 下午3:47:29 
	* @param content 上下文
	* @param url 访问地址
	* @param cla list数据填充类型
	* @param handler UI更新
	* @param listview list控件
	* @param layoutId 填充list的item的id
	 */
	public JsonListUtil(String url) {
		// TODO Auto-generated constructor stub
		this.murl=url;
		this.mhandler=new Handler();
		this.mclass=getMyClass();
	}
	
	/**
	 * http获取json
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
	* @date 2015年8月17日 上午11:28:12 
	* @Title: getMyClass 
	* @Description: 获取实例化的泛型class类型
	* @return    设定文件 
	* @return Class<T>    返回类型 
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
	* @date 2015年8月3日 下午3:04:44 
	* @Title: initShowData 
	* @Description: 对http请求产生的数据进行处理
	* @return void    返回类型 
	* @throws
	 */
	public abstract void initShowData(List<T> datas,Class cla);

}


