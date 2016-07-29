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
 * 兩阶贝塞尔曲线（除其实点与结束点外还有一个控制点）
 *
 * Created by neopi on 16-7-28.
 */
public class DoubleOrderBezier  extends View {

  private static final String TAG = "DoubleOrderBezier";

  private Paint mPaintBezier ;
  private Paint mPaintAuxiliary ;
  private Paint mPaintAuxiliaryText ;


  private float mAuxiliaryX ;
  private float mAuxiliaryY ;

  private float mStartPointX ;
  private float mStartPointY ;

  private float mEndPointX ;
  private float mEndPointY ;

  private Path mPath ;

  public DoubleOrderBezier(Context context) {
    this(context,null);
  }

  public DoubleOrderBezier(Context context, AttributeSet attrs) {
    this(context, attrs,0);
  }

  public DoubleOrderBezier(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    initPaint();
  }

  /**
   * 初始化一些必要的画笔
   *
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

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    Log.d(TAG, "onMeasure: ");
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
    Log.d(TAG, "onDraw: ");
    mPath.reset();
    mPath.moveTo(mStartPointX,mStartPointY);

    // 辅助点
    canvas.drawPoint(mAuxiliaryX,mAuxiliaryY,mPaintAuxiliary);
    canvas.drawText("起始点",mStartPointX,mStartPointY,mPaintAuxiliaryText);
    canvas.drawText("终止点",mEndPointX,mEndPointY,mPaintAuxiliaryText);
    canvas.drawText("控制点",mAuxiliaryX,mAuxiliaryY,mPaintAuxiliaryText);

    // 辅助线
    canvas.drawLine(mStartPointX,mStartPointY,mAuxiliaryX,mAuxiliaryY,mPaintAuxiliary);
    canvas.drawLine(mEndPointX,mEndPointY,mAuxiliaryX,mAuxiliaryY,mPaintAuxiliary);

    // 二阶贝塞尔曲线
    mPath.quadTo(mAuxiliaryX,mAuxiliaryY,mEndPointX,mEndPointY);
    canvas.drawPath(mPath,mPaintBezier);
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    switch (event.getAction()){
      case MotionEvent.ACTION_MOVE:
        mAuxiliaryX = event.getX();
        mAuxiliaryY = event.getY();

        invalidate(); // 强刷
        return true;
    }
    return super.onTouchEvent(event);
  }
}
