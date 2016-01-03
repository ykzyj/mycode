package com.sunnyit.common.adapter;

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

/**   
* @Title: AdapterUnit.java 
* @Package com.sunnyit.common.adapter 
* @Description: TODO
* @author yk
* @date 2015年7月31日 上午11:05:03 
* @version V1.0   
*/
public abstract class AdapterUnit<T> extends BaseAdapter {
	
	protected LayoutInflater mInflater;
	protected Context mcontext;
	protected List<T> mdata;
	private int mlayoutId;
	
	public AdapterUnit(Context content,List<T> datas,int layoutId) {
		// TODO Auto-generated constructor stub
		mInflater=LayoutInflater.from(content);
		mcontext=content;
		mdata=datas;
		mlayoutId=layoutId;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mdata.size();
	}

	@Override
	public T getItem(int position) {
		// TODO Auto-generated method stub
		return mdata.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolderUnit viewHolder=ViewHolderUnit.getViViewHolder(mcontext, position, convertView, parent, mlayoutId);
		
		initShowDate(viewHolder, getItem(position));
		
		return viewHolder.getMconvertView();
	}

	public abstract  void initShowDate(ViewHolderUnit viewHolder,T t);
	

}
