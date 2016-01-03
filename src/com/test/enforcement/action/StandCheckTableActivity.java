package com.sunnyit.enforcement.action;

import java.util.ArrayList;
import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.company.action.CompanyInfoAddActivity;
import com.sunnyit.enforcement.data.CheckTablesRadioAdapter;
import com.sunnyit.enforcement.model.CheckTables;
import com.sunnyit.hiddencheck.data.CompanyNameAdapter;
import com.sunnyit.hiddencheck.model.CompanyMin;
import com.sunnyit.system.model.User;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import test.action.DialogShowActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

/**   
* @Title: WelcomeActivity.java 
* @Package com.sunnyit.system.action 
* @Description: TODO
* @author yk
* @date 2015年8月1日 上午9:47:36 
* @version V1.0   
*/
public class StandCheckTableActivity extends BaseActivity  {

	private TopBarView topbar_standcheck_table;
	private EditText et_table_companyname;
	private Button but_standtable_add;
	private ListView lv_standtable_choice;
	private ListView lv_search_tx;
	private String mSqlStr;
	private ImageView img_com_add;
	
	private String checkCompanyName;
	private String tableName="";
	private String tableid="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.en_stand_table);
		
		User user=getCurrentUser();
		mSqlStr=" select distinct sc_tableName,sc_tableId from CheckTables where sc_userId='"+user.getUserId()+"'";
		
		initComponent();
		
