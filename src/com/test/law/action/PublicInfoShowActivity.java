package com.sunnyit.law.action;

import java.io.UnsupportedEncodingException;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.law.model.PublishInfo;

import android.os.Bundle;
import android.webkit.WebView;

/**   
* @Title: HiddenList.java 
* @Package com.sunnyit.hiddencheck.action 
* @Description: TODO
* @author yk
* @date 2015年9月7日 下午3:27:12 
* @version V1.0   
*/
public class PublicInfoShowActivity extends BaseActivity {
	
	private TopBarView topbar_law_show;
	private WebView wv_law_show;
	
	private PublishInfo mInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.law_info_show);
		
		mInfo = (PublishInfo) getIntent().getSerializableExtra("PublishInfo"); 
		
		initComponent();
		
		topbar_law_show.setTopBarClick(new ITopBarClick() {
			
			@Override
			public void onClickRightBut() {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onClickLeftBut() {
				// TODO Auto-generated method stub
				finish();
			}
		});
		topbar_law_show.setTitle(mInfo.getKeyword()+"信息");
		
		wv_law_show.getSettings().setDefaultTextEncodingName("UTF -8");//设置默认为utf-8  
		wv_law_show.loadData(mInfo.getContent(), "text/html; charset=UTF-8", null);//这种写法可以正确解码  
		
		/*try {
			wv_law_show.getSettings().setDefaultTextEncodingName("utf-8"); 
			String html = new String(mInfo.getContent().toString().getBytes("gbk"),"utf-8");
			wv_law_show.loadData(html, "text/html", "gbk"); 
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   */
		
	}

	private void initComponent() {
		// TODO Auto-generated method stub
		topbar_law_show=(TopBarView) findViewById(R.id.topbar_law_show);
		wv_law_show=(WebView) findViewById(R.id.wv_law_show);
	}
	
}


