package com.sunnyit.common.adapter;

import java.util.List;

import com.sunnyit.common.cache.DiskCacheUtil;
import com.sunnyit.common.cache.ImageCache;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**   
* @Title: AdapterUnit.java 
* @Package com.sunnyit.common.adapter 
* @Description: TODO
* @author yk
* @date 2015��7��31�� ����11:05:03 
* @version V1.0   
*/
public abstract class AdapterUtil<T> extends BaseAdapter {
	
	protected LayoutInflater mInflater;
	protected Context mcontext;
	protected List<T> mdata;
	private int mlayoutId;
	
	private ImageCache mimageCache;
	private DiskCacheUtil mimageDiskCache;
	
	/**
	* @author yk
	* @date 2015��8��4�� ����10:31:12 
	* @param content ������
	* @param datas �������
	* @param layoutId listitem����ID
	 */
	public AdapterUtil(Context context,List<T> datas,int layoutId) {
		// TODO Auto-generated constructor stub
		mInflater=LayoutInflater.from(context);
		mcontext=context;
		mdata=datas;
		mlayoutId=layoutId;
		
		mimageCache=new ImageCache();
		mimageDiskCache=new DiskCacheUtil(context);
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
		ViewHolderUtil viewHolder=ViewHolderUtil.getViViewHolder(mcontext, position, convertView, parent, mlayoutId,mimageCache,mimageDiskCache);
		
		initShowDate(viewHolder, getItem(position),position);
		
		return viewHolder.getMconvertView();
	}

	/**
	* @author yk 
	* @date 2015��8��4�� ����10:30:24 
	* @Title: initShowDate 
	* @Description:��listview��item���и�ֵ
	* @param viewHolder
	* @param t    �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	public abstract  void initShowDate(ViewHolderUtil viewHolder,T t,int position);
	

}
