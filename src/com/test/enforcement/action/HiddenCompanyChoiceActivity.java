package com.sunnyit.enforcement.action;

import java.util.ArrayList;
import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.dialog.CustomDialog;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.ClickLoadListview;
import com.sunnyit.common.view.CustomFAB;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.ClickLoadListview.IClickLoadListListener;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.company.action.CompanyInfoActivity;
import com.sunnyit.company.action.CompanyInfoAddActivity;
import com.sunnyit.company.model.SimpleEnterprise;
import com.sunnyit.enforcement.data.DailyCheckAdapter;
import com.sunnyit.enforcement.data.SimpleEnterpriseNameAdapter;
import com.sunnyit.enforcement.model.DailyCheck;
import com.sunnyit.hiddencheck.action.HiddenTabListActivity;
import com.sunnyit.hiddencheck.action.HiddenTabSortDatilActivity;
import com.sunnyit.hiddencheck.data.CompanyNameAdapter;
import com.sunnyit.hiddencheck.model.CompanyMin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import test.action.DialogShowActivity;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

/**   
* @Title: OneMonthNoReport.java 
* @Package com.sunnyit.hiddencheck.action 
* @Description: TODO
* @author yk
* @date 2015��9��14�� ����11:21:27 
* @version V1.0   
*/
public class HiddenCompanyChoiceActivity extends BaseActivity {
	
	private ClickLoadListview mlistview;
	private TopBarView topbar_com_name;
	private List<SimpleEnterprise> mDatas;
	private SimpleEnterpriseNameAdapter mAdapter;
	
	private String mSqlStr=" select * from SimpleEnterprise ";
	private String mKey;
	
	private CustomFAB  company_hidden_search;
	
	private EditText  et_hidden_check;
	
	private int pangSize=10;
	private int pageCount=0;
	
	private CustomDialog cusdialog;
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.what==0)
			{
				mlistview.setAdapter(mAdapter);
			}
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hidden_companychoice_list);
		
		initComponent();
		//initDate();
		
		cusdialog=new CustomDialog(HiddenCompanyChoiceActivity.this);
		cusdialog.setViewAndAlpha(R.layout.progressbar_rw_blue,0);
		cusdialog.setOutCancel(false);
		cusdialog.show();
		
		new Thread(new Runnable() {
			public void run() {
				initDate();
        		
        		Message msg=Message.obtain();
        		msg.what=0;
        		handler.sendMessage(msg);
        		cusdialog.dismiss();
			}
		}).start();
		
		topbar_com_name.setTopBarClick(new ITopBarClick() {
			
			@Override
			public void onClickRightBut() {
				mSqlStr=" select * from SimpleEnterprise where e_companyName='"+mKey+"' ";
				SqlOperate<SimpleEnterprise> opetaterSimpleEnterprise=
						new SqlOperate<SimpleEnterprise>(HiddenCompanyChoiceActivity.this, SimpleEnterprise.class);
		        List<SimpleEnterprise> ls_SimpleEnterprise=opetaterSimpleEnterprise.SelectEntitysBySqlCondition(mSqlStr);
		        opetaterSimpleEnterprise.close();
		        
		        if(ls_SimpleEnterprise.size()==0)
		        {
		        	AlertDialog.Builder builder=new AlertDialog.Builder(HiddenCompanyChoiceActivity.this);  //�ȵõ�������  
		            builder.setTitle("��ʾ"); //���ñ���  
		            builder.setMessage("��ҵ��Ϣ�����ڣ��Ƿ��������?"); //��������  
		            builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() { //����ȷ����ť  
		                @Override  
		                public void onClick(DialogInterface dialog, int which) {  
		                    dialog.dismiss(); //�ر�dialog  
		                    Intent intent=new Intent(HiddenCompanyChoiceActivity.this, CompanyInfoAddActivity.class);
		    				intent.putExtra("isChoice", true);
		    				intent.putExtra("checkCompanyName", et_hidden_check.getText().toString());
		    				startActivityForResult(intent, 1);
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
		        
			}
			
			@Override
			public void onClickLeftBut() {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("companyName", "");
				setResult(1, intent);
				finish();
			}
		});
		topbar_com_name.setTitle("��ҵ��Ϣѡ��");
		topbar_com_name.setRightButText("�ύ");
		
		et_hidden_check.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				String key=et_hidden_check.getText().toString().trim();
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
		
	}
	
	private void selectCompanyByKey(String key) {
		// TODO Auto-generated method stub
		mKey=key;
		mSqlStr=" select * from SimpleEnterprise where e_companyName like '%"+key+"%' ";
		pageCount=0;
		initDate();
		mlistview.setAdapter(mAdapter);
	}
	
	private void initComponent() {
		// TODO Auto-generated method stub
		mlistview=(ClickLoadListview) findViewById(R.id.ListView_hidden_check);
		company_hidden_search=(CustomFAB) findViewById(R.id.company_hidden_search);
		topbar_com_name=(TopBarView) findViewById(R.id.topbar_com_name);
		company_hidden_search.setVisibility(View.GONE);
		et_hidden_check=(EditText) findViewById(R.id.et_hidden_check);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch(resultCode){
        case 1:
        	Intent intent = new Intent();
			intent.putExtra("companyName", data.getStringExtra("companyName"));
			setResult(1, intent);
			finish();
        	break;
        default:
        	break;
     }
	}
	
	private void initDate() {
		// TODO Auto-generated method stub
        SqlOperate<SimpleEnterprise> opetaterCompanyName=new SqlOperate<SimpleEnterprise>(this, SimpleEnterprise.class);
        mDatas=opetaterCompanyName.SelectOffsetEntitysBySqlCondition(mSqlStr, pangSize, pageCount);
        pageCount=1;
		opetaterCompanyName.close();
		
		if(mDatas.size()<pangSize)
		{
			mlistview.hindLoadView(false);
		}
		
		mAdapter= new SimpleEnterpriseNameAdapter(this, mDatas, R.layout.company_industry_item);
        
        mlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				try {
					if(mDatas.get(arg2)!=null)
					{
						SimpleEnterprise companyMin=mDatas.get(arg2);
						Intent intent = new Intent();
						intent.putExtra("companyName", companyMin.getE_companyName());
						setResult(1, intent);
						finish();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
				}
			}
		});
        
        mlistview.setIClickLoadListListener(new IClickLoadListListener() {
			
			@Override
			public void onLoad(Handler handler) {
				// TODO Auto-generated method stub
				 SqlOperate<SimpleEnterprise> opetaterCompanyName=new SqlOperate<SimpleEnterprise>(HiddenCompanyChoiceActivity.this, SimpleEnterprise.class);
				final List<SimpleEnterprise> data=opetaterCompanyName.SelectOffsetEntitysBySqlCondition(mSqlStr, pangSize, pageCount);
		        pageCount++;
				opetaterCompanyName.close();
				
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
	}
	
}





