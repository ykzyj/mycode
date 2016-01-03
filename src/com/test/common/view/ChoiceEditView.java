package com.sunnyit.common.view;

import com.sunnyit.R;
import com.sunnyit.common.convert.PpConvert;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.Toast;

/**   
* @Title: TopBar.java 
* @Package com.sunnyit.common.view 
* @Description: TODO
* @author yk
* @date 2015年8月20日 下午3:38:20 
* @version V1.0   
*/
public class ChoiceEditView extends EditText {
	
	 /**
     * 日历按钮
     */
    private Drawable delImg;
    private String mTitle;
    
    private Context mContext;
    private onChoiceEditItemListener mChoiceEditItemListener;
    private onClickEditListener mClickEditListener;
    
	public ChoiceEditView(Context context) {
		this(context,null);
		// TODO Auto-generated constructor stub
	}
	
	public ChoiceEditView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
		// TODO Auto-generated constructor stub
	}
	
	public ChoiceEditView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.mContext=context;
		initshow();
		setTextSize(PpConvert.sp2px(context, 8));
	}

	private void initshow() {
		// TODO Auto-generated method stub
		
		delImg = getCompoundDrawables()[2];
        if (delImg == null) {
            // throw new
            // 获取删除的图片资源，可以自己找一张图片放到drawable文件夹下 ;
            delImg = getResources().getDrawable(R.drawable.note_stand48);
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
                	
                	if(mChoiceEditItemListener!=null)
                	{
                		final String items[]=mChoiceEditItemListener.onSetChoiceEditItem();
                		 AlertDialog.Builder builder=new AlertDialog.Builder(mContext);  //先得到构造器  
                		 if(mTitle!=null)
                		 {
                			 builder.setTitle(mTitle); 
                		 }
                		 else
                		 {
                			 builder.setTitle("信息选择"); //设置标题  
                		 }
                         //builder.setMessage("是否确认退出?"); //设置内容  
                         builder.setIcon(R.drawable.note_stand48);//设置图标，图片id即可  
                         //设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。  
                         builder.setItems(items,new DialogInterface.OnClickListener() {  
                             @Override  
                             public void onClick(DialogInterface dialog, int which) {  
                                 dialog.dismiss();  
                                 ChoiceEditView.this.setText(items[which]);
                             }  
                         });  
                         builder.create().show();
                	}
                	if(mClickEditListener!=null)
                	{
                		mClickEditListener.onClickEdit();
                	}
                }
            }
        }*/
		
		if (event.getAction() == MotionEvent.ACTION_UP) {
			if(mChoiceEditItemListener!=null)
        	{
        		final String items[]=mChoiceEditItemListener.onSetChoiceEditItem();
        		if(items!=null)
        		{
	           		 AlertDialog.Builder builder=new AlertDialog.Builder(mContext);  //先得到构造器  
	           		 if(mTitle!=null)
	           		 {
	           			 builder.setTitle(mTitle); 
	           		 }
	           		 else
	           		 {
	           			 builder.setTitle("信息选择"); //设置标题  
	           		 }
                    //builder.setMessage("是否确认退出?"); //设置内容  
                    builder.setIcon(R.drawable.note_stand48);//设置图标，图片id即可  
                    //设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。  
                    builder.setItems(items,new DialogInterface.OnClickListener() {  
                        @Override  
                        public void onClick(DialogInterface dialog, int which) {  
                            dialog.dismiss();  
                            ChoiceEditView.this.setText(items[which]);
                        }  
                    });  
                    builder.create().show();
        		}
        	}
        	if(mClickEditListener!=null)
        	{
        		mClickEditListener.onClickEdit();
        	}
		}
		return super.onTouchEvent(event);
	}
	
	public void setDialogTitle(String title)
	{
		mTitle=title;
	}
	
	public void setChoiceEditItem(onChoiceEditItemListener mChoiceEditItemListener)
	{
		this.mChoiceEditItemListener=mChoiceEditItemListener;
	}
	
	public void setOnClickEditListener(onClickEditListener mClickEditListener)
	{
		this.mClickEditListener=mClickEditListener;
	}
	
	public interface onChoiceEditItemListener
	{
		public String[] onSetChoiceEditItem();
	}
	
	public interface onClickEditListener
	{
		public void onClickEdit();
	}
	
}


