package com.sunnyit.common.thread;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.jakewharton.disklrucache.DiskLruCache;
import com.jakewharton.disklrucache.DiskLruCache.Snapshot;
import com.sunnyit.common.cache.DecodeImage;
import com.sunnyit.common.cache.DiskCacheUtil;
import com.sunnyit.common.cache.ImageCache;
import com.sunnyit.common.encrypt.StringToMd5;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

/**   
* @Title: HttpImgUnit.java 
* @Package com.sunnyit.common.http 
* @Description: TODO
* @author yk
* @date 2015��8��3�� ����4:25:23 
* @version V1.0   
*/
public class ImgUtil extends Thread {
	
	private String murl;
	private ImageView mimageView;
	
	private ImageCache mimageCache;
	private DiskCacheUtil mimageDiskCache;
	
	private Handler mhandler=new Handler(){
		public void handleMessage(Message msg) {
			if(mimageView.getTag().equals(murl))
			{
				mimageView.setImageBitmap((Bitmap) msg.obj);
			}
		};
	};
	
	/**
	* @author yk
	* @date 2015��8��4�� ����10:11:34 
	* @param url ͼƬ���ʵĵ�ַ
	* @param imageview imageview�ؼ�
	* @param imagecache   ͼƬ����
	 */
	public ImgUtil(String url,ImageView imageview,ImageCache imagecache,DiskCacheUtil imageDiskCache) {
		// TODO Auto-generated constructor stub
		this.murl=url;
		this.mimageView=imageview;
		this.mimageCache=imagecache;
		this.mimageDiskCache=imageDiskCache;
	}
	
	/**
	 * �����������ֱ�Ӽ��أ��������������ʼ���
	 */
	@Override
	public void run() {
		//�ȴ��ڴ滺���м���
		Bitmap cachebitmap=mimageCache.getMcache().get(murl);
		if(cachebitmap==null)
		{
			//���̻����м���
			cachebitmap = getBitmapFromSnapshot(mimageDiskCache.getBitmapFromDisk(murl));
	        if (cachebitmap != null) {
	        	//Log.i("disk", murl);
	        	mimageCache.getMcache().put(murl, cachebitmap);
	        	Message message=Message.obtain();
				message.obj=cachebitmap;
				mhandler.sendMessage(message);
	        }
	        else
	        {
	        	//web����
	        	//Log.i("web", murl);
	        	OutputStream outputStream=null;
				try {
					String key =StringToMd5.hashKeyForUrl(murl);
		             // ���سɹ���ֱ�ӽ�ͼƬ��д���ļ�����
		             DiskLruCache.Editor editor = mimageDiskCache.getMdiskLruCache().edit(key);
		             if (editor != null) {
		                 outputStream = editor.newOutputStream(0);
		                 if (getImageStream(outputStream)) {
		                     editor.commit();
		                 } else {
		                     editor.abort();
		                 }
		             }
		             mimageDiskCache.getMdiskLruCache().flush();

		             cachebitmap = getBitmapFromSnapshot(mimageDiskCache.getBitmapFromDisk(murl));
		             if (cachebitmap != null) {
		                 // ��ͼƬ���뵽�ڴ滺����
		            	 mimageCache.getMcache().put(murl, cachebitmap);
		             }
					 Message message=Message.obtain();
		             message.obj=cachebitmap;
		             mhandler.sendMessage(message);
		            
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        	finally {
	        		try {
						if(outputStream!=null)
						{
							outputStream.close();
						}
	        		} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
	        }
		}
		else
		{
			//Log.i("cache", murl);
			Message message=Message.obtain();
			message.obj=cachebitmap;
			mhandler.sendMessage(message);
		}
	}

	/**
	* @author yk 
	* @date 2015��8��5�� ����3:26:21 
	* @Title: getImageStream 
	* @Description: ��ȡͼƬ�ļ���
	* @param outputStream
	* @return    �趨�ļ� 
	* @return Boolean    �������� 
	* @throws
	 */
	private Boolean getImageStream(OutputStream outputStream) {
		HttpURLConnection conn=null;
		BufferedInputStream in=null;
		BufferedOutputStream out=null;
		try {
			URL httpurl=new URL(murl);
			try {
				conn=(HttpURLConnection)httpurl.openConnection();
				//conn.setConnectTimeout(5000);
				//conn.setRequestMethod("GET");
				//conn.setDoInput(true);
				in=new BufferedInputStream(conn.getInputStream(), 8 * 1024);
				//Bitmap bitmap=BitmapFactory.decodeStream(in);
				out = new BufferedOutputStream(outputStream, 8 * 1024); 
                int b;
                while ((b = in.read()) != -1) {
                    out.write(b);
                }
				/* mimageCache.getMcache().put(murl, bitmap);
				 Message message=Message.obtain();
	             message.obj=bitmap;
	             mhandler.sendMessage(message);*/
                return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				if(conn!=null)
				{
					conn.disconnect();
				}
				try {
					if(in!=null)
					{
						in.close();
					}
					if(out!=null)
					{
						out.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	* @author yk 
	* @date 2015��8��5�� ����5:25:06 
	* @Title: getBitmapFromSnapshot 
	* @Description: TODO
	* @param snapShot
	* @return    �趨�ļ� 
	* @return Bitmap    �������� 
	* @throws
	 */
	private Bitmap getBitmapFromSnapshot(Snapshot snapShot)
	{
		if (snapShot != null) {
            InputStream in = snapShot.getInputStream(0);
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            //Bitmap bitmap = DecodeImage.decodeSampledBitmapFromResource(in, mimageView);
            return bitmap;
        }
		return null;
	}
}


