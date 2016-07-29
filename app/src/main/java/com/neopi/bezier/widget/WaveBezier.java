package com.neopi.bezier.widget;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * 波浪的效果
 *
 * Created by neopi on 16-7-28.
 */
public class WaveBezier extends View {

  private static final String TAG = "WaveBezier";

  private Paint mPaint;
  private Path mPath;
  private int mWaveLength = 1000;
  private int mOffset;
  private int mScreenHeight;
  private int mScreenWidth;
  private int mWaveCount;
  private int mCenterY;

  public WaveBezier(Context context) {
    super(context, null);
  }

  public WaveBezier(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public WaveBezier(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initData();
  }

  public void initData() {
    mPath = new Path();
    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mPaint.setColor(Color.parseColor("#FF1CA6EA"));
    mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
  }

  @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    mScreenHeight = h;
    mScreenWidth = w;
    mWaveCount = (int) Math.round(mScreenWidth / mWaveLength + 1.5);
    mCenterY = mScreenHeight;
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    mPath.reset();
    mPaint.setAlpha(paintAlpha);
    mPath.moveTo(-mWaveLength + mOffset, mCenterY);
    for (int i = 0; i < mWaveCount; i++) {
      // + (i * mWaveLength)
      // + mOffset
      mPath.quadTo((-mWaveLength * 3 / 4) + (i * mWaveLength) + mOffset, mCenterY + 60,
          (-mWaveLength / 2) + (i * mWaveLength) + mOffset, mCenterY);
      mPath.quadTo((-mWaveLength / 4) + (i * mWaveLength) + mOffset, mCenterY - 60,
          i * mWaveLength + mOffset, mCenterY);
    }
    mPath.lineTo(mScreenWidth, mScreenHeight);
    mPath.lineTo(0, mScreenHeight);
    mPath.close();
    canvas.drawPath(mPath, mPaint);
  }

  /**
   * 开始波浪的动画
   */
  private ValueAnimator animator ;
  private boolean rise = true; // 水位上升
  private int paintAlpha = 30; // 设置透明度
  public void start() {
    PropertyValuesHolder xholder = PropertyValuesHolder.ofInt("X",0,mWaveLength);
    //PropertyValuesHolder yholder = PropertyValuesHolder.ofInt("Y",0,mCenterY);
    //ValueAnimator animator = ValueAnimator.ofInt(0, mWaveLength);
    animator = ValueAnimator.ofPropertyValuesHolder(xholder);
    animator.setDuration(1000);
    animator.setRepeatCount(ValueAnimator.INFINITE);
    animator.setInterpolator(new LinearInterpolator());
    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator animation) {

        if ( rise ) {
          mCenterY -- ;
          if (mCenterY < 0)
            rise = false;
        } else {
          mCenterY ++ ;
          if (mCenterY > getMeasuredHeight() ){
            rise = true;
          }
        }
        mOffset = (int) animation.getAnimatedValue("X");
        if (getMeasuredHeight() > 0){
          paintAlpha = (int) ( (1 - mCenterY * 1f / getMeasuredHeight()) * 100 );
          if (paintAlpha < 30)
            paintAlpha = 30;
        }
        Log.d(TAG,mOffset+","+mCenterY+","+paintAlpha);
        postInvalidate();
      }
    });
    animator.start();
  }

  /**
   * 取消动画
   *
   */
  public void cancle(){
    animator.cancel();
  }

}