		topbar_standcheck_table.setTopBarClick(new ITopBarClick() {
			
			@Override
			public void onClickRightBut() {
				checkCompanyName=et_table_companyname.getText().toString().trim();
				if("".equals(checkCompanyName))
				{
					Toast.makeText(StandCheckTableActivity.this, "检查企业名称不能为空", Toast.LENGTH_SHORT).show(); 
				}
				else if("".equals(tableid))
				{
					Toast.makeText(StandCheckTableActivity.this, "检查表选择项不能为空", Toast.LENGTH_SHORT).show(); 
				}
				else
				{
					String comSqlStr="select e_Id,e_companyName from SimpleEnterprise "+
							" where e_companyName like '%"+checkCompanyName+"%' ";
					
					SqlOperate<CompanyMin> opetaterCompanyName=new SqlOperate<CompanyMin>(StandCheckTableActivity.this, CompanyMin.class);
					final List<CompanyMin> mCompanyMin=opetaterCompanyName.SelectEntitysBySqlCondition(comSqlStr);
					opetaterCompanyName.close();
					
					if(mCompanyMin!=null&&mCompanyMin.size()>0)
					{
						Intent intent=new Intent(StandCheckTableActivity.this,HiddenStandCheckAddActivity.class);
						intent.putExtra("checkCompanyName", checkCompanyName);
						intent.putExtra("tableid", tableid);
						intent.putExtra("tableName", tableName);
						startActivityForResult(intent, 2);
					}
					else
					{
						AlertDialog.Builder builder=new AlertDialog.Builder(StandCheckTableActivity.this);  //先得到构造器  
				        builder.setTitle("提示"); //设置标题  
				        builder.setMessage("所检查企业信息不存在，现在添加?"); //设置内容  
				        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮  
				            @Override  
				            public void onClick(DialogInterface dialog, int which) {  
				                dialog.dismiss(); //关闭dialog  
				                Intent intent=new Intent(StandCheckTableActivity.this,CompanyInfoAddActivity.class);
								intent.putExtra("isChoice", true);
								intent.putExtra("checkCompanyName", checkCompanyName);
								startActivityForResult(intent, 1);
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
					
					
				}
				
			}
			
			@Override
			public void onClickLeftBut() {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		initAdapter();
		
		et_table_companyname.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				String key=et_table_companyname.getText().toString().trim();
				if(key!=null&&!key.equals(""))
				{
					selectCompanyByKey(key);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
		but_standtable_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(StandCheckTableActivity.this,StandListChoiceActivity.class);
				startActivityForResult(intent, 0);
			}
		});
		
		img_com_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(StandCheckTableActivity.this,CompanyInfoAddActivity.class);
				intent.putExtra("isChoice", true);
				startActivityForResult(intent, 1);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch(resultCode){
		case 1:
			if(data!=null)
			{
				/*String companyName=data.getStringExtra("companyName");
				et_table_companyname.setText(companyName);
				List<CompanyMin> mCompanyMin=new ArrayList<CompanyMin>();
				CompanyNameAdapter mComMinAdapter= new CompanyNameAdapter(StandCheckTableActivity.this, mCompanyMin, R.layout.company_industry_item);
				lv_search_tx.setAdapter(mComMinAdapter);*/
				
				Intent intent=new Intent(StandCheckTableActivity.this,HiddenStandCheckAddActivity.class);
				intent.putExtra("checkCompanyName", checkCompanyName);
				intent.putExtra("tableid", tableid);
				intent.putExtra("tableName", tableName);
				startActivityForResult(intent, 2);
			}
			break;
        case 2:
        	
        	SqlOperate<CheckTables> opetaterSelfCheck=new SqlOperate<CheckTables>(this, CheckTables.class);
    		final List<CheckTables> mDatas=opetaterSelfCheck.SelectEntitysBySqlCondition(mSqlStr);
            opetaterSelfCheck.close();
            
            final CheckTablesRadioAdapter mAdapter= new CheckTablesRadioAdapter(this, mDatas, R.layout.en_stand_table_item);
    		lv_standtable_choice.setAdapter(mAdapter);
    		
    		lv_standtable_choice.setOnItemClickListener(new OnItemClickListener() {

    			@Override
    			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
    				// TODO Auto-generated method stub
    				mAdapter.setSelectItem(arg2);  
    				mAdapter.notifyDataSetInvalidated();  
    				
    				CheckTables ct=mDatas.get(arg2);
    				tableid=ct.getSc_tableId();
    				tableName=ct.getSc_tableName();
    			}
    		});
    		
    		lv_standtable_choice.setOnItemLongClickListener(new OnItemLongClickListener() {

    			@Override
    			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
    				// TODO Auto-generated method stub
    				AlertDialog.Builder builder=new AlertDialog.Builder(StandCheckTableActivity.this);  //先得到构造器  
    		        builder.setTitle("提示"); //设置标题  
    		        builder.setMessage("是否确认删除?"); //设置内容  
    		        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮  
    		            @Override  
    		            public void onClick(DialogInterface dialog, int which) {  
    		            	
    		            	CheckTables ct=mDatas.get(arg2);
    		            	String deleteStr=" where sc_tableId='"+ct.getSc_tableId()+"' ";
    		            	SqlOperate<CheckTables> opetaterCheckTables=new SqlOperate<CheckTables>(StandCheckTableActivity.this, CheckTables.class);
    		            	opetaterCheckTables.DeleteByCondition(deleteStr);
    		            	opetaterCheckTables.close();
    		                dialog.dismiss(); //关闭dialog  
    		                mDatas.remove(ct);
    		                mAdapter.notifyDataSetInvalidated();
    		                Toast.makeText(StandCheckTableActivity.this, "删除成功", Toast.LENGTH_SHORT).show(); 
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
    				return false;
    			}
    		});
        	
        	break;
        case 3:
        	//立即处理
        	/*String Ck_ID=(String)data.getStringExtra("Ck_ID");
        	Intent intent3=new Intent(StandCheckTableActivity.this, HiddenStandCheckedDatilAddActivity.class);
        	intent3.putExtra("ck_id", Ck_ID);
       		startActivityForResult(intent3, 0);*/
        	Intent intent = new Intent();
            intent.putExtra("ck_id", (String)data.getStringExtra("Ck_ID"));
            setResult(11, intent);
        	finish();
			break;
        case 4:
        	//跳转查看列表
        	/*Intent intent=new Intent(StandCheckTableActivity.this,HiddenStandCheckListActivity.class);
			startActivity(intent);*/
        	Intent intent4 = new Intent();
            setResult(100, intent4);
        	finish();
			break;
    	default:
    		break;
		}
	}
	
	private void selectCompanyByKey(String key) {
		// TODO Auto-generated method stub
		String comSqlStr="select e_Id,e_companyName from SimpleEnterprise "+
				" where e_companyName like '%"+key+"%' ";
		
		SqlOperate<CompanyMin> opetaterCompanyName=new SqlOperate<CompanyMin>(this, CompanyMin.class);
		final List<CompanyMin> mCompanyMin=opetaterCompanyName.SelectEntitysBySqlCondition(comSqlStr);
		opetaterCompanyName.close();
		
		CompanyNameAdapter mComMinAdapter= new CompanyNameAdapter(StandCheckTableActivity.this, mCompanyMin, R.layout.company_industry_item);
        
		lv_search_tx.setAdapter(mComMinAdapter);
		lv_search_tx.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				if(mCompanyMin!=null&&mCompanyMin.size()>0)
				{
					CompanyMin commin=mCompanyMin.get(arg2);
					et_table_companyname.setText(commin.getE_companyName());
					
					List<CompanyMin> mCompanyMin=new ArrayList<CompanyMin>();
					CompanyNameAdapter mComMinAdapter= new CompanyNameAdapter(StandCheckTableActivity.this, mCompanyMin, R.layout.company_industry_item);
					lv_search_tx.setAdapter(mComMinAdapter);
				}
			}
		});
	}

	
	private void initAdapter() {
		SqlOperate<CheckTables> opetaterCheckTables=new SqlOperate<CheckTables>(this, CheckTables.class);
		final List<CheckTables> mDatas=opetaterCheckTables.SelectEntitysBySqlCondition(mSqlStr);
		opetaterCheckTables.close();
        
        final CheckTablesRadioAdapter mAdapter= new CheckTablesRadioAdapter(this, mDatas, R.layout.en_stand_table_item);
		lv_standtable_choice.setAdapter(mAdapter);
		
		lv_standtable_choice.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				mAdapter.setSelectItem(arg2);  
				mAdapter.notifyDataSetInvalidated();  
				
				CheckTables ct=mDatas.get(arg2);
				tableid=ct.getSc_tableId();
				tableName=ct.getSc_tableName();
						
			}
		});
		
		lv_standtable_choice.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder=new AlertDialog.Builder(StandCheckTableActivity.this);  //先得到构造器  
		        builder.setTitle("提示"); //设置标题  
		        builder.setMessage("是否确认删除?"); //设置内容  
		        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮  
		            @Override  
		            public void onClick(DialogInterface dialog, int which) {  
		            	
		            	CheckTables ct=mDatas.get(arg2);
		            	String deleteStr=" where sc_tableId='"+ct.getSc_tableId()+"' ";
		            	SqlOperate<CheckTables> opetaterCheckTables=new SqlOperate<CheckTables>(StandCheckTableActivity.this, CheckTables.class);
		            	opetaterCheckTables.DeleteByCondition(deleteStr);
		            	opetaterCheckTables.close();
		                dialog.dismiss(); //关闭dialog  
		                mDatas.remove(ct);
		                mAdapter.notifyDataSetInvalidated();
		                Toast.makeText(StandCheckTableActivity.this, "删除成功", Toast.LENGTH_SHORT).show(); 
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
				return false;
			}
		});
        
	}

	private void initComponent() {
		// TODO Auto-generated method stub
		topbar_standcheck_table=(TopBarView) findViewById(R.id.topbar_standcheck_table);
		et_table_companyname=(EditText) findViewById(R.id.et_table_companyname);
		but_standtable_add=(Button) findViewById(R.id.but_standtable_add);
		lv_standtable_choice=(ListView) findViewById(R.id.lv_standtable_choice);
		lv_search_tx=(ListView) findViewById(R.id.lv_search_tx);
		img_com_add=(ImageView) findViewById(R.id.img_com_add);
		img_com_add.setVisibility(View.GONE);
	}
}


