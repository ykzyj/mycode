package com.sunnyit.hiddencheck.data;

import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.adapter.AdapterUtil;
import com.sunnyit.common.adapter.ViewHolderUtil;
import com.sunnyit.hiddencheck.model.SelfCheck;

import android.content.Context;

/**   
* @Title: HiddenCheckAdapter.java 
* @Package com.sunnyit.hiddencheck.model 
* @Description: TODO
* @author yk
* @date 2015年9月21日 下午2:47:50 
* @version V1.0   
*/
public class HiddenCheckAdapter extends AdapterUtil<SelfCheck> {

	public HiddenCheckAdapter(Context context, List<SelfCheck> datas, int layoutId) {
		super(context, datas, layoutId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initShowDate(ViewHolderUtil viewHolder, SelfCheck t, int position) {
		viewHolder.setImageResource(R.id.img_selfcheck_info,Integer.valueOf(t.getRemark()));
		viewHolder.setText(R.id.txt_hidden_companyName, t.getSc_companyName());
		viewHolder.setText(R.id.txt_hidden_no, t.getSc_id());
		viewHolder.setText(R.id.txt_hidden_time, t.getSc_checkTime());
		viewHolder.setText(R.id.txt_hidden_inspector, t.getSc_inspector());
		viewHolder.setText(R.id.txt_hidden_state, t.getSc_state());
	}

}


