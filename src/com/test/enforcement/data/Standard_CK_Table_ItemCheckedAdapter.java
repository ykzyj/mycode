package com.sunnyit.enforcement.data;

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

/**   
* @Title: DailyCheckAdapter.java 
* @Package com.sunnyit.enforcement.data 
* @Description: TODO
* @author yk
* @date 2015年9月11日 下午9:21:46 
* @version V1.0   
*/
public class Standard_CK_Table_ItemCheckedAdapter extends AdapterUtil<Standard_CK_Table_Item> {
	
	private RepairTypeListener mRepairTypeListener;
	
	public Standard_CK_Table_ItemCheckedAdapter(Context context, List<Standard_CK_Table_Item> datas, int layoutId) {
		super(context, datas, layoutId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initShowDate(final ViewHolderUtil viewHolder, final Standard_CK_Table_Item t, int position) {
		final RadioGroup rg_isok=(RadioGroup) viewHolder.getMconvertView().findViewById(R.id.rg_isok);
		final RadioButton rb_ck_stand_limit = (RadioButton) viewHolder.getMconvertView().findViewById(R.id.rb_ck_stand_limit);
        final RadioButton rb_ck_stand_rightnow = (RadioButton) viewHolder.getMconvertView().findViewById(R.id.rb_ck_stand_rightnow);
        rg_isok.setOnCheckedChangeListener(null);
        rg_isok.clearCheck();
        rb_ck_stand_limit.setTextColor(Color.parseColor("#FF7D899D"));
        rb_ck_stand_rightnow.setTextColor(Color.parseColor("#FF7D899D"));
		
		viewHolder.setText(R.id.txt_stand_item_content, t.getItem_description());
		if(t.getItem_repairType()!=null&&!t.getItem_repairType().equals(""))
		{
			if(t.getItem_repairType().equals("L"))
			{
				viewHolder.setRadioButtonCkecked(R.id.rb_ck_stand_limit, true);
				rb_ck_stand_limit.setTextColor(Color.parseColor("#28aae1"));
			}
			else
			{
				viewHolder.setRadioButtonCkecked(R.id.rb_ck_stand_rightnow, true);
				rb_ck_stand_rightnow.setTextColor(Color.parseColor("#28aae1"));
			}
		}
		
		rg_isok.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				 	int radioButtonId = group.getCheckedRadioButtonId();
	                RadioButton rb = (RadioButton) viewHolder.getMconvertView().findViewById(radioButtonId);
	                
	                rb_ck_stand_limit.setTextColor(Color.parseColor("#FF7D899D"));
	                rb_ck_stand_rightnow.setTextColor(Color.parseColor("#FF7D899D"));
	                
	                rb.setTextColor(Color.parseColor("#28aae1")); 
	                
	                if(mRepairTypeListener!=null)
	                {
	                	switch (radioButtonId) {
						case R.id.rb_ck_stand_limit:
							mRepairTypeListener.setItemRepairTypeListener(t,true);
							break;
						case R.id.rb_ck_stand_rightnow:
							mRepairTypeListener.setItemRepairTypeListener(t,false);
							break;
						default:
							break;
						}
	                }
			}
		});
	}
	
	public void setRepairTypeListener(RepairTypeListener repairTypeListener) {
		mRepairTypeListener=repairTypeListener;
	}
	
	
	public interface RepairTypeListener{
		public void setItemRepairTypeListener(Standard_CK_Table_Item standard_Table_Item,boolean checked);
	}
	
}


