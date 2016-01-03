package com.sunnyit.common.view;

import com.sunnyit.R;
import com.sunnyit.common.convert.PpConvert;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**   
* @Title: LeftSlidingMenu.java 
* @Package com.sunnyit.common.view 
* @Description: TODO
* @author yk
* @date 2015年8月7日 上午9:24:38 
* @version V1.0   
*/
public class LeftSlidingMenu extends HorizontalScrollView {
	
	private LinearLayout mLinearLayout;
	private ViewGroup menuViewGroup;
	private ViewGroup contentViewGroup;
	private int mScreenWidth;
	private int menuWidth;
	private int menuRightPadding;
	
	private int rightdp=80;
	private Boolean mOnce=true;
	
	private Boolean menuIsOpen=false;

	/**
	 * 没有自定义属性时加载
	* @author yk
	* @date 2015年8月7日 下午1:47:19 
	* @param context
	* @param attrs    设定文件
	 */
	public LeftSlidingMenu(Context context, AttributeSet attrs) {
		this(context,attrs,0);
	}
	
	public LeftSlidingMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		
		WindowManager wm=(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics=new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		mScreenWidth=outMetrics.widthPixels;
		/*menuRightPadding=StyleValue.getStyleValue(context, attrs, defStyle, 
				R.styleable.LeftSlidingMenu, R.styleable.LeftSlidingMenu_rightPadding);*/
		TypedArray ta=context.obtainStyledAttributes(attrs, R.styleable.LeftSlidingMenu);
		menuRightPadding=ta.getDimensionPixelSize(R.styleable.LeftSlidingMenu_menurightPadding, 80);
		ta.recycle();
	}

	public LeftSlidingMenu(Context context) {
		this(context,null);
	}

	/**
	 * 设置宽和高
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		
		if(mOnce)
		{
			mLinearLayout=(LinearLayout) getChildAt(0);
			menuViewGroup=(ViewGroup) mLinearLayout.getChildAt(0);
			contentViewGroup=(ViewGroup) mLinearLayout.getChildAt(1);
			menuWidth=mScreenWidth-menuRightPadding;
			menuViewGroup.getLayoutParams().width=menuWidth;
			contentViewGroup.getLayoutParams().width=mScreenWidth;
			mOnce=false;
		}
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		if(changed)
		{
			this.scrollTo(menuWidth, 0);
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		
		int action=ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_UP:
			int scrollx=getScrollX();
			if(scrollx>=(menuWidth/2))
			{
				//关闭
				this.smoothScrollTo(menuWidth, 0);
				menuIsOpen=false;
			}
			else
			{
				//打开
				this.smoothScrollTo(0, 0);
				menuIsOpen=true;
			}
			return true;
		}
		
		
		// TODO Auto-generated method stub
		return super.onTouchEvent(ev);
	}
	
	/**
	 * 设置menu的偏移量
	 * l为getScrollX,
	 */
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		// TODO Auto-generated method stub
		super.onScrollChanged(l, t, oldl, oldt);
		
		float scale=l*1.0f/menuWidth;
		menuViewGroup.setTranslationX(menuWidth*scale*0.7f);
		
		float menuscale=0.7f+0.3f*(1-scale);
		float menualpha=0.6f+0.4f*(1-scale);
		menuViewGroup.setScaleX(menuscale);
		menuViewGroup.setScaleY(menuscale);
		menuViewGroup.setAlpha(menualpha);
		
		
		/**
		 * 内容的缩放
		 */
		float contentscale=0.9f+0.1f*scale;
		contentViewGroup.setPivotX(menuWidth);
		contentViewGroup.setPivotY(contentViewGroup.getHeight()/2);
		contentViewGroup.setScaleX(contentscale);
		contentViewGroup.setScaleY(contentscale);
		
		
	}
	
	public void openMenu()
	{
		if(menuIsOpen)
		{
			return;
		}
		else
		{
			//打开
			this.smoothScrollTo(0, 0);
			menuIsOpen=true;
		}
	}
	
	public void closeMenu()
	{
		if(!menuIsOpen)
		{
			return;
		}
		else
		{
			//打开
			this.smoothScrollTo(menuWidth, 0);
			menuIsOpen=false;
		}
	}
	
	/**
	* @author yk 
	* @date 2015年8月7日 下午2:47:49 
	* @Title: menuSwitch 
	* @Description: 切换打开的状态
	* @return void    返回类型 
	* @throws
	 */
	public void menuSwitch()
	{
		if(menuIsOpen)
		{
			closeMenu();
		}
		else
		{
			openMenu();
		}
	}

}


