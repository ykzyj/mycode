package com.sunnyit.common.adapter;


import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**   
* @Title: ViewHolderUnit.java 
* @Package com.sunnyit.common.adapter 
* @Description: TODO
* @author yk
* @date 2015年7月31日 上午11:05:03 
* @version V1.0   
*/
public class ViewHolderUnit {
	
	
	private SparseArray<View> mView;
	private int mposition;
	private View mconvertView;
	
	/**
	 * 
	* @author yk
	* @date 2015年7月31日 上午10:45:19 
	* @param context
	* @param position
	* @param parent
	* @param layoutid    设定文件
	 */
	public ViewHolderUnit(Context context,int position, ViewGroup parent,int layoutid) {
		// TODO Auto-generated constructor stub
		this.mposition=position;
		this.mView=new SparseArray<View>();
		mconvertView=LayoutInflater.from(context).inflate(layoutid, parent, false);
		mconvertView.setTag(this);
	}
	
	/**
	* @author yk 
	* @date 2015年7月30日 下午1:38:13 
	* @Title: getViViewHolder 
	* @Description: ViewHolderUnit的入口方法
	* @param context 上下文
	* @param position 当前位置
	* @param convertView 呈现文本
	* @param parent 
	* @param layoutid
	* @return    设定文件 
	* @return ViewHolderUnit    返回类型 
	* @throws
	 */
	public static ViewHolderUnit getViViewHolder(Context context,int position, View convertView, ViewGroup parent,int layoutid) {
		if(convertView==null)
		{
			return new ViewHolderUnit(context,position,parent,layoutid);
		}
		else
		{
			ViewHolderUnit viewholder=(ViewHolderUnit)convertView.getTag();
			viewholder.mposition=position;
			return viewholder;
		}
	}
	
	/**
	* @author yk 
	* @date 2015年7月30日 下午1:43:35 
	* @Title: getView 
	* @Description: TODO
	* @param viewid
	* @return    设定文件 
	* @return T    返回类型 
	* @throws
	 */
	public <T extends View>T getView(int viewId)
	{
		View view=mView.get(viewId);
		if(view==null)
		{
			view=mconvertView.findViewById(viewId);
			mView.put(viewId, view);
		}
		return (T) view;
	}

	public View getMconvertView() {
		return mconvertView;
	}
	
	public void setText(int viewId,String txt)
	{
		TextView txtview=getView(viewId);
		txtview.setText(txt);
	}
	
	public void setImageResource(int viewId,int resourceId)
	{
		ImageView imgview=getView(viewId);
		imgview.setImageResource(resourceId);
	}
	
}
