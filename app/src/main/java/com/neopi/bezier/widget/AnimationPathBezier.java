package com.neopi.bezier.widget;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import com.neopi.bezier.utils.BezierUtils;

/**
 * 该view主要是利用贝塞尔曲线完成的动画效果，这里的变量是写死在代码里的，自行扩展
 *
 * Created by neopi on 16-7-29.
 */
public class AnimationPathBezier extends View {

  private Path mPath; // 路径

  private Paint mPathPaint ; // 路径画笔
  private Paint mCirclePaint ; // 圆形画笔

  private int mStartPointX ; // 起始点X
  private int mStartPointY ; // 其实点Y

  private int mEndPointX ; // 终止点X
  private int mEndPointY ; // 终止点Y

  private int mMovePointX ; // 移动点X
  private int mMovePointY ; // 移动点Y

  private int mControlPointX ; // 中心点X
  private int mControlPointY ; // 中心点Y

  public AnimationPathBezier(Context context) {
    this(context,null);
  }

  public AnimationPathBezier(Context context, AttributeSet attrs) {
    this(context, attrs,0);
  }

  public AnimationPathBezier(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    initData();
  }

  /**
   * 初始化一些变量
   */
  private void initData() {
    mPath = new Path();
    mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
    mCirclePaint.setColor(Color.RED);
    mCirclePaint.setStrokeWidth(20);

    mPathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mPathPaint.setStyle(Paint.Style.STROKE);
    mPathPaint.setColor(Color.BLACK);
    mPathPaint.setStrokeWidth(2);

  }

  @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);

    mStartPointX = w / 5 ;
    mStartPointY = h / 10 ;

    mEndPointX = mStartPointX + 500 ;
    mEndPointY = mStartPointY + 1000 ;

    mControlPointX = mEndPointX;
    mControlPointY = mStartPointY ;

    mMovePointX = mStartPointX;
    mMovePointY = mStartPointY;

  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    mPath.reset();
    mPath.moveTo(mStartPointX,mStartPointY);

    canvas.drawCircle(mStartPointX,mStartPointY, 30 ,mCirclePaint);
    canvas.drawCircle(mEndPointX,mEndPointY, 30 ,mCirclePaint);
    canvas.drawCircle(mMovePointX, mMovePointY, 30, mCirclePaint);

    mPath.quadTo(mControlPointX,mControlPointY,mEndPointX,mEndPointY);
    canvas.drawPath(mPath,mPathPaint);
  }

  /**
   * 开始动画
   */
  private ValueAnimator valueAnimator = null;
  public void start(){
    BezierEvaluator mBezierEvaluator = new BezierEvaluator(new PointF(mControlPointX,mControlPointY));
    valueAnimator = ValueAnimator.ofObject(mBezierEvaluator,
                        new PointF(mStartPointX,mStartPointY),
                        new PointF(mEndPointX,mEndPointY));
    valueAnimator.setDuration(1000);
    valueAnimator.setRepeatCount(ValueAnimator.REVERSE);
    valueAnimator.setInterpolator(new LinearInterpolator());
    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator animation) {
        PointF point = (PointF) animation.getAnimatedValue();
        mMovePointX = (int) point.x;
        mMovePointY = (int) point.y;
        invalidate();
      }
    });
    valueAnimator.start();
  }

  /**
   * 取消动画
   */
  public void cancel(){
    if (valueAnimator != null){
      valueAnimator.cancel();
    }
  }

  public ValueAnimator getValueAnimator() {
    return valueAnimator;
  }

  class BezierEvaluator implements TypeEvaluator<PointF> {

    private PointF mControlPointF ;

    public BezierEvaluator(PointF controlPointF) {
      this.mControlPointF = controlPointF;
    }

    @Override public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
      return BezierUtils.calculateBezierPointF(fraction,startValue,mControlPointF , endValue);
    }
  }
}
