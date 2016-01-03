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
* @date 2015��8��17�� ����2:08:59 
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
	* @date 2015��8��3�� ����3:47:29 
	* @param content ������
	* @param url ���ʵ�ַ
	* @param cla list�����������
	* @param handler UI����
	* @param listview list�ؼ�
	* @param layoutId ���list��item��id
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


