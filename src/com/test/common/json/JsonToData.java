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
* @date 2015��8��19�� ����10:56:09 
* @version V1.0   
*/
public class JsonToData {
	
	/**
	* @author yk 
	* @date 2015��9��1�� ����4:10:57 
	* @Title: initJsonData 
	* @Description: ��ȡjson����
	* @param jsonstr
	* @param cla
	* @return    �趨�ļ� 
	* @return List<T>    �������� 
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
	* @date 2015��9��1�� ����4:11:14 
	* @Title: initJsonSuccess 
	* @Description: ��ȡsuccess
	* @param jsonstr
	* @return
	* @throws JSONException    �趨�ļ� 
	* @return String    �������� 
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
	* @date 2015��9��1�� ����4:11:34 
	* @Title: initJsonMsg 
	* @Description: ��ȡmsg
	* @param jsonstr
	* @return
	* @throws JSONException    �趨�ļ� 
	* @return String    �������� 
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


