package com.sunnyit.enforcement.data;

import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.adapter.AdapterUtil;
import com.sunnyit.common.adapter.ViewHolderUtil;
import com.sunnyit.enforcement.model.Standard_CK_Table;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.LinearLayout.LayoutParams;

/**   
* @Title: DailyCheckAdapter.java 
* @Package com.sunnyit.enforcement.data 
* @Description: TODO
* @author yk
* @date 2015年9月11日 下午9:21:46 
* @version V1.0   
*/
public class Standard_CK_TableShowAdapter extends AdapterUtil<Standard_CK_Table> {
	
	private int mRightWidth = 0;
	private Context mContext;

	public Standard_CK_TableShowAdapter(Context context, List<Standard_CK_Table> datas, int layoutId) {
		super(context, datas, layoutId);
		// TODO Auto-generated constructor stub
		mContext=context;
	}

	@Override
	public void initShowDate(ViewHolderUtil viewHolder, final Standard_CK_Table t, int position) {
		if(t!=null)
		{
	        //viewHolder.setImageResource(R.id.img_publishInfo,Integer.valueOf(t.getRemark()));
			viewHolder.setText(R.id.txt_ck_companyName, t.getCompanyName());
			viewHolder.setText(R.id.txt_ck_time, t.getCk_time());
			if(t.getIsExistDanger().equals("YES"))
			{
				viewHolder.setText(R.id.txt_ck_isExistDanger, "合格");
				viewHolder.setTextColor(R.id.txt_ck_isExistDanger, Color.GREEN);
				viewHolder.setText(R.id.txt_ck_deadLine, "无");
				viewHolder.setTextColor(R.id.txt_ck_deadLine, Color.GREEN);
			}
			else
			{
				viewHolder.setText(R.id.txt_ck_isExistDanger, "不合格");
				viewHolder.setTextColor(R.id.txt_ck_isExistDanger, Color.RED);
				if(t.getCk_deadLine()==null||t.getCk_deadLine().equals(""))
				{
					if(t.getCk_state().equals("CKING")||
							t.getCk_state().equals("CKEND")||
							t.getCk_state().equals("DOING"))
					{
						viewHolder.setText(R.id.txt_ck_deadLine, "无");
						viewHolder.setTextColor(R.id.txt_ck_deadLine, Color.GREEN);
					}
					else
					{
						viewHolder.setText(R.id.txt_ck_deadLine, "立即整改");
						viewHolder.setTextColor(R.id.txt_ck_deadLine, Color.RED);
					}
				}
				else
				{
					viewHolder.setText(R.id.txt_ck_deadLine,t.getCk_deadLine());
					viewHolder.setTextColor(R.id.txt_ck_deadLine, Color.rgb(254, 145, 3));
				}
			}
			
			switch (t.getCk_state()) {
			case "CKING":
				viewHolder.setText(R.id.txt_ck_state, "检查中");
				viewHolder.setTextColor(R.id.txt_ck_state, Color.rgb(119, 34, 129));
				break;
			case "CKEND":
				viewHolder.setText(R.id.txt_ck_state, "未处理");
				viewHolder.setTextColor(R.id.txt_ck_state, Color.rgb(119, 34, 129));
				break;
			case "DOING":
				viewHolder.setText(R.id.txt_ck_state, "处理中");
				viewHolder.setTextColor(R.id.txt_ck_state, Color.rgb(119, 34, 129));
				break;
			case "NONEED":
				viewHolder.setText(R.id.txt_ck_state, "无需整改");
				viewHolder.setTextColor(R.id.txt_ck_state, Color.GREEN);
				break;
			case "NOYET":
				viewHolder.setText(R.id.txt_ck_state, "未整改");
				viewHolder.setTextColor(R.id.txt_ck_state, Color.rgb(119, 34, 129));
				break;
			case "ING":
				viewHolder.setText(R.id.txt_ck_state, "整改中");
				viewHolder.setTextColor(R.id.txt_ck_state, Color.rgb(1, 19, 251));
				break;
			case "FINISH":
				viewHolder.setText(R.id.txt_ck_state, "申请复查");
				viewHolder.setTextColor(R.id.txt_ck_state, Color.rgb(119, 34, 129));
				break;
			case "REVIEWING":
				viewHolder.setText(R.id.txt_ck_state, "复查中");
				viewHolder.setTextColor(R.id.txt_ck_state, Color.rgb(119, 34, 129));
				break;
			case "REVIEWED":
				viewHolder.setText(R.id.txt_ck_state, "已复查");
				viewHolder.setTextColor(R.id.txt_ck_state, Color.rgb(119, 34, 129));
				break;
			case "RECORDED":
				viewHolder.setText(R.id.txt_ck_state, "已立案");
				viewHolder.setTextColor(R.id.txt_ck_state, Color.rgb(119, 34, 129));
				break;
			case "DESTORY":
				viewHolder.setText(R.id.txt_ck_state, "已销号");
				viewHolder.setTextColor(R.id.txt_ck_state, Color.GRAY);
				break;
			default:
				viewHolder.setText(R.id.txt_ck_state, "");
				break;
			}
			
		}
	}
	
}


