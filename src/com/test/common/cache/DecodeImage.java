package com.sunnyit.common.cache;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

/**   
* @Title: DecodeImage.java 
* @Package com.sunnyit.common.cache 
* @Description: TODO
* @author yk
* @date 2015��8��5�� ����4:44:22 
* @version V1.0   
*/
public class DecodeImage {
	
	public static Bitmap decodeSampledBitmapFromResource(InputStream in, ImageView imageview) throws IOException {
		int reqWidth=imageview.getWidth();
		int reqHeight=imageview.getHeight();
		// ��һ�ν�����inJustDecodeBounds����Ϊtrue������ȡͼƬ��С
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    //BitmapFactory.decodeResource(res, resId, options);
	    BitmapFactory.decodeStream(in, null, options);
	    // �������涨��ķ�������inSampleSizeֵ
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
	    // ʹ�û�ȡ����inSampleSizeֵ�ٴν���ͼƬ
	    options.inJustDecodeBounds = false;
	    in.skip(0);
	    return BitmapFactory.decodeStream(in, null, options);
	}
	
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// ԴͼƬ�ĸ߶ȺͿ��
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			// �����ʵ�ʿ�ߺ�Ŀ���ߵı���
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			// ѡ���͸�����С�ı�����ΪinSampleSize��ֵ���������Ա�֤����ͼƬ�Ŀ�͸�
			// һ��������ڵ���Ŀ��Ŀ�͸ߡ�
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}
}


