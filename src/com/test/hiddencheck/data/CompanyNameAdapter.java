package com.sunnyit.hiddencheck.data;

import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.adapter.AdapterUtil;
import com.sunnyit.common.adapter.ViewHolderUtil;
import com.sunnyit.hiddencheck.model.CompanyMin;

import android.content.Context;

public class CompanyNameAdapter extends AdapterUtil<CompanyMin> {

	public CompanyNameAdapter(Context context, List<CompanyMin> datas, int layoutId) {
		super(context, datas, layoutId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initShowDate(ViewHolderUtil viewHolder, CompanyMin t, int position) {
		// TODO Auto-generated method stub
		viewHolder.setImageResource(R.id.img_industry_type,R.drawable.often_question);
		viewHolder.setText(R.id.tv_industry_type, t.getE_companyName());
	}

}
