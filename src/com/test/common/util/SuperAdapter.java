package com.sunnyit.common.util;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class SuperAdapter<T> extends BaseAdapter {

	private List<T> mDatas;
	private Context mContext;
	private int mLayout;

	public SuperAdapter(Context context, List<T> datas, int layout) {
		this.mContext = context;
		this.mDatas = datas;
		this.mLayout = layout;
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public T getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = ViewHolder.get(mContext, convertView, parent, mLayout, position);
		convert(holder, getItem(position), position);
		return holder.getConvertView();
	}

	public abstract void convert(ViewHolder holder, T t, int position);
}
