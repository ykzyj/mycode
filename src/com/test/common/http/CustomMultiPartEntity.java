package com.sunnyit.common.http;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.http.entity.mime.MultipartEntity;

/**   
* @Title: CustomMultiPartEntity.java 
* @Package com.sunnyit.common.http 
* @Description: TODO
* @author yk
* @date 2015年9月1日 上午9:23:23 
* @version V1.0   
*/
public class CustomMultiPartEntity extends MultipartEntity {
	
	private ProgressListener listener;	
	
	public void setProgressListener(ProgressListener listener){
		this.listener = listener;
	}
	

	@Override
	public void writeTo(OutputStream outstream) throws IOException {
		super.writeTo(new CountingOutputStream(outstream));
	}	
	
	private class CountingOutputStream extends FilterOutputStream {
		private long transferred;
		
		public CountingOutputStream(OutputStream out) {
			super(out);
			this.transferred = 0;			
		}		
		
		@Override
		public void write(int b) throws IOException {
			out.write(b);
			this.transferred++;
			if(listener != null){
				listener.cumulative(this.transferred);			
				listener.progress((int) ((transferred / (float) getContentLength()) * 100));
			}
		}		
		
		@Override
		public void write(byte[] b, int off, int len) throws IOException {
			out.write(b, off, len);
			this.transferred += len;
			if(listener != null){
				listener.cumulative(this.transferred);
				listener.progress((int) ((transferred / (float) getContentLength()) * 100));
			}
		}
	}	
	
	public interface ProgressListener {
		public void cumulative(long num);
		public void progress(int progress);
	}

}


