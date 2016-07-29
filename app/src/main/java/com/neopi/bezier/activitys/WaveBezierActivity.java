package com.neopi.bezier.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.neopi.bezier.R;
import com.neopi.bezier.widget.WaveBezier;

/**
 * Created by neopi on 16-7-28.
 */
public class WaveBezierActivity extends AppCompatActivity{


  private WaveBezier waveBezier ;
  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_wave_bezier);

    waveBezier = (WaveBezier) findViewById(R.id.wave_bezier);
    waveBezier.start();

  }

  @Override protected void onPause() {
    super.onPause();
    waveBezier.cancle();
  }
}
