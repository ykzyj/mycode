package com.sunnyit.enforcement.data;

import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.adapter.AdapterUtil;
import com.sunnyit.common.adapter.ViewHolderUtil;
import com.sunnyit.enforcement.model.DailyCheck;
import com.sunnyit.hiddencheck.data.SelfCheckAdapter.onRightItemClickListener;
import com.sunnyit.hiddencheck.model.SelfCheck;

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
public class DailyCheckShowAdapter extends AdapterUtil<DailyCheck> {

	private Context mContext;

	public DailyCheckShowAdapter(Context context, List<DailyCheck> datas, int layoutId) {
		super(context, datas, layoutId);
		// TODO Auto-generated constructor stub
		mContext=context;
	}

	@Override
	public void initShowDate(ViewHolderUtil viewHolder, final DailyCheck t, int position) {
		// TODO Auto-generated method stub
		if(t!=null)
		{
			viewHolder.setText(R.id.txt_ck_checkeddepartment, t.getCk_checkeddepartment());
			if(t.getHaveopinion().equals("有"))
			{
				viewHolder.setText(R.id.txt_haveopinion, "不合格");
				viewHolder.setTextColor(R.id.txt_haveopinion, Color.RED);
				switch (t.getCk_state()) {
				case "CKING":
					viewHolder.setText(R.id.txt_ck_state, "检查中");
					viewHolder.setTextColor(R.id.txt_ck_state, Color.rgb(119, 34, 129));
					break;
				case "NONEED":
					viewHolder.setText(R.id.txt_ck_state, "无需整改");
					viewHolder.setTextColor(R.id.txt_ck_state, Color.GREEN);
					break;
				case "NOTYET":
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
					viewHolder.setTextColor(R.id.txt_ck_state, Color.GRAY);
					break;
				case "DESTORY":
					viewHolder.setText(R.id.txt_ck_state, "已销号");
					viewHolder.setTextColor(R.id.txt_ck_state, Color.GRAY);
					break;
				default:
					viewHolder.setText(R.id.txt_ck_state, "未知状态");
					break;
				}
			}
			else
			{
				viewHolder.setText(R.id.txt_haveopinion, "合格");
				viewHolder.setTextColor(R.id.txt_haveopinion, Color.GREEN);
				viewHolder.setText(R.id.txt_ck_state, "无需整改");
				viewHolder.setTextColor(R.id.txt_ck_state, Color.GREEN);
			}
			viewHolder.setText(R.id.txt_ck_time, t.getCk_time());
			viewHolder.setText(R.id.txt_ck_checkingdepartment, t.getCk_checkingdepartment());
			
		}
	}

}


