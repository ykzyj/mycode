package com.sunnyit.enforcement.action;

import com.sunnyit.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

/**   
* @Title: DailyCheckList.java 
* @Package com.sunnyit.hiddencheck.action 
* @Description: TODO
* @author yk
* @date 2015年9月7日 下午3:20:13 
* @version V1.0   
*/
public class ReviewCheckChoice extends Fragment {

	
	private View mView;
	
	private Context mContext;
	
	private Button but_daily_check;
	private Button but_stand_check;
	private Button but_special_check;
	
	public ReviewCheckChoice(Context context) {
		// TODO Auto-generated constructor stub
		this.mContext=context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mView=inflater.inflate(R.layout.en_review_choice, container, false);
		
		return mView;
	}
	
	private void initComponent() {
		// TODO Auto-generated method stub
		but_daily_check=(Button) mView.findViewById(R.id.but_daily_check);
		but_stand_check=(Button) mView.findViewById(R.id.but_stand_check);
		but_special_check=(Button) mView.findViewById(R.id.but_special_check);
	}
	
}


