package com.sunnyit.law.action;

import com.sunnyit.R;
import com.sunnyit.common.dialog.CustomDialog;
import com.sunnyit.common.view.ChoiceEditView;
import com.sunnyit.common.view.ChoiceEditView.onChoiceEditItemListener;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

/**   
* @Title: CompanySearch.java 
* @Package com.sunnyit.company.action 
* @Description: TODO
* @author yk
* @date 2015年9月17日 下午4:44:10 
* @version V1.0   
*/
public class LawSearch {
	
	public static ChoiceEditView et_law_keyword;
	public static ChoiceEditView et_law_publish;
	public static EditText et_law_content;
	
	/**
	* @author yk 
	* @date 2015年9月17日 下午4:45:42 
	* @Title: searchCompanyInfo 
	* @Description: 企业信息搜索
	* @param context    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public static void searchStandInfo(final Context context) 
	{
		final CustomDialog cusdialog=new CustomDialog(context);
		cusdialog.setViewAndAlpha(R.layout.dialog_law_search,0);
		cusdialog.findViewById(R.id.but_dg_standinfo_search).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(et_law_content.getText().toString().trim().equals(""))
				{
					Toast.makeText(context, "搜索内容不能为空", Toast.LENGTH_SHORT).show();
				}
				else
				{
					int imgId;
					switch (et_law_keyword.getText().toString().trim()) {
					case "法律":
						imgId=R.drawable.country_law150;
						break;
					case "行业规章":
						imgId=R.drawable.industry_law150;
						break;
					case "部门法规":
						imgId=R.drawable.department_law150;
						break;
					case "地方性法规":
						imgId=R.drawable.local_law150;
						break;
					case "规范性文件":
						imgId=R.drawable.file_law150;
						break;
					case "行业标准":
						imgId=R.drawable.industry_stand150;
						break;
					default:
						imgId=R.drawable.about_our;
						break;
					}
					
					Intent intent=new Intent(context, PublicInfoActivity.class);
					intent.putExtra("law", R.drawable.about_our);
					intent.putExtra("title", et_law_publish.getText().toString().trim());
					intent.putExtra("content", et_law_content.getText().toString().trim());
					((LawChoiceActivity)context).startActivity(intent);
					
					cusdialog.dismiss();
				}
			}
		});
		cusdialog.findViewById(R.id.but_dg_standinfo_cancel).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cusdialog.dismiss();
			}
		});
		cusdialog.show();
		
		et_law_keyword = (ChoiceEditView)cusdialog.findViewById(R.id.et_law_keyword);
		et_law_publish = (ChoiceEditView)cusdialog.findViewById(R.id.et_law_publish);
		et_law_content = (EditText)cusdialog.findViewById(R.id.et_law_content);
		
		et_law_keyword.setDialogTitle("检索类型");
		et_law_keyword.setChoiceEditItem(new onChoiceEditItemListener() {
			
			@Override
			public String[] onSetChoiceEditItem() {
				// TODO Auto-generated method stub
				String items[]={"全部","法律","行业规章","部门法规","地方性法规","规范性文件","行业标准"};  
				return items;
			}
		});
		
		et_law_publish.setDialogTitle("检索范围");
		et_law_publish.setChoiceEditItem(new onChoiceEditItemListener() {
			
			@Override
			public String[] onSetChoiceEditItem() {
				// TODO Auto-generated method stub
				String items[]={"标题","内容"};  
				return items;
			}
		});
		
	}
}


