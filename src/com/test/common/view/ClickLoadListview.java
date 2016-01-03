package com.sunnyit.common.view;

import com.sunnyit.R;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**   
* @Title: LoadListview.java 
* @Package com.sunnyit.common.view 
* @Description: TODO
* @author yk
* @date 2015年8月14日 下午5:42:27 
* @version V1.0   
*/
public class ClickLoadListview extends ListView {
	
	private LinearLayout lay_preloading;
	private TextView tv_preload;
	private LinearLayout lay_loading;
	private View footer;
	private Boolean upflag=true;
	private IClickLoadListListener mIClickLoadListListener;
	
	private Context mContext;

	public ClickLoadListview(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initView(context);
		this.mContext=context;
	}

	public ClickLoadListview(Context context, AttributeSet attrs) {
		this(context, attrs,0);
		// TODO Auto-generated constructor stub
	}

	public ClickLoadListview(Context context) {
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
		footer=mlayout.inflate(R.layout.footclickload, null);
		lay_preloading=(LinearLayout) footer.findViewById(R.id.lay_preloading);
		tv_preload=(TextView) footer.findViewById(R.id.tv_preload);
		lay_loading=(LinearLayout) footer.findViewById(R.id.lay_loading);
		lay_preloading.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*if(upflag)
				{
					tv_preload.setVisibility(View.GONE);
					lay_loading.setVisibility(View.VISIBLE);
					upflag=false;
					if(mIClickLoadListListener!=null)
					{
						mIClickLoadListListener.onLoad();
						moreDateLoadComplete();
					}
				}
				else
				{
					tv_preload.setVisibility(View.VISIBLE);
					lay_loading.setVisibility(View.GONE);
					upflag=true;
				}*/
				
				upflag=false;
				tv_preload.setVisibility(View.GONE);
				lay_loading.setVisibility(View.VISIBLE);
				new Thread(new Runnable(){  
		            public void run(){  
		            	if(mIClickLoadListListener!=null)
						{
							mIClickLoadListListener.onLoad(mHandler);
							Message msg=Message.obtain();
							msg.what=0;
							mHandler.sendMessage(msg);
						}
		            }  
		        }).start();  
			}
		});
		this.addFooterView(footer);
	}
	
	private Handler mHandler=new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what==0)
			{
				moreDateLoadComplete();
			}
			else if(msg.what==1)
			{
				lay_preloading.setVisibility(View.GONE);
				if((boolean) msg.obj)
				{
					Toast.makeText(mContext, "所有数据加载完毕", Toast.LENGTH_SHORT).show();
				}
			}
		};
	};
	
	public void moreDateLoadComplete()
	{
		if(!upflag)
		{
			tv_preload.setVisibility(View.VISIBLE);
			lay_loading.setVisibility(View.GONE);
			upflag=true;
		}
	}
	
	public void hindLoadView(boolean isShow)
	{
		Message msg=Message.obtain();
		msg.what=1;
		msg.obj=true;
		mHandler.sendMessage(msg);
	}
	
	public void showLoadView() {
		lay_preloading.setVisibility(View.VISIBLE);
	}
	
	public void searchNullHindLoadView()
	{
		lay_preloading.setVisibility(View.GONE);
		Toast.makeText(mContext, "搜索数据为空", Toast.LENGTH_SHORT).show();
	}
	
	public void setIClickLoadListListener(IClickLoadListListener clickLoadListListener)
	{
		this.mIClickLoadListListener=clickLoadListListener;
	}
	
	public interface IClickLoadListListener 
	{
		public void onLoad(Handler handler);
	}
	
}


