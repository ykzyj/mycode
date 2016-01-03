package com.sunnyit.common.util;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class SuperAnatherHeadAdapter<T> extends BaseAdapter {

	private List<T> mDatas;
	private Context mContext;
	private int mheadlayout, mBodyLayout;

	public SuperAnatherHeadAdapter(Context context, List<T> datas,
			int headlayout, int bodyLayout) {
		this.mContext = context;
		this.mDatas = datas;
		this.mheadlayout = headlayout;
		this.mBodyLayout = bodyLayout;
	}

	@Override
	public int getCount() {
		return mDatas.size() + 1;
	}

	public void updateData(List<T> mDatas) {
		this.mDatas = mDatas;
	}

	@Override
	public T getItem(int position) {
		if (position < 1) {
			return null;
		}
		return mDatas.get(position - 1);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (position < 1) {
			holder = ViewHolder.get(mContext, convertView, parent, mheadlayout,
					position);
			convertHead(holder);
		} else {
			holder = convertBody(mContext, getItem(position), convertView,
					parent, mBodyLayout, position);
		}
		return holder.getConvertView();
	}

	public void updateListView(List<T> t) {
		this.mDatas = t;
		notifyDataSetChanged();
	}

	public abstract void convertHead(ViewHolder headHolder);

	public abstract ViewHolder convertBody(Context context, T t,
			View convertView, ViewGroup parent, int mBodyLayout2, int position);
}
