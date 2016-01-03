package com.sunnyit.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class UIUtil {

	public static int SCREEN_WIDTH;
	public static int SCREEN_HEIGHT;


	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static void copayDatabaseToSdS() {
		File file = new File("/data/data/com.yaoma/databases");
		File[] files = file.listFiles();
		if (files == null) {
			return;
		}
		File savefileter = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/YM_COPE_DATABASE");
		if (!savefileter.exists()) {
			savefileter.mkdirs();
		} else {
			File[] saveFileters = savefileter.listFiles();
			for (File ff : saveFileters) {
				ff.delete();
			}
		}

		for (File save : files) {
			try {
				FileOutputStream fos = new FileOutputStream(savefileter.getAbsoluteFile() + "/" + save.getName());
				FileInputStream fis = new FileInputStream(save);
				byte[] b = new byte[8859];
				int len = 0;
				while ((len = fis.read(b)) != -1) {
					fos.write(b, 0, len);
				}
				fos.flush();
				fos.close();
				fis.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	public static void setOnclickBack(EditText editText,final View view) {

		editText.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
                    view.setBackgroundColor(Color.parseColor("#009eff")) ; 
				} else {
                    view.setBackgroundColor(Color.parseColor("#d7d7d7")) ; 
				}
			}
		});
	}
	




	public static void init(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		UIUtil.SCREEN_WIDTH = dm.widthPixels; 
		UIUtil.SCREEN_HEIGHT = dm.heightPixels;
	}


	private static String YAOMA_FILETER_NAME = "/YaoMa";
	public static String YAOMA_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + YAOMA_FILETER_NAME;
	public static String IMAGE_CACHES = YAOMA_DIR + "/YaoMaCache";
	public static String IMAGE_SENDS = YAOMA_DIR + "/YaoMaImage";



	
}
