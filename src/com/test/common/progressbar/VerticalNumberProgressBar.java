package com.sunnyit.common.progressbar;

import com.sunnyit.R;
import com.sunnyit.common.convert.PpConvert;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**   
* @Title: HorizontalNumberProgressBar.java 
* @Package com.sunnyit.common.progressbar 
* @Description: TODO
* @author yk
* @date 2015年8月26日 上午10:02:29 
* @version V1.0   
*/
public class VerticalNumberProgressBar extends ProgressBar {
	
	protected Context mContext;
	
	protected static final int Default_TEXT_SIZE=15;
	protected static final int DEFAULT_TEXT_COLOR=0XFFFC00D1;
	protected static final int DEFAULT_COLOR_UNREACHED_PROGRESS_BAR=0xFFd3d6da;
	protected static final int DEFAULT_HEIGHT_UNREACHED_PROGRESS_BAR=2;
	protected static final int DEFAULT_HEIGHT_REACHED_PROGRESS_BAR=2;
	protected static final int DEFAULT_TEXT_OFFSET=10;
	protected static final int VISIBLE = 0;
	
	/*
	 * 画笔
	 */
	protected Paint mPaint=new Paint();
	
	protected int mTextSize;
	protected int mTextColor=DEFAULT_TEXT_COLOR;
	protected int mTextOffset;
	protected int mUnreachedProgressBarHeight;
	protected int mReachedProgressBarHeight;
	protected int mUnreachedProgressBarColor=DEFAULT_TEXT_COLOR;
	protected int mReachedProgressBarColor=DEFAULT_COLOR_UNREACHED_PROGRESS_BAR;
	
	protected int mRealHeight;  
    protected boolean mIfDrawText = true;  

	public VerticalNumberProgressBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public VerticalNumberProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		this.mContext=context;
		mTextSize=PpConvert.sp2px(mContext, Default_TEXT_SIZE);
		mTextOffset=PpConvert.dp2px(mContext, DEFAULT_TEXT_OFFSET);
		mUnreachedProgressBarHeight=PpConvert.dp2px(mContext, DEFAULT_HEIGHT_UNREACHED_PROGRESS_BAR);
		mReachedProgressBarHeight=PpConvert.dp2px(mContext, DEFAULT_HEIGHT_REACHED_PROGRESS_BAR);
		//this.setHorizontalScrollBarEnabled(true);
		
		 obtainStyledAttributes(attrs);  
		 
		 mPaint.setTextSize(mTextSize);
		 mPaint.setColor(mTextColor);
	}

	private void obtainStyledAttributes(AttributeSet attrs) {
		TypedArray ta=mContext.obtainStyledAttributes(attrs, R.styleable.HorizontalNumberProgressBar);
		mTextSize=ta.getDimensionPixelSize(R.styleable.HorizontalNumberProgressBar_progress_text_size,
				Default_TEXT_SIZE);
		mTextColor=ta.getColor(R.styleable.HorizontalNumberProgressBar_progress_text_color, 
				DEFAULT_TEXT_COLOR);
		mTextOffset=ta.getDimensionPixelSize(R.styleable.HorizontalNumberProgressBar_progress_text_offset,
				DEFAULT_TEXT_OFFSET);
		mUnreachedProgressBarHeight=ta.getDimensionPixelSize(R.styleable.HorizontalNumberProgressBar_progress_unreached_bar_height, 
				DEFAULT_HEIGHT_UNREACHED_PROGRESS_BAR);
		mReachedProgressBarHeight=ta.getDimensionPixelSize(R.styleable.HorizontalNumberProgressBar_progress_reached_bar_height,
				DEFAULT_HEIGHT_REACHED_PROGRESS_BAR);
		mUnreachedProgressBarColor=ta.getColor(R.styleable.HorizontalNumberProgressBar_progress_unreached_color,
				DEFAULT_COLOR_UNREACHED_PROGRESS_BAR);
		mReachedProgressBarColor=ta.getColor(R.styleable.HorizontalNumberProgressBar_progress_reached_color, 
				DEFAULT_TEXT_COLOR);
		int visible=ta.getInt(R.styleable.HorizontalNumberProgressBar_progress_text_visibility,VISIBLE);
		if(visible!=VISIBLE)
		{
			mIfDrawText=false;
		}
		ta.recycle();
	}
	
	@Override
	protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		
		/**
		 * 如果未定义高度，重新计算高度
		 */
		int width = measureWeight(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		setMeasuredDimension(width, height);

		mRealHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
	}
	
	private int measureWeight(int measureSpec)
	{
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);
		if (specMode == MeasureSpec.EXACTLY)
		{
			result = specSize;
		} else
		{
			float textHeight = (mPaint.descent() - mPaint.ascent());
			result = (int) (getPaddingLeft() + getPaddingRight() + Math.max(
					Math.max(mReachedProgressBarHeight,
							mUnreachedProgressBarHeight), Math.abs(textHeight)));
			if (specMode == MeasureSpec.AT_MOST)
			{
				result = Math.min(result, specSize);
			}
		}
		return result;
	}
	
	@Override
	protected synchronized void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.save();
		canvas.translate(getWidth()/2, getHeight()-getPaddingBottom());
		/*
		 * 当前%
		 */
		float rate=getProgress()*1.0f/getMax();
		/*
		 * 已有宽度
		 */
		float currentProgressY=mRealHeight*rate;
		String mText=String.valueOf(getProgress())+"%";
		
		float textWidth=mPaint.measureText(mText);
		float textHeight=(mPaint.ascent()+mPaint.descent())/2;
		
		/*
		 * 已经达到的长度
		 */
		if(currentProgressY<=mRealHeight)
		{
			mPaint.setColor(mReachedProgressBarColor);
			mPaint.setStrokeWidth(mReachedProgressBarHeight);
			canvas.drawLine(0, 0, 0, -currentProgressY, mPaint);
		}
		
		/*
		 * 绘制文本
		 */
		if(mIfDrawText)
		{
			mPaint.setColor(mTextColor);
			mPaint.setTextSize(mTextSize);
			canvas.drawText(mText, 0-textWidth/2, -(currentProgressY+mTextOffset/2), mPaint);
		}
		
		/*
		 * 未达到的长度
		 */
		float UnReachedStartY=currentProgressY+mTextOffset+(-textHeight*2);
		if(UnReachedStartY<=mRealHeight)
		{
			
			mPaint.setColor(mUnreachedProgressBarColor);
			mPaint.setStrokeWidth(mUnreachedProgressBarHeight);
			canvas.drawLine(0, -UnReachedStartY, 0, -mRealHeight, mPaint);
		}
		
		
		canvas.restore();
	}

}


