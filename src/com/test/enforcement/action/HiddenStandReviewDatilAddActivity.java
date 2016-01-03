package com.sunnyit.enforcement.action;

import java.util.List;
import java.util.UUID;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.dataOperate.dOperate;
import com.sunnyit.common.dialog.CustomDialog;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.DateEditView;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.enforcement.data.Standard_CK_Table_ItemReviewAdapter;
import com.sunnyit.enforcement.data.Standard_CK_Table_ItemReviewAdapter.ApcceptOpinionListener;
import com.sunnyit.enforcement.model.Standard_CK_Table;
import com.sunnyit.enforcement.model.Standard_CK_Table_Item;
import com.sunnyit.synchronous.model.UpInfo;
import com.sunnyit.system.model.User;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import test.action.DialogShowActivity;
import android.widget.EditText;
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
public class HiddenStandReviewDatilAddActivity extends BaseActivity {

	private TopBarView topbar_standcheck_review_add;
	private EditText et_ck_reviewDept;
	private DateEditView et_ck_reviewTime;
	private EditText et_ck_reviewPeople;
	private ListView lv_stand_review;
	
	private Standard_CK_Table mStandTable;
	
	private List<Standard_CK_Table_Item> mData;
	private Standard_CK_Table_ItemReviewAdapter mAdapter;
	
	private User mUser;
	
	private String Ck_state="";
	private int oKCount=0;
	
	private boolean isRECORDED=false;
	private boolean isDESTORY=false;
	
