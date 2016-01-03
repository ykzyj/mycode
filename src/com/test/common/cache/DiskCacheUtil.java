package com.sunnyit.common.cache;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.jakewharton.disklrucache.DiskLruCache;
import com.jakewharton.disklrucache.DiskLruCache.Snapshot;
import com.sunnyit.common.encrypt.StringToMd5;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

/**   
* @Title: DiskCacheUnit.java 
* @Package com.sunnyit.common.cache 
* @Description: TODO
* @author yk
* @date 2015��8��4�� ����2:09:29 
* @version V1.0   
*/
public class DiskCacheUtil {
	
	// �ļ�����Ĭ�� 10M
    private int DISK_CACHE_DEFAULT_SIZE = 10 * 1024 * 1024;
	
    //�������
	protected DiskLruCache mdiskLruCache;
	
	/**
	* @author yk
	* @date 2015��8��5�� ����11:22:41 
	* @param context ���������������
	 */
	public DiskCacheUtil(Context context) {
		// TODO Auto-generated constructor stub
		 try {
	            File cacheDir = getDiskCacheDir(context, "cachefile");
	            if (!cacheDir.exists()) {
	                cacheDir.mkdirs();
	            }
	            mdiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(context), 1, DISK_CACHE_DEFAULT_SIZE);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}
	
	/**
	* @author yk 
	* @date 2015��8��5�� ����11:20:38 
	* @Title: getDiskCacheDir 
	* @Description: ��ȡ����Ĵ��̵�ַ�������ַ�������򴴽��õ�ַ
	* @param context
	* @param uniqueName
	* @return    �趨�ļ� 
	* @return File    �������� 
	* @throws
	 */
	private File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

	/**
	* @author yk 
	* @date 2015��8��5�� ����3:01:36 
	* @Title: getAppVersion 
	* @Description: ��ȡ����İ汾��
	* @param context
	* @return    �趨�ļ� 
	* @return int    �������� 
	* @throws
	 */
    private int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
    * @author yk 
    * @date 2015��8��5�� ����5:24:02 
    * @Title: getBitmapFromDisk 
    * @Description: ��ȡ��������
    * @param url url��md5����
    * @return    �趨�ļ� 
    * @return Snapshot    �������� 
    * @throws
     */
    public Snapshot getBitmapFromDisk(String url)
    {
    	try {
            String key = StringToMd5.hashKeyForUrl(url);
            DiskLruCache.Snapshot snapShot = mdiskLruCache.get(key);
            return snapShot;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

	public DiskLruCache getMdiskLruCache() {
		return mdiskLruCache;
	}

}


