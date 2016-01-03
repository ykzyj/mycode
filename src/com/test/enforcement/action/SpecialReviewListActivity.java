package com.sunnyit.enforcement.action;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.dialog.CustomDialog;
import com.sunnyit.common.poi.PoiDailyCheckNoOperate;
import com.sunnyit.common.poi.PoiDailyCheckYesOperate;
import com.sunnyit.common.poi.PoiSpecialCheckNoOperate;
import com.sunnyit.common.poi.PoiSpecialCheckYesOperate;
import com.sunnyit.common.poi.PoiSpecialRecordOperate;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.SwipeLoadListView;
import com.sunnyit.common.view.SwipeLoadListView.IClickLoadListListener;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.company.model.SimpleEnterprise;
import com.sunnyit.enforcement.data.SpecialCheckAdapter;
import com.sunnyit.enforcement.model.Inspector;
import com.sunnyit.enforcement.model.SpecialCheck;
import com.sunnyit.synchronous.model.UpInfo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import test.action.DialogShowActivity;

/**   
* @Title: WelcomeActivity.java 
* @Package com.sunnyit.system.action 
* @Description: TODO
* @author yk
* @date 2015年8月1日 上午9:47:36 
* @version V1.0   
*/
public class SpecialReviewListActivity extends BaseActivity  {

	private TopBarView topbar_en_check;
	private SwipeLoadListView mlistview;
	private List<SpecialCheck> mDatas;
	private SpecialCheckAdapter mAdapter;
	private int pangSize=10;
	private int pageCount=0;
	
