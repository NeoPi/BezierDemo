package com.neopi.bezier.widget;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import com.neopi.bezier.utils.BezierUtils;

/**
 * Created by neopi on 16-7-29.
 */
public class CalculateBezierPointView extends View {

  private Paint mPaint;
  private ValueAnimator mAnimatorQuadratic;
  private ValueAnimator mAnimatorCubic;
  private PointF mPointQuadratic;
  private PointF mPointCubic;
  private AnimatorSet set;

  public CalculateBezierPointView(Context context) {
    super(context);
  }

  public CalculateBezierPointView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public CalculateBezierPointView(Context context, AttributeSet attrs) {
    super(context, attrs);
    set = new AnimatorSet();
    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mAnimatorQuadratic = ValueAnimator.ofFloat(0, 1);
    mAnimatorQuadratic.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator valueAnimator) {
        PointF point = BezierUtils.calculateBezierPointF(valueAnimator.getAnimatedFraction(),
            new PointF(100, 100), new PointF(500, 100), new PointF(500, 500));
        mPointQuadratic.x = point.x;
        mPointQuadratic.y = point.y;
        invalidate();
      }
    });

    mAnimatorCubic = ValueAnimator.ofFloat(0, 1);
    mAnimatorCubic.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator valueAnimator) {
        PointF point = BezierUtils.CalculateBezierPointForCubic(valueAnimator.getAnimatedFraction(),
            new PointF(100, 600), new PointF(100, 1100), new PointF(500, 1000), new PointF(500, 600));
        mPointCubic.x = point.x;
        mPointCubic.y = point.y;
        //invalidate();
      }
    });

    mPointQuadratic = new PointF();
    mPointQuadratic.x = 100;
    mPointQuadratic.y = 100;

    mPointCubic = new PointF();
    mPointCubic.x = 100;
    mPointCubic.y = 600;

  }

  @Override
  protected void onDraw(final Canvas canvas) {
    super.onDraw(canvas);
    canvas.drawCircle(mPointQuadratic.x, mPointQuadratic.y, 10, mPaint);
    canvas.drawCircle(mPointCubic.x, mPointCubic.y, 10, mPaint);
  }

  /**
   * 开始动画
   *
   */
  public void start(){

    set.playTogether(mAnimatorQuadratic, mAnimatorCubic);
    set.setDuration(2000);
    set.start();
  }

  public void cancel() {
    if (set != null && set.isRunning()){
      set.cancel();
    }
  }
}
