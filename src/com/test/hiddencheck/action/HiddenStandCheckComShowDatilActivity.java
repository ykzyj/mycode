package com.sunnyit.hiddencheck.action;

import java.util.ArrayList;
import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.async.HttpClientAsyncGet;
import com.sunnyit.common.async.HttpClientAsyncGet.onHttpClientReturnListener;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.filetransfer.model.TransportFile;
import com.sunnyit.hiddencheck.data.StandcheckDetailShowAdapter;
import com.sunnyit.hiddencheck.model.StandcheckDetail;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

/**   
* @Title: HiddenList.java 
* @Package com.sunnyit.hiddencheck.action 
* @Description: TODO
* @author yk
* @date 2015年9月7日 下午3:27:12 
* @version V1.0   
*/
public class HiddenStandCheckComShowDatilActivity extends BaseActivity {

	private TopBarView topbar_endatil_standcheck_add;
	private ListView lv_stand_table_item;
	
	private String id;
	
	private List<StandcheckDetail> mData;
	private StandcheckDetailShowAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hidden_endatil_standcheck_add);
		
		id = getIntent().getStringExtra("id");
		
		initComponent();
		
		topbar_endatil_standcheck_add.setTopBarClick(new ITopBarClick() {
			
			@Override
			public void onClickRightBut() {}
			
			@Override
			public void onClickLeftBut() {
				finish();
			}
		});
		topbar_endatil_standcheck_add.setRightButVisibility(View.GONE);
		
		getStandcheckDetailOfNet();
	}
	
	private void initAdapter(List<Object> lo) {
		mData=new ArrayList<>();
		for(Object standcheckDetail:lo)
		{
			mData.add((StandcheckDetail) standcheckDetail);
		}
		mAdapter=new StandcheckDetailShowAdapter(this, mData, R.layout.hidden_enshow_standcheck_item);
		
		lv_stand_table_item.setAdapter(mAdapter);
		lv_stand_table_item.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder=new AlertDialog.Builder(HiddenStandCheckComShowDatilActivity.this);  //先得到构造器  
		        builder.setTitle("依据说明"); //设置标题  
		        builder.setMessage(mData.get(arg2).getH_basis()); //设置内容  
		        builder.setIcon(R.drawable.note_stand48);//设置图标，图片id即可  
		        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮  
		            @Override  
		            public void onClick(DialogInterface dialog, int which) {  
		                dialog.dismiss();  
		            }  
		        });  
		        //参数都设置完成了，创建并显示出来  
		        builder.create().show();
			}
		});
	}
	
	private void getStandcheckDetailOfNet() {
		HttpClientAsyncGet post=new HttpClientAsyncGet(HiddenStandCheckComShowDatilActivity.this,new StandcheckDetail());
		String baseURL=getBaseUrl();
		if(baseURL!=null)
		{
			post.execute(baseURL+"/appWebGet/getStandCheckDetailByID?id="+id);
			post.setHttpClientReturnListener(new onHttpClientReturnListener() {
				@Override
				public void returnPostExecute(boolean isSuccess, String msg, List<Object> lo) {
					// TODO Auto-generated method stub
					if(isSuccess)
					{
						initAdapter(lo);
					}
					else
					{
						Toast.makeText(HiddenStandCheckComShowDatilActivity.this,msg, Toast.LENGTH_LONG).show();
					}
				}
			});
		}
	}
	
	private void initComponent() {
		// TODO Auto-generated method stub
		topbar_endatil_standcheck_add=(TopBarView) findViewById(R.id.topbar_endatil_standcheck_add);
		lv_stand_table_item=(ListView) findViewById(R.id.lv_stand_table_item);
	}
}


