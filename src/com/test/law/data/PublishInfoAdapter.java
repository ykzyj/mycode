package com.sunnyit.law.data;

import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.adapter.AdapterUtil;
import com.sunnyit.common.adapter.ViewHolderUtil;
import com.sunnyit.law.model.PublishInfo;

import android.content.Context;

public class PublishInfoAdapter extends AdapterUtil<PublishInfo> {

	public PublishInfoAdapter(Context context, List<PublishInfo> datas, int layoutId) {
		super(context, datas, layoutId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initShowDate(ViewHolderUtil viewHolder, PublishInfo t, int position) {
		// TODO Auto-generated method stub
		//viewHolder.setImageResource(R.id.img_publishInfo,Integer.valueOf(t.getPublishDeptId()));
		viewHolder.setText(R.id.txt_publishInfo_title, t.getTitle());
		viewHolder.setText(R.id.txt_publishInfo_keyword, t.getKeyword());
		viewHolder.setText(R.id.txt_publishInfo_publishTime, t.getPublishTime());
		viewHolder.setText(R.id.txt_publishInfo_publishDeptName, t.getPublishDeptName());
		viewHolder.setText(R.id.txt_publishInfo_infoType, t.getKeyword());
	}

}
