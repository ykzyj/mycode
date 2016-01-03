package com.sunnyit.common.thread;

import java.util.List;

import com.sunnyit.common.http.HttpGetOperate;
import com.sunnyit.common.json.JsonToData;
import com.sunnyit.common.view.ClickLoadListview;
import com.sunnyit.common.view.ScrollLoadListview;

import android.os.Handler;
import android.widget.BaseAdapter;

/**   
* @Title: MoreDateAsyncTask.java 
* @Package com.sunnyit.common.async 
* @Description: TODO
* @author yk
* @date 2015年8月17日 下午2:08:59 
* @version V1.0   
*/
public class MoreDateJsonList<T> extends Thread{
	private String murl;
	private Class mclass;
	
	private List<T> mdata;
	private BaseAdapter madapter;
	private ClickLoadListview mListView;
	private Handler mhandler;
	
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
	public MoreDateJsonList(String url,BaseAdapter adapter,List<T> datas,ClickLoadListview listview,Class cla) {
		// TODO Auto-generated constructor stub
		this.murl=url;
		this.madapter=adapter;
		this.mdata=datas;
		this.mListView=listview;
		this.mclass=cla;
		this.mhandler=new Handler();
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
					initShow(lt);
				}
			});
		}
	}
	
	public void initShow(List<T> data) {
		// TODO Auto-generated method stub
		mdata.addAll(data);
		madapter.notifyDataSetChanged();
		mListView.moreDateLoadComplete();
	}
}


