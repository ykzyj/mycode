package com.sunnyit.enforcement.action;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.dialog.CustomDialog;
import com.sunnyit.common.poi.PoiDailyCheckNoOperate;
import com.sunnyit.common.poi.PoiDailyCheckYesOperate;
import com.sunnyit.common.poi.PoiStandCheckNoOperate;
import com.sunnyit.common.poi.PoiStandCheckYesOperate;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.SwipeLoadListView;
import com.sunnyit.common.view.SwipeLoadListView.IClickLoadListListener;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.company.model.SimpleEnterprise;
import com.sunnyit.enforcement.data.DailyCheckAdapter;
import com.sunnyit.enforcement.model.DailyCheck;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

/**   
* @Title: WelcomeActivity.java 
* @Package com.sunnyit.system.action 
* @Description: TODO
* @author yk
* @date 2015��8��1�� ����9:47:36 
* @version V1.0   
*/
public class DailyReviewListActivity extends BaseActivity  {

	private TopBarView topbar_en_check;
	private SwipeLoadListView mlistview;
	private List<DailyCheck> mDatas;
	private DailyCheckAdapter mAdapter;
	private int pangSize=10;
	private int pageCount=0;
	
	private String mSqlStr;
	
	private CustomDialog cusdialog;
	
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
					mSqlStr=" select * from DailyCheck where ck_state='FINISH' or ck_state='REVIEWING' "
							+ " or ck_state='NOTYET' or ck_state='ING'  order by ck_time desc ";
					topbar_en_check.setTitle("�ճ���鸴����Ϣ�б�");
					topbar_en_check.setRightButVisibility(View.GONE);
				}
				else
				{
					mSqlStr=" select * from DailyCheck order by ck_time desc ";
					topbar_en_check.setTitle("�ճ������Ϣ�б�");
				}
			}
			else
			{
				switch (state) {
				case "�����":
					state="CKING";
					break;
				case "δ����":
					state="NOTYET";
					break;
				case "������":
					state="ING";
					break;
				case "���븴��":
					state="FINISH";
					break;
				case "������":
					state="REVIEWING";
					break;
				case "�������":
					state="REVIEWED";
					break;
				case "����":
					state="RECORDED";
					break;
				case "����":
					state="DESTORY";
					break;
				case "��������":
					state="NONEED";
					break;

				default:
					break;
				}
				
				mSqlStr=" select * from DailyCheck where ck_state='"+state+"' order by ck_time desc ";
				topbar_en_check.setTitle("�ճ������Ϣ�б�");
				topbar_en_check.setRightButVisibility(View.GONE);
			}
		}
		else
		{
			mSqlStr=intent.getStringExtra("sql");
			topbar_en_check.setTitle("�ճ���鳬������");
			topbar_en_check.setRightButVisibility(View.GONE);
		}
		
		topbar_en_check.setTopBarClick(new ITopBarClick() {
			
			@Override
			public void onClickRightBut() {
				Intent intent=new Intent(DailyReviewListActivity.this,HiddenDailyCheckAddActivity.class);
				startActivityForResult(intent, 0);
			}
			
			@Override
			public void onClickLeftBut() {
				// TODO Auto-generated method stub
				finish();
			}
		});
		//topbar_en_check.setRightButVisibility(View.GONE);
		topbar_en_check.setRightButText("���");
		
		cusdialog=new CustomDialog(DailyReviewListActivity.this);
		cusdialog.setViewAndAlpha(R.layout.progressbar_rw_blue,0);
		cusdialog.setOutCancel(false);
		cusdialog.show();
		
		new Thread(new Runnable() {
			public void run() {
				SqlOperate<DailyCheck> opetaterDailyCheck=new SqlOperate<DailyCheck>(DailyReviewListActivity.this, DailyCheck.class);
        		mDatas=opetaterDailyCheck.SelectOffsetEntitysBySqlCondition(mSqlStr, pangSize, pageCount);
                pageCount=1;
                opetaterDailyCheck.close();
        		mAdapter= new DailyCheckAdapter(DailyReviewListActivity.this, mDatas, R.layout.en_dailycheck_item,mlistview.getRightViewWidth());
        		
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
			SqlOperate<DailyCheck> opetaterDailyCheck=new SqlOperate<DailyCheck>(this, DailyCheck.class);
			List<DailyCheck> mDatas_temp=opetaterDailyCheck.SelectOffsetEntitysBySqlCondition(mSqlStr, pangSize, pageCount);
	        pageCount=1;
	        opetaterDailyCheck.close();
	        
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
			SqlOperate<DailyCheck> opetaterDailyCheckAll=new SqlOperate<DailyCheck>(this, DailyCheck.class);
			List<DailyCheck> mDatas_tempAll=opetaterDailyCheckAll.SelectOffsetEntitysBySqlCondition(mSqlStr, pangSize, pageCount);
	        pageCount=1;
	        opetaterDailyCheckAll.close();
	        
	        mDatas.removeAll(mDatas);
	        mDatas.addAll(mDatas_tempAll);
	        mAdapter.notifyDataSetChanged();
			
			break;
		}
	}

	
	private void initSelfCheckDate() {
		// TODO Auto-generated method stub
        mlistview.setIClickLoadListListener(new IClickLoadListListener() {
			
			@Override
			public void onLoad(Handler handler) {
				// TODO Auto-generated method stub
				SqlOperate<DailyCheck> opetaterDailyCheck=new SqlOperate<DailyCheck>(DailyReviewListActivity.this, DailyCheck.class);
				final List<DailyCheck> data=opetaterDailyCheck.SelectOffsetEntitysBySqlCondition(mSqlStr, pangSize, pageCount);
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
				// TODO Auto-generated method stub
				try {
					DailyCheck t=mDatas.get(arg2);
					switch (t.getCk_state()) {
					case "CKING":
						normalOperate(arg2); 
						break;
					case "NONEED":
						normalOperate(arg2); 
						break;
					case "NOTYET":
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
        
        mAdapter.setOnRightItemClickListener(new DailyCheckAdapter.onRightItemClickListener() {
			
			@Override
			public void onRightItemClick(View v, final DailyCheck t) {
				
		        AlertDialog.Builder builder=new AlertDialog.Builder(DailyReviewListActivity.this);  //�ȵõ�������  
		        builder.setTitle("��ʾ"); //���ñ���  
		        builder.setMessage("�Ƿ�ȷ��ɾ����ǰ��¼?"); //��������  
		        builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() { //����ȷ����ť  
		            @Override  
		            public void onClick(DialogInterface dialog, int which){
		            	SqlOperate<DailyCheck> opetaterDailyCheck=new SqlOperate<DailyCheck>(DailyReviewListActivity.this, DailyCheck.class);
						opetaterDailyCheck.DeleteContent(t.getC_id());
				        opetaterDailyCheck.close();
				        
				        try {
							SqlOperate<UpInfo> opetaterUpInfo=new SqlOperate<UpInfo>(DailyReviewListActivity.this, UpInfo.class);
							opetaterUpInfo.DeleteByCondition(" where InfoId='"+t.getC_id()+"' ");
							opetaterUpInfo.close();
						} catch (Exception e) {
						}
				        
				        mDatas.remove(t);
				        mAdapter.notifyDataSetChanged();
		                dialog.dismiss(); //�ر�dialog
		            }  
		        });  
		        builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() { //����ȡ����ť  
		            @Override  
		            public void onClick(DialogInterface dialog, int which) {  
		                dialog.dismiss();
		            }  
		        });  
		        //��������������ˣ���������ʾ����  
		        builder.create().show(); 
				
			}
		});
	}
	
	private void reviewOperate(final int arg2) {
		// TODO Auto-generated method stub
		final String items[]={"��Ϣ�鿴","�������","����"};  
        //dialog��������  
        AlertDialog.Builder builder=new AlertDialog.Builder(DailyReviewListActivity.this);  //�ȵõ�������  
        builder.setTitle("��ʾ"); //���ñ���  
        //�����б���ʾ��ע���������б���ʾ�Ͳ�Ҫ����builder.setMessage()�ˣ������б������á�  
        builder.setItems(items,new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
                //Toast.makeText(HiddenDailyCheckAddActivity.this, items[which], Toast.LENGTH_SHORT).show();  
                switch (items[which]) {
				case "��Ϣ�鿴":
					lookDailyCheck(arg2);
					break;
				case "�������":
					if(mDatas.get(arg2)!=null)
					{
						openCheckBook(arg2);
					}
					break;
				case "����":
					Bundle bundle=new Bundle();
					bundle.putSerializable("DailyCheck", mDatas.get(arg2));
					Intent intent=new Intent(DailyReviewListActivity.this,HiddenDailyReviewAddActivity.class);
					intent.putExtras(bundle);
					intent.putExtra("index", arg2);
					startActivityForResult(intent, 0);
					break;
					
				default:
					break;
				}
            }

        });  
        builder.setPositiveButton("ȡ��",new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
            }  
        });  
        builder.create().show();
	}
	
	private void endOperate(final int arg2) {
		// TODO Auto-generated method stub
		final String items[]={"��Ϣ�鿴","�������","����"};  
        //dialog��������  
        AlertDialog.Builder builder=new AlertDialog.Builder(DailyReviewListActivity.this);  //�ȵõ�������  
        builder.setTitle("��ʾ"); //���ñ���  
        //�����б���ʾ��ע���������б���ʾ�Ͳ�Ҫ����builder.setMessage()�ˣ������б������á�  
        builder.setItems(items,new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
                //Toast.makeText(HiddenDailyCheckAddActivity.this, items[which], Toast.LENGTH_SHORT).show();  
                switch (items[which]) {
				case "��Ϣ�鿴":
					lookDailyCheck(arg2);
					break;
				case "�������":
					if(mDatas.get(arg2)!=null)
					{
						openCheckBook(arg2);
					}
					break;
					
				case "����":
					
					doDestory(arg2);
					
					break;

				default:
					break;
				}
            }

        });  
        builder.setPositiveButton("ȡ��",new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
            }  
        });  
        builder.create().show();
	}
	
	private void doDestory(final int arg2) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder=new AlertDialog.Builder(this);  //�ȵõ�������  
        builder.setTitle("��ʾ"); //���ñ���  
        builder.setMessage("�Ƿ�ȷ��ע����ǰ�����Ϣ?"); //��������  
        builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() { //����ȷ����ť  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss(); //�ر�dialog  
                String sqlStr=" update DailyCheck set ck_state='DESTORY' "
						+ "where c_id='"+mDatas.get(arg2).getC_id()+"' ";
				SqlOperate<SpecialCheck> opetaterSpecialCheck=new SqlOperate<SpecialCheck>(DailyReviewListActivity.this, DailyCheck.class);
				opetaterSpecialCheck.execMySQL(sqlStr);
				opetaterSpecialCheck.close();
				
		    	SqlOperate<UpInfo> opetaterUpInfo=new SqlOperate<UpInfo>(DailyReviewListActivity.this, UpInfo.class);
				String conditionSqlStr="  where InfoId='"+mDatas.get(arg2).getC_id()+"' ";
		    	List<UpInfo> ls_in=opetaterUpInfo.SelectEntitysByCondition(conditionSqlStr);
		    	if(ls_in.size()==0)
		    	{

		    		UpInfo info=new UpInfo();
		    		info.setU_Id(UUID.randomUUID().toString());
		    		info.setInfoTable("DailyCheck");
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
        builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() { //����ȡ����ť  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
            }  
        });  
        //��������������ˣ���������ʾ����  
        builder.create().show(); 
	}
	
	private void superviseOperate(final int arg2) {
		final String items[]={"��Ϣ�鿴","�������","���ζ���","����"};  
        //dialog��������  
        AlertDialog.Builder builder=new AlertDialog.Builder(DailyReviewListActivity.this);  //�ȵõ�������  
        builder.setTitle("��ʾ"); //���ñ���  
        //�����б���ʾ��ע���������б���ʾ�Ͳ�Ҫ����builder.setMessage()�ˣ������б������á�  
        builder.setItems(items,new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
                //Toast.makeText(HiddenDailyCheckAddActivity.this, items[which], Toast.LENGTH_SHORT).show();  
                switch (items[which]) {
				case "��Ϣ�鿴":
					lookDailyCheck(arg2);
					break;
				case "�������":
					if(mDatas.get(arg2)!=null)
					{
						openCheckBook(arg2);
					}
					break;
				case "���ζ���":
					String conditionStr_dc=" where InfoTable='DailyCheck' and OperateType='add' and InfoId='"+mDatas.get(arg2).getC_id()+"' ";
					SqlOperate<UpInfo> opetaterInfo_dc=new SqlOperate<UpInfo>(DailyReviewListActivity.this, UpInfo.class);
					final List<UpInfo> ls_UpInfo_dc=opetaterInfo_dc.SelectEntitysByCondition(conditionStr_dc);
					opetaterInfo_dc.close();
					
					if(ls_UpInfo_dc!=null&&ls_UpInfo_dc.size()>0)
					{
						Toast.makeText(DailyReviewListActivity.this, "����Ϣ���ڱ��ش��ڣ����ϴ����ٶ���", Toast.LENGTH_SHORT).show();
					}
					else
					{
						Intent intent=new Intent(DailyReviewListActivity.this,HiddenSuperviseAddActivity.class);
						intent.putExtra("addtype", "Daily");
						intent.putExtra("ID", mDatas.get(arg2).getC_id());
						startActivityForResult(intent, 0);
					}
					break;
				case "����":
					Bundle bundle=new Bundle();
					bundle.putSerializable("DailyCheck", mDatas.get(arg2));
					Intent intent=new Intent(DailyReviewListActivity.this,HiddenDailyReviewAddActivity.class);
					intent.putExtras(bundle);
					intent.putExtra("index", arg2);
					startActivityForResult(intent, 0);
					break;
				default:
					break;
				}
            }

        });  
        builder.setPositiveButton("ȡ��",new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
            }  
        });  
        builder.create().show();
	}
	
	private void normalOperate(final int arg2) {
		final String items[]={"��Ϣ�鿴","�������"};  
        //dialog��������  
        AlertDialog.Builder builder=new AlertDialog.Builder(DailyReviewListActivity.this);  //�ȵõ�������  
        builder.setTitle("��ʾ"); //���ñ���  
        //�����б���ʾ��ע���������б���ʾ�Ͳ�Ҫ����builder.setMessage()�ˣ������б������á�  
        builder.setItems(items,new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
                //Toast.makeText(HiddenDailyCheckAddActivity.this, items[which], Toast.LENGTH_SHORT).show();  
                switch (items[which]) {
				case "��Ϣ�鿴":
					lookDailyCheck(arg2);
					break;
				case "�������":
					if(mDatas.get(arg2)!=null)
					{
						openCheckBook(arg2);
					}
					break;

				default:
					break;
				}
            }

        });  
        builder.setPositiveButton("ȡ��",new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
            }  
        });  
        builder.create().show();
	}
	
	private void openCheckBook(final int arg2) {
		String sqlCondition=" where e_companyName='"+mDatas.get(arg2).getCk_checkeddepartment()+"' ";
		SqlOperate<SimpleEnterprise> opetaterSimpleEnterprise=new SqlOperate<SimpleEnterprise>(DailyReviewListActivity.this, SimpleEnterprise.class);
		List<SimpleEnterprise> data=opetaterSimpleEnterprise.SelectEntitysByCondition(sqlCondition);
		opetaterSimpleEnterprise.close();
		if(data!=null&&data.size()>0)
		{
			if(mDatas.get(arg2).getCk_state().equals("NONEED"))
			{
				PoiDailyCheckYesOperate op=new PoiDailyCheckYesOperate(DailyReviewListActivity.this,mDatas.get(arg2),data.get(0));
				try {
					op.doScanofDailyCheck();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				PoiDailyCheckNoOperate op=new PoiDailyCheckNoOperate(DailyReviewListActivity.this,mDatas.get(arg2),data.get(0));
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
			Toast.makeText(DailyReviewListActivity.this, "��ҵ��Ϣ�����ڣ��޷����ɼ����", Toast.LENGTH_SHORT).show();
		}
	}  
	
	private void lookDailyCheck(final int arg2) {
		Bundle bundle=new Bundle();
		bundle.putSerializable("DailyCheck", mDatas.get(arg2));
		Intent intent=new Intent(DailyReviewListActivity.this,HiddenDailyCheckShowActivity.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}  


	private void initComponent() {
		// TODO Auto-generated method stub
		mlistview=(SwipeLoadListView) findViewById(R.id.listView_en_check);
		topbar_en_check=(TopBarView) findViewById(R.id.topbar_en_check);
	}
}


