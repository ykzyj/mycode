package com.sunnyit.company.action;

import java.util.HashSet;
import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.activity.ExpandListActivity;
import com.sunnyit.common.dialog.CustomDialog;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.ChoiceEditView;
import com.sunnyit.common.view.ChoiceEditView.onChoiceEditItemListener;
import com.sunnyit.common.view.ChoiceEditView.onClickEditListener;
import com.sunnyit.enforcement.action.YearMounthCheckActivity;
import com.sunnyit.hiddencheck.action.HiddenTabSortDatilActivity;
import com.sunnyit.synchronous.model.Industry;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

/**   
* @Title: CompanySearch.java 
* @Package com.sunnyit.company.action 
* @Description: TODO
* @author yk
* @date 2015年9月17日 下午4:44:10 
* @version V1.0   
*/
public class CompanySearch {
	
	public static EditText et_search_companyName;
	public static ChoiceEditView et_search_companyProperty;
	public static ChoiceEditView et_search_belongIndustry;
	public static ChoiceEditView et_search_managerlayer;
	public static ChoiceEditView et_search_mangementMethod;
	public static ChoiceEditView et_search_localName;
	public static ChoiceEditView et_search_departmentName;
	
	/**
	* @author yk 
	* @date 2015年9月17日 下午4:45:42 
	* @Title: searchCompanyInfo 
	* @Description: 企业信息搜索
	* @param context    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public static void searchCompanyInfo(final Context context,final int activityflag) 
	{
		final CustomDialog cusdialog=new CustomDialog(context);
		cusdialog.setViewAndAlpha(R.layout.dialog_company_search,0);
		cusdialog.findViewById(R.id.but_dg_cominfo_search).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String companyName=et_search_companyName.getText().toString();
				String companyProperty=et_search_companyProperty.getText().toString();
				String belongIndustry=et_search_belongIndustry.getText().toString();
				String managerlayer=et_search_managerlayer.getText().toString();
				String mangementMethod=et_search_mangementMethod.getText().toString();
				String localName=et_search_localName.getText().toString();
				String departmentName=et_search_departmentName.getText().toString();
				
				if(companyName.trim().equals("")&&companyProperty.trim().equals("")&&belongIndustry.trim().equals("")&&
						managerlayer.trim().equals("")&&mangementMethod.trim().equals("")&&localName.trim().equals("")&&
						departmentName.trim().equals(""))
				{
					Toast.makeText(context, "至少填写一个搜索条件", Toast.LENGTH_SHORT).show();
				}
				else
				{
					if(!companyName.trim().equals(""))
					{
						companyName=" e_companyName like '%"+companyName+"%' and";
					}
					if(!companyProperty.trim().equals(""))
					{
						companyProperty=" e_companyProperty like '%"+companyProperty+"%' and";
					}
					if(!belongIndustry.trim().equals(""))
					{
						belongIndustry=" e_belongIndustry like '%"+belongIndustry+"%' and";
					}
					if(!managerlayer.trim().equals(""))
					{
						managerlayer=" e_managerlayer like '%"+managerlayer+"%' and";
					}
					if(!mangementMethod.trim().equals(""))
					{
						mangementMethod=" e_mangementMethod like '%"+mangementMethod+"%' and";
					}
					if(!localName.trim().equals(""))
					{
						localName=" e_localName like '%"+localName+"%' and";
					}
					if(!departmentName.trim().equals(""))
					{
						departmentName=" e_departmentName like '%"+departmentName+"%' and";
					}
					
					String conditionStr=" where "+companyName+companyProperty+belongIndustry+managerlayer+
							mangementMethod+localName+departmentName;
					conditionStr=conditionStr.substring(0, conditionStr.length()-3);
					
					conditionStr=conditionStr+" order by e_Id";
					
					cusdialog.dismiss();
					Intent intent=new Intent(context, CompanyListActivity.class);
					intent.putExtra("conditionStr", conditionStr);
					if(activityflag==0)
					{
						((CompanyIndustryActivity)context).startActivity(intent);
					}
					else if(activityflag==1)
					{
						((HiddenTabSortDatilActivity)context).startActivity(intent);
					}
					else if(activityflag==2)
					{
						((YearMounthCheckActivity)context).startActivity(intent);
					}
					
				}
				
			}
		});
		cusdialog.findViewById(R.id.but_dg_cominfo_cancel).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cusdialog.dismiss();
			}
		});
		cusdialog.show();
		
		
		et_search_companyName = (EditText)cusdialog.findViewById(R.id.et_e_companyName);
		et_search_managerlayer = (ChoiceEditView)cusdialog.findViewById(R.id.et_e_managerlayer);
		et_search_belongIndustry = (ChoiceEditView)cusdialog.findViewById(R.id.et_e_belongIndustry);
		
		et_search_departmentName = (ChoiceEditView)cusdialog.findViewById(R.id.et_e_departmentName);
		et_search_localName = (ChoiceEditView)cusdialog.findViewById(R.id.et_e_localName);
		et_search_mangementMethod = (ChoiceEditView)cusdialog.findViewById(R.id.et_e_mangementMethod);
		et_search_companyProperty = (ChoiceEditView)cusdialog.findViewById(R.id.et_e_companyProperty);
		
		et_search_managerlayer.setDialogTitle("监管层级");
		et_search_managerlayer.setChoiceEditItem(new onChoiceEditItemListener() {
			
			@Override
			public String[] onSetChoiceEditItem() {
				// TODO Auto-generated method stub
				String items[]={"市级监管","区县监管"};  
				return items;
			}
		});
		
		
		et_search_companyProperty.setDialogTitle("企业性质");
		et_search_companyProperty.setChoiceEditItem(new onChoiceEditItemListener() {
			
			@Override
			public String[] onSetChoiceEditItem() {
				// TODO Auto-generated method stub
				String items[]={"国有","集体","私营","外资","股份制","个体","其它"};  
				return items;
			}
		});
		
		et_search_mangementMethod.setDialogTitle("生产经营方式");
		et_search_mangementMethod.setChoiceEditItem(new onChoiceEditItemListener() {
			
			@Override
			public String[] onSetChoiceEditItem() {
				// TODO Auto-generated method stub
				String items[]={"生产（制造）","批发经营","储存","使用","其它"};  
				return items;
			}
		});
		
		et_search_localName.setOnClickEditListener(new onClickEditListener() {
			
			@Override
			public void onClickEdit() {
				// TODO Auto-generated method stub
				Intent intent=new Intent(context, ExpandListActivity.class);
				intent.putExtra("choiceflag", 0);
				if(activityflag==0)
				{
					((CompanyIndustryActivity)context).startActivityForResult(intent, 0);
				}
				else if(activityflag==1)
				{
					((HiddenTabSortDatilActivity)context).startActivityForResult(intent, 0);
				}
				else if(activityflag==2)
				{
					((YearMounthCheckActivity)context).startActivityForResult(intent, 0);
				}
			}
		});
		
		et_search_departmentName.setOnClickEditListener(new onClickEditListener() {
			
			@Override
			public void onClickEdit() {
				// TODO Auto-generated method stub
				Intent intent=new Intent(context, ExpandListActivity.class);
				intent.putExtra("choiceflag", 1);
				if(activityflag==0)
				{
					((CompanyIndustryActivity)context).startActivityForResult(intent, 0);
				}
				else if(activityflag==1)
				{
					((HiddenTabSortDatilActivity)context).startActivityForResult(intent, 0);
				}
				else if(activityflag==2)
				{
					((YearMounthCheckActivity)context).startActivityForResult(intent, 0);
				}
			}
		});
		
		et_search_belongIndustry.setDialogTitle("行业类型");
		et_search_belongIndustry.setChoiceEditItem(new onChoiceEditItemListener() {
			
			@Override
			public String[] onSetChoiceEditItem() {
				// TODO Auto-generated method stub
				
				SqlOperate<Industry> opetaterIn=new SqlOperate<Industry>(context, Industry.class);
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


