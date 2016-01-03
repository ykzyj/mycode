package com.sunnyit.common.view;

import com.sunnyit.common.convert.PpConvert;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ScrollView;

/**   
* @Title: LeftSlidingMenu.java 
* @Package com.sunnyit.common.view 
* @Description: TODO
* @author yk
* @date 2015��8��7�� ����9:24:38 
* @version V1.0   
*/
public class TopOneHide extends ScrollView {
	
	private int mOneHideHeight;
	private int mScreenHeight;
	
	private Boolean menuIsOpen=false;

	/**
	 * û���Զ�������ʱ����
	* @author yk
	* @date 2015��8��7�� ����1:47:19 
	* @param context
	* @param attrs    �趨�ļ�
	 */
	public TopOneHide(Context context, AttributeSet attrs) {
		this(context,attrs,0);
	}
	
	public TopOneHide(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		
		WindowManager wm=(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics=new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		mScreenHeight=outMetrics.heightPixels;
		
		mOneHideHeight=PpConvert.dp2px(context, 50);
	}

	public TopOneHide(Context context) {
		this(context,null);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		this.scrollTo(0, mOneHideHeight);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		
		int action=ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_UP:
			int scrolly=getScrollY();
			if(scrolly>=(mOneHideHeight/2)&&scrolly<=mOneHideHeight)
			{
				//�ر�
				this.smoothScrollTo(0, mOneHideHeight);
				menuIsOpen=false;
			}
			if(scrolly<(mOneHideHeight/2))
			{
				//��
				this.smoothScrollTo(0, 0);
				menuIsOpen=true;
			}
			return true;
		}
		
		
		// TODO Auto-generated method stub
		return super.onTouchEvent(ev);
	}
	
}


