package test.action;


import java.util.ArrayList;
import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.view.ClickLoadListview;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import test.data.HttpJsonShow;
import test.data.InfoAdapter;
import test.data.MyAdapter;
import test.model.test_info;

public class TestActivity extends BaseActivity {
	
	private ClickLoadListview mlistview;
	private List<test_info> mdate=new ArrayList<test_info>();
	private InfoAdapter madapter; 
	private MyAdapter myAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initDate();
		initView();
		
		String url="http://www.imooc.com/api/teacher?type=4&num=30";
		new HttpJsonShow(this,url,
				mlistview,R.layout.list_item_info).start();
		/*new HttpJsonAsyncShow(this, mlistview, R.layout.list_item_info).execute(url);*/
		
	}

	private void initView() {
		// TODO Auto-generated method stub
		mlistview=(ClickLoadListview) findViewById(R.id.list_info);
		//mlistview.setAdapter(madapter);
		//mlistview.setAdapter(myAdapter);
		/*mlistview.setAdapter(new AdapterUnit<test_info>(this,mdate,R.layout.list_item_info) {

			@Override
			public void initShowDate(ViewHolderUnit viewHolder, test_info t) {
				// TODO Auto-generated method stub
				viewHolder.setImageResource(R.id.img_info, t.getImageid());
				viewHolder.setText(R.id.txt_info_title, t.getInfotitle());
				viewHolder.setText(R.id.txt_info_time, t.getInfotime());
				viewHolder.setText(R.id.txt_info_content, t.getInfocontent());
			}
		});*/
	}

	private void initDate() {
		// TODO Auto-generated method stub
		for(int i=1;i<15;i++)
		{
			test_info info=new test_info(R.drawable.phone,"±êÌâ"+String.valueOf(i),
					"2015-07-30","ÄÚÈÝÊÇÊ²Ã´£¬Äã²ÂÄã²ÂÄã²ÂÄã²ÂÄã²ÂÄã²ÂÄã²ÂÄã²ÂÄã²ÂÄã²ÂÄã²ÂÄã²ÂÄã²ÂÄã²ÂÄã²ÂÄã²ÂÄã²ÂÄã²ÂÄã²ÂÄã²ÂÄã²Â",false);
			mdate.add(info);
		}
		//madapter=new InfoAdapter(this, mdate);
		myAdapter=new MyAdapter(this, mdate,R.layout.list_item_info);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
