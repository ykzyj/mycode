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
* @date 2015年9月1日 上午9:48:59 
* @version V1.0   
 * @param <T>
*/
public class CheckDataOfUserGetAsync extends AsyncTask<String, Long, String> {

	
	private CustomDialog cusdialog;//网络进度条
	private CustomDialog cusdialogt;//数据写入进度条
	
	private Context mContext;
	
	String DailyCheckStr="";
	String SpecialCheckStr="";
	String InspectorStr="";
	String Standard_CK_TableStr="";
	String Standard_CK_Table_ItemStr="";
	String CheckConditionItemStr="";
	
	private float minp;//进度条每一个最小进度
	private int basecount=0;//进度条显示数据
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
				Toast.makeText(mContext, "取消", Toast.LENGTH_SHORT).show();  
			}
		});
		cusdialog.show();
	}
	
	@Override
	protected String doInBackground(String... params) {
		
		String resultStr="";
		try {
			/**
			 * 获取新数据
			 */
			DailyCheckStr=HttpClientGetOperate.httpClientGet(params[0]);
			SpecialCheckStr=HttpClientGetOperate.httpClientGet(params[1]);
			InspectorStr=HttpClientGetOperate.httpClientGet(params[2]);
			Standard_CK_TableStr=HttpClientGetOperate.httpClientGet(params[3]);
			Standard_CK_Table_ItemStr=HttpClientGetOperate.httpClientGet(params[4]);
			CheckConditionItemStr=HttpClientGetOperate.httpClientGet(params[5]);
			
			/**
			 * 数据处理，得到数据的list
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
			Toast.makeText(mContext, "连接失败，请确认网络和服务器是否可用！", Toast.LENGTH_SHORT).show();
		}
		else
		{
			/*
			 * 删除上传记录数据
			 */
			deleteTable(new UpInfo());
			/**
			 * 删除本地已有数据
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
			cusdialogt.setText(R.id.tv_dg_title, "数据写入中");
			cusdialogt.findViewById(R.id.rel_cb_divider).setVisibility(View.GONE);
			cusdialogt.findViewById(R.id.lin_but_divider).setVisibility(View.GONE);
			cusdialogt.show();
			cusdialog.dismiss();
			
			minp=100.0f/pro_max;
			
			new Thread(new Runnable(){  
	            
	            public void run(){  
	            	
	            	/**
	            	 * 数据写书
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
	* @date 2015年9月23日 下午5:58:59 
	* @Title: dateSave 
	* @Description: 数据写入，更新progressbar
	* @param ls_o
	* @param t    设定文件 
	* @return void    返回类型 
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
	* @date 2015年9月23日 下午5:59:26 
	* @Title: deleteTable 
	* @Description: 本地数据删除
	* @param t    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private <T> void deleteTable(T t) {
		SqlOperate<T> opetater=new SqlOperate<T>(mContext, t.getClass());
		opetater.DeleteAllContent();
		opetater.close();
	}
	
	/**
	* @author yk 
	* @date 2015年9月23日 下午5:59:41 
	* @Title: getObjectList 
	* @Description: 获取list数据
	* @param jsonStr
	* @param Cla
	* @return    设定文件 
	* @return List<Object>    返回类型 
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
			Toast.makeText(mContext, "更新完成", Toast.LENGTH_SHORT).show();
		}
	};
	

}


