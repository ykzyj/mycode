package com.sunnyit.common.cache;

import android.graphics.Bitmap;

/**   
* @Title: ImageCache.java 
* @Package com.sunnyit.common.http 
* @Description: TODO
* @author yk
* @date 2015年8月4日 下午1:55:20 
* @version V1.0   
*/
public class ImageCache extends CacheUtil<String, Bitmap> {
	
	public ImageCache() {
		// TODO Auto-generated constructor stub
		super();
	}

	@Override
	public int getreturn(String key, Bitmap value) {
		// TODO Auto-generated method stub
		return value.getByteCount();
	}

}


