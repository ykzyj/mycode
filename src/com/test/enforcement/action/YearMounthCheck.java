package com.sunnyit.enforcement.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.sunnyit.R;
import com.sunnyit.enforcement.data.ExAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

/**   
* @Title: DailyCheckList.java 
* @Package com.sunnyit.hiddencheck.action 
* @Description: TODO
* @author yk
* @date 2015年9月7日 下午3:20:13 
* @version V1.0   
*/
public class YearMounthCheck extends Fragment {

	
	private View mView;
	
	private ExpandableListView exlist_govern_ym;
	private Context mContext;
	
	private List<BasicNameValuePair> fatherList;
	private List<List<BasicNameValuePair>> childList;
	
	private ExAdapter mAdapter;
	
	public YearMounthCheck(Context context) {
		// TODO Auto-generated constructor stub
		this.mContext=context;
		
		fatherList = new ArrayList<BasicNameValuePair>();
		childList = new ArrayList<List<BasicNameValuePair>>();
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mView=inflater.inflate(R.layout.en_check_yearmounth, container, false);
		
		initComponent();
		
		initExpandableListView();
		
		mAdapter=new ExAdapter(mContext, fatherList, childList);
		exlist_govern_ym.setAdapter(mAdapter);
		exlist_govern_ym.setGroupIndicator(null);
		exlist_govern_ym.setDivider(null);
		exlist_govern_ym.expandGroup(0);  
		
		exlist_govern_ym.setOnChildClickListener(new OnChildClickListener() 
		{	     
		    @Override
		    public boolean onChildClick(ExpandableListView parent, View v,
		            int groupPosition, int childPosition, long id) 
		    {
		    	String father_str=fatherList.get(groupPosition).getValue();
		    	String child_str=childList.get(groupPosition).get(childPosition).getValue();
		    	
		    	Intent intent=new Intent(mContext, HiddenMounthActivity.class);
		    	intent.putExtra("father_str", father_str);
		    	intent.putExtra("child_str", child_str);
	       		startActivity(intent);
	    	
		        return true;
		    }
		});
		
		return mView;
	}
	
	private void initComponent() {
		// TODO Auto-generated method stub
		exlist_govern_ym=(ExpandableListView) mView.findViewById(R.id.exlist_govern_ym);
	}
	
	
	private void initExpandableListView()
	{
		Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
		int year = c.get(Calendar.YEAR); 
		while(year>2012)
		{
			fatherList.add(new BasicNameValuePair("father", year+"年"));
			List<BasicNameValuePair> cList_yt = new ArrayList<BasicNameValuePair>();
			cList_yt.add(new BasicNameValuePair("child", "1月被检查企业"));
			cList_yt.add(new BasicNameValuePair("child", "2月被检查企业"));
			cList_yt.add(new BasicNameValuePair("child", "3月被检查企业"));
			cList_yt.add(new BasicNameValuePair("child", "4月被检查企业"));
			cList_yt.add(new BasicNameValuePair("child", "5月被检查企业"));
			cList_yt.add(new BasicNameValuePair("child", "6月被检查企业"));
			cList_yt.add(new BasicNameValuePair("child", "7月被检查企业"));
			cList_yt.add(new BasicNameValuePair("child", "8月被检查企业"));
			cList_yt.add(new BasicNameValuePair("child", "9月被检查企业"));
			cList_yt.add(new BasicNameValuePair("child", "10被月检查企业"));
			cList_yt.add(new BasicNameValuePair("child", "11被月检查企业"));
			cList_yt.add(new BasicNameValuePair("child", "12被月检查企业"));
			childList.add(cList_yt);
			
			year--;
		}
    }
}


