package com.sunnyit.enforcement.data;

import java.util.ArrayList;
import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.adapter.AdapterUtil;
import com.sunnyit.common.adapter.ViewHolderUtil;
import com.sunnyit.enforcement.model.CheckTables;
import com.sunnyit.enforcement.model.Standard_CK_Table_Item;

import android.content.Context;
import android.graphics.Color;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

/**   
* @Title: DailyCheckAdapter.java 
* @Package com.sunnyit.enforcement.data 
* @Description: TODO
* @author yk
* @date 2015年9月11日 下午9:21:46 
* @version V1.0   
*/
public class Standard_CK_Table_ItemIngAdapter extends AdapterUtil<Standard_CK_Table_Item> {
	
	private QualifiedListener mQualifiedListener;
	
	public Standard_CK_Table_ItemIngAdapter(Context context, List<Standard_CK_Table_Item> datas, int layoutId) {
		super(context, datas, layoutId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initShowDate(final ViewHolderUtil viewHolder, final Standard_CK_Table_Item t, final int position) {
		final RadioGroup rg_isok=(RadioGroup) viewHolder.getMconvertView().findViewById(R.id.rg_isok);
		final RadioButton rb_ck_stand_yes = (RadioButton) viewHolder.getMconvertView().findViewById(R.id.rb_ck_stand_yes);
        final RadioButton rb_ck_stand_no = (RadioButton) viewHolder.getMconvertView().findViewById(R.id.rb_ck_stand_no);
        rg_isok.setOnCheckedChangeListener(null);
        rg_isok.clearCheck();
        
		viewHolder.setText(R.id.txt_stand_item_content, t.getItem_description());
		rb_ck_stand_yes.setTextColor(Color.parseColor("#FF7D899D"));
        rb_ck_stand_no.setTextColor(Color.parseColor("#FF7D899D"));
		if(t.getItem_isQualified()!=null&&!t.getItem_isQualified().equals(""))
		{
			if(t.getItem_isQualified().equals("YES"))
			{
				rb_ck_stand_yes.setTextColor(Color.parseColor("#28aae1"));
				rb_ck_stand_yes.setChecked(true);
			}
			else
			{
				rb_ck_stand_no.setTextColor(Color.parseColor("#28aae1"));
				rb_ck_stand_no.setChecked(true);
			}
		}
		
		rg_isok.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub
				int radioButtonId = group.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) viewHolder.getMconvertView().findViewById(radioButtonId);
                
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
		public void setItenIsQualified(Standard_CK_Table_Item standard_Table_Item,boolean checked);
	}
	
}


