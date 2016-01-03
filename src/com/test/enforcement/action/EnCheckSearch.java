package com.sunnyit.enforcement.action;

import com.sunnyit.R;
import com.sunnyit.common.dialog.CustomDialog;
import com.sunnyit.common.view.ChoiceEditView;
import com.sunnyit.common.view.ChoiceEditView.onChoiceEditItemListener;
import com.sunnyit.law.action.LawChoiceActivity;
import com.sunnyit.law.action.PublicInfoActivity;

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
public class EnCheckSearch {
	
	public static ChoiceEditView et_en_ck_type;
	public static ChoiceEditView et_en_ck_state;
	
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
		cusdialog.setViewAndAlpha(R.layout.dialog_en_check_search,0);
		cusdialog.findViewById(R.id.but_dg_standinfo_search).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(et_en_ck_type.getText().toString().equals(""))
				{
					Toast.makeText(context, "������Ͳ���Ϊ��", Toast.LENGTH_SHORT).show();
				}
				else if(et_en_ck_type.getText().toString().equals(""))
				{
					Toast.makeText(context, "���״̬����Ϊ��", Toast.LENGTH_SHORT).show();
				}
				else
				{
					
					Intent intent = null;
					switch (et_en_ck_type.getText().toString()) {
					case "�ճ��������":
						intent=new Intent(context, DailyReviewListActivity.class);
						break;
					case "���ձ�׼���":
						intent=new Intent(context, HiddenStandCheckListActivity.class);
						break;
					case "ר����":
						intent=new Intent(context, SpecialReviewListActivity.class);
						break;
					default:
						break;
					}
					intent.putExtra("state", et_en_ck_state.getText().toString());
					((ReviewCheckChoiceActivity)context).startActivity(intent);
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
		
		et_en_ck_type = (ChoiceEditView)cusdialog.findViewById(R.id.et_en_ck_type);
		et_en_ck_state = (ChoiceEditView)cusdialog.findViewById(R.id.et_en_ck_state);
		
		et_en_ck_type.setDialogTitle("�������");
		et_en_ck_type.setChoiceEditItem(new onChoiceEditItemListener() {
			
			@Override
			public String[] onSetChoiceEditItem() {
				// TODO Auto-generated method stub
				String items[]={"�ճ��������","���ձ�׼���","ר����"};  
				et_en_ck_state.setText("");
				return items;
			}
		});
		
		et_en_ck_state.setDialogTitle("���״̬");
		et_en_ck_state.setChoiceEditItem(new onChoiceEditItemListener() {
			
			@Override
			public String[] onSetChoiceEditItem() {
				// TODO Auto-generated method stub
				if(et_en_ck_type.getText().toString().equals(""))
				{
					Toast.makeText(context, "����ѡ��������", Toast.LENGTH_SHORT).show();
				}
				else
				{
					switch (et_en_ck_type.getText().toString()) {
					case "�ճ��������":
					case "ר����":
						String items_ds[]={"�����","δ����","������","���븴��","������","�Ѹ���","����","����","��������"};
						return items_ds;
					case "���ձ�׼���":
						String items_st[]={"�����","δ����","������","δ����","������","���븴��","������","�Ѹ���","����","����","��������"};
						return items_st;
					default:
						break;
					}
				}
				return null;
			}
		});
	}
}


