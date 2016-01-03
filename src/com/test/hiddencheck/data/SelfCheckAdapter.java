
package com.sunnyit.hiddencheck.data;

import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.adapter.AdapterUtil;
import com.sunnyit.common.adapter.ViewHolderUtil;
import com.sunnyit.hiddencheck.model.SelfCheck;
import com.sunnyit.hiddencheck.model.SelfStandCheck;
import com.sunnyit.menu.data.SwipeAdapter.onRightItemClickListener;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SelfCheckAdapter extends AdapterUtil<SelfCheck> {

	private int mRightWidth = 0;
	private Context mContext;

	public SelfCheckAdapter(Context context, List<SelfCheck> datas, int layoutId,int rightWidth) {
		super(context, datas, layoutId);
		// TODO Auto-generated constructor stub
		mRightWidth=rightWidth;
		mContext=context;
	}

	@Override
	public void initShowDate(ViewHolderUtil viewHolder, final SelfCheck t, int position) {
		// TODO Auto-generated method stub
		if(t!=null)
		{
			LinearLayout.LayoutParams lp1 = new LayoutParams(LayoutParams.MATCH_PARENT,
	                LayoutParams.MATCH_PARENT);
			viewHolder.setLayoutParams(R.id.item_left, lp1);
	        LinearLayout.LayoutParams lp2 = new LayoutParams(mRightWidth, LayoutParams.MATCH_PARENT);
	        viewHolder.setLayoutParams(R.id.item_right, lp2);
	        
	        viewHolder.setImageResource(R.id.img_selfcheck_info,R.drawable.hiddentrouble160);
			viewHolder.setText(R.id.txt_hidden_companyName, t.getSc_companyName());
			//viewHolder.setText(R.id.txt_hidden_no, t.getSc_id());
			if(t.getSc_deadline().equals(""))
			{
				viewHolder.setText(R.id.txt_sc_deadline, "无");
			}
			else
			{
				viewHolder.setText(R.id.txt_sc_deadline, t.getSc_deadline());
			}
			viewHolder.setText(R.id.txt_hidden_time, t.getSc_checkTime());
			viewHolder.setText(R.id.txt_hidden_inspector, t.getSc_inspector());
			
			switch (t.getSc_state()) {
			case "ING":
				viewHolder.setText(R.id.txt_hidden_state, "整改中");
				viewHolder.setTextColor(R.id.txt_hidden_state, Color.RED);
				break;
			case "NONEED":
				viewHolder.setText(R.id.txt_hidden_state, "无需整改");
				viewHolder.setTextColor(R.id.txt_hidden_state, Color.GREEN);
				break;
			case "FINISH":
				viewHolder.setText(R.id.txt_hidden_state, "整改完成");
				viewHolder.setTextColor(R.id.txt_hidden_state, Color.rgb(254, 145, 3));
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
	}
	
	private onRightItemClickListener mListener = null;
	    
    public void setOnRightItemClickListener(onRightItemClickListener listener){
    	mListener = listener;
    }

    public interface onRightItemClickListener {
        void onRightItemClick(View v, SelfCheck t);
    }
	
}
