package com.sunnyit.law.action;

import com.sunnyit.R;
import com.sunnyit.common.dialog.CustomDialog;

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
public class StandSearch {
	
	public static EditText et_stand_info;
	public static ToggleButton ToggleBut_stand_model;
	
	/**
	* @author yk 
	* @date 2015年9月17日 下午4:45:42 
	* @Title: searchCompanyInfo 
	* @Description: 企业信息搜索
	* @param context    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public static void searchStandInfo(final Context context,final int activityflag) 
	{
		final CustomDialog cusdialog=new CustomDialog(context);
		cusdialog.setViewAndAlpha(R.layout.dialog_stand_search,0);
		if(activityflag==0)
		{
			cusdialog.setText(R.id.tv_dg_title, "隐患排查标准信息搜索");
		}
		else
		{
			cusdialog.setText(R.id.tv_dg_title, "执法检查标准信息搜索");
		}
		cusdialog.findViewById(R.id.but_dg_standinfo_search).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(et_stand_info.getText().toString().equals(""))
				{
					Toast.makeText(context, "搜索内容不能为空", Toast.LENGTH_SHORT).show();
				}
				else
				{
					if(ToggleBut_stand_model.isChecked())
					{
						
						char[] split_info= et_stand_info.getText().toString().toCharArray();
						StringBuffer str_info = new StringBuffer();
						for(char c:split_info)
						{
							str_info.append(c);
							str_info.append("%");
						}
						String search_info=str_info.substring(0, str_info.length()-1);
						
						Intent intent=new Intent(context, StandSearchFileActivity.class);
						intent.putExtra("activityflag", String.valueOf(activityflag));
						intent.putExtra("searchInfo", search_info);
						intent.putExtra("searchtitle", et_stand_info.getText().toString());
						((StandListActivity)context).startActivity(intent);
					}
					else
					{
						Intent intent=new Intent(context, StandSearchFileActivity.class);
						intent.putExtra("activityflag", String.valueOf(activityflag));
						intent.putExtra("searchInfo", et_stand_info.getText().toString());
						intent.putExtra("searchtitle", et_stand_info.getText().toString());
						((StandListActivity)context).startActivity(intent);
					}
					
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
		
		
		et_stand_info = (EditText)cusdialog.findViewById(R.id.et_stand_info);
		ToggleBut_stand_model = (ToggleButton)cusdialog.findViewById(R.id.ToggleBut_stand_model	);
	}
}


