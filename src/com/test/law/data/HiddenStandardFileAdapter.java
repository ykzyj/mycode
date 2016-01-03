package com.sunnyit.law.data;

import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.adapter.AdapterUtil;
import com.sunnyit.common.adapter.ViewHolderUtil;
import com.sunnyit.law.model.HiddenStandardFile;

import android.content.Context;

public class HiddenStandardFileAdapter extends AdapterUtil<HiddenStandardFile> {

	public HiddenStandardFileAdapter(Context context, List<HiddenStandardFile> datas, int layoutId) {
		super(context, datas, layoutId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initShowDate(ViewHolderUtil viewHolder, HiddenStandardFile t, int position) {
		// TODO Auto-generated method stub
		viewHolder.setImageResource(R.id.img_industry_type, R.drawable.often_question);
		viewHolder.setText(R.id.tv_industry_type, t.getHf_name());
	}

}
