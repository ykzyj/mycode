package com.sunnyit.common.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.util.Base64;

/**   
* @Title: StringToMd5.java 
* @Package com.sunnyit.common.encrypt 
* @Description: TODO
* @author yk
* @date 2015年8月4日 下午2:45:01 
* @version V1.0   
*/
public class StringToMd5 {
	
	/**
	* @author yk 
	* @date 2015年8月4日 下午2:47:00 
	* @Title: hashKeyForKey 
	* @Description: 转md5
	* @param key
	* @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
    public static String hashKeyForUrl(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }
    
    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
    
    public static String md5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");

			byte[] b1 = md.digest(str.getBytes()); // !!!

			BASE64Encoder encoder = new BASE64Encoder();
			return encoder.encode(b1);
			
			
		} catch (Exception e) {
			return null;
		}
	}
    
}


