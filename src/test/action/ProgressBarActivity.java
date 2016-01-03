package test.action;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.progressbar.HorizontalNumberProgressBar;
import com.sunnyit.common.progressbar.RoundNumberProgressBar;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

/**   
* @Title: ProgressBarActivity.java 
* @Package test.action 
* @Description: TODO
* @author yk
* @date 2015年8月26日 下午3:27:07 
* @version V1.0   
*/
public class ProgressBarActivity extends BaseActivity {
	
	private HorizontalNumberProgressBar mProgressBarH;  
    private static final int MSG_PROGRESS_UPDATE = 0x110;
    
    private Handler mHandler = new Handler() {  
        public void handleMessage(android.os.Message msg) {  
            int progressh1 = mProgressBarH.getProgress();  
            mProgressBarH.setProgress(++progressh1);  
            if (progressh1 >= 100) {  
                mHandler.removeMessages(MSG_PROGRESS_UPDATE);  
                  
            }  
            mHandler.sendEmptyMessageDelayed(MSG_PROGRESS_UPDATE, 100);  
        };  
    };  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_custom);
		mProgressBarH = (HorizontalNumberProgressBar) findViewById(R.id.id_progressbarh);  
        mHandler.sendEmptyMessage(MSG_PROGRESS_UPDATE);  
	}

}


