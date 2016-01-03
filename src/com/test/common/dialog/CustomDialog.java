package com.sunnyit.common.dialog;

import com.sunnyit.R;
import com.sunnyit.common.progressbar.HorizontalNumberProgressBar;
import com.sunnyit.common.progressbar.RoundNumberProgressBar;
import com.sunnyit.common.progressbar.VerticalNumberProgressBar;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**   
* @Title: HorPbDialog.java 
* @Package com.sunnyit.common.dialog 
* @Description: TODO
* @author yk
* @date 2015��8��27�� ����10:14:02 
* @version V1.0   
*/
public class CustomDialog {
	
	private Dialog mDialog;
	private View mView;
	private Context mContext;
	private HorizontalNumberProgressBar mProgressBarH;
	private RoundNumberProgressBar mProgressBarR;
	private VerticalNumberProgressBar mProgressBarV;
	
	private  boolean isSetOutCancel=false;
	
	private SparseArray<View> mSaView;
	private CancelDialogListener mCancelDialogListener;
	
	/**
	* @author yk
	* @date 2015��8��27�� ����10:41:52 
	* @param context    �趨�ļ�
	 */
	public CustomDialog(Context context) {
		this.mContext=context;
		this.mSaView=new SparseArray<View>();
		mDialog = new Dialog(context,R.style.dialog);
		
		mDialog.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub
				if(mCancelDialogListener!=null)
				{
					mCancelDialogListener.onCancelDialog();
				}
			}
		});
	}
	
	/**
	* @author yk 
	* @date 2015��8��27�� ����10:41:57 
	* @Title: setView 
	* @Description: dialog��view��͸����
	* @param layoutId
	* @param bgAlpha
	* @return    �趨�ļ� 
	* @return Dialog    �������� 
	* @throws
	 */
	public CustomDialog setViewAndAlpha(int layoutId,int bgAlpha)
	{
		mView = LayoutInflater.from(mContext).inflate(layoutId, null);
        //�ص�������仰���ѱ�����͸������Ϊ��ȫ͸�����Ϳ����������Ǹ��Դ�һ�����ͼ�ˡ�
		try
		{
			mView.getBackground().setAlpha(bgAlpha);
		}
		catch(Exception e)
		{
			Log.i("cd_error", "layout background is not init");
		}
        
        mDialog.setContentView(mView);
		return this;
	}
	
	/**
	* @author yk 
	* @date 2015��8��27�� ����10:41:38 
	* @Title: setView 
	* @Description: dialog��view
	* @param layoutId
	* @return    �趨�ļ� 
	* @return Dialog    �������� 
	* @throws
	 */
	public CustomDialog setView(int layoutId)
	{
		mView = LayoutInflater.from(mContext).inflate(layoutId, null);
		mDialog.setContentView(mView);
		return this;
	}
	
	
	public CustomDialog setView(View view,int bgAlpha)
	{
		try
		{
			mView.getBackground().setAlpha(bgAlpha);
		}
		catch(Exception e)
		{
			Log.i("cd_error", "layout background is not init");
		}
		mDialog.setContentView(view);
		return this;
	}
	
	/**
	* @author yk 
	* @date 2015��8��27�� ����2:11:00 
	* @Title: findViewById 
	* @Description: ��ȡview
	* @param Id
	* @return    �趨�ļ� 
	* @return T    �������� 
	* @throws
	 */
	public <T extends View> T findViewById(int Id)
	{
		View view=mSaView.get(Id);
		if(mView!=null)
		{
			view=mView.findViewById(Id);
			mSaView.put(Id, view);
			view.setVisibility(View.VISIBLE);
		}
		return (T) view;
	}
	
	/**
	* @author yk 
	* @date 2015��8��28�� ����10:23:10 
	* @Title: setText 
	* @Description: ����text
	* @param Id
	* @param textStr
	* @return    �趨�ļ� 
	* @return CustomDialog    �������� 
	* @throws
	 */
	public CustomDialog setText(int Id,String textStr) {
		View view=findViewById(Id);
		Class<? extends View> mclass=view.getClass();
		if(mclass==TextView.class)
		{
			TextView txView=(TextView)view;
			txView.setText(textStr);
		}
		else if(mclass==Button.class)
		{
			Button butView=(Button)view;
			butView.setText(textStr);
		}
		else if(mclass==EditText.class)
		{
			EditText etView=(EditText)view;
			etView.setText(textStr);
		}
		return this;
	}
	
	/**
	* @author yk 
	* @date 2015��9��2�� ����11:49:15 
	* @Title: getText 
	* @Description: ��ȡtext
	* @param Id
	* @return    �趨�ļ� 
	* @return String    �������� 
	* @throws
	 */
	public String getText(int Id) {
		String result=null;
		View view=findViewById(Id);
		Class<? extends View> mclass=view.getClass();
		if(mclass==TextView.class)
		{
			TextView txView=(TextView)view;
			result=txView.getText().toString();
		}
		else if(mclass==Button.class)
		{
			Button butView=(Button)view;
			result=butView.getText().toString();
		}
		else if(mclass==EditText.class)
		{
			EditText etView=(EditText)view;
			result=etView.getText().toString();
		}
		return result;
	}
	
	/**
	* @author yk 
	* @date 2015��8��27�� ����2:11:12 
	* @Title: initProgressBar 
	* @Description: ��ʼ��progressbar
	* @param Id    �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	public void initProgressBar(int Id)
	{
		Class<? extends View> mclass=findViewById(Id).getClass();
		if(mclass==HorizontalNumberProgressBar.class)
		{
			mProgressBarH=(HorizontalNumberProgressBar)findViewById(Id);
			mProgressBarH.setVisibility(View.VISIBLE);
		}
		else if(mclass==RoundNumberProgressBar.class)
		{
			mProgressBarR=(RoundNumberProgressBar)findViewById(Id);
			mProgressBarR.setVisibility(View.VISIBLE);
		}
		else if(mclass==VerticalNumberProgressBar.class)
		{
			mProgressBarV=(VerticalNumberProgressBar)findViewById(Id);
			mProgressBarV.setVisibility(View.VISIBLE);
		}
	}
	
	/**
	* @author yk 
	* @date 2015��8��27�� ����2:11:35 
	* @Title: setProgress 
	* @Description: ����profressbar����
	* @param count    �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	public void setProgress(int count)
	{
		if(mProgressBarH!=null)
		{
			mProgressBarH.setProgress(count); 
		}
		else if(mProgressBarR!=null)
		{
			mProgressBarR.setProgress(count); 
		}
		else if(mProgressBarV!=null)
		{
			mProgressBarV.setProgress(count); 
		}
	}
	
	/**
	* @author yk 
	* @date 2015��8��27�� ����2:36:12 
	* @Title: dismiss 
	* @Description:�رնԻ���
	* @return void    �������� 
	* @throws
	 */
	public void dismiss() {
		mDialog.dismiss();
	}
	
	/**
	* @author yk 
	* @date 2015��8��27�� ����10:41:15 
	* @Title: show 
	* @Description: dialog��չʾ
	* @return void    �������� 
	* @throws
	 */
	public void show()
	{
		Window window = mDialog.getWindow();        
		WindowManager.LayoutParams lp = window.getAttributes();        
		//lp.alpha = 0.6f; 
		//lp.gravity=Gravity.CENTER-15;
		lp.x = 0;
		lp.y = -100;
		window.setAttributes(lp);
		if(!isSetOutCancel)
		{
			mDialog.setCanceledOnTouchOutside(isSetOutCancel);
		}
		mDialog.show();
	}
	
	/**
	* @author yk 
	* @date 2015��8��31�� ����5:29:00 
	* @Title: setOutCancel 
	* @Description: �ⲿ���ȡ��
	* @param flag
	* @return    �趨�ļ� 
	* @return CustomDialog    �������� 
	* @throws
	 */
	public CustomDialog setOutCancel(boolean flag) {
		mDialog.setCanceledOnTouchOutside(flag);
		isSetOutCancel=true;
		return this;
	}
	
	public void setCancelDialogLiatener(CancelDialogListener cancelDialogListener)
	{
		this.mCancelDialogListener=cancelDialogListener;
	}
	
	public interface CancelDialogListener
	{
		public void onCancelDialog();
	}
}


