package com.sunnyit.company.action;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.activity.ExpandListActivity;
import com.sunnyit.common.async.HttpClientAsyncPost;
import com.sunnyit.common.async.HttpClientAsyncPost.onHttpClientReturnListener;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.ChoiceEditView;
import com.sunnyit.common.view.ChoiceEditView.onChoiceEditItemListener;
import com.sunnyit.common.view.ChoiceEditView.onClickEditListener;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.company.model.SimpleEnterprise;
import com.sunnyit.synchronous.model.Industry;
import com.sunnyit.synchronous.model.UpInfo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import test.action.DialogShowActivity;

/**   
* @Title: WelcomeActivity.java 
* @Package com.sunnyit.system.action 
* @Description: TODO
* @author yk
* @date 2015年8月1日 上午9:47:36 
* @version V1.0   
*/
public class CompanyInfoAddActivity extends BaseActivity  {
	
	private TopBarView topbar_comInfo_add;
	private EditText et_e_companyName,et_e_companyAddress;
	private EditText et_e_legal_representative,et_e_contact_phone;
	private EditText et_e_businessCode,et_e_safe_person,et_e_safe_person_phone;
	
	private ChoiceEditView et_e_companyProperty,et_e_belongIndustry;
	private ChoiceEditView et_e_managerlayer,et_e_mangementMethod;
	private ChoiceEditView et_e_localName,et_e_departmentName;
	
	private LinearLayout lin_e_companyProperty,lin_e_managerlayer;
	private LinearLayout lin_e_departmentName,lin_e_localName;
	private LinearLayout lin_e_belongIndustry,lin_e_mangementMethod;
	
	private LinearLayout lin_e_legal_representative,lin_e_contact_phone;
	private LinearLayout lin_e_safe_person,lin_e_safe_person_phone;
	
	private TextView tv_e_companyName,tv_e_companyAddress,tv_e_businessCode;
	
	private boolean isChoice;
	
