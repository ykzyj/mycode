package com.sunnyit.common.async;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.sunnyit.R;
import com.sunnyit.common.dialog.CustomDialog;
import com.sunnyit.common.dialog.CustomDialog.CancelDialogListener;
import com.sunnyit.common.http.CustomMultiPartEntity.ProgressListener;
import com.sunnyit.common.http.HttpClientGetOperate;
import com.sunnyit.common.http.HttpClientPostOperate;
import com.sunnyit.common.json.JsonToData;

import android.content.Context;
import android.os.AsyncTask;
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
public class HttpClientAsyncGet extends AsyncTask<String, Long, String> {
	
	private List<File> mList_file;
	private Class mClass;
	private Object mO;
	private CustomDialog cusdialog;
	
	private Context mContext;
	private onHttpClientReturnListener mHttpClientReturnListener;
	
	public <T> HttpClientAsyncGet(Context context,List<File> lf,T t) {
		this.mList_file=lf;
		this.mClass=t.getClass();
		this.mO=t;
		this.mContext=context;
	}
	
	public <T> HttpClientAsyncGet(Context context,T t) {
		this(context,null,t);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		cusdialog=new CustomDialog(mContext);
		if(mList_file==null)
		{
			cusdialog.setViewAndAlpha(R.layout.progressbar_rw_blue,0);
		}
		else
		{
			cusdialog.setViewAndAlpha(R.layout.dialog_custom,0);
			cusdialog.initProgressBar(R.id.id_progressbarh);
		}
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
		ProgressListener mlistener=null;
		if(mList_file!=null)
		{
			mlistener=new ProgressListener() {
				@Override
				public void progress(int progress) {
					// TODO Auto-generated method stub
					publishProgress((long)progress);
				}
				@Override
				public void cumulative(long num) {
					// TODO Auto-generated method stub
				}
			};
		}
		
		String resultStr="";
		try {
			resultStr=HttpClientGetOperate.httpClientGet(params[0]);
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
		if(mList_file!=null)
		{
			cusdialog.setProgress((int)(long)values[0]);
		}
	}
	
	@Override
	protected void onPostExecute(String result) {
		if(result!=null)
		{
			if(result.equals("connect_error"))
			{
				cusdialog.dismiss();
				Toast.makeText(mContext, "连接失败，请确认网络和服务器是否可用！", Toast.LENGTH_SHORT).show();
			}
			else
			{
				JsonToData jd=new JsonToData();
				boolean isSuccess;
				String msg;
				List<Object> lo = null;
				try {
					isSuccess=jd.initJsonSuccess(result);
					msg=jd.initJsonMsg(result);
					if(isSuccess)
					{
						lo= jd.initJsonData(result, mClass);
					}
					if(mHttpClientReturnListener!=null)
					{
						mHttpClientReturnListener.returnPostExecute(isSuccess, msg, lo);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cusdialog.dismiss();
			}
		}
	}
	
	
	public void setHttpClientReturnListener(onHttpClientReturnListener httpClientReturnListener)
	{
		this.mHttpClientReturnListener=httpClientReturnListener;
	}
	
	public interface onHttpClientReturnListener
	{
		public void returnPostExecute(boolean isSuccess,String msg,List<Object> lo);
	}

}


