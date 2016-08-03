package com.neopi.bezier.activitys;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.neopi.bezier.R;
import com.neopi.bezier.widget.BezierLoaderView;

/**
 * Created by neopi on 16-8-2.
 */
public class BezierLoadingActivity extends AppCompatActivity {

  BezierLoaderView mBezierLoaderView = null;
  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_loading_bezier);

    mBezierLoaderView = (BezierLoaderView) findViewById(R.id.loading_bezier);

    new Handler(){
      @Override public void handleMessage(Message msg) {
        mBezierLoaderView.start();
      }
    }.sendEmptyMessageDelayed(0x11,2000);
  }

  @Override protected void onStop() {
    super.onStop();
    mBezierLoaderView.stop();
  }
}
