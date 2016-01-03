package com.sunnyit.common.view;

import com.sunnyit.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListView;

/**   
* @Title: LoadListview.java 
* @Package com.sunnyit.common.view 
* @Description: TODO
* @author yk
* @date 2015年8月14日 下午5:42:27 
* @version V1.0   
*/
public class ScrollLoadListview extends ListView implements OnScrollListener {
	
	private LinearLayout lay_load;
	private View footer;
	
	private int lastVisibleItem;
	private int totalItemCount;
	
	private boolean isLoading=false;
	private IScrollLoadListListener mIScrollLoadListListener;

	public ScrollLoadListview(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initView(context);
	}

	public ScrollLoadListview(Context context, AttributeSet attrs) {
		this(context, attrs,0);
		// TODO Auto-generated constructor stub
	}

	public ScrollLoadListview(Context context) {
		this(context,null);
		// TODO Auto-generated constructor stub
	}
	
	/**
	* @author yk 
	* @date 2015年8月14日 下午5:44:33 
	* @Title: initView 
	* @Description: 初始化布局
	* @return void    返回类型 
	* @throws
	 */
	private void initView(Context context)
	{
		LayoutInflater mlayout=LayoutInflater.from(context);
		footer=mlayout.inflate(R.layout.footscrollload, null);
		lay_load=(LinearLayout) footer.findViewById(R.id.lay_load);
		lay_load.setVisibility(View.GONE);
		lay_load.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				lay_load.setVisibility(View.GONE);
				isLoading=false;
			}
		});
		this.addFooterView(footer);
		this.setOnScrollListener(this);
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		this.lastVisibleItem=firstVisibleItem+visibleItemCount;
		this.totalItemCount=totalItemCount;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		if(totalItemCount==lastVisibleItem
				&&scrollState==SCROLL_STATE_IDLE)
		{
			if(!isLoading)
			{
				lay_load.setVisibility(View.VISIBLE);
				isLoading=true;
				if(this.mIScrollLoadListListener!=null)
				{
					this.mIScrollLoadListListener.onLoad();
				}
			}
		}
	}
	
	public void setILoadListListener(IScrollLoadListListener iLoadListListener)
	{
		this.mIScrollLoadListListener=iLoadListListener;
	}
	
	public void moreDateLoadComplete()
	{
		lay_load.setVisibility(View.GONE);
		isLoading=false;
	}

	public interface IScrollLoadListListener
	{
		public void onLoad();
	}
	
	
	
}


