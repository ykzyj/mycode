package com.sunnyit.enforcement.action;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.dialog.CustomDialog;
import com.sunnyit.common.poi.PoiDailyCheckNoOperate;
import com.sunnyit.common.poi.PoiStandCheckNoOperate;
import com.sunnyit.common.poi.PoiStandCheckYesOperate;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.SwipeLoadListView;
import com.sunnyit.common.view.SwipeLoadListView.IClickLoadListListener;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.company.model.SimpleEnterprise;
import com.sunnyit.enforcement.data.Standard_CK_TableAdapter;
import com.sunnyit.enforcement.data.Standard_CK_TableAdapter.onRightItemClickListener;
import com.sunnyit.enforcement.model.SpecialCheck;
import com.sunnyit.enforcement.model.Standard_CK_Table;
import com.sunnyit.enforcement.model.Standard_CK_Table_Item;
import com.sunnyit.synchronous.model.UpInfo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**   
* @Title: HiddenList.java 
* @Package com.sunnyit.hiddencheck.action 
* @Description: TODO
* @author yk
* @date 2015年9月7日 下午3:27:12 
* @version V1.0   
*/
public class HiddenStandCheckListActivity extends BaseActivity {
	
	private TopBarView topbar_en_check;
	private SwipeLoadListView mlistview;
	
	private List<Standard_CK_Table> mDatas;
	private Standard_CK_TableAdapter mAdapter;
	private int pangSize=10;
	private int pageCount=0;
	
	private String mSqlStr=" where 1=1 order by ck_time desc ";
	
