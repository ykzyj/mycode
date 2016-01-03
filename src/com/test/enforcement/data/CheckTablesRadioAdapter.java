package com.sunnyit.enforcement.data;

import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.adapter.AdapterUtil;
import com.sunnyit.common.adapter.ViewHolderUtil;
import com.sunnyit.enforcement.model.CheckTables;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

/**   
* @Title: DailyCheckAdapter.java 
* @Package com.sunnyit.enforcement.data 
* @Description: TODO
* @author yk
* @date 2015年9月11日 下午9:21:46 
* @version V1.0   
*/
public class CheckTablesRadioAdapter extends AdapterUtil<CheckTables> {
	
	private int  selectItem=-1;  
	private Context  mContext;  

	public CheckTablesRadioAdapter(Context context, List<CheckTables> datas, int layoutId) {
		super(context, datas, layoutId);
		// TODO Auto-generated constructor stub
		mContext=context;
	}

	@Override
	public void initShowDate(ViewHolderUtil viewHolder, final CheckTables t, int position) {
		viewHolder.setText(R.id.tv_redio_tableName, t.getSc_tableName());
		TextView tv=viewHolder.getView(R.id.tv_redio_tableName);
		if (position == selectItem) {  
			
			viewHolder.getMconvertView().setBackgroundResource(R.drawable.radio_checked_blue_pressed32);
			int mycolor = mContext.getResources().getColor(R.color.text_blue);
			tv.setTextColor(mycolor);
        }   
        else 
        {  
        	viewHolder.getMconvertView().setBackgroundResource(R.drawable.radio_checked_grey32);
        	int mycolor = mContext.getResources().getColor(R.color.radio_grey);
			tv.setTextColor(mycolor);
        }   
	}
	
	public  void setSelectItem(int selectItem) {  
        this.selectItem = selectItem;  
	}

}


