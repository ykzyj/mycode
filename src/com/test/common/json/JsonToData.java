package com.sunnyit.common.json;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sunnyit.system.model.User;

/**   
* @Title: JsonToData.java 
* @Package com.sunnyit.common.json 
* @Description: TODO
* @author yk
* @date 2015年8月19日 上午10:56:09 
* @version V1.0   
*/
public class JsonToData {
	
	/**
	* @author yk 
	* @date 2015年9月1日 下午4:10:57 
	* @Title: initJsonData 
	* @Description: 获取json数据
	* @param jsonstr
	* @param cla
	* @return    设定文件 
	* @return List<T>    返回类型 
	* @throws
	 */
	public <T> List<T> initJsonData(String jsonstr,Class<?> cla)
	{
		List<T> listdata=new ArrayList<T>();
		Field[] field=cla.getDeclaredFields();
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
					T T_stance=(T) cla.newInstance();
					jsonobject=jsonarray.getJSONObject(i);
					for(Field f:field)
					{
						try {
							
							if(f.getGenericType().toString().equals("class java.lang.Integer")||
									f.getGenericType().toString().equals("int"))
							{
								f.set(T_stance, Integer.valueOf(jsonobject.get(f.getName()).toString()));
							}
							else
							{
								f.set(T_stance, jsonobject.get(f.getName()).toString());
							}
						} catch (Exception e) {
							f.set(T_stance, "");
						}
					}
					listdata.add(T_stance);
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listdata;
	}
	
	/**
	 * @throws JSONException 
	* @author yk 
	* @date 2015年9月1日 下午4:11:14 
	* @Title: initJsonSuccess 
	* @Description: 获取success
	* @param jsonstr
	* @return
	* @throws JSONException    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public boolean initJsonSuccess(String jsonstr) throws JSONException
	{
		StringBuffer result=new StringBuffer();
		JSONObject jsonobject=new JSONObject(jsonstr);
		result.append(jsonobject.get("success").toString());
		return Boolean.valueOf(result.toString());
	}
	
	/**
	* @author yk 
	* @date 2015年9月1日 下午4:11:34 
	* @Title: initJsonMsg 
	* @Description: 获取msg
	* @param jsonstr
	* @return
	* @throws JSONException    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public String initJsonMsg(String jsonstr) throws JSONException
	{
		StringBuffer result=new StringBuffer();
		JSONObject jsonobject=new JSONObject(jsonstr);
		result.append(jsonobject.get("msg").toString());
		return result.toString();
	}
}


