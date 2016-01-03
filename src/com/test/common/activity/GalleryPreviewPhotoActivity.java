package com.sunnyit.common.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.sunnyit.R;
import com.sunnyit.common.photoview.PhotoView;
import com.sunnyit.common.util.UIUtil;
import com.sunnyit.common.util.Utils;

/**
 * 
 * 请传入一个{@link ArrayList}的图片集�?
 * 
 */
public class GalleryPreviewPhotoActivity extends Activity implements Callback,
		OnItemClickListener, OnClickListener {

	private static final int TYPE_BY_FATIE = -1;
	private static final int TYPE_BY_KANTIE = 1;
	private ViewPager mViewPager;
	private Gallery mGallery;
	ProgressBar pb;
	private List<ImageView> views, gviews;
	private ArrayList<String> imgPaths;
	private TextView titleText, contentTitleText;
	private View titleBar;
	private ViewPagerAdapter mAdapter;
	private BaseAdapter mGAdapter;
	private Handler mHandler;
	private TextView delete;
	private int showType;
	private SparseArray<Bitmap> allBit = new SparseArray<Bitmap>();
	private static int screenWidth;
	private static int screenHeight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.peter_activity_priviewphoto);
		titleBar = findViewById(R.id.peter_privewphoto_top_bar);
		pb = (ProgressBar) findViewById(R.id.progressBar1);
		pb.setVisibility(View.GONE);
		mHandler = new Handler(this);
		DisplayMetrics dm = new DisplayMetrics();
		// 取得窗口属性
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		// 窗口的宽度
		 screenWidth = dm.widthPixels;
		// 窗口高度
		 screenHeight = dm.heightPixels;
		initData();
		initView();
	}

	public void FinishThisActivity(View view) {
		finish();
	}
	
	public void onClickRemove(View view) {
		final int position = mViewPager.getCurrentItem();
		views.remove(position);
		gviews.remove(position);
		imgPaths.remove(position);
		if (imgPaths.size() == 0) {
			chackOk();
			return;
		}
		titleText.setText((position + 1) + "/" + imgPaths.size());
		mAdapter.notifyDataSetChanged();
		mGAdapter.notifyDataSetChanged();
	}

	private void chackOk() {
		if (imgPaths == null) {
			finish();
		} else {
			Intent intent = new Intent();
			intent.putStringArrayListExtra("DATA", imgPaths);
			setResult(RESULT_OK, intent);
			finish();
		}
	}

	@Override
	public void onBackPressed() {
		chackOk();
	}

	private void initData() {
		Intent intent = getIntent();
		showType = intent.getIntExtra("TYPE", TYPE_BY_KANTIE);
		imgPaths = intent.getStringArrayListExtra("DATA");
		if (imgPaths == null || imgPaths.size() <= 0) {
			finish();
		}
		titleText = (TextView) findViewById(R.id.peter_privewphoto_title_text);
		contentTitleText = (TextView) findViewById(R.id.peter_privewphoto_title_text_contnt);
		delete = (TextView) findViewById(R.id.peter_priview_photo_delets);
		if (showType == TYPE_BY_KANTIE) {
			delete.setVisibility(View.GONE);
			titleBar.setVisibility(View.GONE);
			contentTitleText.setVisibility(View.VISIBLE);
		} else {
			titleBar.setVisibility(View.VISIBLE);
			contentTitleText.setVisibility(View.GONE);
		}

		if (showType == TYPE_BY_FATIE) {
			for (int i = 0; i < imgPaths.size(); i++) {
				File f = new File(imgPaths.get(i));
				if (!f.exists()) {
					imgPaths.remove(i);
					i--;
				}
			}
		}

		if (imgPaths == null || imgPaths.size() <= 0) {
			chackOk();
		}
		views = new ArrayList<ImageView>();
		gviews = new ArrayList<ImageView>();
		mViewPager = (ViewPager) findViewById(R.id.peter_privewphoto_viewpager);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		android.widget.Gallery.LayoutParams glp = new Gallery.LayoutParams(150,
				100);
		for (int i = 0; i < imgPaths.size(); i++) {
			// ImageView iv = new ImageView(getApplicationContext());
			PhotoView iv = new PhotoView(getApplicationContext());
			iv.setLayoutParams(lp);
			iv.setOnClickListener(this);
			ImageView giv = new ImageView(getApplicationContext());
			giv.setLayoutParams(glp);
			giv.setScaleType(ImageView.ScaleType.CENTER_CROP);
			if (showType == TYPE_BY_KANTIE) {
				Utils.setPreViewImage(GalleryPreviewPhotoActivity.this,
						imgPaths.get(i), iv,
						R.drawable.ic_launcher, new ImageLoadingListener() {

							@Override
							public void onLoadingStarted(String arg0, View arg1) {
								pb.setVisibility(View.VISIBLE);
								arg1.setVisibility(View.GONE);
							}

							@Override
							public void onLoadingFailed(String arg0, View arg1,
									FailReason arg2) {
								pb.setVisibility(View.GONE);
								arg1.setVisibility(View.VISIBLE);
							}

							@Override
							public void onLoadingComplete(String arg0,
									View arg1, Bitmap arg2) {
								pb.setVisibility(View.GONE);
								arg1.setVisibility(View.VISIBLE);
							}

							@Override
							public void onLoadingCancelled(String arg0,
									View arg1) {

							}
						});
				Utils.setPreViewImage(GalleryPreviewPhotoActivity.this,
						imgPaths.get(i), giv,
						R.drawable.ic_launcher);
			} else {
				new LoadImage(i).start();
			}
			views.add(iv);
			gviews.add(giv);
		}
		titleText.setText("1/" + imgPaths.size());
		contentTitleText.setText("1/" + imgPaths.size());
	}

	class LoadImage extends Thread {

		private int key;

		public LoadImage(int key) {
			this.key = key;
		}

		@Override
		public void run() {
			allBit.put(
					key,
					smalls(UIUtil.SCREEN_WIDTH,
							UIUtil.SCREEN_HEIGHT
									- UIUtil.dip2px(getApplicationContext(),
											100), getimage(imgPaths.get(key))));
			mHandler.sendEmptyMessage(key);
		}

	}

	private Bitmap getimage(String srcPath) {

		// 现在主流手机比较多是800*480分辨率，�?��高和宽我们设置为
		float hh = UIUtil.SCREEN_WIDTH;// 这里设置高度�?00f
		float ww = UIUtil.SCREEN_HEIGHT;// 这里设置宽度�?80f
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// �?��读入图片，此时把options.inJustDecodeBounds 设回true�?
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;

		// 缩放比�?由于是固定比例缩放，只用高或者宽其中�?��数据进行计算即可
		int be = 1;// be=1表示不缩�?
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩�?
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩�?
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false�?
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		return bitmap;// 压缩好比例大小后再进行质量压�?
	}

	private static Bitmap smalls(int needW, int needH, Bitmap bitmap) {
		int bitWidth = bitmap.getWidth();
		int bitHeight = bitmap.getHeight();

		float scleW;
		if (bitWidth > screenWidth) {
			scleW = screenWidth / (float) bitWidth;
		} else {
			scleW = bitWidth / (float) screenWidth;
		}
		Matrix matrix = new Matrix();
		matrix.reset();
		matrix.postScale(scleW, scleW);
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
		bitWidth = bitmap.getWidth();
		bitHeight = bitmap.getHeight();
		if (bitHeight > needH) {
			int kongyu = (bitHeight - needH) / 2;
			Matrix matrix2 = new Matrix();
			matrix2.postScale(1, 1);
			bitmap = Bitmap.createBitmap(bitmap, 0, kongyu, bitmap.getWidth(),
					bitmap.getHeight() - kongyu, matrix2, true);
			return bitmap;
		}
		return bitmap;
	}

	private void initView() {
		mGallery = (Gallery) findViewById(R.id.peter_privewphoto_gallery);
		mGallery.setOnItemClickListener(this);
		mAdapter = new ViewPagerAdapter(views);
		mGAdapter = new ImageGalleryAdapter(gviews);
		mViewPager.setAdapter(mAdapter);
		mGallery.setAdapter(mGAdapter);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				titleText.setText((arg0 + 1) + "/" + imgPaths.size());
				contentTitleText.setText((arg0 + 1) + "/" + imgPaths.size());
				if (isTouch) {
					mGallery.setSelection(arg0);
					isTouch = false;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				if (arg0 == 1) {
					isTouch = true;
				}
			}
		});
	}

	private boolean isTouch;

	class ViewPagerAdapter extends PagerAdapter {

		private List<ImageView> mViews;

		public ViewPagerAdapter(List<ImageView> views) {
			this.mViews = views;
		}

		@Override
		public int getCount() {
			return mViews.size();
		}

		public void setViews(List<ImageView> views) {
			this.mViews = views;
			notifyDataSetChanged();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			((ViewPager) container).addView(mViews.get(position));
			return mViews.get(position);
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

	}

	class ImageGalleryAdapter extends BaseAdapter {

		private List<ImageView> mViews;

		public ImageGalleryAdapter(List<ImageView> mViews) {
			this.mViews = mViews;
		}

		@Override
		public int getCount() {
			return mViews.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return mViews.get(position);
		}

	}

	@Override
	public boolean handleMessage(Message msg) {
		Bitmap bit = allBit.get(msg.what);
		views.get(msg.what).setImageBitmap(bit);
		gviews.get(msg.what).setImageBitmap(bit);
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		mViewPager.setCurrentItem(position);
	}

	@Override
	public void onClick(View v) {
		finish();
	}

}
