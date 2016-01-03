package com.sunnyit.common.view;

import java.util.List;

import com.sunnyit.R;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**   
* @Title: PageViewTab.java 
* @Package com.sunnyit.common.view 
* @Description: pageview的加载显示，现最多支持五个fragment
* @author yk
* @date 2015年9月7日 下午3:45:18 
* @version V1.0   
*/
public class PageViewTab {
	
	private ViewPager mViewPager;
	private List<Fragment> mDatas;
	private FragmentPagerAdapter mAdapter;
	private FragmentManager mFragmentManager;
	private String[] mTabName;
	
	private TextView tv_tab_1,tv_tab_2,tv_tab_3;
	private TextView tv_tab_4,tv_tab_5;
	
	private LinearLayout layout_tab_1,layout_tab_2,layout_tab_3;
	private LinearLayout layout_tab_4,layout_tab_5;
	
	private View mView;
	
	private ImageView img_tab;
	private int tabImgWidth;
	
	/**
	* @author yk
	* @date 2015年9月8日 上午8:35:22 
	* @param activity
	* @param fragmentManager
	* @param viewPager
	* @param lsf
	* @param tabname    设定文件
	 */
	public PageViewTab(Activity activity,FragmentManager fragmentManager,ViewPager viewPager,List<Fragment> lsf,String[] tabname) {
		mView=activity.getWindow().getDecorView();
		this.mViewPager=viewPager;
		this.mDatas=lsf;
		this.mFragmentManager=fragmentManager;
		this.mTabName=tabname;
		initCompant();
		
		Display mDisplay=activity.getWindow().getWindowManager().getDefaultDisplay();
		DisplayMetrics outMetrics=new DisplayMetrics();
		mDisplay.getMetrics(outMetrics);
		tabImgWidth=outMetrics.widthPixels/lsf.size();
		
		LayoutParams lp=img_tab.getLayoutParams();
		lp.width=tabImgWidth;
		img_tab.setLayoutParams(lp);
		
		initAdapter();
		initShow();
	}

	/**
	* @author yk 
	* @date 2015年9月8日 上午8:38:13 
	* @Title: initShow 
	* @Description: 初始化pageview适配器的滑动过程
	* @return void    返回类型 
	* @throws
	 */
	private void initShow() {
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				switch (mDatas.size()) {
					case 5:
						tv_tab_5.setTextColor(Color.parseColor("#7d7d7d"));
						layout_tab_5.setBackgroundColor(Color.parseColor("#f3f3f3"));
					case 4:
						tv_tab_4.setTextColor(Color.parseColor("#7d7d7d"));
						layout_tab_4.setBackgroundColor(Color.parseColor("#f3f3f3"));
					case 3:
						tv_tab_3.setTextColor(Color.parseColor("#7d7d7d"));
						layout_tab_3.setBackgroundColor(Color.parseColor("#f3f3f3"));
					case 2:
						tv_tab_2.setTextColor(Color.parseColor("#7d7d7d"));
						layout_tab_2.setBackgroundColor(Color.parseColor("#f3f3f3"));
					case 1:
						tv_tab_1.setTextColor(Color.parseColor("#7d7d7d"));
						layout_tab_1.setBackgroundColor(Color.parseColor("#f3f3f3"));
						break;
				}
				switch (position) {
					case 4:
						tv_tab_5.setTextColor(Color.parseColor("#33b5e5"));
						layout_tab_5.setBackgroundColor(Color.parseColor("#def5fe"));
						break;
					case 3:
						tv_tab_4.setTextColor(Color.parseColor("#33b5e5"));
						layout_tab_4.setBackgroundColor(Color.parseColor("#def5fe"));
						break;
					case 2:
						tv_tab_3.setTextColor(Color.parseColor("#33b5e5"));
						layout_tab_3.setBackgroundColor(Color.parseColor("#def5fe"));
						break;
					case 1:
						tv_tab_2.setTextColor(Color.parseColor("#33b5e5"));
						layout_tab_2.setBackgroundColor(Color.parseColor("#def5fe"));
						break;
					case 0:
						tv_tab_1.setTextColor(Color.parseColor("#33b5e5"));
						layout_tab_1.setBackgroundColor(Color.parseColor("#def5fe"));
						break;
				}
				
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPx) {
				// TODO Auto-generated method stub
				Log.i("tag", String.valueOf(position)+"------"+String.valueOf(positionOffset)+"------"+String.valueOf(positionOffsetPx)+"------");
				
				LinearLayout.LayoutParams lp=(android.widget.LinearLayout.LayoutParams) img_tab.getLayoutParams();
				lp.leftMargin=(int) ((position+positionOffset)*tabImgWidth);
				img_tab.setLayoutParams(lp);
			}
			
