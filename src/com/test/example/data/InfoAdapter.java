package com.sunnyit.example.data;

import java.util.List;

import com.sunnyit.R;
import com.sunnyit.example.model.test_info;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class InfoAdapter extends BaseAdapter {
	
	private LayoutInflater mInflater;
	private List<test_info> mdate;
	
	public InfoAdapter(Context context,List<test_info> dates) {
		// TODO Auto-generated constructor stub
		mInflater=LayoutInflater.from(context);
		mdate=dates;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mdate.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mdate.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewholder=null;
		if(convertView==null)
		{
			convertView=mInflater.inflate(R.layout.list_item_info, parent, false);
			viewholder=new ViewHolder();
			viewholder.img_info=(ImageView)convertView.findViewById(R.id.img_info);
			viewholder.txt_info_title=(TextView)convertView.findViewById(R.id.txt_info_title);
			viewholder.txt_info_time=(TextView)convertView.findViewById(R.id.txt_info_time);
			viewholder.txt_info_content=(TextView)convertView.findViewById(R.id.txt_info_content);
			convertView.setTag(viewholder);
		}
		else
		{
			viewholder=(ViewHolder)convertView.getTag();
		}
		test_info info=(test_info) getItem(position);
		viewholder.img_info.setImageResource(info.getImageid());
		viewholder.txt_info_title.setText(info.getInfotitle());
		viewholder.txt_info_time.setText(info.getInfotime());
		viewholder.txt_info_content.setText(info.getInfocontent());
		
		return convertView;
	}
	
	class ViewHolder{
		ImageView img_info;
		TextView txt_info_title;
		TextView txt_info_time;
		TextView txt_info_content;
	}

}