	private String industryName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.company_info_add);
		
		initView();
		Intent intent=getIntent();
		isChoice=intent.getBooleanExtra("isChoice", false);
		industryName=intent.getStringExtra("industryName");
		
		if(isChoice)
		{
			String checkCompanyName=intent.getStringExtra("checkCompanyName");
			if(checkCompanyName!=null)
			{
				et_e_companyName.setText(checkCompanyName);
			}
		}
		
		topbar_comInfo_add.setTopBarClick(new TopBarView.ITopBarClick() {
			@Override
			public void onClickRightBut() {
				addInfo();
			}
			
			@Override
			public void onClickLeftBut() {
				finish();
			}
		});
		
		if(industryName!=null)
		{
			et_e_belongIndustry.setText(industryName);
			ArchitectureChoice(); 
		}
		else
		{
			industryChoice(); 
		}
		
	}
	
	private void industryChoice() {
		SqlOperate<Industry> opetaterIn=new SqlOperate<Industry>(CompanyInfoAddActivity.this, Industry.class);
		List<Industry> ls_Industry=opetaterIn.SelectEntitys();
    	opetaterIn.close();
    	
    	HashSet<String> hs = new HashSet<String>();
    	for(int i=0;i<ls_Industry.size();i++)
    	{
    		if(!ls_Industry.get(i).getIndustryType().equals("其他"))
    		{
    			hs.add(ls_Industry.get(i).getIndustryType());
    		}
    	}
    	
    	final String[] IndustryStr = new String[hs.size()+1]; 
    	int in=0;
    	for(String s:hs)
    	{
    		IndustryStr[in]=s;
    		in++;
    	}
    	IndustryStr[in]="其他";
        AlertDialog.Builder builder=new AlertDialog.Builder(CompanyInfoAddActivity.this);  //先得到构造器  
        builder.setTitle("企业所属行业类型"); //设置标题  
        builder.setItems(IndustryStr,new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
                //Toast.makeText(CompanyInfoAddActivity.this, IndustryStr[which], Toast.LENGTH_SHORT).show();  
                et_e_belongIndustry.setText(IndustryStr[which]);
                if(IndustryStr[which].equals("建筑施工"))
                {
                	AlertDialog.Builder builder=new AlertDialog.Builder(CompanyInfoAddActivity.this);  //先得到构造器  
                    builder.setTitle("提示"); //设置标题  
                    builder.setMessage("是否为项目部?"); //设置内容  
                    builder.setPositiveButton("是", new DialogInterface.OnClickListener() { //设置确定按钮  
                        @Override  
                        public void onClick(DialogInterface dialog, int which) {  
                            dialog.dismiss(); //关闭dialog  
                            tv_e_companyName.setText("项目名称:");
                            et_e_companyName.setHint("请输入项目名称");
                    		tv_e_companyAddress.setText("项目地址:");
                    		et_e_companyAddress.setHint("请输入项目地址");
                    		tv_e_businessCode.setText("建筑施工许可证号:");
                    		et_e_businessCode.setHint("请输入许可证号");
                    		lin_e_legal_representative.setVisibility(View.GONE);
                    		lin_e_contact_phone.setVisibility(View.GONE);
                    		lin_e_safe_person.setVisibility(View.VISIBLE);
                    		lin_e_safe_person_phone.setVisibility(View.VISIBLE);
                        }  
                    });  
                    builder.setNegativeButton("否", new DialogInterface.OnClickListener() { //设置取消按钮  
                        @Override  
                        public void onClick(DialogInterface dialog, int which) {  
                            dialog.dismiss();  
                        }  
                    });  
                    //参数都设置完成了，创建并显示出来  
                    builder.create().show(); 
                }
            }  
        });  
        builder.setPositiveButton("返回",new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
                finish();
            }  
        });  
        builder.create().show();
	}

	private void ArchitectureChoice() {
		
		if(industryName!=null)
		{
			if(industryName.equals("建筑施工"))
	        {
	        	AlertDialog.Builder builder=new AlertDialog.Builder(CompanyInfoAddActivity.this);  //先得到构造器  
	            builder.setTitle("提示"); //设置标题  
	            builder.setMessage("是否为项目部?"); //设置内容  
	            builder.setPositiveButton("是", new DialogInterface.OnClickListener() { //设置确定按钮  
	                @Override  
	                public void onClick(DialogInterface dialog, int which) {  
	                    dialog.dismiss(); //关闭dialog  
	                    tv_e_companyName.setText("项目名称:");
	                    et_e_companyName.setHint("请输入项目名称");
	            		tv_e_companyAddress.setText("项目地址:");
	            		et_e_companyAddress.setHint("请输入项目地址");
	            		tv_e_businessCode.setText("建筑施工许可证号:");
	            		et_e_businessCode.setHint("请输入许可证号");
	            		lin_e_legal_representative.setVisibility(View.GONE);
	            		lin_e_contact_phone.setVisibility(View.GONE);
	            		lin_e_safe_person.setVisibility(View.VISIBLE);
	            		lin_e_safe_person_phone.setVisibility(View.VISIBLE);
	                }  
	            });  
	            builder.setNegativeButton("否", new DialogInterface.OnClickListener() { //设置取消按钮  
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
	
	private void addInfo() {
		if(et_e_companyName.getText().toString().trim().equals(""))
		{
			Toast.makeText(CompanyInfoAddActivity.this, "企业名称不能为空", Toast.LENGTH_SHORT).show();
		}
		/*else if(et_e_companyAddress.getText().toString().trim().equals(""))
		{
			Toast.makeText(CompanyInfoAddActivity.this, "企业地址不能为空", Toast.LENGTH_SHORT).show();
		}
		else if(et_e_legal_representative.getText().toString().trim().equals(""))
		{
			Toast.makeText(CompanyInfoAddActivity.this, "企业法人不能为空", Toast.LENGTH_SHORT).show();
		}
		else if(et_e_businessCode.getText().toString().trim().equals(""))
		{
			Toast.makeText(CompanyInfoAddActivity.this, "营业执照号不能为空", Toast.LENGTH_SHORT).show();
		}*/
		else
		{
			final SimpleEnterprise simp=new SimpleEnterprise();
			simp.setE_Id(UUID.randomUUID().toString());
			simp.setE_companyName(et_e_companyName.getText().toString());
			simp.setE_companyAddress(et_e_companyAddress.getText().toString());
			simp.setE_managerlayer(et_e_managerlayer.getText().toString());
			simp.setE_departmentId("");
			simp.setE_departmentName(et_e_departmentName.getText().toString());
			simp.setE_localId("");
			simp.setE_localName(et_e_localName.getText().toString());
			simp.setE_businessCode(et_e_businessCode.getText().toString());
			simp.setE_legal_representative(et_e_legal_representative.getText().toString());
			simp.setE_contact_phone(et_e_contact_phone.getText().toString());
			simp.setE_safe_person(et_e_safe_person.getText().toString());
			simp.setE_safe_person_phone(et_e_safe_person_phone.getText().toString());
			simp.setE_belongIndustry(et_e_belongIndustry.getText().toString());
			simp.setE_mangementMethod(et_e_mangementMethod.getText().toString());
			simp.setE_companyProperty(et_e_companyProperty.getText().toString());
			simp.setRemark("");
			
			String wifi=sp.getString("wifi", null);
			if(wifi.equals("yes"))
			{
				//本地保存
				SqlOperate<SimpleEnterprise> opetaterSimpleEnterprise=new SqlOperate<SimpleEnterprise>(this, SimpleEnterprise.class);
				opetaterSimpleEnterprise.saveContent(simp);
				opetaterSimpleEnterprise.close();
		    	
				UpInfo info=new UpInfo();
				info.setU_Id(UUID.randomUUID().toString());
				info.setInfoTable("SimpleEnterprise");
				info.setInfoId(simp.getE_Id());
				info.setOperateType("add");
				info.setRemark("");
				
		    	SqlOperate<UpInfo> opetaterUpInfo=new SqlOperate<UpInfo>(this, UpInfo.class);
		    	opetaterUpInfo.saveContent(info);
		    	opetaterUpInfo.close();
		    	
		    	Toast.makeText(CompanyInfoAddActivity.this, "数据本地保存成功！", Toast.LENGTH_SHORT).show();
		    	if(isChoice)
		    	{
		    		Intent intent = new Intent();
					intent.putExtra("companyName", simp.getE_companyName());
					setResult(1, intent);
					finish();
		    	}
		    	else
		    	{
		    		Intent intent = new Intent();
					intent.putExtra("SimpleEnterprise", simp);
					setResult(1, intent);
					finish();
		    	}
			}
			else
			{
				//直接上传
				String addEnUrl=getBaseUrl()+"/appWebSave/saveEnterprise";
				
				HttpClientAsyncPost post=new HttpClientAsyncPost(CompanyInfoAddActivity.this, simp);
	        	post.execute(addEnUrl);
	        	post.setHttpClientReturnListener(new onHttpClientReturnListener() {
					
					@Override
					public void returnPostExecute(boolean isSuccess, String msg, List<Object> lo) {
						// TODO Auto-generated method stub
						Toast.makeText(CompanyInfoAddActivity.this, msg, Toast.LENGTH_SHORT).show();
						if(isSuccess)
						{
							if(isChoice)
					    	{
					    		Intent intent = new Intent();
								intent.putExtra("companyName", simp.getE_companyName());
								setResult(1, intent);
								finish();
					    	}
					    	else
					    	{
					    		Intent intent = new Intent();
								intent.putExtra("SimpleEnterprise", simp);
								setResult(1, intent);
								finish();
					    	}
						}
					}
				});
			}
		}
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch(resultCode){
        case 1:
        	et_e_localName.setText(data.getStringExtra("localname"));
        	break;
        case 2:
        	et_e_departmentName.setText(data.getStringExtra("depname"));
        	break;
        case 3:
        	break;
     }
	}

	private void initView() {
		topbar_comInfo_add = (TopBarView)findViewById(R.id.topbar_comInfo_add);
		
		et_e_companyName = (EditText)findViewById(R.id.et_e_companyName);
		et_e_companyAddress = (EditText)findViewById(R.id.et_e_companyAddress);
		et_e_managerlayer = (ChoiceEditView)findViewById(R.id.et_e_managerlayer);
		et_e_belongIndustry = (ChoiceEditView)findViewById(R.id.et_e_belongIndustry);
		et_e_belongIndustry.setText("其他");
		et_e_legal_representative = (EditText)findViewById(R.id.et_e_legal_representative);
		et_e_contact_phone = (EditText)findViewById(R.id.et_e_contact_phone);
		et_e_businessCode = (EditText)findViewById(R.id.et_e_businessCode);
		et_e_safe_person = (EditText)findViewById(R.id.et_e_safe_person);
		et_e_safe_person_phone = (EditText)findViewById(R.id.et_e_safe_person_phone);
		
		et_e_departmentName = (ChoiceEditView)findViewById(R.id.et_e_departmentName);
		et_e_localName = (ChoiceEditView)findViewById(R.id.et_e_localName);
		et_e_mangementMethod = (ChoiceEditView)findViewById(R.id.et_e_mangementMethod);
		et_e_companyProperty = (ChoiceEditView)findViewById(R.id.et_e_companyProperty);
		
		lin_e_companyProperty = (LinearLayout)findViewById(R.id.lin_e_companyProperty);
		lin_e_managerlayer = (LinearLayout)findViewById(R.id.lin_e_managerlayer);
		lin_e_departmentName = (LinearLayout)findViewById(R.id.lin_e_departmentName);
		lin_e_localName = (LinearLayout)findViewById(R.id.lin_e_localName);
		lin_e_belongIndustry = (LinearLayout)findViewById(R.id.lin_e_belongIndustry);
		lin_e_mangementMethod = (LinearLayout)findViewById(R.id.lin_e_mangementMethod);
		lin_e_safe_person = (LinearLayout)findViewById(R.id.lin_e_safe_person);
		lin_e_safe_person_phone = (LinearLayout)findViewById(R.id.lin_e_safe_person_phone);
		
		lin_e_legal_representative = (LinearLayout)findViewById(R.id.lin_e_legal_representative);
		lin_e_contact_phone = (LinearLayout)findViewById(R.id.lin_e_contact_phone);
		tv_e_companyName = (TextView)findViewById(R.id.tv_e_companyName);
		tv_e_companyAddress = (TextView)findViewById(R.id.tv_e_companyAddress);
		tv_e_businessCode = (TextView)findViewById(R.id.tv_e_businessCode);
		
		lin_e_companyProperty.setVisibility(View.GONE);
		lin_e_managerlayer.setVisibility(View.GONE);
		lin_e_departmentName.setVisibility(View.GONE);
		lin_e_localName.setVisibility(View.GONE);
		lin_e_belongIndustry.setVisibility(View.GONE);
		lin_e_mangementMethod.setVisibility(View.GONE);
		lin_e_safe_person.setVisibility(View.GONE);
		lin_e_safe_person_phone.setVisibility(View.GONE);
		
		
		et_e_managerlayer.setDialogTitle("监管层级");
		et_e_managerlayer.setChoiceEditItem(new onChoiceEditItemListener() {
			
			@Override
			public String[] onSetChoiceEditItem() {
				// TODO Auto-generated method stub
				String items[]={"市级监管","区县监管"};  
				return items;
			}
		});
		
		
		et_e_companyProperty.setDialogTitle("企业性质");
		et_e_companyProperty.setChoiceEditItem(new onChoiceEditItemListener() {
			
			@Override
			public String[] onSetChoiceEditItem() {
				// TODO Auto-generated method stub
				String items[]={"国有","集体","私营","外资","股份制","个体","其它"};  
				return items;
			}
		});
		
		et_e_mangementMethod.setDialogTitle("生产经营方式");
		et_e_mangementMethod.setChoiceEditItem(new onChoiceEditItemListener() {
			
			@Override
			public String[] onSetChoiceEditItem() {
				// TODO Auto-generated method stub
				String items[]={"生产（制造）","批发经营","储存","使用","其它"};  
				return items;
			}
		});
		
		et_e_localName.setOnClickEditListener(new onClickEditListener() {
			
			@Override
			public void onClickEdit() {
				// TODO Auto-generated method stub
				Intent intent=new Intent(CompanyInfoAddActivity.this, ExpandListActivity.class);
				intent.putExtra("choiceflag", 0);
	       		startActivityForResult(intent, 0);
			}
		});
		
		et_e_departmentName.setOnClickEditListener(new onClickEditListener() {
			
			@Override
			public void onClickEdit() {
				// TODO Auto-generated method stub
				Intent intent=new Intent(CompanyInfoAddActivity.this, ExpandListActivity.class);
				intent.putExtra("choiceflag", 1);
	       		startActivityForResult(intent, 1);
			}
		});
		
		et_e_belongIndustry.setDialogTitle("行业类型");
		et_e_belongIndustry.setChoiceEditItem(new onChoiceEditItemListener() {
			@Override
			public String[] onSetChoiceEditItem() {
				// TODO Auto-generated method stub
				SqlOperate<Industry> opetaterIn=new SqlOperate<Industry>(CompanyInfoAddActivity.this, Industry.class);
				List<Industry> ls_Industry=opetaterIn.SelectEntitys();
		    	opetaterIn.close();
		    	
		    	HashSet<String> hs = new HashSet<String>();
		    	for(int i=0;i<ls_Industry.size();i++)
		    	{
		    		if(!ls_Industry.get(i).getIndustryType().equals("其他"))
		    		{
		    			hs.add(ls_Industry.get(i).getIndustryType());
		    		}
		    	}
		    	
		    	final String[] IndustryStr = new String[hs.size()+1]; 
		    	int in=0;
		    	for(String s:hs)
		    	{
		    		IndustryStr[in]=s;
		    		in++;
		    	}
		    	IndustryStr[in]="其他";
				return IndustryStr;
			}
		});
	}
	
}


