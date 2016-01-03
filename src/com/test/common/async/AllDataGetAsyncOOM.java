package com.sunnyit.common.async;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sunnyit.R;
import com.sunnyit.chemicals.action.ChemicalsDirectoryActivity;
import com.sunnyit.chemicals.model.Chemicals;
import com.sunnyit.common.dialog.CustomDialog;
import com.sunnyit.common.dialog.CustomDialog.CancelDialogListener;
import com.sunnyit.common.http.HttpClientGetOperate;
import com.sunnyit.common.json.JsonToData;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.company.model.SimpleEnterprise;
import com.sunnyit.enforcement.model.CheckConditionItem;
import com.sunnyit.enforcement.model.DailyCheck;
import com.sunnyit.enforcement.model.Inspector;
import com.sunnyit.enforcement.model.SpecialCheck;
import com.sunnyit.enforcement.model.Standard_CK_Table;
import com.sunnyit.enforcement.model.Standard_CK_Table_Item;
import com.sunnyit.hiddencheck.model.SelfCheck;
import com.sunnyit.hiddencheck.model.SelfStandCheck;
import com.sunnyit.hiddencheck.model.StandcheckDetail;
import com.sunnyit.law.model.ExcelCell;
import com.sunnyit.law.model.HiddenStandardFile;
import com.sunnyit.law.model.PublishInfo;
import com.sunnyit.remind.model.InformationRemind;
import com.sunnyit.synchronous.model.CountyArea;
import com.sunnyit.synchronous.model.CountyDepartment;
import com.sunnyit.synchronous.model.Industry;
import com.sunnyit.synchronous.model.UpInfo;
import com.sunnyit.system.model.User;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;
import jxl.Sheet;
import jxl.Workbook;
import test.action.DialogShowActivity;

/**   
* @Title: HttpClientFileUpload.java 
* @Package com.sunnyit.common.async 
* @Description: TODO
* @author yk
* @date 2015年9月1日 上午9:48:59 
* @version V1.0   
 * @param <T>
*/
public class AllDataGetAsyncOOM extends AsyncTask<String, Long, String> {
	
	private CustomDialog cusdialog;//网络进度条
	private CustomDialog cusdialogt;//数据写入进度条
	
	private Context mContext;
	
	String SimpleEnterpriseStr="";
	String IndustryStr="";
	String CountyAreaStr="";
	String CountyDepartmentStr="";
	String SelfCheckStr="";
	String SelfStandCheckStr="";
	String StandcheckDetailStr="";
	String UserStr="";
	String PublishInfoStr="";
	String ExcelCellStr="";
	String HiddenStandardFileStr="";
	String DailyCheckStr="";
	String SpecialCheckStr="";
	String InspectorStr="";
	String Standard_CK_TableStr="";
	String Standard_CK_Table_ItemStr="";
	String ChemicalsStr="";
	String CheckConditionItemStr="";
	
	private float minp;//进度条每一个最小进度
	private int basecount=0;//进度条显示数据
	private int pro_max=0;
	
