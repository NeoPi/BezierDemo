package com.neopi.bezier.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by neopi on 16-8-2.
 */
public class BezierLoaderView  extends View{

  private Paint mCirclePaint ;
  private Path mPath ;

  private static float RADIUS = 30.0f; // 半径
  private static float DEF_RADIUS = 30.0f; // 半径
  private static int DISTANCE = 200; // 球距
  private float scale = 0.6f;

  private float x1,x2,x3,y;

  private float moveX ,mContorX1,mContorX2;


  public BezierLoaderView(Context context) {
    this(context,null);
  }

  public BezierLoaderView(Context context, AttributeSet attrs) {
    this(context, attrs,0);
  }

  public BezierLoaderView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    init();
  }

  /**
   * 初始化数据
   *
   */
  private void init() {
    mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mCirclePaint.setColor(Color.RED);
    mCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

    mPath = new Path();

  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }

  @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    int centerX = w / 2;
    int centerY = h / 2;
    y = centerY;

    x2 = centerX;
    x1 = x2 - DISTANCE;
    x3 = x2 + DISTANCE;
    moveX = x1 - DISTANCE;

  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    mPath.reset();

    if ( moveX <= x1){
      RADIUS = ( moveX - x1 ) / 10 + DEF_RADIUS ;
      mContorX1 = (x1 + moveX) / 2;
      mPath.moveTo(x1,y-RADIUS);
      mPath.quadTo(mContorX1,y,moveX,y-RADIUS);
      mPath.lineTo(moveX,y+RADIUS);
      mPath.quadTo(mContorX1,y,x1,y+RADIUS);
      mPath.lineTo(x1,y+RADIUS);

      canvas.drawCircle(x1,y,RADIUS,mCirclePaint);
      canvas.drawCircle(x2,y,DEF_RADIUS,mCirclePaint);
      canvas.drawCircle(x3,y,DEF_RADIUS,mCirclePaint);
    } else if (moveX > x1 && moveX <= x2) {

      float r1 = (x1 - moveX) / 10 +DEF_RADIUS;
      float r2 = (moveX - x2) /10 + DEF_RADIUS ;

      if ( moveX < (x1+x2) / 2)
        RADIUS = r1;
      else
        RADIUS = r2;
      mContorX1 = (x1 + moveX) / 2;
      mContorX2 = (moveX + x2) / 2;

      mPath.moveTo(x1,y-r1);
      mPath.quadTo(mContorX1,y,moveX,y-r1);
      mPath.lineTo(moveX,y-r1);
      mPath.quadTo(mContorX2,y,x2,y-r2);
      mPath.lineTo(x2,y+r2);
      mPath.quadTo(mContorX2,y,moveX,y+r2);
      mPath.lineTo(moveX,y+r2);
      mPath.quadTo(mContorX1,y,x1,y+r1);
      mPath.lineTo(x1,y-r1);

      canvas.drawCircle(x1,y,r1,mCirclePaint);
      canvas.drawCircle(x2,y,r2,mCirclePaint);
      canvas.drawCircle(x3,y,DEF_RADIUS,mCirclePaint);

    } else if (moveX > x2 && moveX <= x3) {

      mContorX1 = (x2 + moveX) / 2;
      mContorX2 = (moveX + x3) / 2;
      float r2 = (x2 - moveX) / 10 + DEF_RADIUS;
      float r3 = (moveX - x3) / 10 + DEF_RADIUS ;
      if ( moveX < (x3+x2) / 2){
        RADIUS = r2;

      }else{
        RADIUS = r3;

      }

      mPath.moveTo(x2,y-r2);
      mPath.quadTo(mContorX1,y,moveX,y-r2);
      mPath.lineTo(moveX,y-r2);
      mPath.quadTo(mContorX2,y,x3,y-r3);
      mPath.lineTo(x3,y+r3);
      mPath.quadTo(mContorX2,y,moveX,y+r3);
      mPath.lineTo(moveX,y+r3);
      mPath.quadTo(mContorX1,y,x2,y+r2);
      mPath.lineTo(x2,y-r2);

      canvas.drawCircle(x1,y,DEF_RADIUS,mCirclePaint);
      canvas.drawCircle(x2,y,r2,mCirclePaint);
      canvas.drawCircle(x3,y,r3,mCirclePaint);

    } else {
      RADIUS = ( x3 - moveX ) / 10 + DEF_RADIUS ;
      mContorX1 = (x3 + moveX) / 2;
      mPath.moveTo(x3,y-RADIUS);
      mPath.quadTo(mContorX1,y,moveX,y-RADIUS);
      mPath.lineTo(moveX,y+RADIUS);
      mPath.quadTo(mContorX1,y,x3,y+RADIUS);
      mPath.lineTo(x3,y+RADIUS);

      canvas.drawCircle(x1,y,DEF_RADIUS,mCirclePaint);
      canvas.drawCircle(x2,y,DEF_RADIUS,mCirclePaint);
      canvas.drawCircle(x3,y,RADIUS,mCirclePaint);
    }


    canvas.drawCircle(moveX,y,RADIUS,mCirclePaint);
    canvas.drawPath(mPath,mCirclePaint);

  }

  /**
   * 开始动画
   *
   */
  private static final String TAG = "BezierLoaderView";
  private ValueAnimator mValueAnimator ;
  private boolean forward = true;
  public void start(){

    Keyframe k0 = Keyframe.ofFloat(0.0f,x1 - DISTANCE);
    Keyframe k1 = Keyframe.ofFloat(0.5f,x3 + DISTANCE);
    Keyframe k2 = Keyframe.ofFloat(1.0f,x1 - DISTANCE);

    PropertyValuesHolder mHolder = PropertyValuesHolder.ofKeyframe(X,k0,k1,k2);
    mValueAnimator = mValueAnimator.ofPropertyValuesHolder(mHolder);
    mValueAnimator.setInterpolator(new LinearInterpolator());
    mValueAnimator.setDuration(5500);
    mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
    mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator animation) {
        moveX = (float) animation.getAnimatedValue();
        //if ( forward ){
        //  moveX ++ ;
        //  if ( moveX >= x3 + DISTANCE - 10){
        //    forward = false;
        //  }
        //} else {
        //  moveX -- ;
        //  if(moveX <= x1 - DISTANCE){
        //    forward = true;
        //  }
        //}
        invalidate();
      }
    });
    mValueAnimator.start();

  }

  /**
   *
   */
  public void stop(){
    if (mValueAnimator != null && mValueAnimator.isRunning()){
      mValueAnimator.end();
    }
  }

}