	private String mSqlStr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.en_check_list);
		initComponent();
		Intent intent=getIntent();
		int activityFlag=intent.getIntExtra("activityFlag", 1);
		
		String state=intent.getStringExtra("state");
		
		String remind=intent.getStringExtra("remind");
		
		if(remind==null)
		{
			if(state==null)
			{
				if(activityFlag==0)
				{
					mSqlStr=" select * from SpecialCheck where ck_state='FINISH' or ck_state='REVIEWING' "
							+ " or ck_state='NOYET' or ck_state='ING' order by ck_time desc ";
					topbar_en_check.setTitle("专项检查复查信息列表");
					topbar_en_check.setRightButVisibility(View.GONE);
				}
				else
				{
					mSqlStr=" select * from SpecialCheck order by ck_time desc ";
					topbar_en_check.setTitle("专项检查信息列表");
				}
			}
			else
			{
				switch (state) {
				case "检查中":
					state="CKING";
					break;
				case "未整改":
					state="NOYET";
					break;
				case "整改中":
					state="ING";
					break;
				case "申请复查":
					state="FINISH";
					break;
				case "复查中":
					state="REVIEWING";
					break;
				case "复查完成":
					state="REVIEWED";
					break;
				case "立案":
					state="RECORDED";
					break;
				case "销号":
					state="DESTORY";
					break;
				case "无需整改":
					state="NONEED";
					break;

				default:
					break;
				}
				
				mSqlStr=" select * from SpecialCheck where ck_state='"+state+"' or ck_state='REVIEWING' order by ck_time desc ";
				topbar_en_check.setTitle("专项检查信息列表");
				topbar_en_check.setRightButVisibility(View.GONE);
			}
		}
		else
		{
			mSqlStr=intent.getStringExtra("sql");
			topbar_en_check.setTitle("专项检查超期提醒");
			topbar_en_check.setRightButVisibility(View.GONE);
		}
		
		
		topbar_en_check.setTopBarClick(new ITopBarClick() {
			
			@Override
			public void onClickRightBut() 
			{
				Intent intent=new Intent(SpecialReviewListActivity.this,HiddenSpecialCheckAddActivity.class);
				startActivityForResult(intent, 0);
			}
			
			@Override
			public void onClickLeftBut() {
				// TODO Auto-generated method stub
				finish();
			}
		});
		//topbar_en_check.setRightButVisibility(View.GONE);
		topbar_en_check.setRightButText("添加");
		
		final CustomDialog cusdialog=new CustomDialog(SpecialReviewListActivity.this);
		cusdialog.setViewAndAlpha(R.layout.progressbar_rw_blue,0);
		cusdialog.setOutCancel(false);
		cusdialog.show();
		
		new Thread(new Runnable(){  
            
            public void run(){

            	SqlOperate<SpecialCheck> opetaterSpecialCheck=new SqlOperate<SpecialCheck>(SpecialReviewListActivity.this, SpecialCheck.class);
        		mDatas=opetaterSpecialCheck.SelectOffsetEntitysBySqlCondition(mSqlStr, pangSize, pageCount);
                pageCount=1;
                opetaterSpecialCheck.close();
        		mAdapter= new SpecialCheckAdapter(SpecialReviewListActivity.this, mDatas, R.layout.en_specialcheck_item,mlistview.getRightViewWidth());
                
        		try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
        		
        		Message msg=Message.obtain();
        		msg.what=0;
        		handler.sendMessage(msg);
        		
        		cusdialog.dismiss();
        		
            }  
        }).start();
	}
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.what==0)
			{
				if(mDatas.size()<pangSize)
				{
					mlistview.hindLoadView(false);
				}
				mlistview.setAdapter(mAdapter);
				initSelfCheckDate();
			}
		}
		
	};
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (resultCode) {
		case 0:
			pageCount=0;
			SqlOperate<SpecialCheck> opetaterSpecialCheck=new SqlOperate<SpecialCheck>(this, SpecialCheck.class);
			List<SpecialCheck> mDatas_temp=opetaterSpecialCheck.SelectOffsetEntitysBySqlCondition(mSqlStr, pangSize, pageCount);
	        pageCount=1;
	        opetaterSpecialCheck.close();
	        
	        mDatas.removeAll(mDatas);
	        mDatas.addAll(mDatas_temp);
	        mAdapter.notifyDataSetChanged();
			
			break;
			
		case 3:
			String ck_state=data.getStringExtra("ck_state");
			int index=data.getIntExtra("index", -1);
			if(index!=-1)
			{
				mDatas.get(index).setCk_state(ck_state);
				mAdapter.notifyDataSetChanged();
			}
			break;

		default:
			pageCount=0;
			SqlOperate<SpecialCheck> opetaterSpecialCheckAll=new SqlOperate<SpecialCheck>(this, SpecialCheck.class);
			List<SpecialCheck> mDatas_tempAll=opetaterSpecialCheckAll.SelectOffsetEntitysBySqlCondition(mSqlStr, pangSize, pageCount);
	        pageCount=1;
	        opetaterSpecialCheckAll.close();
	        
	        mDatas.removeAll(mDatas);
	        mDatas.addAll(mDatas_tempAll);
	        mAdapter.notifyDataSetChanged();
			break;
		}
	}

	
	private void initSelfCheckDate() {
        
        mlistview.setIClickLoadListListener(new IClickLoadListListener() {
			
			@Override
			public void onLoad(Handler handler) {
				// TODO Auto-generated method stub
				SqlOperate<SpecialCheck> opetaterSpecialCheck=new SqlOperate<SpecialCheck>(SpecialReviewListActivity.this, SpecialCheck.class);
				final List<SpecialCheck> data=opetaterSpecialCheck.SelectOffsetEntitysBySqlCondition(mSqlStr, pangSize, pageCount);
		        pageCount++;
		        opetaterSpecialCheck.close();
				
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
				try {
					SpecialCheck t=mDatas.get(arg2);
					switch (t.getCk_state()) {
					case "CKING":
						normalOperate(arg2); 
						break;
					case "NONEED":
						normalOperate(arg2); 
						break;
					case "NOYET":
						superviseOperate(arg2); 
						break;
					case "ING":
						superviseOperate(arg2);  
						break;
					case "FINISH":
						reviewOperate(arg2);  
						break;
					case "REVIEWING":
						reviewOperate(arg2);  
						break;
					case "REVIEWED":
						endOperate(arg2); 
						break;
					case "RECORDED":
						normalOperate(arg2); 
						break;
					case "DESTORY":
						normalOperate(arg2); 
						break;
					default:
						break;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
				}
				
			}
		});
        
        mAdapter.setOnRightItemClickListener(new SpecialCheckAdapter.onRightItemClickListener() {
			
			@Override
			public void onRightItemClick(View v, final SpecialCheck t) {
				// TODO Auto-generated method stub
		        AlertDialog.Builder builder=new AlertDialog.Builder(SpecialReviewListActivity.this);  //先得到构造器  
		        builder.setTitle("提示"); //设置标题  
		        builder.setMessage("是否确认删除当前记录?"); //设置内容  
		        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮  
		            @Override  
		            public void onClick(DialogInterface dialog, int which){
		            	SqlOperate<SpecialCheck> opetaterSpecialCheck=new SqlOperate<SpecialCheck>(SpecialReviewListActivity.this, SpecialCheck.class);
						opetaterSpecialCheck.DeleteContent(t.getC_id());
				        opetaterSpecialCheck.close();
				        
				        try {
							SqlOperate<UpInfo> opetaterUpInfo=new SqlOperate<UpInfo>(SpecialReviewListActivity.this, UpInfo.class);
							opetaterUpInfo.DeleteByCondition(" where InfoId='"+t.getC_id()+"' ");
							opetaterUpInfo.close();
						} catch (Exception e) {
						}
				        
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
	
	private void reviewOperate(final int arg2) {
		// TODO Auto-generated method stub
		final String items[]={"信息查看","检查文书","检查记录表","复查"};  
        //dialog参数设置  
        AlertDialog.Builder builder=new AlertDialog.Builder(SpecialReviewListActivity.this);  //先得到构造器  
        builder.setTitle("提示"); //设置标题  
        builder.setItems(items,new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
                switch (items[which]) {
				case "信息查看":
					lookSpecialCheck(arg2);
					break;
				case "检查文书":
					if(mDatas.get(arg2)!=null)
					{
						openCheckBook(arg2);
					}
					break;
				case "检查记录表":
					openCheckRecord(arg2);
					break;
				case "复查":
					Bundle bundle=new Bundle();
					bundle.putSerializable("SpecialCheck", mDatas.get(arg2));
					Intent intent=new Intent(SpecialReviewListActivity.this,HiddenSpecialReviewAddActivity.class);
					intent.putExtras(bundle);
					intent.putExtra("index", arg2);
					startActivityForResult(intent, 0);
					break;
					
				default:
					break;
				}
            }

        });  
        builder.setPositiveButton("取消",new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
            }  
        });  
        builder.create().show();
	}
	
	private void endOperate(final int arg2) {
		// TODO Auto-generated method stub
		final String items[]={"信息查看","检查文书","检查记录表","销号"};  
        //dialog参数设置  
        AlertDialog.Builder builder=new AlertDialog.Builder(SpecialReviewListActivity.this);  //先得到构造器  
        builder.setTitle("提示"); //设置标题  
        //设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。  
        builder.setItems(items,new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
                //Toast.makeText(HiddenDailyCheckAddActivity.this, items[which], Toast.LENGTH_SHORT).show();  
                switch (items[which]) {
				case "信息查看":
					lookSpecialCheck(arg2);
					break;
				case "检查文书":
					if(mDatas.get(arg2)!=null)
					{
						openCheckBook(arg2);
					}
					break;
				case "检查记录表":
					openCheckRecord(arg2);
					break;
					
				case "销号":
					
					doDestory(arg2);
					
					break;

				default:
					break;
				}
            }


        });  
        builder.setPositiveButton("取消",new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
            }  
        });  
        builder.create().show();
	}
	
	private void doDestory(final int arg2) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder=new AlertDialog.Builder(this);  //先得到构造器  
        builder.setTitle("提示"); //设置标题  
        builder.setMessage("是否确认注销当前检查信息?"); //设置内容  
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss(); //关闭dialog  
                String sqlStr=" update SpecialCheck set ck_state='DESTORY' "
						+ "where c_id='"+mDatas.get(arg2).getC_id()+"' ";
				SqlOperate<SpecialCheck> opetaterSpecialCheck=new SqlOperate<SpecialCheck>(SpecialReviewListActivity.this, SpecialCheck.class);
				opetaterSpecialCheck.execMySQL(sqlStr);
				opetaterSpecialCheck.close();
				
		    	SqlOperate<UpInfo> opetaterUpInfo=new SqlOperate<UpInfo>(SpecialReviewListActivity.this, UpInfo.class);
				String conditionSqlStr="  where InfoId='"+mDatas.get(arg2).getC_id()+"' ";
		    	List<UpInfo> ls_in=opetaterUpInfo.SelectEntitysByCondition(conditionSqlStr);
		    	if(ls_in.size()==0)
		    	{

		    		UpInfo info=new UpInfo();
		    		info.setU_Id(UUID.randomUUID().toString());
		    		info.setInfoTable("SpecialCheck");
		    		info.setInfoId(mDatas.get(arg2).getC_id());
		    		info.setOperateType("addreview");
		    		info.setRemark("");
		    		opetaterUpInfo.saveContent(info);
		    	}
		    	opetaterUpInfo.close();
		    	
		    	mDatas.get(arg2).setCk_state("DESTORY");
		    	mAdapter.notifyDataSetChanged();
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
	
	private void superviseOperate(final int arg2) {
		final String items[]={"信息查看","检查文书","检查记录表","整治督促","复查"};  
        //dialog参数设置  
        AlertDialog.Builder builder=new AlertDialog.Builder(SpecialReviewListActivity.this);  //先得到构造器  
        builder.setTitle("提示"); //设置标题  
        //设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。  
        builder.setItems(items,new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
                //Toast.makeText(HiddenDailyCheckAddActivity.this, items[which], Toast.LENGTH_SHORT).show();  
                switch (items[which]) {
				case "信息查看":
					lookSpecialCheck(arg2);
					break;
				case "检查文书":
					if(mDatas.get(arg2)!=null)
					{
						openCheckBook(arg2);
					}
					break;
				case "检查记录表":
					openCheckRecord(arg2);
					break;
				case "整治督促":
					String conditionStr_dc=" where InfoTable='SpecialCheck' and OperateType='add' and InfoId='"+mDatas.get(arg2).getC_id()+"' ";
					SqlOperate<UpInfo> opetaterInfo_sc=new SqlOperate<UpInfo>(SpecialReviewListActivity.this, UpInfo.class);
					final List<UpInfo> ls_UpInfo_sc=opetaterInfo_sc.SelectEntitysByCondition(conditionStr_dc);
					opetaterInfo_sc.close();
					
					if(ls_UpInfo_sc!=null&&ls_UpInfo_sc.size()>0)
					{
						Toast.makeText(SpecialReviewListActivity.this, "此信息仅在本地存在，请上传后再督促", Toast.LENGTH_SHORT).show();
					}
					else
					{
						Intent intent=new Intent(SpecialReviewListActivity.this,HiddenSuperviseAddActivity.class);
						intent.putExtra("addtype", "Special");
						intent.putExtra("ID", mDatas.get(arg2).getC_id());
						startActivityForResult(intent, 0);
					}
					break;
				case "复查":
					Bundle bundle=new Bundle();
					bundle.putSerializable("SpecialCheck", mDatas.get(arg2));
					Intent intent=new Intent(SpecialReviewListActivity.this,HiddenSpecialReviewAddActivity.class);
					intent.putExtras(bundle);
					intent.putExtra("index", arg2);
					startActivityForResult(intent, 0);
					break;
				default:
					break;
				}
            }

        });  
        builder.setPositiveButton("取消",new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
            }  
        });  
        builder.create().show();
	}
	
	private void normalOperate(final int arg2) {
		final String items[]={"信息查看","检查文书","检查记录表"};  
        //dialog参数设置  
        AlertDialog.Builder builder=new AlertDialog.Builder(SpecialReviewListActivity.this);  //先得到构造器  
        builder.setTitle("提示"); //设置标题  
        //设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。  
        builder.setItems(items,new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
                //Toast.makeText(HiddenDailyCheckAddActivity.this, items[which], Toast.LENGTH_SHORT).show();  
                switch (items[which]) {
				case "信息查看":
					lookSpecialCheck(arg2);
					break;
				case "检查文书":
					if(mDatas.get(arg2)!=null)
					{
						openCheckBook(arg2);
					}
					break;
				case "检查记录表":
					openCheckRecord(arg2);
					break;

				default:
					break;
				}
            }

        });  
        builder.setPositiveButton("取消",new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
            }  
        });  
        builder.create().show();
	}
	
	private void openCheckRecord(final int arg2) {
		PoiSpecialRecordOperate op=
				new PoiSpecialRecordOperate(SpecialReviewListActivity.this,
				mDatas.get(arg2));
		try {
			op.doScanofDailyCheck();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}  
	
	
	
	private void openCheckBook(final int arg2) {
		String sqlSimpCondition=" where e_companyName='"+mDatas.get(arg2).getCk_checkeddepartment()+"' ";
		SqlOperate<SimpleEnterprise> opetaterSimpleEnterprise=new SqlOperate<SimpleEnterprise>(SpecialReviewListActivity.this, SimpleEnterprise.class);
		List<SimpleEnterprise> data=opetaterSimpleEnterprise.SelectEntitysByCondition(sqlSimpCondition);
		opetaterSimpleEnterprise.close();
		
		List<Inspector> dataofInspector=new ArrayList<Inspector>();
		String sqlInspectorCondition=" where ck_id='"+mDatas.get(arg2).getCk_id()+"' and inspectortype='检查人员' ";
		SqlOperate<Inspector> opetaterInspector=new SqlOperate<Inspector>(SpecialReviewListActivity.this, Inspector.class);
		dataofInspector=opetaterInspector.SelectEntitysByCondition(sqlInspectorCondition);
		opetaterInspector.close();
		
		if(data!=null&&data.size()>0)
		{
			if(mDatas.get(arg2).getCk_state().equals("NONEED"))
			{
				PoiSpecialCheckYesOperate op=new PoiSpecialCheckYesOperate(SpecialReviewListActivity.this,
						mDatas.get(arg2),data.get(0),dataofInspector);
				try {
					op.doScanofDailyCheck();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				PoiSpecialCheckNoOperate op=new PoiSpecialCheckNoOperate(SpecialReviewListActivity.this,
						mDatas.get(arg2),data.get(0),dataofInspector);
				try {
					op.doScanofDailyCheck();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else
		{
			Toast.makeText(SpecialReviewListActivity.this, "企业信息不存在，无法生成检查表格！", Toast.LENGTH_SHORT).show();
		}
	}  
	
	private void lookSpecialCheck(final int arg2) {
		Bundle bundle=new Bundle();
		bundle.putSerializable("SpecialCheck", mDatas.get(arg2));
		Intent intent=new Intent(SpecialReviewListActivity.this,HiddenSpecialCheckShowActivity.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}  

	private void initComponent() {
		// TODO Auto-generated method stub
		mlistview=(SwipeLoadListView) findViewById(R.id.listView_en_check);
		topbar_en_check=(TopBarView) findViewById(R.id.topbar_en_check);
	}
}


