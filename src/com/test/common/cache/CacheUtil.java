package com.sunnyit.common.cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**   
* @Title: ImageCacheUnit.java 
* @Package com.sunnyit.common.http 
* @Description: ͼƬ�����ڴ�
* @author yk
* @date 2015��8��4�� ����9:55:51 
* @version V1.0   
*/
public abstract class CacheUtil<T1,T2> {

	
	/**
	 * ͼƬ�������ڴ�
	 */
	private LruCache<T1, T2> mcache;
	
	/**
	* @author yk
	* @date 2015��8��4�� ����9:59:43     �趨�ļ�
	* @Description: ��ʼ���������ڴ�����
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


