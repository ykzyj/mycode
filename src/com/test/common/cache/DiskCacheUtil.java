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
* @date 2015年8月4日 下午2:09:29 
* @version V1.0   
*/
public class DiskCacheUtil {
	
	// 文件缓存默认 10M
    private int DISK_CACHE_DEFAULT_SIZE = 10 * 1024 * 1024;
	
    //缓存变量
	protected DiskLruCache mdiskLruCache;
	
	/**
	* @author yk
	* @date 2015年8月5日 上午11:22:41 
	* @param context 创建缓存的上下文
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
	* @date 2015年8月5日 上午11:20:38 
	* @Title: getDiskCacheDir 
	* @Description: 获取缓存的磁盘地址，如果地址不存在则创建该地址
	* @param context
	* @param uniqueName
	* @return    设定文件 
	* @return File    返回类型 
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
	* @date 2015年8月5日 下午3:01:36 
	* @Title: getAppVersion 
	* @Description: 获取程序的版本号
	* @param context
	* @return    设定文件 
	* @return int    返回类型 
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
    * @date 2015年8月5日 下午5:24:02 
    * @Title: getBitmapFromDisk 
    * @Description: 获取缓存数据
    * @param url url的md5编码
    * @return    设定文件 
    * @return Snapshot    返回类型 
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


