package com.sunnyit.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Matrix;
import android.os.Environment;

public class ImageThumbnail {

	public static int reckonThumbnail(int oldWidth, int oldHeight,
			int newWidth, int newHeight) {
		if ((oldHeight > newHeight && oldWidth > newWidth)
				|| (oldHeight <= newHeight && oldWidth > newWidth)) {
			int be = (int) (oldWidth / (float) newWidth);
			if (be <= 1)
				be = 1;
			return be;
		} else if (oldHeight > newHeight && oldWidth <= newWidth) {
			int be = (int) (oldHeight / (float) newHeight);
			if (be <= 1)
				be = 1;
			return be;
		}
		return 1;
	}

	public static Bitmap PicZoom(Bitmap bmp, int width, int height) {
		int bmpWidth = bmp.getWidth();
		int bmpHeght = bmp.getHeight();
		Matrix matrix = new Matrix();
		matrix.postScale((float) width / bmpWidth, (float) height / bmpHeght);

		return Bitmap.createBitmap(bmp, 0, 0, bmpWidth, bmpHeght, matrix, true);
	}

	public static String savaPhotoToLocal(Intent data, Bitmap btp) {
		// ����ļ��в������򴴽��ļ��У�����bitmapͼ���ļ�����
		File rootdir = Environment.getExternalStorageDirectory();
		String imagerDir = rootdir.getPath() + "/DCIM/Camera";
		File dirpath = createDir(imagerDir);
		String filename = System.currentTimeMillis() + ".jpg";
		File tempFile = new File(dirpath, filename);
		String filePath = tempFile.getAbsolutePath();
		try {
			// ��bitmapתΪjpg�ļ�����
			FileOutputStream fileOut = new FileOutputStream(tempFile);
			btp.compress(CompressFormat.JPEG, 100, fileOut);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return filePath;
	}

	public static File createDir(String destDirName) {
		File dir = new File(destDirName);
		dir.mkdirs();
		return dir;
	}

}
