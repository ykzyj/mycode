package com.sunnyit.common.adapter;

import com.sunnyit.R;
import com.sunnyit.R.color;
import com.sunnyit.R.drawable;
import com.sunnyit.common.cache.DiskCacheUtil;
import com.sunnyit.common.cache.ImageCache;
import com.sunnyit.common.thread.ImgUtil;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**   
* @Title: ViewHolderUnit.java 
* @Package com.sunnyit.common.adapter 
* @Description: TODO
* @author yk
* @date 2015��7��31�� ����11:05:03 
* @version V1.0   
*/
public class ViewHolderUtil {
	
	
	private SparseArray<View> mView;
	private int mposition;
	private View mconvertView;
	
	private ImageCache mimageCache;
	private DiskCacheUtil mimageDiskCache;
	
	/**
	 * 
	* @author yk
	* @date 2015��7��31�� ����10:45:19 
	* @param context
	* @param position
	* @param parent
	* @param layoutid    �趨�ļ�
	 */
	public ViewHolderUtil(Context context,int position, ViewGroup parent,
			int layoutid,ImageCache imageCache,DiskCacheUtil imageDiskCache) {
		// TODO Auto-generated constructor stub
		this.mposition=position;
		this.mView=new SparseArray<View>();
		mconvertView=LayoutInflater.from(context).inflate(layoutid, parent, false);
		mconvertView.setTag(this);
		this.mimageCache=imageCache;
		this.mimageDiskCache=imageDiskCache;
	}
	
	/**
	* @author yk 
	* @date 2015��7��30�� ����1:38:13 
	* @Title: getViViewHolder 
	* @Description: ViewHolderUnit����ڷ���
	* @param context ������
	* @param position ��ǰλ��
	* @param convertView �����ı�
	* @param parent 
	* @param layoutid
	* @return    �趨�ļ� 
	* @return ViewHolderUnit    �������� 
	* @throws
	 */
	public static ViewHolderUtil getViViewHolder(Context context,int position, 
			View convertView, ViewGroup parent,int layoutid,ImageCache imageCache,DiskCacheUtil imageDiskCache) {
		if(convertView==null)
		{
			return new ViewHolderUtil(context,position,parent,layoutid,imageCache,imageDiskCache);
		}
		else
		{
			ViewHolderUtil viewholder=(ViewHolderUtil)convertView.getTag();
			viewholder.mposition=position;
			return viewholder;
		}
	}
	
	/**
	* @author yk 
	* @date 2015��7��30�� ����1:43:35 
	* @Title: getView 
	* @Description: TODO
	* @param viewid
	* @return    �趨�ļ� 
	* @return T    �������� 
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
	
	/**
	* @author yk 
	* @date 2015��8��5�� ����11:24:39 
	* @Title: setText 
	* @Description: Text����ʾ����
	* @param viewId
	* @param txt    �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	public void setText(int viewId,String txt)
	{
		TextView txtview=getView(viewId);
		txtview.setText(txt);
	}
	
	/**
	* @author yk 
	* @date 2015��10��14�� ����5:08:56 
	* @Title: setText 
	* @Description: TODO
	* @param viewId
	* @param txt    �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	public void setTextColor(int viewId,int color)
	{
		TextView txtview=getView(viewId);
		txtview.setTextColor(color);
	}
	
	/**
	* @author yk 
	* @date 2015��8��5�� ����11:24:13 
	* @Title: setImageResource 
	* @Description: ͼƬ���ؼ���
	* @param viewId
	* @param resourceId    �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	public void setImageResource(int viewId,int resourceId)
	{
		ImageView imgview=getView(viewId);
		imgview.setImageResource(resourceId);
	}
	
	public void setViewVisible(int viewId,int showview)
	{
		View view=getView(viewId);
		view.setVisibility(showview);
	}
	
	public void setCheckBoxCkecked(int viewId,boolean isCkeck)
	{
		CheckBox view=getView(viewId);
		view.setChecked(isCkeck);
	}
	
	public void setRadioButtonCkecked(int viewId,boolean isCkeck)
	{
		RadioButton view=getView(viewId);
		view.setChecked(isCkeck);
	}
	
	public void setRadioButtonColor(int viewId,int color)
	{
		RadioButton view=getView(viewId);
		view.setTextColor(color);
	}
	
	public void setRadioButBgResource(int viewId,Drawable drawable)
	{
		RadioButton view=getView(viewId);
		view.setBackground(drawable);
	}
	
	/**
	* @author yk 
	* @date 2015��8��5�� ����11:23:52 
	* @Title: setImageURI 
	* @Description: ͼƬ���������
	* @param viewId
	* @param url    �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	public void setImageURI(int viewId,String url)
	{
		ImageView imgview=getView(viewId);
		mView.get(viewId).setTag(url);
		new ImgUtil(url, imgview,mimageCache,mimageDiskCache).start();
	}
	
	public void setLayoutParams(int viewId,LinearLayout.LayoutParams Params)
	{
		RelativeLayout relative=getView(viewId);
		relative.setLayoutParams(Params);
	}
	
}
