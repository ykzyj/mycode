package com.sunnyit.enforcement.action;

import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.dialog.CustomDialog;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.enforcement.data.Standard_CK_Table_ItemIngAdapter;
import com.sunnyit.enforcement.data.Standard_CK_Table_ItemIngAdapter.QualifiedListener;
import com.sunnyit.enforcement.model.Standard_CK_Table;
import com.sunnyit.enforcement.model.Standard_CK_Table_Item;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
public class HiddenStandIngDatilAddActivity extends BaseActivity {

	private TopBarView topbar_endatil_standcheck_add;
	private ListView lv_stand_table_item;
	
	private Standard_CK_Table standTable;
	private String ck_id;
	
	private List<Standard_CK_Table_Item> mData;
	private Standard_CK_Table_ItemIngAdapter mAdapter;
	
	private boolean haveReview=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hidden_endatil_standcheck_add);
		
		ck_id = getIntent().getStringExtra("ck_id");
		String conditionStr="  where ck_id='"+ck_id+"' ";
		SqlOperate<Standard_CK_Table> operaterStandard_CK_Table=new SqlOperate<Standard_CK_Table>(HiddenStandIngDatilAddActivity.this, Standard_CK_Table.class);
    	List<Standard_CK_Table> ls_st=operaterStandard_CK_Table.SelectEntitysByCondition(conditionStr);
    	operaterStandard_CK_Table.close();
    	
    	if(ls_st!=null&&ls_st.size()>0)
    	{
    		standTable=ls_st.get(0);
    	}
		
		initComponent();
		
		initAdapter();
		
		
		topbar_endatil_standcheck_add.setTopBarClick(new ITopBarClick() {
			
			@Override
			public void onClickRightBut() {
				// TODO Auto-generated method stub
				final CustomDialog cusdialog=new CustomDialog(HiddenStandIngDatilAddActivity.this);
        		cusdialog.setViewAndAlpha(R.layout.progressbar_rw_blue,0);
        		cusdialog.setOutCancel(false);
        		cusdialog.show();
        		
        		new Thread(new Runnable(){  
                    
                    public void run(){

                    	int qcount=0;
                    	for(Standard_CK_Table_Item scti:mData)
                    	{
                    		if(scti.getItem_isQualified()!=null&&
                    				!scti.getItem_isQualified().equals(""))
                    		{
                    			SqlOperate<Standard_CK_Table_Item> operaterStandard_CK_Table_Item=new 
                        				SqlOperate<Standard_CK_Table_Item>(HiddenStandIngDatilAddActivity.this, Standard_CK_Table_Item.class);
                				operaterStandard_CK_Table_Item.upContent(scti);
                        		operaterStandard_CK_Table_Item.close();
                        		qcount++;
                    		}
                    	}
                    	
                    	for(Standard_CK_Table_Item scti:mData)
                    	{
                    		if(scti.getItem_isQualified()!=null&&
                    				!scti.getItem_isQualified().equals(""))
                    		{
                    			if(scti.getItem_isQualified().equals("NO"))
                        		{
                        			haveReview=true;
                        			standTable.setIsExistDanger("NO");
                        			break;
                        		}
                    		}
                    	}
                    	
                    	if(qcount==mData.size())
                    	{
                    		standTable.setCk_state("CKEND");
                    		if(!haveReview)
                        	{
                        		standTable.setCk_state("NONEED");
                        		standTable.setIsExistDanger("YES");//有隐患
                        	}
                    	}
                    	
                    	SqlOperate<Standard_CK_Table> operaterStandard_CK_Table=new 
                				SqlOperate<Standard_CK_Table>(HiddenStandIngDatilAddActivity.this, Standard_CK_Table.class);
        				operaterStandard_CK_Table.upContent(standTable);
        				operaterStandard_CK_Table.close();
                    	
                    	Message msg=Message.obtain();
                		msg.what=0;
                		msg.obj=standTable.getCk_id();
						mHandler.sendMessage(msg);
						
						cusdialog.dismiss();
                    }  
                }).start();  
			}
			
			@Override
			public void onClickLeftBut() {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
                setResult(100, intent);
				finish();
			}
		});
	}
	
	private Handler mHandler=new Handler(){
		public void handleMessage(final Message msg) {
			int flag=msg.what;
			if(flag==0)
			{
				if(standTable.getCk_state().equals("CKEND"))
				{
					AlertDialog.Builder builder=new AlertDialog.Builder(HiddenStandIngDatilAddActivity.this);  //先得到构造器  
			        builder.setTitle("提示"); //设置标题  
			        builder.setMessage("存在不合格项，要立即处理吗?"); //设置内容  
			        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮  
			            @Override  
			            public void onClick(DialogInterface dialog, int which) {  
			                dialog.dismiss(); //关闭dialog  
			                Intent intent = new Intent();
			                intent.putExtra("ck_id", standTable.getCk_id());
			                setResult(11, intent);
							finish();
			            }  
			        });  
			        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮  
			            @Override  
			            public void onClick(DialogInterface dialog, int which) {  
			            	dialog.dismiss(); //关闭dialog  
			            	Intent intent = new Intent();
			                setResult(1, intent);
							finish();
			            }  
			        });  
			        //参数都设置完成了，创建并显示出来  
			        builder.create().show(); 
				}
				else
				{
					Toast.makeText(HiddenStandIngDatilAddActivity.this, "保存成功", Toast.LENGTH_SHORT).show();  
					Intent intent = new Intent();
	                setResult(100, intent);
					finish();
				}
			}
		};
	};

	private void initAdapter() {
		String conStr=" where ck_id='"+ck_id+"' ";
		SqlOperate<Standard_CK_Table_Item> operaterStandard_CK_Table_Item=new SqlOperate<Standard_CK_Table_Item>(HiddenStandIngDatilAddActivity.this, Standard_CK_Table_Item.class);
		mData=operaterStandard_CK_Table_Item.SelectEntitysByCondition(conStr);
		operaterStandard_CK_Table_Item.close();
		mAdapter=new Standard_CK_Table_ItemIngAdapter(this, mData, R.layout.hidden_endatil_standcheck_item);
		lv_stand_table_item.setAdapter(mAdapter);
		
		lv_stand_table_item.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				Standard_CK_Table_Item ct=mData.get(arg2);
				AlertDialog.Builder builder=new AlertDialog.Builder(HiddenStandIngDatilAddActivity.this);  //先得到构造器  
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
		
		mAdapter.setQualifiedListener(new QualifiedListener() {
			
			@Override
			public void setItenIsQualified(Standard_CK_Table_Item standard_Table_Item, boolean checked) {
				// TODO Auto-generated method stub
				for(int i=0;i<mData.size();i++)
				{
					if(mData.get(i).getId().equals(standard_Table_Item.getId()))
					{
						if(checked)
						{
							mData.get(i).setItem_isQualified("YES");
							mData.get(i).setItem_repairState("NONEED");
						}
						else
						{
							mData.get(i).setItem_isQualified("NO");
							mData.get(i).setItem_repairState("NOYET");
						}
						break;
					}
				}
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch(resultCode){
		case 1:
			break;
    	default:
    		break;
		}
	}
	
	private void initComponent() {
		// TODO Auto-generated method stub
		topbar_endatil_standcheck_add=(TopBarView) findViewById(R.id.topbar_endatil_standcheck_add);
		lv_stand_table_item=(ListView) findViewById(R.id.lv_stand_table_item);
	}
}


