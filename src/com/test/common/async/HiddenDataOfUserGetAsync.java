package com.sunnyit.common.async;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.sunnyit.R;
import com.sunnyit.common.dialog.CustomDialog;
import com.sunnyit.common.dialog.CustomDialog.CancelDialogListener;
import com.sunnyit.common.http.HttpClientGetOperate;
import com.sunnyit.common.json.JsonToData;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.company.model.SimpleEnterprise;
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
import com.sunnyit.synchronous.model.CountyArea;
import com.sunnyit.synchronous.model.CountyDepartment;
import com.sunnyit.synchronous.model.Industry;
import com.sunnyit.synchronous.model.UpInfo;
import com.sunnyit.system.model.User;

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
public class HiddenDataOfUserGetAsync extends AsyncTask<String, Long, String> {
	
	private CustomDialog cusdialog;//���������
	private CustomDialog cusdialogt;//����д�������
	
	private Context mContext;
	
	private List<Object> ls_SelfCheck;
	private List<Object> ls_SelfStandCheck;
	private List<Object> ls_StandcheckDetail;
	
	private float minp;//������ÿһ����С����
	private int basecount=0;//��������ʾ����
	private int pro_max=0;
	
	public HiddenDataOfUserGetAsync(Context context) {
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
			String SelfCheckStr=HttpClientGetOperate.httpClientGet(params[0]);
			String SelfStandCheckStr=HttpClientGetOperate.httpClientGet(params[1]);
			String StandcheckDetailStr=HttpClientGetOperate.httpClientGet(params[2]);
			
			/**
			 * ���ݴ����õ����ݵ�list
			 */
			ls_SelfCheck = getObjectList(SelfCheckStr,SelfCheck.class);
			ls_SelfStandCheck = getObjectList(SelfStandCheckStr,SelfStandCheck.class);
			ls_StandcheckDetail = getObjectList(StandcheckDetailStr,StandcheckDetail.class);
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
			/**
			 * ɾ��������������
			 */
			deleteTable(new SelfCheck());
			deleteTable(new SelfStandCheck());
			deleteTable(new StandcheckDetail());
			
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
	            	dateSave(ls_SelfCheck,new SelfCheck());
	            	dateSave(ls_SelfStandCheck,new SelfStandCheck());
	            	dateSave(ls_StandcheckDetail,new StandcheckDetail());
	            	
	            	cusdialogt.dismiss();
	            	handler.sendEmptyMessage(0);
	            }  
	        }).start();  
			
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
		SqlOperate<T> opetaterSim=new SqlOperate<T>(mContext, t.getClass());
    	for(int i=0;i<ls_o.size();i++)
		{
    		opetaterSim.saveContent((T)ls_o.get(i));
    		//cusdialogt.setProgress((int)(minp*(i+basecount+1)));
    		cusdialogt.setProgress((int)Math.rint(minp*(i+basecount+1)));
		}
    	opetaterSim.close();
    	basecount=basecount+ls_o.size();
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
	private List<Object> getObjectList(String jsonStr,Class<?> Cla) {
		JsonToData jd=new JsonToData();
		boolean isSuccess;
		String msg;
		List<Object> ls = new ArrayList<>();
		try {
			isSuccess=jd.initJsonSuccess(jsonStr);
			msg=jd.initJsonMsg(jsonStr);
			if(isSuccess)
			{
				ls= jd.initJsonData(jsonStr, Cla);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pro_max=pro_max+ls.size();
		return ls;
	}
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			Toast.makeText(mContext, "���ݸ������", Toast.LENGTH_SHORT).show();
			if(mEndOperateListener!=null)
			{
				mEndOperateListener.setEndOperate();
			}
		}
	};
	
	public EndOperateListener mEndOperateListener;
	
	public void setEndOperateListener(EndOperateListener endOperateListener) {
		mEndOperateListener=endOperateListener;
	}
	
	public interface EndOperateListener
	{
		public void setEndOperate();
	}
	
}


