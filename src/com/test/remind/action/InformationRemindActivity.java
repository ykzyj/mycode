package com.sunnyit.remind.action;

import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.broadcast.DateChangeReceiver;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.SwipeLoadListView;
import com.sunnyit.common.view.SwipeLoadListView.IClickLoadListListener;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.enforcement.action.DailyReviewListActivity;
import com.sunnyit.enforcement.action.HiddenGovernChoiceTabActivity;
import com.sunnyit.enforcement.action.HiddenStandCheckListActivity;
import com.sunnyit.enforcement.action.SpecialReviewListActivity;
import com.sunnyit.hiddencheck.action.HiddenTabSortDatilActivity;
import com.sunnyit.menu.action.UserInfoUpActivity;
import com.sunnyit.remind.data.InformationRemindAdapter;
import com.sunnyit.remind.data.InformationRemindAdapter.onRightItemClickListener;
import com.sunnyit.remind.model.InformationRemind;
import com.sunnyit.synchronous.action.SynchronousActivity;
import com.sunnyit.system.action.MainActivity;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**   
* @Title: DailyCheckList.java 
* @Package com.sunnyit.hiddencheck.action 
* @Description: TODO
* @author yk
* @date 2015年9月7日 下午3:20:13 
* @version V1.0   
*/
public class InformationRemindActivity extends BaseActivity {

	
	private TopBarView topbar_file_tf_operate;
	private SwipeLoadListView mlistview	;
	
	private InformationRemindAdapter mAdapter;
	private List<InformationRemind> mDatas;
	
	private int pangSize=10;
	private int pageCount=0;
	
	String sqlStr=" select * from InformationRemind ";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.en_check_list);
		
		NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		manager.cancel(12312);
		
		initComponent();
		
		topbar_file_tf_operate.setTopBarClick(new ITopBarClick() {
			
			@Override
			public void onClickRightBut() {
				
			}
			
			@Override
			public void onClickLeftBut() {
				finish();
			}
		});
		topbar_file_tf_operate.setTitle("信息提醒历史记录");
		topbar_file_tf_operate.setRightButVisibility(View.GONE);
		
		initData();
		
	}
	
	private void initData() {
		SqlOperate<InformationRemind> opetaterDailyCheck=new SqlOperate<InformationRemind>(InformationRemindActivity.this, InformationRemind.class);
		mDatas=opetaterDailyCheck.SelectOffsetEntitysBySqlCondition(sqlStr, pangSize, pageCount);
        pageCount++;
        opetaterDailyCheck.close();
		mAdapter= new InformationRemindAdapter(InformationRemindActivity.this, mDatas, R.layout.list_item,mlistview.getRightViewWidth());
		
		if(mDatas.size()<pangSize)
		{
			mlistview.hindLoadView(false);
		}
		mlistview.setAdapter(mAdapter);
		

		// TODO Auto-generated method stub
        mlistview.setIClickLoadListListener(new IClickLoadListListener() {
			
			@Override
			public void onLoad(Handler handler) {
				// TODO Auto-generated method stub
				SqlOperate<InformationRemind> opetaterDailyCheck=new SqlOperate<InformationRemind>(InformationRemindActivity.this, InformationRemind.class);
				final List<InformationRemind> data=opetaterDailyCheck.SelectOffsetEntitysBySqlCondition(sqlStr, pangSize, pageCount);
		        pageCount++;
		        opetaterDailyCheck.close();
				
				if(data!=null&&data.size()>0)
		        {
					handler.post(new Runnable() {
						public void run() {
							mDatas.addAll(data);
							mAdapter.notifyDataSetChanged();
							if(data.size()<pangSize)
							{
								mlistview.hindLoadView(true);
							}
						}
					});
		        }
		        else
		        {
		        	mlistview.hindLoadView(true);
		        }
			}
		});
        
        mlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
				
				InformationRemind informationRemind=mDatas.get(arg2);
				if(informationRemind!=null&&informationRemind.getI_HaveLook().equals("0"))
				{
					informationRemind.setI_HaveLook("1");
					SqlOperate<InformationRemind> opetaterInformationRemind=new SqlOperate<InformationRemind>(InformationRemindActivity.this, InformationRemind.class);
					opetaterInformationRemind.upContent(informationRemind);
					opetaterInformationRemind.close();
					mAdapter.notifyDataSetChanged();
				}
				
				Intent intent;
				switch (mDatas.get(arg2).getI_Title()) {
				case "移动安监数据上传提醒":
					startActivity(new Intent(InformationRemindActivity.this, SynchronousActivity.class));
					break;
				case "日常检查超期提醒":
					intent=new Intent(InformationRemindActivity.this,DailyReviewListActivity.class);
					intent.putExtra("remind", "remind");
					intent.putExtra("sql", DateChangeReceiver.mDailyExtendedStr);
					startActivity(intent);
					break;
				case "专项检查超期提醒":
					intent=new Intent(InformationRemindActivity.this,SpecialReviewListActivity.class);
					intent.putExtra("remind", "remind");
					intent.putExtra("sql", DateChangeReceiver.mSpecialExtendedStr);
					startActivity(intent);
					break;
				case "对照标准检查超期提醒":
					intent=new Intent(InformationRemindActivity.this,HiddenStandCheckListActivity.class);
					intent.putExtra("remind", "remind");
					intent.putExtra("sql", DateChangeReceiver.mStandExtendedStr);
					startActivity(intent);
					break;
				case "企业自查超期提醒":
					String mRectificationStr="select e_Id,e_companyName from SimpleEnterprise "+
							" where e_Id in "+
								"(select sc_companyId from SelfCheck"
								+ " where sc_deadline<date('now') and sc_state='ING' UNION "+
								" select sc_companyId from SelfStandCheck "
								+ "where sc_deadline<date('now') and (sc_state='ing' or sc_state='noyet' ))";
					intent=new Intent(InformationRemindActivity.this,HiddenTabSortDatilActivity.class);
			        intent.putExtra("sql", mRectificationStr);
			        intent.putExtra("remind", "remind");
					startActivity(intent);
					break;

				default:
					break;
				}
				
			}

		});
        
        mAdapter.setOnRightItemClickListener(new onRightItemClickListener() {

			@Override
			public void onRightItemClick(View v, final InformationRemind t) {
				AlertDialog.Builder builder=new AlertDialog.Builder(InformationRemindActivity.this);  //先得到构造器  
		        builder.setTitle("提示"); //设置标题  
		        builder.setMessage("是否确认删除当前记录?"); //设置内容  
		        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮  
		            @Override  
		            public void onClick(DialogInterface dialog, int which){
		            	SqlOperate<InformationRemind> opetaterInformationRemind=new SqlOperate<InformationRemind>(InformationRemindActivity.this, InformationRemind.class);
		            	opetaterInformationRemind.DeleteContent(t.getI_ID());
		            	opetaterInformationRemind.close();
				        
				        mDatas.remove(t);
				        mAdapter.notifyDataSetChanged();
		                dialog.dismiss(); //关闭dialog
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
	
	}

	private void initComponent() {
		topbar_file_tf_operate=(TopBarView) findViewById(R.id.topbar_en_check);
		mlistview=(SwipeLoadListView) findViewById(R.id.listView_en_check);
	}
	
}


