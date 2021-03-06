package com.neopi.bezier.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by neopi on 16-7-29.
 */
public class FitBezierView extends View {

  private static final String TAG = "RectangleBezier";

  private Paint mPaint ;  // 圆形画笔
  private Path mPath ;

  private int mPointOneX ; // 第一个圆的圆心坐标
  private int mPointOneY ;

  private int mPointTwoX ; // 第二个圆的圆心坐标
  private int mPointTwoY ;

  private static final int RADIUS = 80;

  public FitBezierView(Context context) {
    this(context,null);
  }

  public FitBezierView(Context context, AttributeSet attrs) {
    this(context, attrs,0);
  }

  public FitBezierView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initData();
  }

  private void initData() {
    mPath = new Path();

    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mPaint.setColor(Color.RED);
    mPaint.setStyle(Paint.Style.FILL);

    mPointOneX = 300 ;
    mPointOneY = 300 ;

    mPointTwoX = 800 ;
    mPointTwoY = 1100 ;



  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    mPath.reset();

    int mControlPointX = (mPointTwoX + mPointOneX) / 2 ;
    int mControlPointY = (mPointTwoY + mPointOneY) / 2 ;

    int dx = mControlPointX - mPointOneX ;
    int dy = mControlPointY - mPointOneY ;

    /* 控制点到起始点的距离 */
    double distance = Math.sqrt(((mControlPointX - mPointOneX) * (mControlPointX - mPointOneX)) + ((mControlPointY - mPointOneY) * (mControlPointY - mPointOneY)));
    double acos = Math.acos(RADIUS / distance);
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


    canvas.drawCircle(mPointOneX,mPointOneY,RADIUS,mPaint);
    canvas.drawCircle(mPointTwoX,mPointTwoY,RADIUS,mPaint);
    canvas.drawCircle(mControlPointX,mControlPointY,10,mPaint);

    mPath.moveTo(x1,y1);
    mPath.quadTo(mControlPointX,mControlPointY,x3,y3);
    mPath.lineTo(x4,y4);
    mPath.quadTo(mControlPointX,mControlPointY,x2,y2);
    canvas.drawPath(mPath,mPaint);

  }
}











