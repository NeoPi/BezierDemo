package com.neopi.bezier.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;

/**
 * 本例是在{@link ThreeOrderBezier}的基础上实现曲线变形的动画效果
 *
 * Created by neopi on 16-7-28.
 */
public class PathMorphBezier extends View {

  private static final String TAG = "PathMorphBezier";

  private Path mPath; // 路径

  private Paint mPaintBezier; // 路径贝塞尔曲线画笔
  private Paint mPaintAuxiliary; // 辅助点画笔
  private Paint mPaintAuxiliaryText; // 辅助点文字

  private float mAuxiliaryOneX; // 控制点1 x 坐标
  private float mAuxiliaryOneY; // 控制点1 y 坐标

  private float mAuxiliaryTwoX; // 控制点2 x 坐标
  private float mAuxiliaryTwoY; // 控制点2 y 坐标

  private float mStartPointX; // 起始点坐标X
  private float mStartPointY; // 其实点坐标Y

  private float mEndPointX; // 结束点坐标X
  private float mEndPointY; // 结束点坐标Y

  public PathMorphBezier(Context context) {
    this(context, null);
  }

  public PathMorphBezier(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public PathMorphBezier(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initPaint();
  }

  /**
   * 初始化一些变量
   */
  private void initPaint() {
    mPaintBezier = new Paint(Paint.ANTI_ALIAS_FLAG);
    mPaintBezier.setStyle(Paint.Style.STROKE);
    mPaintBezier.setStrokeWidth(8);

    mPaintAuxiliary = new Paint(Paint.ANTI_ALIAS_FLAG);
    mPaintAuxiliary.setStyle(Paint.Style.STROKE);
    mPaintAuxiliary.setStrokeWidth(2);

    mPaintAuxiliaryText = new Paint(Paint.ANTI_ALIAS_FLAG);
    mPaintAuxiliaryText.setStyle(Paint.Style.STROKE);
    mPaintAuxiliaryText.setTextSize(20);

    mPath = new Path();
  }

  @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);

    Log.d(TAG, "onSizeChanged: ");
    mStartPointX = w / 4;
    mStartPointY = h / 2 - 200;

    mEndPointX = w / 4 * 3;
    mEndPointY = h / 2 - 200;

    mAuxiliaryOneX = mStartPointX;
    mAuxiliaryOneY = mStartPointY;

    mAuxiliaryTwoX = mEndPointX;
    mAuxiliaryTwoY = mEndPointY;

  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    mPath.reset(); // 路径重置
    mPath.moveTo(mStartPointX, mStartPointY); // 移动到起始点

    canvas.drawPoint(mAuxiliaryOneX, mAuxiliaryOneY, mPaintAuxiliary);
    canvas.drawPoint(mAuxiliaryTwoX, mAuxiliaryTwoY, mPaintAuxiliary);
    canvas.drawText("起始点", mStartPointX, mStartPointY, mPaintAuxiliaryText);
    canvas.drawText("终止点", mEndPointX, mEndPointY, mPaintAuxiliaryText);
    canvas.drawText("控制点1", mAuxiliaryOneX, mAuxiliaryOneY, mPaintAuxiliaryText);
    canvas.drawText("控制点2", mAuxiliaryTwoX, mAuxiliaryTwoY, mPaintAuxiliaryText);

    canvas.drawLine(mStartPointX, mStartPointY, mAuxiliaryOneX, mAuxiliaryOneY, mPaintAuxiliary);
    canvas.drawLine(mEndPointX, mEndPointY, mAuxiliaryTwoX, mAuxiliaryTwoY, mPaintAuxiliary);
    canvas.drawLine(mAuxiliaryOneX, mAuxiliaryOneY, mAuxiliaryTwoX, mAuxiliaryTwoY,
        mPaintAuxiliary);

    // 三阶贝塞尔曲线
    mPath.cubicTo(mAuxiliaryOneX, mAuxiliaryOneY, mAuxiliaryTwoX, mAuxiliaryTwoY, mEndPointX,
        mEndPointY);
    canvas.drawPath(mPath, mPaintBezier);
  }

  /**
   * 开始变形的动画
   */
  public void start(){
    ValueAnimator valueAnimator = ValueAnimator.ofFloat(mStartPointY,getMeasuredHeight());
    valueAnimator.setDuration(1000);
    valueAnimator.setInterpolator(new BounceInterpolator());
    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator animation) {
        mAuxiliaryOneY = (float) animation.getAnimatedValue();
        mAuxiliaryTwoY = (float) animation.getAnimatedValue();
        invalidate();
      }
    });
    valueAnimator.start();
  }
}
