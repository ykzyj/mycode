package com.sunnyit.common.async;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sunnyit.R;
import com.sunnyit.common.dialog.CustomDialog;
import com.sunnyit.common.dialog.CustomDialog.CancelDialogListener;
import com.sunnyit.common.http.HttpClientGetOperate;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.company.model.SimpleEnterprise;
import com.sunnyit.enforcement.model.CheckConditionItem;
import com.sunnyit.enforcement.model.DailyCheck;
import com.sunnyit.enforcement.model.Inspector;
import com.sunnyit.enforcement.model.SpecialCheck;
import com.sunnyit.enforcement.model.Standard_CK_Table;
import com.sunnyit.enforcement.model.Standard_CK_Table_Item;
import com.sunnyit.synchronous.model.UpInfo;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

/**   
* @Title: HttpClientFileUpload.java 
* @Package com.sunnyit.common.async 
* @Description: TODO
* @author yk
* @date 2015��9��1�� ����9:48:59 
* @version V1.0   
 * @param <T>
*/
public class CheckDataOfUserGetAsync extends AsyncTask<String, Long, String> {

	
	private CustomDialog cusdialog;//���������
	private CustomDialog cusdialogt;//����д�������
	
	private Context mContext;
	
	String DailyCheckStr="";
	String SpecialCheckStr="";
	String InspectorStr="";
	String Standard_CK_TableStr="";
	String Standard_CK_Table_ItemStr="";
	String CheckConditionItemStr="";
	
	private float minp;//������ÿһ����С����
	private int basecount=0;//��������ʾ����
	private int pro_max=0;
	
	public CheckDataOfUserGetAsync(Context context) {
		this.mContext=context;
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		cusdialog=new CustomDialog(mContext);
		cusdialog.setViewAndAlpha(R.layout.progressbar_rw_blue,0);
		cusdialog.setOutCancel(false);
		cusdialog.setCancelDialogLiatener(new CancelDialogListener() {
			@Override
			public void onCancelDialog() {
				// TODO Auto-generated method stub
				Toast.makeText(mContext, "ȡ��", Toast.LENGTH_SHORT).show();  
			}
		});
		cusdialog.show();
	}
	
	@Override
	protected String doInBackground(String... params) {
		
		String resultStr="";
		try {
			/**
			 * ��ȡ������
			 */
			DailyCheckStr=HttpClientGetOperate.httpClientGet(params[0]);
			SpecialCheckStr=HttpClientGetOperate.httpClientGet(params[1]);
			InspectorStr=HttpClientGetOperate.httpClientGet(params[2]);
			Standard_CK_TableStr=HttpClientGetOperate.httpClientGet(params[3]);
			Standard_CK_Table_ItemStr=HttpClientGetOperate.httpClientGet(params[4]);
			CheckConditionItemStr=HttpClientGetOperate.httpClientGet(params[5]);
			
			/**
			 * ���ݴ����õ����ݵ�list
			 */
			initProgressCount(DailyCheckStr);
			initProgressCount(SpecialCheckStr);
			initProgressCount(InspectorStr);
			initProgressCount(Standard_CK_TableStr);
			initProgressCount(Standard_CK_Table_ItemStr);
			initProgressCount(CheckConditionItemStr);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			resultStr="connect_error";
		}
		
		return resultStr;
	}
	
	@Override
	protected void onProgressUpdate(Long... values) {
		// TODO Auto-generated method stub
	}
	
