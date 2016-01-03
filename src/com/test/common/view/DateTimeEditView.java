package com.sunnyit.common.view;

import java.util.Calendar;

import com.sunnyit.R;
import com.sunnyit.common.convert.PpConvert;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

/**   
* @Title: TopBar.java 
* @Package com.sunnyit.common.view 
* @Description: TODO
* @author yk
* @date 2015年8月20日 下午3:38:20 
* @version V1.0   
*/
public class DateTimeEditView extends EditText {
	
	 /**
     * 日历按钮
     */
    private Drawable delImg;
    
    private Context mContext;
    
    private Calendar cal;
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	
	private int monthOfYear, dayOfMonth, hourOfDay;

	public DateTimeEditView(Context context) {
		this(context,null);
		// TODO Auto-generated constructor stub
	}
	
	public DateTimeEditView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
		// TODO Auto-generated constructor stub
	}
	
	public DateTimeEditView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.mContext=context;
		initshow();
	}

	private void initshow() {
		// TODO Auto-generated method stub
		
		delImg = getCompoundDrawables()[2];
        if (delImg == null) {
            // throw new
            // 获取删除的图片资源，可以自己找一张图片放到drawable文件夹下 ;
            delImg = getResources().getDrawable(R.drawable.datepicker48);
        }
        setCompoundDrawablesWithIntrinsicBounds(null, null, delImg, null);  
        setGravity(Gravity.CENTER_VERTICAL);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		/*if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())&& (event.getX() < ((getWidth() - getPaddingRight())));
                if (touchable) {
                    //Toast.makeText(mContext, "111", Toast.LENGTH_SHORT).show();
                	cal = Calendar.getInstance();
            		//获取年月日时分秒的信息
            		 year = cal.get(Calendar.YEAR);
            		 month = cal.get(Calendar.MONTH)+1;
            		 day = cal.get(Calendar.DAY_OF_MONTH);
            		 hour = cal.get(Calendar.HOUR_OF_DAY);
            		 minute = cal.get(Calendar.MINUTE);
                	new DatePickerDialog(mContext, new OnDateSetListener() {
        				
        				@Override
        				public void onDateSet(DatePicker view, int year, int monthOfYear,
        						int dayOfMonth) {
        					// TODO Auto-generated method stub
        					setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
        				}
        			}, year, cal.get(Calendar.MONTH), day).show();
                }
            }
        }*/
		
		if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
            	
            	final StringBuffer sb_date=new StringBuffer();
            	final StringBuffer sb_stime=new StringBuffer();
            	final StringBuffer sb_etime=new StringBuffer();
            	
            	cal = Calendar.getInstance();
        		//获取年月日时分秒的信息
        		 year = cal.get(Calendar.YEAR);
        		 month = cal.get(Calendar.MONTH)+1;
        		 day = cal.get(Calendar.DAY_OF_MONTH);
        		 hour = cal.get(Calendar.HOUR_OF_DAY);
        		 minute = cal.get(Calendar.MINUTE);
            	
				TimePickerDialog timePickerDialogE = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener()
                {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
                    {
                        //editText2.setText("Time: " + );
                    	if(sb_etime.toString().equals(""))
    					{
                    		if(hourOfDay<10)
    						{
                    			if(minute<10)
        						{
                    				sb_etime.append("0"+hourOfDay + ":0"+ minute);
        						}
        						else
        						{
        							sb_etime.append("0"+hourOfDay + ":"+ minute);
        						}
    						}
    						else
    						{
    							if(minute<10)
        						{
    								sb_etime.append(hourOfDay + ":0"+ minute);
        						}
        						else
        						{
        							sb_etime.append(hourOfDay + ":"+ minute);
        						}
    						}
                        	setText(sb_date.toString()+"  "+sb_stime.toString()+"至"+sb_etime.toString());
    					}
                    }
                }, hour, minute, true);
            	timePickerDialogE.show();
            	
            	TimePickerDialog timePickerDialogS = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener()
                {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
                    {
                        //editText2.setText("Time: " + );
                    	if(sb_stime.toString().equals(""))
    					{
                    		if(hourOfDay<10)
    						{
                    			if(minute<10)
        						{
                    				sb_stime.append("0"+hourOfDay + ":0"+ minute);
        						}
        						else
        						{
        							sb_stime.append("0"+hourOfDay + ":"+ minute);
        						}
    						}
    						else
    						{
    							if(minute<10)
        						{
                    				sb_stime.append(hourOfDay + ":0"+ minute);
        						}
        						else
        						{
        							sb_stime.append(hourOfDay + ":"+ minute);
        						}
    						}
    					}
                    }
                }, hour, minute, true);
				timePickerDialogS.show();
            	
            	new DatePickerDialog(mContext, new OnDateSetListener() {
    				
    				@Override
    				public void onDateSet(DatePicker view, int year, int monthOfYear,
    						int dayOfMonth) {
    					// TODO Auto-generated method stub
    					if(sb_date.toString().equals(""))
    					{
    						/*if((monthOfYear+1)<10)
    						{
    							sb_date.append(year + "-" +"0"+(monthOfYear + 1) + "-" + dayOfMonth);
    						}
    						else
    						{
    							sb_date.append(year + "-" +(monthOfYear + 1) + "-" + dayOfMonth);
    						}*/
    						if((monthOfYear+1)<10)
    						{
    							if((dayOfMonth+1)<10)
	    						{
	    							sb_date.append(year + "-" +"0"+(monthOfYear + 1) + "-" + "0" +dayOfMonth);
	    						}
	    						else
	    						{
	    							sb_date.append(year + "-" +"0"+(monthOfYear + 1) + "-" + dayOfMonth);
	    						}
    						}
    						else
    						{
    							if((dayOfMonth+1)<10)
	    						{
    								sb_date.append(year + "-" +(monthOfYear + 1) + "-" +"0"+ dayOfMonth);
	    						}
	    						else
	    						{
	    							sb_date.append(year + "-" +(monthOfYear + 1) + "-" + dayOfMonth);
	    						}
    						}
    					}
    					//setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
    				}
    			}, year, cal.get(Calendar.MONTH), day).show();
				
            }
        }
		return super.onTouchEvent(event);
	}
	
}


