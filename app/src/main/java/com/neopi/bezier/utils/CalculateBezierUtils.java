package com.neopi.bezier.utils;

/**
 * Created by neopi on 16-7-28.
 */
public class CalculateBezierUtils {

  private static class CalculateBezierUtilsHolder {
    public static final CalculateBezierUtils instance = new CalculateBezierUtils();
  }

  public static CalculateBezierUtils getInstance() {
    return CalculateBezierUtilsHolder.instance;
  }

  private CalculateBezierUtils() {}



}