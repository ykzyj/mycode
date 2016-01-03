package com.sunnyit.chemicals.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.sunnyit.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**   
* @Title: ExAdapter.java 
* @Package com.sunnyit.enforcement.data 
* @Description: TODO
* @author yk
* @date 2015年9月14日 下午2:45:53 
* @version V1.0   
*/
public class ChemicalsShowAdapter extends BaseExpandableListAdapter {
	
	private List<BasicNameValuePair> mfatherList;
	private List<List<BasicNameValuePair>> mchildList;
	private Context mContext;
	
	public ChemicalsShowAdapter(Context context,List<BasicNameValuePair> fatherList,List<List<BasicNameValuePair>> childList) {
		// TODO Auto-generated constructor stub
		mfatherList = fatherList;
		mchildList = childList;
		mContext=context;
	}


	@Override
	public int getGroupCount() {
		return mfatherList.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return mchildList.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return mfatherList.get(groupPosition).getValue();
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return mchildList.get(groupPosition).get(childPosition).getValue();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			view = inflater.inflate(R.layout.ym_group, null);
		}
		TextView t = (TextView) view.findViewById(R.id.tv_ym_group);
		t.setText(mfatherList.get(groupPosition).getValue());
		t.setPadding(15, 0, 0, 0);
		
		ImageView gImg = (ImageView) view.findViewById(R.id.tubiao);
		ImageView hImg = (ImageView) view.findViewById(R.id.home_qu);
		hImg.setVisibility(View.GONE);
		if (isExpanded)
			gImg.setBackgroundResource(R.drawable.mm_submenu_down_normal);
		else
			gImg.setBackgroundResource(R.drawable.mm_submenu_normal);
		return view;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			view = inflater.inflate(R.layout.ym_child, null);
		}
		
		ImageView tbImg = (ImageView) view.findViewById(R.id.tubiao);
		tbImg.setVisibility(View.GONE);
		ImageView jfImg = (ImageView) view.findViewById(R.id.child_jb_flag);
		jfImg.setVisibility(View.GONE);
		
		TextView t = (TextView) view.findViewById(R.id.tv_ym_child);
		t.setText(mchildList.get(groupPosition).get(childPosition).getValue());
		
		return view;
	}

	@Override
	public boolean hasStableIds() 
	{
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) 
	{
		return true;
	}
}


