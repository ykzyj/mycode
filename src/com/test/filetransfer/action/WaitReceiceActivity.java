package com.sunnyit.filetransfer.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.async.HttpClientAsyncGet;
import com.sunnyit.common.async.HttpClientAsyncPost;
import com.sunnyit.common.dataOperate.dOperate;
import com.sunnyit.common.async.HttpClientAsyncGet.onHttpClientReturnListener;
import com.sunnyit.common.http.HttpClientGetOperate;
import com.sunnyit.common.json.JsonToData;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.ClickLoadListview;
import com.sunnyit.common.view.CustomFAB;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.ClickLoadListview.IClickLoadListListener;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.filetransfer.data.TransportFileAdapter;
import com.sunnyit.filetransfer.model.TransportFile;
import com.sunnyit.system.action.LoginActivity;
import com.sunnyit.system.action.MainActivity;
import com.sunnyit.system.model.User;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**   
* @Title: DailyCheckList.java 
* @Package com.sunnyit.hiddencheck.action 
* @Description: TODO
* @author yk
* @date 2015年9月7日 下午3:20:13 
* @version V1.0   
*/
public class WaitReceiceActivity extends BaseActivity {

	
	private TopBarView topbar_file_tf_operate;
	private ClickLoadListview mlistview	;
	
	private TransportFileAdapter mAdapter;
	private List<TransportFile> mData;
	
	private int pangSize=10;
	private int pageCount=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.file_transfer_operate);
		
		initComponent();
		
		getTransportFileOfNet();
		
		topbar_file_tf_operate.setTopBarClick(new ITopBarClick() {
			
			@Override
			public void onClickRightBut() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onClickLeftBut() {
				// TODO Auto-generated method stub
				finish();
			}
		});
		topbar_file_tf_operate.setTitle("待接收文件");
		topbar_file_tf_operate.setRightButVisibility(View.GONE);
		
	}
	
	private Handler mHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			
			mAdapter.notifyDataSetChanged();
			if(msg.arg1<pangSize)
			{
				mlistview.hindLoadView(true);
			}
		};
	};

	private void initAdapter(List<Object> lo) {
		mData=new ArrayList<>();
		for(Object transportFile:lo)
		{
			mData.add((TransportFile) transportFile);
		}
		mAdapter=new TransportFileAdapter(WaitReceiceActivity.this, mData, R.layout.file_transfer_item);
		if(mData.size()<pangSize)
		{
			mlistview.hindLoadView(true);
		}
		
		mlistview.setAdapter(mAdapter);
		mlistview.setIClickLoadListListener(new IClickLoadListListener() {
			@Override
			public void onLoad(Handler handler) {
				JsonToData jd=new JsonToData();
				String resultStr;
				try {
					resultStr = HttpClientGetOperate.httpClientGet(getBaseUrl()+
							"/appWebGet/getTransportFileByPageNo?PageNo="+String.valueOf(pageCount)
							+ "&requestType=waitreceive&receiverId="+getCurrentUser().getUserId());
					List<TransportFile> ls_TransportFile = jd.initJsonData(resultStr, TransportFile.class);
					mData.addAll(ls_TransportFile);
					pageCount++;
					Message msg=mHandler.obtainMessage();
					msg.arg1=ls_TransportFile.size();
					mHandler.sendMessage(msg);
				} 
				catch (ClientProtocolException e) {
					e.printStackTrace();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		mlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				upTransportFileOfNet(arg2);
			}
		});
	}

	private void initComponent() {
		topbar_file_tf_operate=(TopBarView) findViewById(R.id.topbar_file_tf_operate);
		mlistview=(ClickLoadListview) findViewById(R.id.lv_file_tf_operate);
	}
	
	private void getTransportFileOfNet() {
		HttpClientAsyncGet post=new HttpClientAsyncGet(WaitReceiceActivity.this,new TransportFile());
		String baseURL=getBaseUrl();
		if(baseURL!=null)
		{
			post.execute(baseURL+"/appWebGet/getTransportFileByPageNo?PageNo="+String.valueOf(pageCount)
					+ "&requestType=waitreceive&receiverId="+getCurrentUser().getUserId());
			post.setHttpClientReturnListener(new onHttpClientReturnListener() {
				@Override
				public void returnPostExecute(boolean isSuccess, String msg, List<Object> lo) {
					// TODO Auto-generated method stub
					if(isSuccess)
					{
						initAdapter(lo);
						pageCount++;
					}
					else
					{
						Toast.makeText(WaitReceiceActivity.this,msg, Toast.LENGTH_LONG).show();
					}
				}
			});
		}
	}
	
	private void upTransportFileOfNet(final int position) {
		mData.get(position).setReceiveTime(dOperate.getCurrentLongDate());
		mData.get(position).setState("1");
		HttpClientAsyncPost post=new HttpClientAsyncPost(WaitReceiceActivity.this, mData.get(position));
		String baseURL=getBaseUrl();
		if(baseURL!=null)
		{
			post.execute(baseURL+"/appWebSave/upTransportFile");
			post.setHttpClientReturnListener(new HttpClientAsyncPost.onHttpClientReturnListener() {
				@Override
				public void returnPostExecute(boolean isSuccess, String msg, List<Object> lo) {
					// TODO Auto-generated method stub
					if(isSuccess)
					{
						//Toast.makeText(WaitReceiceActivity.this,msg, Toast.LENGTH_LONG).show();
						Intent intent=new Intent(WaitReceiceActivity.this, TransportFileShowActivity.class);
						intent.putExtra("TransportFile", mData.get(position));
						startActivity(intent);
						
						mData.remove(position);
						mAdapter.notifyDataSetChanged();
					}
					else
					{
						Toast.makeText(WaitReceiceActivity.this,msg, Toast.LENGTH_LONG).show();
					}
				}
			});
		}
	}

}


