package com.sunnyit.enforcement.data;

import java.util.List;

import com.sunnyit.R;
import com.sunnyit.R.color;
import com.sunnyit.common.adapter.AdapterUtil;
import com.sunnyit.common.adapter.ViewHolderUtil;
import com.sunnyit.enforcement.model.Standard_CK_Table_Item;

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
public class Standard_CK_Table_ItemShowAdapter extends AdapterUtil<Standard_CK_Table_Item> {
	
	public Standard_CK_Table_ItemShowAdapter(Context context, List<Standard_CK_Table_Item> datas, int layoutId) {
		super(context, datas, layoutId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initShowDate(final ViewHolderUtil viewHolder, final Standard_CK_Table_Item t, int position) {
		viewHolder.setText(R.id.txt_item_description, t.getItem_description());
		
		if(t.getItem_isQualified()==null||t.getItem_isQualified().equals(""))
		{
			viewHolder.setText(R.id.txt_item_isQualified, "未添加");
			viewHolder.setText(R.id.txt_item_repairType, "未添加");
			viewHolder.setText(R.id.txt_item_repairLimit, "未添加");
			viewHolder.setText(R.id.txt_item_repairState, "未添加");
		}
		else
		{
			if(t.getItem_isQualified().equals("YES"))
			{
				viewHolder.setText(R.id.txt_item_isQualified, "合格");
				viewHolder.setTextColor(R.id.txt_item_isQualified, Color.GREEN);
				viewHolder.setText(R.id.txt_item_repairType, "无");
				viewHolder.setText(R.id.txt_item_repairLimit, "无");
				viewHolder.setText(R.id.txt_item_repairState, "无需整改");
				viewHolder.setTextColor(R.id.txt_item_repairState, Color.GREEN);
			}
			else
			{
				viewHolder.setText(R.id.txt_item_isQualified, "不合格");
				viewHolder.setTextColor(R.id.txt_item_isQualified, Color.RED);
				
				if(t.getItem_repairType()==null||t.getItem_repairType().equals(""))
				{
					viewHolder.setText(R.id.txt_item_repairType, "未添加");
					viewHolder.setText(R.id.txt_item_repairLimit, "未添加");
					viewHolder.setText(R.id.txt_item_repairState, "未添加");
				}
				else
				{
					if(t.getItem_repairType().equals("L"))
					{
						viewHolder.setText(R.id.txt_item_repairType, "限期整改");
						viewHolder.setTextColor(R.id.txt_item_repairType, Color.rgb(253, 113, 0));
						viewHolder.setText(R.id.txt_item_repairLimit, t.getItem_repairLimit());
						viewHolder.setTextColor(R.id.txt_item_repairLimit, Color.rgb(253, 113, 0));
					}
					else
					{
						viewHolder.setText(R.id.txt_item_repairType, "立即整改");
						viewHolder.setTextColor(R.id.txt_item_repairType, Color.RED);
						viewHolder.setText(R.id.txt_item_repairLimit, "无");
					}
					
					if(t.getItem_repairState()==null||t.getItem_repairState().equals(""))
					{
						viewHolder.setText(R.id.txt_item_repairState, "未添加");
					}
					else
					{
						if((t.getItem_repairType()==null||t.getItem_repairType().equals(""))
								&&(t.getItem_repairState()==null||t.getItem_repairState().equals("")))
						{
							viewHolder.setText(R.id.txt_item_repairState, "未添加");
						}
						else
						{
							switch (t.getItem_repairState()) {
							case "NONEED":
								viewHolder.setText(R.id.txt_item_repairState, "无需整改");
								viewHolder.setTextColor(R.id.txt_item_repairState, Color.GREEN);
								break;
							case "NOYET":
								viewHolder.setText(R.id.txt_item_repairState, "未整改");
								viewHolder.setTextColor(R.id.txt_item_repairState, Color.RED);
								break;
							case "FINISH":
								viewHolder.setText(R.id.txt_item_repairState, "整改完成");
								viewHolder.setTextColor(R.id.txt_item_repairState, Color.GREEN);
								break;
							default:
								viewHolder.setText(R.id.txt_item_repairState, "未添加");
								break;
							}
						}
					}
				}
				
			}
			
		}
		
	}
	
}


