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
* @date 2015年9月17日 下午4:44:10 
* @version V1.0   
*/
public class EnCheckSearch {
	
	public static ChoiceEditView et_en_ck_type;
	public static ChoiceEditView et_en_ck_state;
	
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
		cusdialog.setViewAndAlpha(R.layout.dialog_en_check_search,0);
		cusdialog.findViewById(R.id.but_dg_standinfo_search).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(et_en_ck_type.getText().toString().equals(""))
				{
					Toast.makeText(context, "检查类型不能为空", Toast.LENGTH_SHORT).show();
				}
				else if(et_en_ck_type.getText().toString().equals(""))
				{
					Toast.makeText(context, "检查状态不能为空", Toast.LENGTH_SHORT).show();
				}
				else
				{
					
					Intent intent = null;
					switch (et_en_ck_type.getText().toString()) {
					case "日常隐患检查":
						intent=new Intent(context, DailyReviewListActivity.class);
						break;
					case "对照标准检查":
						intent=new Intent(context, HiddenStandCheckListActivity.class);
						break;
					case "专项检查":
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
		
		et_en_ck_type.setDialogTitle("检查类型");
		et_en_ck_type.setChoiceEditItem(new onChoiceEditItemListener() {
			
			@Override
			public String[] onSetChoiceEditItem() {
				// TODO Auto-generated method stub
				String items[]={"日常隐患检查","对照标准检查","专项检查"};  
				et_en_ck_state.setText("");
				return items;
			}
		});
		
		et_en_ck_state.setDialogTitle("检查状态");
		et_en_ck_state.setChoiceEditItem(new onChoiceEditItemListener() {
			
			@Override
			public String[] onSetChoiceEditItem() {
				// TODO Auto-generated method stub
				if(et_en_ck_type.getText().toString().equals(""))
				{
					Toast.makeText(context, "请先选择检查类型", Toast.LENGTH_SHORT).show();
				}
				else
				{
					switch (et_en_ck_type.getText().toString()) {
					case "日常隐患检查":
					case "专项检查":
						String items_ds[]={"检查中","未整改","整改中","申请复查","复查中","已复查","立案","销号","无需整改"};
						return items_ds;
					case "对照标准检查":
						String items_st[]={"检查中","未处理","处理中","未整改","整改中","申请复查","复查中","已复查","立案","销号","无需整改"};
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


