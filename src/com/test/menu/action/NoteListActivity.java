package com.sunnyit.menu.action;

import java.util.ArrayList;
import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.SwipeListView;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.menu.data.SwipeAdapter;
import com.sunnyit.menu.model.WXMessage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import test.action.DialogShowActivity;

/**   
* @Title: NoteListActivity.java 
* @Package com.sunnyit.menu.action 
* @Description: TODO
* @author yk
* @date 2015年8月10日 上午10:35:01 
* @version V1.0   
*/
public class NoteListActivity extends BaseActivity {
	private List<WXMessage> data = new ArrayList<WXMessage>();
    private SwipeListView mListView;
    private RelativeLayout rel_info_null;
    private SwipeAdapter mAdapter;
    private TopBarView topbar_pwup ;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leftmenu_nodepad_list);
		
		initView();
		initData();
		
		topbar_pwup.setTopBarClick(new TopBarView.ITopBarClick() {
			@Override
			public void onClickRightBut() {
				// TODO Auto-generated method stub
				Intent intent=new Intent(NoteListActivity.this, NoteAddActivity.class);
				startActivityForResult (intent, 1);
			}
			
			@Override
			public void onClickLeftBut() {
				// TODO Auto-generated method stub
				finish();
			}
		});
        
        rel_info_null.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(NoteListActivity.this, NoteAddActivity.class);
				startActivityForResult (intent, 1);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		// TODO Auto-generated method stub
		switch(resultCode){
        case 1:
        //来自按钮1的请求，作相应业务处理
        	SqlOperate<WXMessage> opetater=new SqlOperate<WXMessage>(NoteListActivity.this, WXMessage.class);
    		List<WXMessage> datawx=opetater.SelectEntitys();
    		opetater.close();
    		data.clear();
    		data.addAll(datawx);
    		mAdapter.notifyDataSetChanged();
    		if(data.size()!=0)
			{
				rel_info_null.setVisibility(View.GONE);
			}
    		break;
        case 2:
        	//来自按钮2的请求，作相应业务处理
        	break;
     }
	}

	private void initData() {
		
    	/*for(int i=0;i<20;i++){
    		WXMessage msg = null;
    		msg = new WXMessage("信息标题", "信息内容,你在哪你在哪你在哪你在哪你在哪你在哪你在哪你在哪你在哪你在哪你在哪你在哪你在哪你在哪", "2015-08-10 10:15:23");
			msg.setIcon_id(R.drawable.notepad);
    		
    		data.add(msg);
    	}*/
		
		SqlOperate<WXMessage> opetater=new SqlOperate<WXMessage>(NoteListActivity.this, WXMessage.class);
		data=opetater.SelectEntitys();
		opetater.close();
		
		if(data.size()==0)
		{
			rel_info_null.setVisibility(View.VISIBLE);
		}
    	
    	 mAdapter = new SwipeAdapter(this,data,mListView.getRightViewWidth());
         mAdapter.setOnRightItemClickListener(new SwipeAdapter.onRightItemClickListener() {
             @Override
             public void onRightItemClick(View v, final int position) {
             	
                 /*Toast.makeText(NoteListActivity.this, "删除第  " + (position+1)+" 对话记录",
                         Toast.LENGTH_SHORT).show();*/
            	 
            	 AlertDialog.Builder builder=new AlertDialog.Builder(NoteListActivity.this);  //先得到构造器  
                 builder.setTitle("提示"); //设置标题  
                 builder.setMessage("是否确认删除当前记录信息?"); //设置内容  
                 builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮  
                     @Override  
                     public void onClick(DialogInterface dialog, int which) {  
                         dialog.dismiss(); //关闭dialog  
                         
                         WXMessage msg=data.get(position);
                    	 data.remove(msg);
                    	 SqlOperate<WXMessage> opetatertemp=new SqlOperate<WXMessage>(NoteListActivity.this, WXMessage.class);
         				 Boolean delflag=opetatertemp.DeleteContent(msg.getId());
         				 opetatertemp.close();
         				 if(delflag)
         				 {
         					mAdapter.notifyDataSetChanged();
         				 }
         				if(data.size()==0)
         				{
         					rel_info_null.setVisibility(View.VISIBLE);
         				}
                     }  
                 });  
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
         
         mListView.setAdapter(mAdapter);
         
         mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 /*Toast.makeText(NoteListActivity.this, "item onclick " + position, Toast.LENGTH_SHORT)
                         .show();*/
				Intent intent = new Intent();
				intent.setClass(NoteListActivity.this, NoteAddActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("msg", data.get(position));
				intent.putExtras(bundle);
				startActivityForResult (intent, 1);
             }
         });
	}

	/**
     * 初始化界面
     */
    private void initView() {
    	
        mListView = (SwipeListView)findViewById(R.id.listview_nodepad);
        topbar_pwup = (TopBarView)findViewById(R.id.topbar_pwup);
        rel_info_null = (RelativeLayout)findViewById(R.id.rel_info_null);
    }
}


