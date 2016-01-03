package com.sunnyit.example.action;

import java.util.ArrayList;
import java.util.List;

import com.sunnyit.R;
import com.sunnyit.R.id;
import com.sunnyit.R.layout;
import com.sunnyit.R.menu;
import com.sunnyit.common.adapter.AdapterUnit;
import com.sunnyit.common.adapter.ViewHolderUnit;
import com.sunnyit.example.data.InfoAdapter;
import com.sunnyit.example.data.MyAdapter;
import com.sunnyit.example.model.test_info;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	private ListView mlistview;
	private List<test_info> mdate=new ArrayList<test_info>();
	private InfoAdapter madapter; 
	private MyAdapter myAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initDate();
		initView();
		
	}

	private void initView() {
		// TODO Auto-generated method stub
		mlistview=(ListView) findViewById(R.id.list_info);
		//mlistview.setAdapter(madapter);
		mlistview.setAdapter(myAdapter);
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
			test_info info=new test_info(R.drawable.phone,"����"+String.valueOf(i),
					"2015-07-30","������ʲô�����������������������������������������������������������������",false);
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