			@Override
			public void onPageScrollStateChanged(int scrollState) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	

	/**
	* @author yk 
	* @date 2015年9月8日 上午8:37:54 
	* @Title: initAdapter 
	* @Description: 初始化pageview适配器
	* @return void    返回类型 
	* @throws
	 */
	private void initAdapter() {
		// TODO Auto-generated method stub
		if(mDatas!=null&&mFragmentManager!=null)
		{
			mAdapter=new FragmentPagerAdapter(mFragmentManager) {
				
				@Override
				public int getCount() {
					// TODO Auto-generated method stub
					return mDatas.size();
				}
				
				@Override
				public Fragment getItem(int arg0) {
					// TODO Auto-generated method stub
					return mDatas.get(arg0);
				}
			};
			mViewPager.setAdapter(mAdapter);
		}
	}
	
	/**
	* @author yk 
	* @date 2015年9月8日 上午8:37:27 
	* @Title: initCompant 
	* @Description: 初始化界面 
	* @return void    返回类型 
	* @throws
	 */
	private void initCompant() {
		switch (mDatas.size()) {
			case 5:
				tv_tab_5=(TextView) mView.findViewById(R.id.tv_tab_5);
				layout_tab_5=(LinearLayout) mView.findViewById(R.id.layout_tab_5);
				layout_tab_5.setVisibility(View.VISIBLE);
				tv_tab_5.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						mViewPager.setCurrentItem(4, true);
					}
				});
				tv_tab_5.setText(mTabName[4]);
			case 4:
				tv_tab_4=(TextView) mView.findViewById(R.id.tv_tab_4);
				layout_tab_4=(LinearLayout) mView.findViewById(R.id.layout_tab_4);
				layout_tab_4.setVisibility(View.VISIBLE);
				tv_tab_4.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						mViewPager.setCurrentItem(3, true);
					}
				});
				tv_tab_4.setText(mTabName[3]);
			case 3:
				tv_tab_3=(TextView) mView.findViewById(R.id.tv_tab_3);
				layout_tab_3=(LinearLayout) mView.findViewById(R.id.layout_tab_3);
				layout_tab_3.setVisibility(View.VISIBLE);
				tv_tab_3.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						mViewPager.setCurrentItem(2, true);
					}
				});
				tv_tab_3.setText(mTabName[2]);
			case 2:
				tv_tab_2=(TextView) mView.findViewById(R.id.tv_tab_2);
				layout_tab_2=(LinearLayout) mView.findViewById(R.id.layout_tab_2);
				layout_tab_2.setVisibility(View.VISIBLE);
				tv_tab_2.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						mViewPager.setCurrentItem(1, true);
					}
				});
				tv_tab_2.setText(mTabName[1]);
			case 1:
				tv_tab_1=(TextView) mView.findViewById(R.id.tv_tab_1);
				layout_tab_1=(LinearLayout) mView.findViewById(R.id.layout_tab_1);
				layout_tab_1.setVisibility(View.VISIBLE);
				tv_tab_1.setText(mTabName[0]);
				tv_tab_1.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						mViewPager.setCurrentItem(0, true);
					}
				});
				break;
		}
		
		img_tab=(ImageView) mView.findViewById(R.id.img_tab);
	}

}

