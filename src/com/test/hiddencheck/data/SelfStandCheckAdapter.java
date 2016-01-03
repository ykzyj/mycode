
package com.sunnyit.hiddencheck.data;

import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.adapter.AdapterUtil;
import com.sunnyit.common.adapter.ViewHolderUtil;
import com.sunnyit.hiddencheck.model.SelfStandCheck;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

public class SelfStandCheckAdapter extends AdapterUtil<SelfStandCheck> {

	private int mRightWidth = 0;
	
	public SelfStandCheckAdapter(Context context, List<SelfStandCheck> datas, int layoutId,int rightWidth) {
		super(context, datas, layoutId);
		// TODO Auto-generated constructor stub
		mRightWidth=rightWidth;
	}

	@Override
	public void initShowDate(ViewHolderUtil viewHolder, final SelfStandCheck t, int position) {
		// TODO Auto-generated method stub
		
		LinearLayout.LayoutParams lp1 = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
		viewHolder.setLayoutParams(R.id.item_left, lp1);
        LinearLayout.LayoutParams lp2 = new LayoutParams(mRightWidth, LayoutParams.MATCH_PARENT);
        viewHolder.setLayoutParams(R.id.item_right, lp2);
		
		viewHolder.setImageResource(R.id.img_selfcheck_info,R.drawable.hiddentrouble160);
		viewHolder.setText(R.id.txt_hidden_companyName, t.getSc_companyName());
		//viewHolder.setText(R.id.txt_hidden_no, t.getSc_idd());
		if(t.getSc_deadline().equals(""))
		{
			viewHolder.setText(R.id.txt_sc_deadline, "无");
		}
		else
		{
			viewHolder.setText(R.id.txt_sc_deadline, t.getSc_deadline());
		}
		viewHolder.setText(R.id.txt_hidden_time, t.getSc_checkTime());
		viewHolder.setText(R.id.txt_hidden_inspector, t.getSc_checkPerson());
		
		switch (t.getSc_state()) {
		case "checking":
			viewHolder.setText(R.id.txt_hidden_state, "检查中");
			viewHolder.setTextColor(R.id.txt_hidden_state, Color.rgb(119, 34, 129));
			break;
		case "undeal":
			viewHolder.setText(R.id.txt_hidden_state, "未处理");
			viewHolder.setTextColor(R.id.txt_hidden_state, Color.rgb(119, 34, 129));
			break;
		case "dealing":
			viewHolder.setText(R.id.txt_hidden_state, "处理中");
			viewHolder.setTextColor(R.id.txt_hidden_state, Color.rgb(119, 34, 129));
			break;
		case "noyet":
			viewHolder.setText(R.id.txt_hidden_state, "未整改");
			viewHolder.setTextColor(R.id.txt_hidden_state, Color.RED);
			break;
		case "ing":
			viewHolder.setText(R.id.txt_hidden_state, "整改中");
			viewHolder.setTextColor(R.id.txt_hidden_state, Color.rgb(1, 19, 251));
			break;
		case "finish":
			viewHolder.setText(R.id.txt_hidden_state, "已整改");
			viewHolder.setTextColor(R.id.txt_hidden_state, Color.GREEN);
			break;
		case "noneed":
			viewHolder.setText(R.id.txt_hidden_state, "无需整改");
			viewHolder.setTextColor(R.id.txt_hidden_state, Color.GREEN);
			break;
		default:
			viewHolder.setText(R.id.txt_hidden_state, "未知状态");
			break;
		}
		
		RelativeLayout item_right=viewHolder.getView(R.id.item_right);
		item_right.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mListener != null) {
                    mListener.onRightItemClick(v, t);
                }
			}
		});
	}

	private onRightItemClickListener mListener = null;
    
    public void setOnRightItemClickListener(onRightItemClickListener listener){
    	mListener = listener;
    }

    public interface onRightItemClickListener {
        void onRightItemClick(View v, SelfStandCheck t);
    }
	

}
