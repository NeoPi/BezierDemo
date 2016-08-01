package com.neopi.bezier.activitys;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.neopi.bezier.R;
import com.neopi.bezier.widget.CalculateBezierPointView;

/**
 * Created by neopi on 16-7-29.
 */
public class PathAnimationActivity extends AppCompatActivity {


  private CalculateBezierPointView mAnimationPathBezier ;
  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_path_animation_bezier);

    new Handler(){
      @Override public void handleMessage(Message msg) {
        mAnimationPathBezier.start();
      }
    }.sendEmptyMessageDelayed(0x11,2000);
    mAnimationPathBezier = (CalculateBezierPointView) findViewById(R.id.anim_path);

  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (mAnimationPathBezier != null) {
      mAnimationPathBezier.cancel();
    }
  }
}
