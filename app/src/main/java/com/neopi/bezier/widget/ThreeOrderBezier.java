package com.neopi.bezier.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 三阶贝塞尔曲线（除其实点与结束点外还有二个控制点）
 * Created by neopi on 16-7-28.
 */
public class ThreeOrderBezier extends View {

  private static final String TAG = "ThreeOrderBezier";

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

  private boolean isSecondPoint; // 由于是三阶的被塞尔曲线，也就是说有两个控制点，改变两是为了控制获取按下不同控制点的坐标

  public ThreeOrderBezier(Context context) {
    this(context, null);
  }

  public ThreeOrderBezier(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public ThreeOrderBezier(Context context, AttributeSet attrs, int defStyleAttr) {
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
   * ACTION_MASK在Android中是应用于多点触摸操作，字面上的意思大概是动作掩码的意思吧。
   * 在onTouchEvent(MotionEvent event)中，
   * 使用switch (event.getAction())可以处理ACTION_DOWN和ACTION_UP事件；
   * 使用switch (event.getAction() & MotionEvent.ACTION_MASK)就可以处理处理多点触摸的ACTION_POINTER_DOWN和ACTION_POINTER_UP事件。
   *
   * ACTION_DOWN和ACTION_UP就是单点触摸屏幕，按下去和放开的操作；
   * ACTION_POINTER_DOWN和ACTION_POINTER_UP就是多点触摸屏幕，当有一只手指按下去的时候，另一只手指按下和放开的动作捕捉；
   * ACTION_MOVE就是手指在屏幕上移动的操作；
   * 常用的都是这几个吧。
   */
  @Override public boolean onTouchEvent(MotionEvent event) {
    switch (event.getAction() & MotionEvent.ACTION_MASK) {
      case MotionEvent.ACTION_POINTER_DOWN:
        isSecondPoint = true;
        break;
      case MotionEvent.ACTION_MOVE:
        mAuxiliaryOneX = event.getX(0);
        mAuxiliaryOneY = event.getY(0);
        if (isSecondPoint) {
          mAuxiliaryTwoX = event.getX(1);
          mAuxiliaryTwoY = event.getY(1);
        }
        invalidate();
        break;
      case MotionEvent.ACTION_POINTER_UP:
        isSecondPoint = false;
        break;
    }
    return true;
  }
}