	public AllDataGetAsyncOOM(Context context) {
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
			SimpleEnterpriseStr=HttpClientGetOperate.httpClientGet(params[0]);
			IndustryStr=HttpClientGetOperate.httpClientGet(params[1]);
			CountyAreaStr=HttpClientGetOperate.httpClientGet(params[2]);
			CountyDepartmentStr=HttpClientGetOperate.httpClientGet(params[3]);
			SelfCheckStr=HttpClientGetOperate.httpClientGet(params[4]);
			SelfStandCheckStr=HttpClientGetOperate.httpClientGet(params[5]);
			StandcheckDetailStr=HttpClientGetOperate.httpClientGet(params[6]);
			UserStr=HttpClientGetOperate.httpClientGet(params[7]);
			PublishInfoStr=HttpClientGetOperate.httpClientGet(params[8]);
			ExcelCellStr=HttpClientGetOperate.httpClientGet(params[9]);
			HiddenStandardFileStr=HttpClientGetOperate.httpClientGet(params[10]);
			
			DailyCheckStr=HttpClientGetOperate.httpClientGet(params[11]);
			SpecialCheckStr=HttpClientGetOperate.httpClientGet(params[12]);
			InspectorStr=HttpClientGetOperate.httpClientGet(params[13]);
			Standard_CK_TableStr=HttpClientGetOperate.httpClientGet(params[14]);
			Standard_CK_Table_ItemStr=HttpClientGetOperate.httpClientGet(params[15]);
			ChemicalsStr=HttpClientGetOperate.httpClientGet(params[16]);
			CheckConditionItemStr=HttpClientGetOperate.httpClientGet(params[17]);
			
			/**
			 * 数据处理，得到数据的list
			 */
			initProgressCount(SimpleEnterpriseStr);
			initProgressCount(IndustryStr);
			initProgressCount(CountyAreaStr);
			initProgressCount(CountyDepartmentStr);
			initProgressCount(SelfCheckStr);
			initProgressCount(SelfStandCheckStr);
			initProgressCount(StandcheckDetailStr);
			initProgressCount(UserStr);
			initProgressCount(PublishInfoStr);
			initProgressCount(ExcelCellStr);
			initProgressCount(HiddenStandardFileStr);
			initProgressCount(DailyCheckStr);
			initProgressCount(SpecialCheckStr);
			initProgressCount(InspectorStr);
			initProgressCount(Standard_CK_TableStr);
			initProgressCount(Standard_CK_Table_ItemStr);
			initProgressCount(ChemicalsStr);
			initProgressCount(CheckConditionItemStr);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			resultStr="connect_error";
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
			/*Toast.makeText(mContext, "连接失败，请确认网络和服务器是否可用！", Toast.LENGTH_SHORT).show();*/
			
			AlertDialog.Builder builder=new AlertDialog.Builder(mContext);  //先得到构造器  
	        builder.setTitle("提示"); //设置标题  
	        builder.setMessage("数据访问异常，请确认请您的网络连接环境以及手机内存是否可用！"); //设置内容  
	        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮  
	            @Override  
	            public void onClick(DialogInterface dialog, int which) {  
	                dialog.dismiss(); //关闭dialog  
	                //Toast.makeText(mContext, "确认" + which, Toast.LENGTH_SHORT).show();  
	            }  
	        });  
	        builder.create().show(); 
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
			deleteTable(new SimpleEnterprise());
			deleteTable(new Industry());
			deleteTable(new CountyArea());
			deleteTable(new CountyDepartment());
			deleteTable(new SelfCheck());
			deleteTable(new SelfStandCheck());
			deleteTable(new StandcheckDetail());
			deleteTable(new User());
			deleteTable(new PublishInfo());
			deleteTable(new ExcelCell());
			deleteTable(new HiddenStandardFile());
			
			deleteTable(new DailyCheck());
			deleteTable(new SpecialCheck());
			deleteTable(new Inspector());
			deleteTable(new Standard_CK_Table());
			deleteTable(new Standard_CK_Table_Item());
			
			deleteTable(new Chemicals());
			deleteTable(new CheckConditionItem());
			
			//deleteTable(new InformationRemind());
			
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
	            	
	            	saveData(SimpleEnterpriseStr,SimpleEnterprise.class);
	            	saveData(IndustryStr,Industry.class);
	            	saveData(CountyAreaStr,CountyArea.class);
	            	saveData(CountyDepartmentStr,CountyDepartment.class);
	            	saveData(SelfCheckStr,SelfCheck.class);
	            	saveData(SelfStandCheckStr,SelfStandCheck.class);
	            	saveData(StandcheckDetailStr,StandcheckDetail.class);
	            	saveData(UserStr,User.class);
	            	saveData(PublishInfoStr,PublishInfo.class);
	            	saveData(ExcelCellStr,ExcelCell.class);
	            	saveData(HiddenStandardFileStr,HiddenStandardFile.class);
	            	
	            	saveData(DailyCheckStr,DailyCheck.class);
	            	saveData(SpecialCheckStr,SpecialCheck.class);
	            	saveData(InspectorStr,Inspector.class);
	            	saveData(Standard_CK_TableStr,Standard_CK_Table.class);
	            	saveData(Standard_CK_Table_ItemStr,Standard_CK_Table_Item.class);
	            	
	            	saveData(ChemicalsStr,Chemicals.class);
	            	saveData(CheckConditionItemStr,CheckConditionItem.class);
	            	
	            	cusdialogt.dismiss();
	            	
	            	SimpleEnterpriseStr=null;
	            	IndustryStr=null;
	            	CountyAreaStr=null;
	            	CountyDepartmentStr=null;
	            	SelfCheckStr=null;
	            	SelfStandCheckStr=null;
	            	StandcheckDetailStr=null;
	            	UserStr=null;
	            	PublishInfoStr=null;
	            	ExcelCellStr=null;
	            	HiddenStandardFileStr=null;
	            	DailyCheckStr=null;
	            	SpecialCheckStr=null;
	            	InspectorStr=null;
	            	Standard_CK_TableStr=null;
	            	Standard_CK_Table_ItemStr=null;
	            	ChemicalsStr=null;
	            	CheckConditionItemStr=null;
	            	
	            	System.gc();
	            	
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
			//Toast.makeText(mContext, "更新完成", Toast.LENGTH_SHORT).show();
			
			AlertDialog.Builder builder=new AlertDialog.Builder(mContext);  //先得到构造器  
	        builder.setTitle("提示"); //设置标题  
	        builder.setMessage("数据获取成功"); //设置内容  
	        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮  
	            @Override  
	            public void onClick(DialogInterface dialog, int which) {  
	                dialog.dismiss(); //关闭dialog  
	                //Toast.makeText(mContext, "确认" + which, Toast.LENGTH_SHORT).show();  
	            }  
	        });  
	        builder.create().show(); 
			
		}
	};
	
}


