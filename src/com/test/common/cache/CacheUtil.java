package com.sunnyit.common.cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**   
* @Title: ImageCacheUnit.java 
* @Package com.sunnyit.common.http 
* @Description: 图片公用内存
* @author yk
* @date 2015年8月4日 上午9:55:51 
* @version V1.0   
*/
public abstract class CacheUtil<T1,T2> {

	
	/**
	 * 图片所申请内存
	 */
	private LruCache<T1, T2> mcache;
	
	/**
	* @author yk
	* @date 2015年8月4日 上午9:59:43     设定文件
	* @Description: 初始化所申请内存区域
	 */
	public CacheUtil(){
		// TODO Auto-generated constructor stub
		int maxmemory=(int) Runtime.getRuntime().maxMemory();
		int cachesize=(maxmemory/4);
		mcache=new LruCache<T1, T2>(cachesize){
			@Override
			protected int sizeOf(T1 key, T2 value) {
				// TODO Auto-generated method stub
				return getreturn(key,value);
			}
		};
	}

	public LruCache<T1, T2> getMcache() {
		return mcache;
	}
	
	
	public abstract int getreturn(T1 key,T2 value);
}


