package com.sunnyit.common.file;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**   
* @Title: FileUtils.java 
* @Package com.sunnyit.common.file 
* @Description: TODO
* @author yk
* @date 2015��12��9�� ����10:51:51 
* @version V1.0   
*/
public class FileUtils {

    public static String getPath(Context context, Uri uri) 
    {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;
 
            try {
                cursor = context.getContentResolver().query(uri, projection,null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        }
 
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
 
        return null;
    }

}


