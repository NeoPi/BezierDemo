package com.neopi.bezier.activitys;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.neopi.bezier.R;
import com.neopi.bezier.widget.PathMorphBezier;

/**
 * Created by neopi on 16-7-28.
 */
public class PathMorphAnimertorActivity extends AppCompatActivity {

  private PathMorphBezier pathMorphBezier ;
  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_path_morph);

    pathMorphBezier = (PathMorphBezier) findViewById(R.id.path_morph);
    new Handler(){
      @Override public void handleMessage(Message msg) {
        pathMorphBezier.start(); // 2秒钟之后开始变形的动画
      }
    }.sendEmptyMessageDelayed(0,2000);
  }
}
