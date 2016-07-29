package com.neopi.bezier.utils;

import android.graphics.PointF;


/**
 * 贝塞尔曲线工具类
 * Created by neopi on 16-7-29.
 */

public class BezierUtils {

  /**
   * B(t) = (1 - t)^2 * P0 + 2t * (1 - t) * P1 + t^2 * P2, t ∈ [0,1]
   *
   * <b>
   *    <br>   获取二阶贝塞尔曲线上点的坐标
   * </b>
   *
   * @param t 曲线长度比例
   * @param p0 起始点
   * @param p1 控制点
   * @param p2 终止点
   * @return t对应的点
   *
   * <b>
   *    <br><br>参考文献：
   * </b>
   *
   * <br> https://github.com/venshine/BezierMaker
   * <br> http://devmag.org.za/2011/04/05/bzier-curves-a-tutorial/
   */
  public static PointF calculateBezierPointF(float t, PointF p0, PointF p1, PointF p2) {
    PointF point = new PointF();
    float temp = 1 - t;
    point.x = temp * temp * p0.x + 2 * t * temp * p1.x + t * t * p2.x;
    point.y = temp * temp * p0.y + 2 * t * temp * p1.y + t * t * p2.y;
    return point;
  }

  /**
   * B(t) = P0 * (1-t)^3 + 3 * P1 * t * (1-t)^2 + 3 * P2 * t^2 * (1-t) + P3 * t^3, t ∈ [0,1]
   *
   *  <b>
   *    <br><br>   获取三阶贝塞尔曲线上点的坐标
   * </b>
   *
   * @param t  曲线长度比例
   * @param p0 起始点
   * @param p1 控制点1
   * @param p2 控制点2
   * @param p3 终止点
   * @return t对应的点
   *
   *
   */
  public static PointF CalculateBezierPointForCubic(float t, PointF p0, PointF p1, PointF p2, PointF p3) {
    PointF point = new PointF();
    float temp = 1 - t;
    point.x = p0.x * temp * temp * temp + 3 * p1.x * t * temp * temp + 3 * p2.x * t * t * temp + p3.x * t * t * t;
    point.y = p0.y * temp * temp * temp + 3 * p1.y * t * temp * temp + 3 * p2.y * t * t * temp + p3.y * t * t * t;
    return point;
  }


}
