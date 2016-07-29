package com.neopi.bezier.utils;

import android.graphics.PointF;

/**
 * Created by neopi on 16-7-29.
 */
public class BezierUtils {

  /**
   * B(t) = (1 - t)^2 * P0 + 2t * (1 - t) * P1 + t^2 * P2, t ∈ [0,1]
   *
   * @param t  曲线长度比例
   * @param p0 起始点
   * @param p1 控制点
   * @param p2 终止点
   * @return t对应的点
   *
   * 二阶贝塞尔曲线
   * https://github.com/venshine/BezierMaker
   * http://devmag.org.za/2011/04/05/bzier-curves-a-tutorial/
   *
   */
  public static PointF calculateBezierPointF(float t, PointF p0, PointF p1, PointF p2){
    PointF point = new PointF();
    float temp = 1 - t;
    point.x = temp * temp * p0.x + 2 * t * temp * p1.x + t * t * p2.x;
    point.y = temp * temp * p0.y + 2 * t * temp * p1.y + t * t * p2.y;
    return point;
  }





}
