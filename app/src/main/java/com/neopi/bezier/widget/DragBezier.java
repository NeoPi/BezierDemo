package com.neopi.bezier.widget;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;
import com.neopi.bezier.R;

/**
 *
 * Created by neopi on 16-7-29.
 */
public class DragBezier extends View {

  private static final String TAG = "RectangleBezier";

  private Paint mPaint ;  // 圆形画笔
  private Paint mTextPaint ;
  private Path mPath;

  private int mPaintcolor ;
  private int mTextPaintColor ;
  private String text;
  private int mTextSize;

  private int mPointOneX; // 第一个圆的圆心坐标
  private int mPointOneY;

  private int mPointTwoX; // 第二个圆的圆心坐标
  private int mPointTwoY;

  private int DEF_RADIUS = 50;
  private double radius = DEF_RADIUS ;
  private int breakLen = 300; // 拉断距离

  public DragBezier(Context context) {
    this(context, null);
  }

  public DragBezier(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public DragBezier(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    initValues(context, attrs, defStyleAttr);
    initData();
  }

  private void initValues(Context context, AttributeSet attrs, int defStyleAttr) {
    TypedArray mTypeValue = context.obtainStyledAttributes(attrs, R.styleable.DragBezier,defStyleAttr,0);

    int count = mTypeValue.getIndexCount();
    for (int i = 0; i < count ; i++){
      int attr = mTypeValue.getIndex(i);
      switch (attr){
        case R.styleable.DragBezier_dragColor:
          mPaintcolor = mTypeValue.getColor(i,Color.RED);
          break;
        case R.styleable.DragBezier_dragText:
          text = mTypeValue.getString(i);
          break;
        case R.styleable.DragBezier_dragTextColor:
          mTextPaintColor = mTypeValue.getColor(i,Color.BLACK);
          break;

        case R.styleable.DragBezier_dragTextSize:
          mTextSize = mTypeValue.getDimensionPixelSize(i,18);
      }
    }
    mTypeValue.recycle();
  }

  private void initData() {
    mPath = new Path();

    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mPaint.setStyle(Paint.Style.STROKE);
    mPaint.setColor(mPaintcolor);

    mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mTextPaint.setStyle(Paint.Style.STROKE);
    mTextPaint.setColor(mTextPaintColor);
    mTextPaint.setTextSize(mTextSize);

    mPointOneX = 300;
    mPointOneY = 300;

    mPointTwoX = mPointOneX + 300;
    mPointTwoY = mPointOneY + 300;
  }

  public void setColor(int color) {
    this.mPaintcolor = color;
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    //int width = MeasureSpec.getSize(widthMeasureSpec);
    //int height = MeasureSpec.getSize(heightMeasureSpec);
    //int wid = Math.min(width,height);
    //
    //DEF_RADIUS = wid/2;
    //setMeasuredDimension(wid,wid);
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    mPath.reset();

    int mControlPointX = (mPointTwoX + mPointOneX) / 2;
    int mControlPointY = (mPointTwoY + mPointOneY) / 2;

    int dx = mControlPointX - mPointOneX;
    int dy = mControlPointY - mPointOneY;

    /* 控制点到起始点的距离 */
    double distance = Math.sqrt((dx * dx) + (dy * dy));
    radius = - distance / 9 + DEF_RADIUS;
    double atan = Math.acos(radius / distance);

    double acos1 = Math.acos(dx / distance);
    int x1 = mPointOneX + (int) ((radius) * Math.cos(atan - acos1));
    int y1 = mPointOneY - (int) ((radius) * Math.sin(atan - acos1));

    double acos2 = Math.asin(dx / distance);
    int x2 = mPointOneX - (int) ((radius) * Math.sin(atan - acos2));
    int y2 = mPointOneY + (int) ((radius) * Math.cos(atan - acos2));

    int x3 = mPointTwoX + (int) (radius * Math.sin(atan - acos2));
    int y3 = mPointTwoY - (int) (radius * Math.cos(atan - acos2));

    int x4 = mPointTwoX - (int) (radius * Math.cos(atan - acos1));
    int y4 = mPointTwoY + (int) (radius * Math.sin(atan - acos1));
    /* (x1,y1),(x2,y2),(x3,y3) ,(x4,y4) 这四个点分别对应第一个圆与第二个圆上的四个切点*/


    mPath.moveTo(x1, y1);
    mPath.quadTo(mControlPointX, mControlPointY, x3, y3);
    mPath.lineTo(x4, y4);
    mPath.quadTo(mControlPointX, mControlPointY, x2, y2);
    mPath.lineTo(x1, y1);

    canvas.drawCircle(mPointOneX, mPointOneY, (float) radius, mPaint);
    canvas.drawCircle(mPointTwoX, mPointTwoY, (float) radius, mPaint);
    //canvas.drawCircle(x1, y1, 5, mPaint);
    //canvas.drawCircle(x2 , y2, 5, mPaint);
    //canvas.drawCircle(x3, y3, 5, mPaint);
    //canvas.drawCircle(x4, y4, 5, mPaint);
    canvas.drawPath(mPath, mPaint);
    canvas.drawText(text,(mPointTwoX-mTextPaint.measureText(text) / 2),(mPointTwoY+mTextPaint.measureText(text,0,1) / 2),mTextPaint);
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
        mPointTwoX = (int) event.getX();
        mPointTwoY = (int) event.getY();
        invalidate();
        break;
      case MotionEvent.ACTION_MOVE:
        mPointTwoX = (int) event.getX();
        mPointTwoY = (int) event.getY();
        int lenght =
            (int) Math.sqrt((mPointTwoX - mPointOneX)*(mPointTwoX - mPointOneX) + (mPointTwoY - mPointOneY)*(mPointTwoY - mPointOneY));
        if (lenght > breakLen){
          //hindPointOne();
        } else {
          //invalidate();
        }
        invalidate();
        break;
      case MotionEvent.ACTION_UP:
        mPointTwoX = (int) event.getX();
        mPointTwoY = (int) event.getY();
        int distance =
            (int) Math.sqrt((mPointTwoX - mPointOneX)*(mPointTwoX - mPointOneX) + (mPointTwoY - mPointOneY)*(mPointTwoY - mPointOneY));
        if (distance <= breakLen) {
          //reset();
        }
        break;
    }
    return true;
  }