	private int index;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hidden_standreview_add);
		
		mStandTable=(Standard_CK_Table) getIntent().getSerializableExtra("Standard_CK_Table");
		index=getIntent().getIntExtra("index", 0);
		
		mUser=getCurrentUser();
		
		initComponent();
		
		initAdapter();
		
		
		topbar_standcheck_review_add.setTopBarClick(new ITopBarClick() {
			
			@Override
			public void onClickRightBut() {
				
				final String ck_reviewDept=et_ck_reviewDept.getText().toString().trim();
				final String ck_reviewTime=et_ck_reviewTime.getText().toString().trim();
				final String ck_reviewPeople=et_ck_reviewPeople.getText().toString().trim();
				
				if("".equals(ck_reviewDept))
				{
					Toast.makeText(HiddenStandReviewDatilAddActivity.this, "复查部门不能为空", Toast.LENGTH_SHORT).show();
				}
				else if("".equals(ck_reviewTime))
				{
					Toast.makeText(HiddenStandReviewDatilAddActivity.this, "复查时间不能为空", Toast.LENGTH_SHORT).show();
				}
				else if("".equals(ck_reviewPeople))
				{
					Toast.makeText(HiddenStandReviewDatilAddActivity.this, "复查人员不能为空", Toast.LENGTH_SHORT).show();
				}
				else
				{
					// TODO Auto-generated method stub
					final CustomDialog cusdialog=new CustomDialog(HiddenStandReviewDatilAddActivity.this);
	        		cusdialog.setViewAndAlpha(R.layout.progressbar_rw_blue,0);
	        		cusdialog.setOutCancel(false);
	        		cusdialog.show();
	        		
	        		new Thread(new Runnable(){  
	                    
	                    public void run(){
	                    	
	                    	mStandTable.setCk_reviewDept(ck_reviewDept);
	                    	mStandTable.setCk_reviewPeople(ck_reviewPeople);
	                    	mStandTable.setCk_reviewTime(ck_reviewTime);
	                    	
	                    	for(Standard_CK_Table_Item item:mData)
	                    	{
	                    		if(item.getItem_reviewResult()!=null
	                    				||!item.getItem_reviewResult().equals(""))
	                    		{
	                    			oKCount++;
	                    			if(item.getItem_reviewResult().equals("NO"))
	                    			{
	                    				Ck_state="REVIEWING";
	                    			}
	                    			SqlOperate<Standard_CK_Table_Item> operaterStandard_CK_Table_Item=new 
	                        				SqlOperate<Standard_CK_Table_Item>(HiddenStandReviewDatilAddActivity.this, Standard_CK_Table_Item.class);
	                				operaterStandard_CK_Table_Item.upContent(item);
	                        		operaterStandard_CK_Table_Item.close();
	                    		}
	                    	}
	                    	
	                    	if(oKCount==mData.size())
	                    	{
	                    		if(Ck_state.equals("REVIEWING"))
	                    		{
	                    			isRECORDED=true;
	                    		}
	                    		else
	                    		{
	                    			isDESTORY=true;
	                    		}
	                    	}
	                    	else
	                    	{
	                    		Ck_state="REVIEWING";
	                    	}

	                    	
	                    	Message msg=Message.obtain();
	                		msg.what=0;
	                		msg.obj=mStandTable.getCk_id();
							mHandler.sendMessage(msg);
							
							cusdialog.dismiss();
	                    }  
	                }).start();  
				}
				
			}
			
			@Override
			public void onClickLeftBut() {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	private Handler mHandler=new Handler(){
		public void handleMessage(final Message msg) {
			int flag=msg.what;
			if(flag==0)
			{
				if(isDESTORY)
				{
					AlertDialog.Builder builder=new AlertDialog.Builder(HiddenStandReviewDatilAddActivity.this);  //先得到构造器  
			        builder.setTitle("提示"); //设置标题  
			        builder.setMessage("是否确认销号?"); //设置内容  
			        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮  
			            @Override  
			            public void onClick(DialogInterface dialog, int which) {  
			                dialog.dismiss(); //关闭dialog  
			                mStandTable.setCk_state("DESTORY");
			                oprateReview();
			            }  
			        });  
			        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮  
			            @Override  
			            public void onClick(DialogInterface dialog, int which) {  
			                dialog.dismiss();  
			                mStandTable.setCk_state("REVIEWED");
			                oprateReview();
			            }  
			        });  
			        //参数都设置完成了，创建并显示出来  
			        builder.create().show(); 
				}
				else if(isRECORDED)
				{
					AlertDialog.Builder builder=new AlertDialog.Builder(HiddenStandReviewDatilAddActivity.this);  //先得到构造器  
			        builder.setTitle("提示"); //设置标题  
			        builder.setMessage("是否确认立案?"); //设置内容  
			        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮  
			            @Override  
			            public void onClick(DialogInterface dialog, int which) {  
			                dialog.dismiss(); //关闭dialog  
			                mStandTable.setCk_state("RECORDED");
			                oprateReview();
			            }  
			        });  
			        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮  
			            @Override  
			            public void onClick(DialogInterface dialog, int which) {  
			                dialog.dismiss();  
			                mStandTable.setCk_state("REVIEWING");
			                oprateReview();
			            }  
			        }); 
				}
				else
				{
					mStandTable.setCk_state(Ck_state);
					oprateReview();
				}
			}
		}

	};
	
	private void oprateReview() {
		SqlOperate<Standard_CK_Table> operaterStandard_CK_Table=new 
				SqlOperate<Standard_CK_Table>(HiddenStandReviewDatilAddActivity.this, Standard_CK_Table.class);
		operaterStandard_CK_Table.upContent(mStandTable);
		operaterStandard_CK_Table.close();
		
		SqlOperate<UpInfo> opetaterUpInfo=new SqlOperate<UpInfo>(HiddenStandReviewDatilAddActivity.this, UpInfo.class);
		String conditionSqlStr="  where InfoId='"+mStandTable.getCk_id()+"' ";
		List<UpInfo> ls_in=opetaterUpInfo.SelectEntitysByCondition(conditionSqlStr);
		if(ls_in.size()==0)
		{
			UpInfo info=new UpInfo();
			info.setU_Id(UUID.randomUUID().toString());
			info.setInfoTable("Standard_CK_Table");
			info.setInfoId(mStandTable.getCk_id());
			info.setOperateType("addreview");
			info.setRemark("");
			opetaterUpInfo.saveContent(info);
		}
		opetaterUpInfo.close();
		//本地保存
		Toast.makeText(HiddenStandReviewDatilAddActivity.this, "数据本地保存成功！", Toast.LENGTH_SHORT).show();
		
		Intent intent = new Intent();
        intent.putExtra("ck_state", mStandTable.getCk_state());
        intent.putExtra("index", index);
        setResult(3, intent);
		finish();
		finish();
	};

	private void initAdapter() {
		String conStr=" where ck_id='"+mStandTable.getCk_id()+"' and item_isQualified='NO' ";
		SqlOperate<Standard_CK_Table_Item> operaterStandard_CK_Table_Item=new SqlOperate<Standard_CK_Table_Item>(HiddenStandReviewDatilAddActivity.this, Standard_CK_Table_Item.class);
		mData=operaterStandard_CK_Table_Item.SelectEntitysByCondition(conStr);
		operaterStandard_CK_Table_Item.close();
		mAdapter=new Standard_CK_Table_ItemReviewAdapter(this, mData, R.layout.hidden_enreview_standcheck_item);
		lv_stand_review.setAdapter(mAdapter);
		
		lv_stand_review.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				Standard_CK_Table_Item ct=mData.get(arg2);
				AlertDialog.Builder builder=new AlertDialog.Builder(HiddenStandReviewDatilAddActivity.this);  //先得到构造器  
		        builder.setTitle("依据说明"); //设置标题  
		        builder.setMessage(ct.getItem_basis()); //设置内容  
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
		
		mAdapter.setApcceptOpinionListener(new ApcceptOpinionListener() {
			
			@Override
			public void setItenApcceptOpinion(Standard_CK_Table_Item standard_Table_Item, boolean checked) {
				// TODO Auto-generated method stub
				for(int i=0;i<mData.size();i++)
				{
					if(mData.get(i).getId().equals(standard_Table_Item.getId()))
					{
						if(checked)
						{
							mData.get(i).setItem_reviewResult("YES");
							mData.get(i).setItem_repairState("FINISH");
						}
						else
						{
							mData.get(i).setItem_reviewResult("NO");
							mData.get(i).setItem_repairState("ING");
						}
						break;
					}
				}
				
			}
		});
	}
	
	private void initComponent() {
		// TODO Auto-generated method stub
		topbar_standcheck_review_add=(TopBarView) findViewById(R.id.topbar_standcheck_review_add);
		lv_stand_review=(ListView) findViewById(R.id.lv_stand_review);
		et_ck_reviewDept=(EditText) findViewById(R.id.et_ck_reviewDept);
		et_ck_reviewTime=(DateEditView) findViewById(R.id.et_ck_reviewTime);
		et_ck_reviewPeople=(EditText) findViewById(R.id.et_ck_reviewPeople);
		
		et_ck_reviewDept.setText(mUser.getDepartmentName());
		et_ck_reviewTime.setText(dOperate.getCurrentDate());
	}
}


