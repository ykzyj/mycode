package com.sunnyit.common.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.sunnyit.R;
import com.sunnyit.common.util.ImageLoader;
import com.sunnyit.common.util.ImageLoader.Type;
import com.sunnyit.common.util.ImageThumbnail;
import com.sunnyit.common.util.SuperAdapter;
import com.sunnyit.common.util.SuperAnatherHeadAdapter;
import com.sunnyit.common.util.UIUtil;
import com.sunnyit.common.util.ViewHolder;
import com.sunnyit.common.view.SelectPhotoDirPopupWindow;

public class SelectPhotoActivity extends Activity implements Callback {

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
	private final static int TAKEPHOTO_REQUEST_CODE = 1;
	private int position;
	private String name;
	private PopupWindow popupWindow;
	private RelativeLayout peter_selectphoto_top_bar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.peter_activity_selectphoto);
		initData();
		initView();
		getImages();
		initListDirPopupWindw();
	}

	public void onClickOk(View view) {
		chackOk();
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

	public void toPreviewPhoto(View view) {
		Intent intent = new Intent(this, GalleryPreviewPhotoActivity.class);
		intent.putStringArrayListExtra("DATA", userSelectedImage);
		startActivity(intent);
		disPopupWindow();
	}

	public void toTakePhoto(View view) {
		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		name = new DateFormat().format("yyyyMMdd_hhmmss",
				Calendar.getInstance(Locale.CHINA))
				+ ".jpg";
		Uri imageUri = Uri.fromFile(new File(Environment
				.getExternalStorageDirectory(), name));
		// 指定照片保存路径（SD卡），workupload.jpg为一个临时文件，每次拍照后这个图片都会被替换
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		startActivityForResult(cameraIntent, TAKEPHOTO_REQUEST_CODE);
		disPopupWindow();
	}

	public void onClicktakePhoto(View view) {
		showPopupWindow(view, SelectPhotoActivity.this,
				peter_selectphoto_top_bar);

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

	private void initListDirPopupWindw() {
		mListImageDirPopupWindow = new ListImageDirPopupWindow(
				LayoutParams.MATCH_PARENT, (int) (UIUtil.SCREEN_HEIGHT * 0.7),
				parentNames, LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.peter_selectphoto_popupwindow, null));
		mListImageDirPopupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1.0f;
				getWindow().setAttributes(lp);
			}
		});
	}

	public void openpopup(View view) {
//		mListImageDirPopupWindow.showAsDropDown(view, 0, 0);
//		WindowManager.LayoutParams lp = getWindow().getAttributes();
//		lp.alpha = .3f;
//		getWindow().setAttributes(lp);
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
				Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				ContentResolver mContentResolver = SelectPhotoActivity.this
						.getContentResolver();
				Cursor mCursor = mContentResolver
						.query(mImageUri, null,
								MediaStore.Images.Media.MIME_TYPE + "=? or "
										+ MediaStore.Images.Media.MIME_TYPE
										+ "=? or "
										+ MediaStore.Images.Media.MIME_TYPE
										+ "=?", new String[] { "image/jpeg",
										"image/png", "image/jpg" },
								MediaStore.Images.Media.DATE_TAKEN);
				mCursor.moveToLast();
				do {
					if (mCursor.getCount() != 0) {
						String path = null;
						String parentName = null;
						path = mCursor.getString(mCursor
								.getColumnIndex(MediaStore.Images.Media.DATA));
						parentName = new File(path).getParentFile().getName();
						if (parentName.equals("SafePhoneImage")
								|| parentName.equals("SafePhoneCamera")) {
							continue;
						}
						List<String> list = allPic.get(parentName);
						if (list == null) {
							list = new ArrayList<String>();
							list.add(path);
							parentNames.add(parentName);
							allPic.put(parentName, list);
						} else {
							list.add(path);
						}
					}
				} while (mCursor.moveToPrevious());
				mCursor.close();
				mHandler.sendEmptyMessage(0);
			}
		}).start();
	}

	private void initAdapter() {
		mImages = new ArrayList<String>();
		for (String parentName : parentNames) {

			if (parentName.equals(ALL_PHOTO_TAG)) {
				continue;
			}
			List<String> paths = allPic.get(parentName);
			for (String path : paths) {
				mImages.add(path);
			}
		}
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
			// iv.setLayoutParams(lp);
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

	class MyHeadGridviewAdapter extends SuperAnatherHeadAdapter<String> {

		public MyHeadGridviewAdapter(Context context, List<String> datas,
				int headlayout, int bodyLayout) {
			super(context, datas, headlayout, bodyLayout);
		}

		@Override
		public void convertHead(ViewHolder headHolder) {
			ImageView takePhoto = headHolder
					.getView(R.id.peter_selectphoto_grideview_item_img);
			takePhoto.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent cameraIntent = new Intent(
							MediaStore.ACTION_IMAGE_CAPTURE);
					Uri imageUri = Uri.fromFile(new File(Environment
							.getExternalStorageDirectory(), name));
					// 指定照片保存路径（SD卡），workupload.jpg为一个临时文件，每次拍照后这个图片都会被替换
					cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
					startActivityForResult(cameraIntent, TAKEPHOTO_REQUEST_CODE);
				}
			});
		}

		@Override
		public ViewHolder convertBody(Context context, final String t,
				View convertView, ViewGroup parent, int mBodyLayout2,
				int position) {
			ViewHolder holder = ViewHolder.get(context, convertView, parent,
					mBodyLayout2, position);
			final ImageView iv = (ImageView) holder
					.getView(R.id.peter_selectphoto_grideview_item_img);
			final TextView selctStatus = (TextView) holder
					.getView(R.id.peter_selectphoto_grideview_item_checkbox);
			if (max_count <= 1) {
				selctStatus.setVisibility(View.GONE);
			}
			iv.setColorFilter(null);
			selctStatus
					.setBackgroundResource(R.drawable.peter_selectphoto_selected_bg_nol);
			// iv.setLayoutParams(lp);
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
			return holder;
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
							datas, R.layout.peter_selectphoto_grideview_item);
					mGridView.setAdapter(mAdapter);
					photoCountText.setText(datas.size() + "张");
					photoDirText.setText(tag);
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

	private String photoLocalPath;
	private Bitmap bitMap;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode != Activity.RESULT_OK) {// result is not correct
			return;
		} else {
			if (requestCode == TAKEPHOTO_REQUEST_CODE) {
				// 将保存在本地的图片取出并缩小后显示在界面上
				Bitmap camorabitmap = BitmapFactory.decodeFile(Environment
						.getExternalStorageDirectory() + "/" + name);
				if (null != camorabitmap) {
					// 下面这两句是对图片按照一定的比例缩放，这样就可以完美地显示出来。
					int scale = ImageThumbnail.reckonThumbnail(
							camorabitmap.getWidth(), camorabitmap.getHeight(),
							500, 600);
					bitMap = ImageThumbnail.PicZoom(camorabitmap,
							camorabitmap.getWidth() / scale,
							camorabitmap.getHeight() / scale);
					// 由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
					camorabitmap.recycle();
					// 将处理过的图片显示在界面上，并保存到本地
					photoLocalPath = ImageThumbnail.savaPhotoToLocal(data,
							bitMap);
					userSelectedImage.add(photoLocalPath);
					chackOk();
				}
			}
		}
	}

	private void disPopupWindow() {
		if (popupWindow != null) {
			popupWindow.dismiss();
		}
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

}
