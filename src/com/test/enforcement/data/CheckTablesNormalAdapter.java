package com.sunnyit.enforcement.data;

import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.adapter.AdapterUtil;
import com.sunnyit.common.adapter.ViewHolderUtil;
import com.sunnyit.enforcement.model.CheckTables;

import android.content.Context;
import android.graphics.Color;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**   
* @Title: DailyCheckAdapter.java 
* @Package com.sunnyit.enforcement.data 
* @Description: TODO
* @author yk
* @date 2015年9月11日 下午9:21:46 
* @version V1.0   
*/
public class CheckTablesNormalAdapter extends AdapterUtil<CheckTables> {
	
	private QualifiedListener mQualifiedListener;
	
	public CheckTablesNormalAdapter(Context context, List<CheckTables> datas, int layoutId) {
		super(context, datas, layoutId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initShowDate(final ViewHolderUtil viewHolder, final CheckTables t, int position) {
		viewHolder.setText(R.id.txt_stand_item_content, t.getH_description());
		
		RadioGroup rg_isok=(RadioGroup) viewHolder.getMconvertView().findViewById(R.id.rg_isok);
		rg_isok.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				 	int radioButtonId = group.getCheckedRadioButtonId();
	                RadioButton rb = (RadioButton) viewHolder.getMconvertView().findViewById(radioButtonId);
	                
	                RadioButton rb_ck_stand_yes = (RadioButton) viewHolder.getMconvertView().findViewById(R.id.rb_ck_stand_yes);
	                RadioButton rb_ck_stand_no = (RadioButton) viewHolder.getMconvertView().findViewById(R.id.rb_ck_stand_no);
	                
	                rb_ck_stand_yes.setTextColor(Color.parseColor("#FF7D899D"));
	                rb_ck_stand_no.setTextColor(Color.parseColor("#FF7D899D"));
	                
	                rb.setTextColor(Color.parseColor("#28aae1")); 
	                
	                if(mQualifiedListener!=null)
	                {
	                	switch (radioButtonId) {
						case R.id.rb_ck_stand_yes:
							mQualifiedListener.setItenIsQualified(t,true);
							break;
						case R.id.rb_ck_stand_no:
							mQualifiedListener.setItenIsQualified(t,false);
							break;
						default:
							break;
						}
	                }
			}
		});
	}
	
	public void setQualifiedListener(QualifiedListener qualifiedListener) {
		mQualifiedListener=qualifiedListener;
	}
	
	
	public interface QualifiedListener{
		public void setItenIsQualified(CheckTables checkTable,boolean checked);
	}
	
}