	@Override
	protected void onPostExecute(String result) {
		if(result.equals("connect_error"))
		{
			cusdialog.dismiss();
			Toast.makeText(mContext, "����ʧ�ܣ���ȷ������ͷ������Ƿ���ã�", Toast.LENGTH_SHORT).show();
		}
		else
		{
			/*
			 * ɾ���ϴ���¼����
			 */
			deleteTable(new UpInfo());
			/**
			 * ɾ��������������
			 */
			deleteTable(new DailyCheck());
			deleteTable(new SpecialCheck());
			deleteTable(new Inspector());
			deleteTable(new Standard_CK_Table());
			deleteTable(new Standard_CK_Table_Item());
			deleteTable(new CheckConditionItem());
			
			cusdialogt=new CustomDialog(mContext);
			cusdialogt.setViewAndAlpha(R.layout.dialog_custom,0);
			cusdialogt.initProgressBar(R.id.id_progressbarh);
			cusdialogt.setOutCancel(false);
			cusdialogt.setText(R.id.tv_dg_title, "����д����");
			cusdialogt.findViewById(R.id.rel_cb_divider).setVisibility(View.GONE);
			cusdialogt.findViewById(R.id.lin_but_divider).setVisibility(View.GONE);
			cusdialogt.show();
			cusdialog.dismiss();
			
			minp=100.0f/pro_max;
			
			new Thread(new Runnable(){  
	            
	            public void run(){  
	            	
	            	/**
	            	 * ����д��
	            	 */
	            	
	            	saveData(DailyCheckStr,DailyCheck.class);
	            	saveData(SpecialCheckStr,SpecialCheck.class);
	            	saveData(InspectorStr,Inspector.class);
	            	saveData(Standard_CK_TableStr,Standard_CK_Table.class);
	            	saveData(Standard_CK_Table_ItemStr,Standard_CK_Table_Item.class);
	            	saveData(CheckConditionItemStr,CheckConditionItem.class);
	            	
	            	cusdialogt.dismiss();
	            	handler.sendEmptyMessage(0);
	            }  
	        }).start();  
			
		}
	}
	
	public <T> void saveData(String jsonstr,Class<?> cla)
	{
		Field[] field=cla.getDeclaredFields();
		for(Field f:field) 
		{
			f.setAccessible(true);
		}
		try {
			JSONObject jsonobject = new JSONObject(jsonstr);
			JSONArray jsonarray=jsonobject.getJSONArray("data");
			T T_stance=(T) cla.newInstance();
			for(int i=0;i<jsonarray.length();i++)
			{
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
				SqlOperate<T> opetaterSim=new SqlOperate<T>(mContext, cla);
				opetaterSim.saveContent(T_stance);
	    		cusdialogt.setProgress((int)Math.rint(minp*(i+basecount+1)));
				opetaterSim.close();
			}
			basecount=basecount+jsonarray.length();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InstantiationException e) {
			e.printStackTrace();
		} 
		catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
	
	}
	
	/**
	* @author yk 
	* @date 2015��9��23�� ����5:58:59 
	* @Title: dateSave 
	* @Description: ����д�룬����progressbar
	* @param ls_o
	* @param t    �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	private <T> void dateSave(List<Object> ls_o,T t) {
		if(ls_o!=null)
		{
			SqlOperate<T> opetaterSim=new SqlOperate<T>(mContext, t.getClass());
			for(int i=0;i<ls_o.size();i++)
			{
	    		opetaterSim.saveContent((T)ls_o.get(i));
	    		cusdialogt.setProgress((int)Math.rint(minp*(i+basecount+1)));
			}
			opetaterSim.close();
	    	basecount=basecount+ls_o.size();
		}
	}  

	/**
	* @author yk 
	* @date 2015��9��23�� ����5:59:26 
	* @Title: deleteTable 
	* @Description: ��������ɾ��
	* @param t    �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	private <T> void deleteTable(T t) {
		SqlOperate<T> opetater=new SqlOperate<T>(mContext, t.getClass());
		opetater.DeleteAllContent();
		opetater.close();
	}
	
	/**
	* @author yk 
	* @date 2015��9��23�� ����5:59:41 
	* @Title: getObjectList 
	* @Description: ��ȡlist����
	* @param jsonStr
	* @param Cla
	* @return    �趨�ļ� 
	* @return List<Object>    �������� 
	* @throws
	 */
	private JSONArray initProgressCount(String jsonStr) {
		JSONObject jsonobject;
		JSONArray jsonarray = null;
		try {
			jsonobject = new JSONObject(jsonStr);
			jsonarray=jsonobject.getJSONArray("data");
			pro_max=pro_max+jsonarray.length();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return jsonarray;
		}
		return jsonarray;
	}
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			Toast.makeText(mContext, "�������", Toast.LENGTH_SHORT).show();
		}
	};
	

}


