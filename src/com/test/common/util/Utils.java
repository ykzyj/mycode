package com.sunnyit.common.util;


import android.content.Context;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;


public class Utils {

	public static class PostTX {
		public static final String CMD_MESSAGE_BROCAST_STR_POST = "com.yaoma.message.cmd.post";
		public static final int POST_TYPE_HAVE_IMAGE = 1;
		public static final int POST_TYPE_HAVENT_IMAGE = 0;

		public static final int POSTTX_TYPE_PINGLUN = 0;
		public static final int POSTTX_TYPE_HUIFU = 1;
		public static final int POSTTX_TYPE_ZAN = 2;
		public static final int POSTTX_TYPE_DASHANG = 3;
		public static final int POSTTX_TYPE_ATSHUI = 4;
		public static final int POSTTX_TYPE_HAIYOUSHUI = 5;

		public static final String BROCAST_UPDATE_TX_NUMBER = "com.yaoma.main.update.tx";
	}

	public static class PostType {
		public static final int INPUT_TYPE_ZHIWEI = 101;
		public static final int INPUT_TYPE_JIANLI = 102;
		public static final int INPUT_TYPE_ZHAOPINHUI = 103;
	}

	public static class BitmapTypeTag {
		public static String BIT_CIRCLE = "image/circle/";
		public static String BIT_HELP = "image/help/";
		public static String BIT_MEMBER = "image/member/";
		public static String BIT_MYGROUP = "image/mygroup/";
		public static String BIT_ZHAOPINHUI = "image/zhaopinhui/";
		public static String BIT_MINGPIAN = "image/mingpian/";
		public static String BIT_POST = "image/post/";
	}


	public class SearchType {
		public static final int SEARCH_TYPE_USER = 0;
		public static final int SEARCH_TYPE_CIRCLE = 1;
	}

	private static Builder optionBuilder = new DisplayImageOptions.Builder();

	public static boolean isEmpty(String str) {
		if (str == null || str.equals("")) {
			return true;
		}
		return false;
	}

	public static void setPreViewImage(Context context, String ImageUri, ImageView imageView, int deflutRes) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory().discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).enableLogging().build();
		ImageLoader.getInstance().init(config);
		ImageLoader.getInstance().displayImage("file://"+ImageUri, imageView, optionBuilder.showStubImage(deflutRes).showImageForEmptyUri(deflutRes).showImageOnFail(deflutRes).cacheInMemory().cacheOnDisc().build());
	}

	public static void setPreViewImage(Context context, String ImageUri, ImageView imageView, int deflutRes, ImageLoadingListener mImageLoadingListener) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory().discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).enableLogging().build();
		ImageLoader.getInstance().init(config);
		ImageLoader.getInstance().displayImage("file://"+ImageUri, imageView, optionBuilder.showStubImage(deflutRes).showImageForEmptyUri(deflutRes).showImageOnFail(deflutRes).cacheInMemory().cacheOnDisc().build(), mImageLoadingListener);
	}



}
