package com.sunnyit.common.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.sunnyit.R;
import com.sunnyit.common.util.ImageLoader;
import com.sunnyit.common.util.ImageLoader.Type;
import com.sunnyit.common.util.SuperAdapter;
import com.sunnyit.common.util.UIUtil;
import com.sunnyit.common.util.ViewHolder;
import com.sunnyit.common.view.SelectPhotoDirPopupWindow;

/**
 * @ClassName :
 * @Description :
 * @author : zhanghr
 * @date : 2015年12月17日 上午11:39:49
 */
public class GridPreViewPhotoActivity extends Activity implements Callback {

	private static final String ALL_PHOTO_TAG = "##**ALL_PHOTO**##";
	private static final String ALL_PHOTO_NAME = "所有图片";
	private int max_count;
	private ArrayList<String> userSelectedImage;
	private ListImageDirPopupWindow mListImageDirPopupWindow;
	private Map<String, List<String>> allPic = new HashMap<String, List<String>>();
	private List<String> parentNames;
	private TextView photoDirText, photoCountText;
	private TextView btnOk;
	private GridView mGridView;
	private Handler mHandler;
	private MyGridviewAdapter mAdapter;
	private List<String> mImages;
	private ProgressDialog mProgressDialog;
	private String nowPath = ALL_PHOTO_TAG;
	private int position;
	private PopupWindow popupWindow;
	private RelativeLayout peter_selectphoto_top_bar;
	private static final int TAKE_REQUEST_CODE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.peter_activity_gridpreviewphoto);
		initData();
		initView();
		getImages();
	}

	public void onClickOk(View view) {
		chackOk();
	}

	public void onClickSelect(View view) {
		showPopupWindow(view, GridPreViewPhotoActivity.this,
				peter_selectphoto_top_bar);
	}

	public void toSelectPhoto(View view) {
		Intent intent = new Intent(GridPreViewPhotoActivity.this,
				SelectPhotoActivity.class);
		intent.putExtra("POSITION", position);
		intent.putStringArrayListExtra("DATA", userSelectedImage);
		startActivityForResult(intent, TAKE_REQUEST_CODE);
	}

	private void chackOk() {
		Intent intent = new Intent();
		intent.putStringArrayListExtra("DATA", userSelectedImage);
		intent.putExtra("POSITION", position);
		setResult(RESULT_OK, intent);
		finish();
	}

	private void initData() {
		Intent intent = getIntent();
		max_count = intent.getIntExtra("COUNT", 1);
		userSelectedImage = intent.getStringArrayListExtra("DATA");
		if (userSelectedImage == null) {
			userSelectedImage = new ArrayList<String>();
		}
		position = intent.getIntExtra("POSITION", 0);
	}

	private void initView() {
		peter_selectphoto_top_bar = (RelativeLayout) this
				.findViewById(R.id.peter_selectphoto_top_bar);
		parentNames = new ArrayList<String>();
		parentNames.add(ALL_PHOTO_TAG);
		File file = new File(UIUtil.IMAGE_CACHES);
		String[] files = file.list();
		if (files != null && files.length > 0) {
			parentNames.add("SafePhoneCamera");
			List<String> data = new ArrayList<String>();
			for (String str : files) {
				data.add(UIUtil.IMAGE_CACHES + "/" + str);
			}
			allPic.put("SafePhoneCamera", data);

		}

		file = new File(UIUtil.IMAGE_SENDS);
		files = file.list();
		if (files != null && files.length > 0) {
			parentNames.add("SafePhoneImage");
			List<String> data = new ArrayList<String>();
			for (String str : files) {
				data.add(UIUtil.IMAGE_SENDS + "/" + str);
			}
			allPic.put("SafePhoneImage", data);
		}

		mHandler = new Handler(this);
		btnOk = (TextView) findViewById(R.id.peter_selectphoto_ok_btns);
		if (max_count <= 1) {
			btnOk.setVisibility(View.GONE);
		}
		if (userSelectedImage.size() > 0) {
			btnOk.setText("完成(" + userSelectedImage.size() + "/" + max_count
					+ ")");
			// btnOk.setEnabled(true);
		}
		photoDirText = (TextView) findViewById(R.id.peter_selectphoto_bottom_dir);
		photoCountText = (TextView) findViewById(R.id.peter_selectphoto_bottom_count);
		mGridView = (GridView) findViewById(R.id.peter_selectphoto__gridview);
	}

	public void openpopup(View view) {
	}

	/**
	 * 扫描所有图片
	 */
	private void getImages() {
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
			return;
		}
		// 显示进度条
		mProgressDialog = ProgressDialog.show(this, null, "正在加载...");
		mProgressDialog.show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				mHandler.sendEmptyMessage(0);
			}
		}).start();
	}

	private void initAdapter() {
		mImages = new ArrayList<String>();
		photoCountText.setText(mImages.size() + "张");
		photoDirText.setText(ALL_PHOTO_NAME);
		mAdapter = new MyGridviewAdapter(getApplicationContext(), mImages,
				R.layout.peter_selectphoto_grideview_item);
		mGridView.setAdapter(mAdapter);
	}

	class MyGridviewAdapter extends SuperAdapter<String> {

		public MyGridviewAdapter(Context context, List<String> datas, int layout) {
			super(context, datas, layout);
		}

		@Override
		public void convert(ViewHolder holder, final String t, int position) {
			final ImageView iv = (ImageView) holder
					.getView(R.id.peter_selectphoto_grideview_item_img);
			final TextView selctStatus = (TextView) holder
					.getView(R.id.peter_selectphoto_grideview_item_checkbox);
			if (max_count <= 1) {
				selctStatus.setVisibility(View.GONE);
			}
			Log.d("tag1", t + "");
			if (userSelectedImage.contains(t)) {
				selctStatus
						.setBackgroundResource(R.drawable.peter_selectphoto_selected_bg_press);
				iv.setColorFilter(Color.parseColor("#99000000"));
			}
			iv.setColorFilter(null);
			selctStatus
					.setBackgroundResource(R.drawable.peter_selectphoto_selected_bg_nol);
			iv.setImageResource(R.drawable.ic_launcher);
			ImageLoader.getInstance(3, Type.LIFO).loadImage(t, iv);
			iv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (max_count <= 1) {
						userSelectedImage.add(t);
						chackOk();
					}
					if (userSelectedImage.contains(t)) {
						userSelectedImage.remove(t);
						selctStatus
								.setBackgroundResource(R.drawable.peter_selectphoto_selected_bg_nol);
						iv.setColorFilter(null);
					} else {
						if (userSelectedImage.size() >= max_count) {
							Toast.makeText(getApplicationContext(),
									"最多选择" + userSelectedImage.size() + "张",
									Toast.LENGTH_SHORT).show();
							return;
						}
						userSelectedImage.add(t);
						selctStatus
								.setBackgroundResource(R.drawable.peter_selectphoto_selected_bg_press);
						iv.setColorFilter(Color.parseColor("#99000000"));
					}
					if (userSelectedImage == null
							|| userSelectedImage.size() <= 0) {
						btnOk.setText("完成");
						// btnOk.setEnabled(false);
					} else {
						btnOk.setText("完成(" + userSelectedImage.size() + "/"
								+ max_count + ")");
						// btnOk.setEnabled(true);
					}
				}
			});
			if (userSelectedImage.contains(t)) {
				selctStatus
						.setBackgroundResource(R.drawable.peter_selectphoto_selected_bg_press);
				iv.setColorFilter(Color.parseColor("#99000000"));
			}
		}
	}

	class ListImageDirPopupWindow extends SelectPhotoDirPopupWindow<String> {
		private ListView mListDir;

		public ListImageDirPopupWindow(int width, int height,
				List<String> datas, View convertView) {
			super(convertView, width, height, true, datas);
		}

		@Override
		public void initViews() {
			mListDir = (ListView) findViewById(R.id.peter_selectphoto_popupwindow_listview);
			mListDir.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			mListDir.setAdapter(new SuperAdapter<String>(context, mDatas,
					R.layout.peter_selectphoto_popupwindow_item) {
				@Override
				public void convert(ViewHolder helper, String item, int position) {
					if (item.equals(ALL_PHOTO_TAG)) {
						helper.setText(R.id.id_dir_item_name, ALL_PHOTO_NAME);
						ImageLoader
								.getInstance(3, Type.LIFO)
								.loadImage(
										mImages.get(0),
										(ImageView) helper
												.getView(R.id.peter_selectphoto_popupwindow_item_img));
						helper.setText(R.id.id_dir_item_count, mImages.size()
								+ "张");
						TextView tv = helper
								.getView(R.id.peter_selectphoto_popupwindow_item_status);
						tv.setBackgroundResource(0);
						tv.setText("");
						if (item.equals(nowPath)) {
							tv.setBackgroundResource(R.drawable.peter_selectphoto_selected_bg_press);
						}
						int count = 0;
						for (String selected : userSelectedImage) {
							String parentName = new File(selected)
									.getParentFile().getName();
							if (item.equals(parentName)) {
								count++;
							}
						}
						if (count > 0) {
							tv.setBackgroundResource(R.drawable.peter_selectphoto_selected_bg_press);
							tv.setText(count + "");
						}
						return;
					}

					List<String> items = allPic.get(item);
					helper.setText(R.id.id_dir_item_name, item);
					ImageLoader
							.getInstance(3, Type.LIFO)
							.loadImage(
									items.get(0),
									(ImageView) helper
											.getView(R.id.peter_selectphoto_popupwindow_item_img));
					helper.setText(R.id.id_dir_item_count, items.size() + "张");
					TextView tv = helper
							.getView(R.id.peter_selectphoto_popupwindow_item_status);
					tv.setBackgroundResource(0);
					tv.setText("");
					if (item.equals(nowPath)) {
						tv.setBackgroundResource(R.drawable.peter_selectphoto_selected_bg_press);
					}
					int count = 0;
					for (String selected : userSelectedImage) {
						String parentName = new File(selected).getParentFile()
								.getName();
						if (item.equals(parentName)) {
							count++;
						}
					}
					if (count > 0) {
						tv.setBackgroundResource(R.drawable.peter_selectphoto_selected_bg_press);
						tv.setText(count + "");
					}
				}
			});
		}

		@Override
		public void initEvents() {
			mListDir.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					String tag = parentNames.get(position);
					if (nowPath != null && nowPath.equals(tag)) {
						mListImageDirPopupWindow.dismiss();
						return;
					}
					nowPath = tag;
					if (tag.equals(ALL_PHOTO_TAG)) {
						mListImageDirPopupWindow.dismiss();
						initAdapter();
						return;
					}

					List<String> mImageFloders = allPic.get(tag);
					List<String> datas = new ArrayList<String>();

					for (String mInFloder : mImageFloders) {
						datas.add(mInFloder);
					}
					mAdapter = new MyGridviewAdapter(getApplicationContext(),
							mImages, R.layout.peter_selectphoto_grideview_item);
					mGridView.setAdapter(mAdapter);
					photoCountText.setText(mImages.size() + "张");
					mListImageDirPopupWindow.dismiss();
				}
			});
		}

		@Override
		public void init() {

		}

		@Override
		protected void beforeInitWeNeedSomeParams(Object... params) {
		}

	}

	@Override
	public boolean handleMessage(Message msg) {
		mProgressDialog.dismiss();
		initAdapter();
		return false;
	}

	public void showPopupWindow(View view, Context context, View asView) {
		// 一个自定义的布局，作为显示的内容
		View contentView = LayoutInflater.from(context).inflate(
				R.layout.popup_imageselect, null);
		popupWindow = new PopupWindow(contentView, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT, true);
		RelativeLayout rel_popupwindow = (RelativeLayout) contentView
				.findViewById(R.id.rel_popupwindow);
		popupWindow.setTouchable(true);
		popupWindow.setTouchInterceptor(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return false;
				// 这里如果返回true的话，touch事件将被拦截
				// 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
			}
		});
		// 我觉得这里是API的一个bug
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		// 设置好参数之后再show
		popupWindow.showAsDropDown(asView, 0, 0);
		rel_popupwindow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (popupWindow.isShowing()) {
					popupWindow.dismiss();
				}
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode != Activity.RESULT_OK) {// result is not correct
			return;
		} else {
		}
	}
}
