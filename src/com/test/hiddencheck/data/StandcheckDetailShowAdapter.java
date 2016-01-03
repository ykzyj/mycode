package com.sunnyit.hiddencheck.data;

import java.util.List;

import com.sunnyit.R;
import com.sunnyit.R.color;
import com.sunnyit.common.adapter.AdapterUtil;
import com.sunnyit.common.adapter.ViewHolderUtil;
import com.sunnyit.enforcement.model.Standard_CK_Table_Item;
import com.sunnyit.hiddencheck.model.StandcheckDetail;

import android.content.Context;
import android.graphics.Color;

/**   
* @Title: DailyCheckAdapter.java 
* @Package com.sunnyit.enforcement.data 
* @Description: TODO
* @author yk
* @date 2015年9月11日 下午9:21:46 
* @version V1.0   
*/
public class StandcheckDetailShowAdapter extends AdapterUtil<StandcheckDetail> {
	
	public StandcheckDetailShowAdapter(Context context, List<StandcheckDetail> datas, int layoutId) {
		super(context, datas, layoutId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initShowDate(final ViewHolderUtil viewHolder, final StandcheckDetail t, int position) {
		viewHolder.setText(R.id.txt_item_description, t.getH_description());
		
		if(t.getSc_isPass()==null||t.getSc_isPass().equals(""))
		{
			viewHolder.setText(R.id.txt_item_isQualified, "未添加");
			viewHolder.setTextColor(R.id.txt_item_isQualified,  Color.rgb(0, 172, 255));
			viewHolder.setText(R.id.txt_item_repairType, "未添加");
			viewHolder.setTextColor(R.id.txt_item_repairType,  Color.rgb(0, 172, 255));
			viewHolder.setText(R.id.txt_item_repairLimit, "未添加");
			viewHolder.setTextColor(R.id.txt_item_repairLimit,  Color.rgb(0, 172, 255));
			viewHolder.setText(R.id.txt_item_repairState, "未添加");
			viewHolder.setTextColor(R.id.txt_item_repairState,  Color.rgb(0, 172, 255));
		}
		else
		{
			if(t.getSc_isPass().equals("yes"))
			{
				viewHolder.setText(R.id.txt_item_isQualified, "合格");
				viewHolder.setTextColor(R.id.txt_item_isQualified, Color.GREEN);
				viewHolder.setText(R.id.txt_item_repairType, "无");
				viewHolder.setTextColor(R.id.txt_item_repairType,  Color.rgb(0, 172, 255));
				viewHolder.setText(R.id.txt_item_repairLimit, "无");
				viewHolder.setTextColor(R.id.txt_item_repairLimit,  Color.rgb(0, 172, 255));
				viewHolder.setText(R.id.txt_item_repairState, "无需整改");
				viewHolder.setTextColor(R.id.txt_item_repairState, Color.GREEN);
			}
			else
			{
				viewHolder.setText(R.id.txt_item_isQualified, "不合格");
				viewHolder.setTextColor(R.id.txt_item_isQualified, Color.RED);
				
				if(t.getSc_type()==null||t.getSc_type().equals(""))
				{
					viewHolder.setText(R.id.txt_item_repairType, "未添加");
					viewHolder.setTextColor(R.id.txt_item_repairType, Color.rgb(253, 113, 0));
					viewHolder.setText(R.id.txt_item_repairLimit, "未添加");
					viewHolder.setTextColor(R.id.txt_item_repairLimit, Color.rgb(253, 113, 0));
					viewHolder.setText(R.id.txt_item_repairState, "未添加");
					viewHolder.setTextColor(R.id.txt_item_repairState, Color.rgb(253, 113, 0));
				}
				else
				{
					if(t.getSc_type().equals("L"))
					{
						viewHolder.setText(R.id.txt_item_repairType, "限期整改");
						viewHolder.setTextColor(R.id.txt_item_repairType, Color.rgb(253, 113, 0));
						viewHolder.setText(R.id.txt_item_repairLimit, t.getSc_deadline());
						viewHolder.setTextColor(R.id.txt_item_repairLimit, Color.rgb(253, 113, 0));
					}
					else
					{
						viewHolder.setText(R.id.txt_item_repairType, "立即整改");
						viewHolder.setTextColor(R.id.txt_item_repairType, Color.RED);
						viewHolder.setText(R.id.txt_item_repairLimit, "无");
					}
					
					if(t.getSc_state()==null||t.getSc_state().equals(""))
					{
						viewHolder.setText(R.id.txt_item_repairState, "未添加");
						viewHolder.setTextColor(R.id.txt_item_repairState, Color.rgb(253, 113, 0));
					}
					else
					{
						if((t.getSc_type()==null||t.getSc_type().equals(""))
								&&(t.getSc_state()==null||t.getSc_state().equals("")))
						{
							viewHolder.setText(R.id.txt_item_repairState, "未添加");
							viewHolder.setTextColor(R.id.txt_item_repairState, Color.rgb(253, 113, 0));
						}
						else
						{
							switch (t.getSc_state()) {
							case "noneed":
								viewHolder.setText(R.id.txt_item_repairState, "无需整改");
								viewHolder.setTextColor(R.id.txt_item_repairState, Color.GREEN);
								break;
							case "noyet":
								viewHolder.setText(R.id.txt_item_repairState, "未整改");
								viewHolder.setTextColor(R.id.txt_item_repairState, Color.RED);
								break;
							case "finish":
								viewHolder.setText(R.id.txt_item_repairState, "整改完成");
								viewHolder.setTextColor(R.id.txt_item_repairState, Color.GREEN);
								break;
							default:
								viewHolder.setText(R.id.txt_item_repairState, "未添加");
								viewHolder.setTextColor(R.id.txt_item_repairState, Color.rgb(253, 113, 0));
								break;
							}
							
						}
					}
				}
				
			}
			
		}
		
	}
	
}


