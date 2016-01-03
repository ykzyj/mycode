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
* @date 2015��9��17�� ����4:44:10 
* @version V1.0   
*/
public class LawSearch {
	
	public static ChoiceEditView et_law_keyword;
	public static ChoiceEditView et_law_publish;
	public static EditText et_law_content;
	
	/**
	* @author yk 
	* @date 2015��9��17�� ����4:45:42 
	* @Title: searchCompanyInfo 
	* @Description: ��ҵ��Ϣ����
	* @param context    �趨�ļ� 
	* @return void    �������� 
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
					Toast.makeText(context, "�������ݲ���Ϊ��", Toast.LENGTH_SHORT).show();
				}
				else
				{
					int imgId;
					switch (et_law_keyword.getText().toString().trim()) {
					case "����":
						imgId=R.drawable.country_law150;
						break;
					case "��ҵ����":
						imgId=R.drawable.industry_law150;
						break;
					case "���ŷ���":
						imgId=R.drawable.department_law150;
						break;
					case "�ط��Է���":
						imgId=R.drawable.local_law150;
						break;
					case "�淶���ļ�":
						imgId=R.drawable.file_law150;
						break;
					case "��ҵ��׼":
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
		
		et_law_keyword.setDialogTitle("��������");
		et_law_keyword.setChoiceEditItem(new onChoiceEditItemListener() {
			
			@Override
			public String[] onSetChoiceEditItem() {
				// TODO Auto-generated method stub
				String items[]={"ȫ��","����","��ҵ����","���ŷ���","�ط��Է���","�淶���ļ�","��ҵ��׼"};  
				return items;
			}
		});
		
		et_law_publish.setDialogTitle("������Χ");
		et_law_publish.setChoiceEditItem(new onChoiceEditItemListener() {
			
			@Override
			public String[] onSetChoiceEditItem() {
				// TODO Auto-generated method stub
				String items[]={"����","����"};  
				return items;
			}
		});
		
	}
}


