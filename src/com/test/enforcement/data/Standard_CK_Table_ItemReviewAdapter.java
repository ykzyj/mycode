package com.sunnyit.enforcement.data;

import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.adapter.AdapterUtil;
import com.sunnyit.common.adapter.ViewHolderUtil;
import com.sunnyit.enforcement.action.HiddenStandReviewDatilAddActivity;
import com.sunnyit.enforcement.model.Standard_CK_Table_Item;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;

/**   
* @Title: DailyCheckAdapter.java 
* @Package com.sunnyit.enforcement.data 
* @Description: TODO
* @author yk
* @date 2015年9月11日 下午9:21:46 
* @version V1.0   
*/
public class Standard_CK_Table_ItemReviewAdapter extends AdapterUtil<Standard_CK_Table_Item> {
	
	private ApcceptOpinionListener mApcceptOpinionListener;
	private Context mContext;
	
	public Standard_CK_Table_ItemReviewAdapter(Context context, List<Standard_CK_Table_Item> datas, int layoutId) {
		super(context, datas, layoutId);
		// TODO Auto-generated constructor stub
		mContext=context;
	}

	@Override
	public void initShowDate(final ViewHolderUtil viewHolder, final Standard_CK_Table_Item t, int position) {
		viewHolder.setText(R.id.txt_item_description, t.getItem_description());
		
		if(t.getItem_repairType()!=null&&!t.getItem_repairType().equals(""))
		{
			if(t.getItem_repairType().equals("L"))
			{
				viewHolder.setText(R.id.txt_item_repairType, "限期整改");
				viewHolder.setTextColor(R.id.txt_item_repairType, Color.rgb(253, 113, 0));
				viewHolder.setText(R.id.txt_item_repairLimit, t.getItem_repairLimit());
				viewHolder.setTextColor(R.id.txt_item_repairLimit, Color.rgb(253, 113, 0));
			}
			else
			{
				viewHolder.setText(R.id.txt_item_repairType, "立即整改");
				viewHolder.setTextColor(R.id.txt_item_repairType, Color.RED);
				viewHolder.setText(R.id.txt_item_repairLimit, "无");
			}
		}
		else
		{
			viewHolder.setText(R.id.txt_item_repairLimit, "无");
			viewHolder.setText(R.id.txt_item_repairLimit, "无");
		}
		
		final RadioGroup rg_isok=(RadioGroup) viewHolder.getMconvertView().findViewById(R.id.rg_isok);
		final RadioButton rb_ck_stand_yes = (RadioButton) viewHolder.getMconvertView().findViewById(R.id.rb_ck_stand_yes);
        final RadioButton rb_ck_stand_no = (RadioButton) viewHolder.getMconvertView().findViewById(R.id.rb_ck_stand_no);
        rg_isok.setOnCheckedChangeListener(null);
        rg_isok.clearCheck();
        rb_ck_stand_yes.setTextColor(Color.parseColor("#FF7D899D"));
        rb_ck_stand_no.setTextColor(Color.parseColor("#FF7D899D"));
		
		
		viewHolder.setRadioButtonCkecked(R.id.rb_ck_stand_yes, false);
		viewHolder.setRadioButtonCkecked(R.id.rb_ck_stand_no, false);
		if(t.getItem_reviewResult()!=null&&!t.getItem_reviewResult().equals(""))
		{
			if(t.getItem_reviewResult().equals("YES"))
			{
				viewHolder.setRadioButtonCkecked(R.id.rb_ck_stand_yes, true);
				rb_ck_stand_yes.setTextColor(Color.parseColor("#28aae1"));
			}
			else
			{
				viewHolder.setRadioButtonCkecked(R.id.rb_ck_stand_no, true);
				rb_ck_stand_no.setTextColor(Color.parseColor("#28aae1"));
			}
		}
		
		RelativeLayout rel_standitem_measure=viewHolder.getView(R.id.rel_standitem_measure);
		rel_standitem_measure.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder=new AlertDialog.Builder(mContext);  //先得到构造器  
		        builder.setTitle("整改措施"); //设置标题  
		        builder.setMessage(t.getItem_repairMethod()); //设置内容  
		        builder.setIcon(R.drawable.note_stand48);//设置图标，图片id即可  
		        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮  
		            @Override  
		            public void onClick(DialogInterface dialog, int which) {  
		                dialog.dismiss();  
		            }  
		        });  
		        //参数都设置完成了，创建并显示出来  
		        builder.create().show(); 
			}
		});
		
		RelativeLayout rel_standitem_condition=viewHolder.getView(R.id.rel_standitem_condition);
		rel_standitem_condition.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder=new AlertDialog.Builder(mContext);  //先得到构造器  
		        builder.setTitle("整改情况"); //设置标题  
		        builder.setMessage(t.getItem_repairCondition()); //设置内容  
		        builder.setIcon(R.drawable.note_stand48);//设置图标，图片id即可  
		        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮  
		            @Override  
		            public void onClick(DialogInterface dialog, int which) {  
		                dialog.dismiss();  
		            }  
		        });  
		        //参数都设置完成了，创建并显示出来  
		        builder.create().show(); 
			}
		});
		
		rg_isok.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				 	int radioButtonId = group.getCheckedRadioButtonId();
	                RadioButton rb = (RadioButton) viewHolder.getMconvertView().findViewById(radioButtonId);
	                
	                rb_ck_stand_yes.setTextColor(Color.parseColor("#FF7D899D"));
	                rb_ck_stand_no.setTextColor(Color.parseColor("#FF7D899D"));
	                
	                rb.setTextColor(Color.parseColor("#28aae1")); 
	                
	                if(mApcceptOpinionListener!=null)
	                {
	                	switch (radioButtonId) {
						case R.id.rb_ck_stand_yes:
							mApcceptOpinionListener.setItenApcceptOpinion(t,true);
							break;
						case R.id.rb_ck_stand_no:
							mApcceptOpinionListener.setItenApcceptOpinion(t,false);
							break;
						default:
							break;
						}
	                }
			}
		});
	}
	
	public void setApcceptOpinionListener(ApcceptOpinionListener apcceptOpinionListener) {
		mApcceptOpinionListener=apcceptOpinionListener;
	}
	
	
	public interface ApcceptOpinionListener{
		public void setItenApcceptOpinion(Standard_CK_Table_Item standard_Table_Item,boolean checked);
	}
	
}


