
package com.sunnyit.remind.data;

import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.adapter.AdapterUtil;
import com.sunnyit.common.adapter.ViewHolderUtil;
import com.sunnyit.remind.model.InformationRemind;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

public class InformationRemindAdapter extends AdapterUtil<InformationRemind> {

	private int mRightWidth = 0;

	public InformationRemindAdapter(Context context, List<InformationRemind> datas, int layoutId,int rightWidth) {
		super(context, datas, layoutId);
		// TODO Auto-generated constructor stub
		mRightWidth=rightWidth;
	}

	@Override
	public void initShowDate(ViewHolderUtil viewHolder, final InformationRemind t, int position) {
		// TODO Auto-generated method stub
		if(t!=null)
		{
			LinearLayout.LayoutParams lp1 = new LayoutParams(LayoutParams.MATCH_PARENT,
	                LayoutParams.MATCH_PARENT);
			viewHolder.setLayoutParams(R.id.item_left, lp1);
	        LinearLayout.LayoutParams lp2 = new LayoutParams(mRightWidth, LayoutParams.MATCH_PARENT);
	        viewHolder.setLayoutParams(R.id.item_right, lp2);
	        
	        if(t.getI_HaveLook().equals("0"))
	        {
	        	viewHolder.setImageResource(R.id.iv_icon, R.drawable.remind_notlook128);
	        }
	        else
	        {
	        	viewHolder.setImageResource(R.id.iv_icon, R.drawable.remind_havelook128);
	        }
			viewHolder.setText(R.id.tv_node_title, t.getI_Title());
			viewHolder.setText(R.id.tv_node_msg, t.getI_Content());
			viewHolder.setText(R.id.tv_node_time, t.getI_Time());
			
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
        void onRightItemClick(View v, InformationRemind t);
    }
	
}