  /**
   *
   */
  private void hindPointOne() {
    PropertyValuesHolder mHolderX = PropertyValuesHolder.ofInt("X", mPointOneX, mPointTwoX);
    PropertyValuesHolder mHolderY = PropertyValuesHolder.ofInt("Y", mPointOneY, mPointTwoY);
    ValueAnimator animator = ValueAnimator.ofPropertyValuesHolder(mHolderX, mHolderY);
    animator.setDuration(1);
    //animator.setInterpolator(new AccelerateInterpolator());
    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator animation) {
        mPointOneX = (int) animation.getAnimatedValue("X");
        mPointOneY = (int) animation.getAnimatedValue("Y");
        invalidate();
      }
    });
    //animator.start();
  }

  private void reset() {
    PropertyValuesHolder mHolderX = PropertyValuesHolder.ofInt("X", mPointTwoX, mPointOneX);
    PropertyValuesHolder mHolderY = PropertyValuesHolder.ofInt("Y", mPointTwoY, mPointOneY);
    ValueAnimator animator = ValueAnimator.ofPropertyValuesHolder(mHolderX, mHolderY);
    animator.setInterpolator(new BounceInterpolator());
    animator.setDuration(500);
    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator animation) {
        mPointTwoX = (int) animation.getAnimatedValue("X");
        mPointTwoY = (int) animation.getAnimatedValue("Y");
        invalidate();
      }
    });
    //animator.start();
  }
}











