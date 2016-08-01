package com.neopi.bezier.widget;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;

/**
 * Created by neopi on 16-7-29.
 */
public class DragBezier extends View {

  private static final String TAG = "RectangleBezier";

  private Paint mPaint ;  // 圆形画笔
  private Path mPath ;

  private int mPointOneX ; // 第一个圆的圆心坐标
  private int mPointOneY ;

  private int mPointTwoX ; // 第二个圆的圆心坐标
  private int mPointTwoY ;


  private int color = Color.RED;

  private int RADIUS = 50;

  public DragBezier(Context context) {
    this(context,null);
  }

  public DragBezier(Context context, AttributeSet attrs) {
    this(context, attrs,0);
  }

  public DragBezier(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initData();
  }

  private void initData() {
    mPath = new Path();

    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mPaint.setStyle(Paint.Style.FILL);

    mPointOneX = 300 ;
    mPointOneY = 300 ;

    mPointTwoX = mPointOneX + 300;
    mPointTwoY = mPointOneY + 300;

  }

  public void setColor(int color){
    this.color = color;
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    //int width = MeasureSpec.getSize(widthMeasureSpec);
    //int height = MeasureSpec.getSize(heightMeasureSpec);
    //int wid = Math.min(width,height);
    //
    //RADIUS = wid/2;
    //setMeasuredDimension(wid,wid);
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    mPath.reset();
    mPaint.setColor(color);

    int mControlPointX = (mPointTwoX + mPointOneX) / 2 ;
    int mControlPointY = (mPointTwoY + mPointOneY) / 2 ;

    int dx = mControlPointX - mPointOneX ;
    int dy = mControlPointY - mPointOneY ;

    /* 控制点到起始点的距离 */
    double distance = Math.sqrt((dx * dx) + (dy * dy));
    double acos = Math.acos(RADIUS / distance);

    //if (mPointTwoY >= mPointTwoX){
      double acos1 = Math.acos( dx / distance );
      int x1 = mPointOneX + (int) (RADIUS * Math.cos(acos - acos1));
      int y1 = mPointOneY - (int) (RADIUS * Math.sin(acos - acos1));

      double acos2 = Math.asin( dx / distance );
      int x2 = mPointOneX - (int)(RADIUS * Math.sin(acos - acos2));
      int y2 = mPointOneY + (int)(RADIUS * Math.cos(acos - acos2));

      int x3 = mPointTwoX + (int) (RADIUS * Math.sin(acos - acos2));
      int y3 = mPointTwoY - (int) (RADIUS * Math.cos(acos - acos2));

      int x4 = mPointTwoX - (int) (RADIUS * Math.cos(acos - acos1));
      int y4 = mPointTwoY + (int) (RADIUS * Math.sin(acos - acos1));
    /* (x1,y1),(x2,y2),(x3,y3) ,(x4,y4) 这四个点分别对应第一个圆与第二个圆上的四个切点*/

      mPath.moveTo(x1,y1);
      mPath.quadTo(mControlPointX,mControlPointY,x3,y3);
      mPath.lineTo(x4,y4);
      mPath.quadTo(mControlPointX,mControlPointY,x2,y2);
    //} else {
    //  double acos1 = Math.acos( dx / distance );
    //  int x1 = mPointOneX - (int) (RADIUS * Math.cos(acos - acos1));
    //  int y1 = mPointOneY + (int) (RADIUS * Math.sin(acos - acos1));
    //
    //  double acos2 = Math.asin( dx / distance );
    //  int x2 = mPointOneX + (int)(RADIUS * Math.sin(acos - acos2));
    //  int y2 = mPointOneY - (int)(RADIUS * Math.cos(acos - acos2));
    //
    //  int x3 = mPointTwoX - (int) (RADIUS * Math.sin(acos - acos2));
    //  int y3 = mPointTwoY + (int) (RADIUS * Math.cos(acos - acos2));
    //
    //  int x4 = mPointTwoX + (int) (RADIUS * Math.cos(acos - acos1));
    //  int y4 = mPointTwoY - (int) (RADIUS * Math.sin(acos - acos1));
    ///* (x1,y1),(x2,y2),(x3,y3) ,(x4,y4) 这四个点分别对应第一个圆与第二个圆上的四个切点*/
    //
    //  mPath.moveTo(x1,y1);
    //  mPath.quadTo(mControlPointX,mControlPointY,x3,y3);
    //  mPath.lineTo(x4,y4);
    //  mPath.quadTo(mControlPointX,mControlPointY,x2,y2);
    //}



    canvas.drawCircle(mPointOneX,mPointOneY,RADIUS,mPaint);
    canvas.drawCircle(mPointTwoX,mPointTwoY,RADIUS,mPaint);


    canvas.drawPath(mPath,mPaint);

  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    switch (event.getAction()){
      case MotionEvent.ACTION_DOWN:
        mPointTwoX = (int) event.getX();
        mPointTwoY = (int) event.getY();
        invalidate();
        break;
      case MotionEvent.ACTION_MOVE:
        mPointTwoX = (int) event.getX();
        mPointTwoY = (int) event.getY();
        invalidate();
        break;
      case MotionEvent.ACTION_UP:
        reset();
        break;
    }
    return true;
  }

  private void reset() {
    PropertyValuesHolder mHolderX = PropertyValuesHolder.ofInt("X",mPointTwoX,mPointOneX);
    PropertyValuesHolder mHolderY = PropertyValuesHolder.ofInt("Y",mPointTwoY,mPointOneY);
    ValueAnimator animator = ValueAnimator.ofPropertyValuesHolder(mHolderX,mHolderY);
    animator.setInterpolator(new BounceInterpolator());
    animator.setDuration(500);
    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator animation) {
        mPointTwoX = (int) animation.getAnimatedValue("X");
        mPointTwoY = (int) animation.getAnimatedValue("Y");
        invalidate();
      }
    });
    animator.start();
  }
}











