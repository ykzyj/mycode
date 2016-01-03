package com.sunnyit.common.progressbar;

import com.sunnyit.R;
import com.sunnyit.common.convert.PpConvert;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**   
* @Title: RoundNumberProgressBar.java 
* @Package com.sunnyit.common.progressbar 
* @Description: TODO
* @author yk
* @date 2015年8月26日 下午2:38:24 
* @version V1.0   
*/
public class RoundNumberProgressBar extends HorizontalNumberProgressBar {
	
	private int mRadius;
	
	public RoundNumberProgressBar(Context context)
	{
		this(context, null);
	}

	public RoundNumberProgressBar(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		mRadius=PpConvert.dp2px(context, 30);
		
		mReachedProgressBarHeight=(int) (mUnreachedProgressBarHeight*2.5f);
		obtainRadiusStyledAttributes(attrs);  
		
		//设置画笔为空心  
		mPaint.setStyle(Style.STROKE);  
		// 设置画笔的锯齿效果。 true是去除
        mPaint.setAntiAlias(true); 
        //是否使用图像抖动
        mPaint.setDither(true);  
        //设置空心的边框
        mPaint.setStrokeCap(Cap.ROUND);  

	}

	private void obtainRadiusStyledAttributes(AttributeSet attrs) {
		// TODO Auto-generated method stub
		TypedArray ta=mContext.obtainStyledAttributes(attrs, R.styleable.RoundNumberProgressBar);
		mRadius=ta.getDimensionPixelSize(R.styleable.RoundNumberProgressBar_radius, mRadius);
		ta.recycle();
	}
	
	@Override
	protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);  
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);  
  
        int paintWidth = Math.max(mReachedProgressBarHeight,  
                mUnreachedProgressBarHeight);  
  
        if (heightMode != MeasureSpec.EXACTLY) {  
  
            int exceptHeight = (int) (getPaddingTop() + getPaddingBottom()  
                    + mRadius * 2 + paintWidth);  
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(exceptHeight,  
                    MeasureSpec.EXACTLY);  
        }  
        if (widthMode != MeasureSpec.EXACTLY) {  
            int exceptWidth = (int) (getPaddingLeft() + getPaddingRight()  
                    + mRadius * 2 + paintWidth);  
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(exceptWidth,  
                    MeasureSpec.EXACTLY);  
        }  
		
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	
	@Override
	protected synchronized void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.save();
		canvas.translate(getPaddingLeft(), getPaddingRight());
		
		String mText=String.valueOf(getProgress())+"%";
		float textWidth=mPaint.measureText(mText);
		float textHeight=(mPaint.ascent()+mPaint.descent())/2;
		
		mPaint.setStyle(Style.STROKE);  
        // draw unreaded bar  
        mPaint.setColor(mUnreachedProgressBarColor);  
        mPaint.setStrokeWidth(mUnreachedProgressBarHeight);  
        canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);  
        // draw reached bar  
        mPaint.setColor(mReachedProgressBarColor);  
        mPaint.setStrokeWidth(mReachedProgressBarHeight);  
        float sweepAngle = getProgress() * 1.0f / getMax() * 360;  
        canvas.drawArc(new RectF(0, 0, mRadius * 2, mRadius * 2), 0,  
                sweepAngle, false, mPaint);  
        // draw text  
        mPaint.setStyle(Style.FILL);  
        canvas.drawText(mText, mRadius - textWidth / 2, mRadius - textHeight,  
                mPaint);  
		
		canvas.restore();
	}
}


