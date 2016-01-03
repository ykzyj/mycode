package com.sunnyit.common.util;


import android.content.Context;
import android.graphics.Color;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder {
	private SparseArray<View> mViews;
	private int mPosition;
	private View mConvertView;
	private Context context;

	private ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
		this.mPosition = position;
		this.context = context;
		this.mViews = new SparseArray<View>();
		this.mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
		this.mConvertView.setTag(this);
	}

	public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
		if (convertView == null) {
			return new ViewHolder(context, parent, layoutId, position);
		} else {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			holder.mPosition = position;
			return holder;
		}
	}

	public <T extends View> T getView(int viewId) {
		View view = mViews.get(viewId);
		if (view == null) {
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}

	public View getConvertView() {
		return this.mConvertView;
	}

	public ViewHolder setText(int viewId, CharSequence text) {
		TextView tv = getView(viewId);
		tv.setText(text);
		return this;
	}

	public ViewHolder setTextThanHide(int viewId, CharSequence text) {
		TextView tv = getView(viewId);
		tv.setVisibility(View.GONE);
		if (text != null && text.length() > 0) {
			tv.setVisibility(View.VISIBLE);
			tv.setText(text);
		}
		return this;
	}

	public ViewHolder setVisibility(int viewId, int visibility) {
		getView(viewId).setVisibility(visibility);
		return this;
	}

	public ViewHolder setButton(int viewId, CharSequence text) {
		Button tv = getView(viewId);
		tv.setText(text);
		return this;
	}
	public ViewHolder setImageView (int viewId, int resId) {
		ImageView iv = getView(viewId);
		iv.setImageResource(resId);
		return this;
	}
	public ViewHolder setTextViewColor (int viewId, int red,int green,int blue) {
		TextView tv = getView(viewId);
		tv.setTextColor(Color.rgb(red, green, blue));
		return this;
	}

	public ViewHolder setViewColor (int viewId, int red,int green,int blue) {
		View view = getView(viewId);
		view.setBackgroundColor(Color.rgb(red, green, blue));
		return this;
	}
	
	public ViewHolder setOnClickListener(int viewId, OnClickListener l) {
		getView(viewId).setOnClickListener(l);
		return this;
	}

//	public ViewHolder setImageUrl(int viewId, String url, int deflutRes) {
//		Utils.setTieZiImage(context, url, (ImageView) getView(viewId), deflutRes);
//		return this;
//	}
//	
//	public ViewHolder setImageUrlRound(int viewId, String url) {
//		UserUtils.setUserImage(context, url, (ImageView) getView(viewId));
//		return this;
//	}

	public ViewHolder setImage(int viewId, int res) {
		((ImageView) getView(viewId)).setImageResource(res);
		return this;
	}

}
