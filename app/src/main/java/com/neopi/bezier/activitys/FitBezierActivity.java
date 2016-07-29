package com.neopi.bezier.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.neopi.bezier.R;
import com.neopi.bezier.widget.RectangleBezier;

public class FitBezierActivity extends AppCompatActivity {

  private RectangleBezier mBezier ;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_fit_bezier);

    mBezier = (RectangleBezier) findViewById(R.id.fit_one);


  }

  @Override protected void onDestroy() {
    super.onDestroy();

  }
}
