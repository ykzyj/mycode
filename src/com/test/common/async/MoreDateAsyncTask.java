package com.sunnyit.common.async;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sunnyit.common.http.HttpGetOperate;
import com.sunnyit.common.view.ScrollLoadListview;

import android.os.AsyncTask;
import android.widget.BaseAdapter;

/**   
* @Title: MoreDateAsyncTask.java 
* @Package com.sunnyit.common.async 
* @Description: TODO
* @author yk
* @date 2015��8��17�� ����2:08:59 
* @version V1.0   
*/
public class MoreDateAsyncTask<T> extends AsyncTask<String, Integer, List<T>> {

	private Class<?> mclass;
	private List<T> mdata;
	private BaseAdapter madapter;
	private ScrollLoadListview mListView;
	
	public MoreDateAsyncTask(BaseAdapter adapter,List<T> datas,ScrollLoadListview listview) {
		// TODO Auto-generated constructor stub
		this.mclass=getMyClass();
		this.madapter=adapter;
		this.mdata=datas;
		this.mListView=listview;
	}

	@Override
	protected List<T> doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		String murl=arg0[0];
		String resultStr=HttpGetOperate.getStringOfHttpGet(murl);
		if(!resultStr.equals(""))
		{
			try {
				List<T> lt=initJsonData(resultStr);
				return lt;
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	@Override
	protected void onPostExecute(List<T> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		Collection cres=result;
		mdata.addAll(cres);
		madapter.notifyDataSetChanged();
		mListView.moreDateLoadComplete();
	}
	
	/**
	 * @throws InstantiationException 
	* @author yk 
	* @date 2015��8��3�� ����3:14:44 
	* @Title: initJsonData 
	* @Description: ��ȡ������ʻ�ȡ��������
	* @param jsonstr    �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	public List<T> initJsonData(String jsonstr) throws InstantiationException
	{
		List<T> mdata=new ArrayList<T>();
		Field[] field=mclass.getDeclaredFields();
		for(Field f:field) 
		{
			f.setAccessible(true);
		}
		try {
			JSONObject jsonobject=new JSONObject(jsonstr);
			JSONArray jsonarray=jsonobject.getJSONArray("data");
			for(int i=0;i<jsonarray.length();i++)
			{
				try {
					T T_stance=(T) mclass.newInstance();
					jsonobject=jsonarray.getJSONObject(i);
					for(Field f:field)
					{
						f.set(T_stance, jsonobject.get(f.getName()).toString());
					}
					mdata.add(T_stance);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mdata;
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
		ParameterizedType parameterizedType=(ParameterizedType) getClass().getGenericSuperclass();
		Type[] types= parameterizedType.getActualTypeArguments(); 
		for(int i=0;i<types.length;i++)
		{
			System.out.print(types[i].toString());
		}
		cla =  (Class<T>) types[2];
		return cla;
	}

}