	private int activityFlag;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.en_check_list);
		
		activityFlag=getIntent().getIntExtra("activityFlag", 1);
		
		initComponent();
		
		String state=getIntent().getStringExtra("state");
		
		String remind=getIntent().getStringExtra("remind");
		
		if(remind==null)
		{
			if(state==null)
			{
				if(activityFlag==0)
				{
					mSqlStr=" where ck_state='FINISH' or ck_state='REVIEWING' "
							+ " or ck_state='NOYET' or ck_state='ING' order by ck_time desc ";
					topbar_en_check.setRightButVisibility(View.GONE);
					topbar_en_check.setTitle("对照标准检查复查信息列表");
				}
				else
				{
					topbar_en_check.setTitle("对照标准检查信息列表");
				}
			}
			else
			{
				switch (state) {
				case "检查中":
					state="CKING";
					break;
				case "未处理":
					state="CKEND";
					break;
				case "处理中":
					state="DOING";
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
				case "已复查":
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
				
				mSqlStr=" where ck_state='"+state+"' order by ck_time desc ";
				topbar_en_check.setRightButVisibility(View.GONE);
				topbar_en_check.setTitle("对照标准检查信息列表");
			}
		}
		else
		{
			mSqlStr=getIntent().getStringExtra("sql");
			topbar_en_check.setTitle("专项检查超期提醒");
			topbar_en_check.setRightButVisibility(View.GONE);
		}
		
		
		topbar_en_check.setTopBarClick(new ITopBarClick() {
			
			@Override
			public void onClickRightBut() {
				Intent intent=new Intent(HiddenStandCheckListActivity.this, StandCheckTableActivity.class);
	       		startActivityForResult(intent, 0);
			}
			
			@Override
			public void onClickLeftBut() {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		topbar_en_check.setRightButText("添加");
		
		final CustomDialog cusdialog=new CustomDialog(HiddenStandCheckListActivity.this);
		cusdialog.setViewAndAlpha(R.layout.progressbar_rw_blue,0);
		cusdialog.setOutCancel(false);
		cusdialog.show();
		
		new Thread(new Runnable(){  
            
            public void run(){

            	/*SqlOperate<Standard_CK_Table> opetaterStandard_CK_Table=new SqlOperate<Standard_CK_Table>(HiddenStandCheckListActivity.this, Standard_CK_Table.class);
            	List<Standard_CK_Table> mDatasTemp=opetaterStandard_CK_Table.SelectOffsetEntitysByCondition(mSqlStr, pangSize, pageCount);
                pageCount=1;
                opetaterStandard_CK_Table.close();
        		
        		if(mDatas.size()<pangSize)
        		{
        			mlistview.hindLoadView(false);
        		}
        		cusdialog.dismiss();
        		
        		mDatas.addAll(mDatasTemp);*/
            	
            	SqlOperate<Standard_CK_Table> opetaterStandard_CK_Table=new SqlOperate<Standard_CK_Table>(HiddenStandCheckListActivity.this, Standard_CK_Table.class);
        		mDatas=opetaterStandard_CK_Table.SelectOffsetEntitysByCondition(mSqlStr, pangSize, pageCount);
                pageCount=1;
                opetaterStandard_CK_Table.close();
        		mAdapter= new Standard_CK_TableAdapter(HiddenStandCheckListActivity.this, mDatas, R.layout.en_standcheck_item,mlistview.getRightViewWidth());
        		
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
				initCheckDate();
			}
		}
		
	};
	
	private void initCheckDate() {
		
		/*SqlOperate<Standard_CK_Table> opetaterStandard_CK_Table=new SqlOperate<Standard_CK_Table>(HiddenStandCheckListActivity.this, Standard_CK_Table.class);
		mDatas=opetaterStandard_CK_Table.SelectOffsetEntitysByCondition(mSqlStr, pangSize, pageCount);
        pageCount=1;
        opetaterStandard_CK_Table.close();
		
		if(mDatas.size()<pangSize)
		{
			mlistview.hindLoadView(false);
		}
		
		mAdapter= new Standard_CK_TableAdapter(this, mDatas, R.layout.en_standcheck_item,mlistview.getRightViewWidth());
		mlistview.setAdapter(mAdapter);*/
        
        mlistview.setIClickLoadListListener(new IClickLoadListListener() {
			
			@Override
			public void onLoad(Handler handler) {
				// TODO Auto-generated method stub
				SqlOperate<Standard_CK_Table> opetaterStandard_CK_TableMore=new SqlOperate<Standard_CK_Table>(HiddenStandCheckListActivity.this, Standard_CK_Table.class);
				final List<Standard_CK_Table> data=opetaterStandard_CK_TableMore.SelectOffsetEntitysByCondition(mSqlStr, pangSize, pageCount);
		        pageCount++;
		        opetaterStandard_CK_TableMore.close();
				
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
					if(data.size()<pangSize)
					{
						mlistview.hindLoadView(true);
					}
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
					if(mDatas.get(arg2)!=null)
					{
						switch (mDatas.get(arg2).getCk_state()) {
						case "CKING":
							operateOfCKING(arg2);
							break;
						case "CKEND":
							operateOfDOING(arg2);
							break;
						case "DOING":
							operateOfDOING(arg2);
							break;
						case "NONEED":
							operateOfNONEED(arg2);
							break;
						case "NOYET":
							operateOfYET(arg2);
							break;
						case "ING":
							operateOfYET(arg2);
							break;
						case "FINISH":
							operateOfReview(arg2);
							break;
						case "REVIEWING":
							operateOfReview(arg2);
							break;
						case "REVIEWED":
							operateOfREVIEWED(arg2);
							break;
						case "RECORDED":
							operateOfNONEED(arg2);
							break;
						case "DESTORY":
							operateOfNONEED(arg2);
							break;
						default:
							break;
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
				}
			}

		});
        
        mAdapter.setOnRightItemClickListener(new onRightItemClickListener() {
			
			@Override
			public void onRightItemClick(View v, final Standard_CK_Table t) {
				AlertDialog.Builder builder=new AlertDialog.Builder(HiddenStandCheckListActivity.this);  //先得到构造器  
		        builder.setTitle("提示"); //设置标题  
		        builder.setMessage("是否确认删除当前记录?"); //设置内容  
		        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮  
		            @Override  
		            public void onClick(DialogInterface dialog, int which){
		            	SqlOperate<Standard_CK_Table> opetaterStandard_CK_TableMore=new SqlOperate<Standard_CK_Table>(HiddenStandCheckListActivity.this, Standard_CK_Table.class);
						opetaterStandard_CK_TableMore.DeleteContent(t.getCk_id());
				        opetaterStandard_CK_TableMore.close();
				        SqlOperate<Standard_CK_Table_Item> opetaterStandard_CK_Table_Item=new SqlOperate<Standard_CK_Table_Item>(HiddenStandCheckListActivity.this, Standard_CK_Table_Item.class);
				        opetaterStandard_CK_Table_Item.DeleteByCondition(" where ck_id='"+t.getCk_id()+"' ");
				        opetaterStandard_CK_Table_Item.close();
				        
				        try {
							SqlOperate<UpInfo> opetaterUpInfo=new SqlOperate<UpInfo>(HiddenStandCheckListActivity.this, UpInfo.class);
							opetaterUpInfo.DeleteByCondition(" where InfoId='"+t.getCk_id()+"' ");
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
	
	private void operateOfREVIEWED(final int arg2) {
		final String items[]={"信息查看","执法文书","销号"};  
        //dialog参数设置  
        AlertDialog.Builder builder=new AlertDialog.Builder(HiddenStandCheckListActivity.this);  //先得到构造器  
        builder.setTitle("提示"); //设置标题  
        builder.setItems(items,new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
                switch (items[which]) {
                case "信息查看":
                	lookStandCheck(arg2);
					break;
				case "执法文书":
					openCheckBook(arg2);
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
		AlertDialog.Builder builder=new AlertDialog.Builder(HiddenStandCheckListActivity.this);  //先得到构造器  
        builder.setTitle("提示"); //设置标题  
        builder.setMessage("是否确认注销当前检查信息?"); //设置内容  
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss(); //关闭dialog  
                String sqlStr=" update Standard_CK_Table set ck_state='DESTORY' "
						+ "where c_id='"+mDatas.get(arg2).getCk_id()+"' ";
				SqlOperate<SpecialCheck> opetaterSpecialCheck=new SqlOperate<SpecialCheck>(HiddenStandCheckListActivity.this, SpecialCheck.class);
				opetaterSpecialCheck.execMySQL(sqlStr);
				opetaterSpecialCheck.close();
				
				SqlOperate<UpInfo> opetaterUpInfo=new SqlOperate<UpInfo>(HiddenStandCheckListActivity.this, UpInfo.class);
		    	String conditionSqlStr="  where InfoId='"+mDatas.get(arg2).getCk_id()+"' ";
		    	List<UpInfo> ls_in=opetaterUpInfo.SelectEntitysByCondition(conditionSqlStr);
		    	if(ls_in.size()==0)
		    	{
		    		UpInfo info=new UpInfo();
					info.setU_Id(UUID.randomUUID().toString());
					info.setInfoTable("Standard_CK_Table");
					info.setInfoId(mDatas.get(arg2).getCk_id());
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
	
	private void operateOfReview(final int arg2) {
		// TODO Auto-generated method stub
		final String items[]={"信息查看","执法文书","复查"};  
        //dialog参数设置  
        AlertDialog.Builder builder=new AlertDialog.Builder(HiddenStandCheckListActivity.this);  //先得到构造器  
        builder.setTitle("提示"); //设置标题  
        builder.setItems(items,new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
                switch (items[which]) {
                case "信息查看":
                	lookStandCheck(arg2);
					break;
				case "执法文书":
					openCheckBook(arg2);
					break;
				case "复查":
					Bundle bundle=new Bundle();
					bundle.putSerializable("Standard_CK_Table", mDatas.get(arg2));
					Intent intent=new Intent(HiddenStandCheckListActivity.this,HiddenStandReviewDatilAddActivity.class);
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
	
	private void operateOfNONEED(final int arg2) {
		// TODO Auto-generated method stub
   		final String items[]={"信息查看","执法文书"};  
        //dialog参数设置  
        AlertDialog.Builder builder=new AlertDialog.Builder(HiddenStandCheckListActivity.this);  //先得到构造器  
        builder.setTitle("提示"); //设置标题  
        builder.setItems(items,new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {
            	 switch (items[which]) {
					case "信息查看":
						lookStandCheck(arg2);
						break;
					case "执法文书":
						openCheckBook(arg2);
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
	
	private void operateOfYET(final int arg2) {
		// TODO Auto-generated method stub
   		final String items[]={"信息查看","执法文书","整治督促","复查"};  
        //dialog参数设置  
        AlertDialog.Builder builder=new AlertDialog.Builder(HiddenStandCheckListActivity.this);  //先得到构造器  
        builder.setTitle("提示"); //设置标题  
        builder.setItems(items,new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {
            	 switch (items[which]) {
					case "信息查看":
						lookStandCheck(arg2);
						break;
					case "执法文书":
						openCheckBook(arg2);
						break;
					case "整治督促":

						String conditionStr_dc=" where InfoTable='Standard_CK_Table' and OperateType='add' and InfoId='"+mDatas.get(arg2).getCk_id()+"' ";
						SqlOperate<UpInfo> opetaterInfo_sc=new SqlOperate<UpInfo>(HiddenStandCheckListActivity.this, UpInfo.class);
						final List<UpInfo> ls_UpInfo_sc=opetaterInfo_sc.SelectEntitysByCondition(conditionStr_dc);
						opetaterInfo_sc.close();
						
						if(ls_UpInfo_sc!=null&&ls_UpInfo_sc.size()>0)
						{
							Toast.makeText(HiddenStandCheckListActivity.this, "此信息仅在本地存在，请上传后再督促", Toast.LENGTH_SHORT).show();
						}
						else
						{
							Intent intent=new Intent(HiddenStandCheckListActivity.this,HiddenSuperviseAddActivity.class);
							intent.putExtra("addtype", "StandCheck");
							intent.putExtra("ID", mDatas.get(arg2).getCk_id());
							startActivityForResult(intent, 0);
						}
						break;
					case "复查":
						Bundle bundle=new Bundle();
						bundle.putSerializable("Standard_CK_Table", mDatas.get(arg2));
						Intent intent=new Intent(HiddenStandCheckListActivity.this,HiddenStandReviewDatilAddActivity.class);
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
	
	private void operateOfDOING(final int arg2) {
		
		final String items[]={"信息查看","继续处理"};  
        //dialog参数设置  
        AlertDialog.Builder builder=new AlertDialog.Builder(HiddenStandCheckListActivity.this);  //先得到构造器  
        builder.setTitle("提示"); //设置标题  
        builder.setItems(items,new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {
            	 switch (items[which]) {
					case "信息查看":
						lookStandCheck(arg2);
						break;
					case "继续处理":
						Intent intent=new Intent(HiddenStandCheckListActivity.this, HiddenStandCheckedDatilAddActivity.class);
				   		intent.putExtra("ck_id", mDatas.get(arg2).getCk_id());
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
	
	private void operateOfCKING(final int arg2) {
		final String items[]={"信息查看","继续检查"};  
        //dialog参数设置  
        AlertDialog.Builder builder=new AlertDialog.Builder(HiddenStandCheckListActivity.this);  //先得到构造器  
        builder.setTitle("提示"); //设置标题  
        builder.setItems(items,new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {
            	 switch (items[which]) {
					case "信息查看":
					lookStandCheck(arg2);
						break;
					case "继续检查":
						Intent intent=new Intent(HiddenStandCheckListActivity.this, HiddenStandIngDatilAddActivity.class);
				   		intent.putExtra("ck_id", mDatas.get(arg2).getCk_id());
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch(resultCode){
		case 1:
			refreshData();
			break;
		case 2:
			break;
		case 3://review
			String ck_state=data.getStringExtra("ck_state");
			int index=data.getIntExtra("index", -1);
			if(index!=-1)
			{
				mDatas.get(index).setCk_state(ck_state);
				mAdapter.notifyDataSetChanged();
			}
			break;
		case 11:
			refreshData();
			Intent intent=new Intent(HiddenStandCheckListActivity.this, HiddenStandCheckedDatilAddActivity.class);
	   		intent.putExtra("ck_id", data.getStringExtra("ck_id"));
	   		startActivityForResult(intent, 0);
			break;
    	default:
    		refreshData();
    		break;
		}
	}
	
	private void lookStandCheck(final int arg2) {
		Bundle bundle=new Bundle();
		bundle.putSerializable("Standard_CK_Table", mDatas.get(arg2));
		Intent intentlook=new Intent(HiddenStandCheckListActivity.this, HiddenStandCheckShowBaseActivity.class);
		intentlook.putExtras(bundle);
		startActivity(intentlook);
	}  
	
	private void openCheckBook(final int arg2) {
		String sqlCondition=" where e_companyName='"+mDatas.get(arg2).getCompanyName()+"' ";
		SqlOperate<SimpleEnterprise> opetaterSimpleEnterprise=new SqlOperate<SimpleEnterprise>(HiddenStandCheckListActivity.this, SimpleEnterprise.class);
		List<SimpleEnterprise> data=opetaterSimpleEnterprise.SelectEntitysByCondition(sqlCondition);
		opetaterSimpleEnterprise.close();
		
		if(data!=null&&data.size()>0)
		{
			if(mDatas.get(arg2).getCk_state().equals("NONEED"))
			{
				PoiStandCheckYesOperate op=new PoiStandCheckYesOperate(HiddenStandCheckListActivity.this,mDatas.get(arg2),data.get(0));
				try {
					op.doScanofDailyCheck();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				PoiStandCheckNoOperate op=new PoiStandCheckNoOperate(HiddenStandCheckListActivity.this,mDatas.get(arg2),data.get(0));
				try {
					op.doScanofDailyCheck();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}  

	private void refreshData() {
		pageCount=0;
		SqlOperate<Standard_CK_Table> opetaterStandard_CK_Table=new SqlOperate<Standard_CK_Table>(HiddenStandCheckListActivity.this, Standard_CK_Table.class);
		List<Standard_CK_Table> mSctData=opetaterStandard_CK_Table.SelectOffsetEntitysByCondition(mSqlStr, pangSize, pageCount);
		pageCount=1;
		opetaterStandard_CK_Table.close();
		
		if(mSctData.size()<pangSize)
		{
			mlistview.hindLoadView(false);
		}
		
		mDatas.removeAll(mDatas);
		mDatas.addAll(mSctData);
		mAdapter.notifyDataSetChanged();
	}
	
	private void initComponent() {
		// TODO Auto-generated method stub
		topbar_en_check=(TopBarView) findViewById(R.id.topbar_en_check);
		mlistview=(SwipeLoadListView) findViewById(R.id.listView_en_check);
	}
	
}


