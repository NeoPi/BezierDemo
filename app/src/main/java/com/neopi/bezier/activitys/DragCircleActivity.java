package com.neopi.bezier.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import com.neopi.bezier.R;
import com.neopi.bezier.widget.DragBezier;

/**
 * Created by neopi on 16-8-1.
 */
public class DragCircleActivity extends AppCompatActivity {

  private DragBezier mFitBezierView ;
  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_drag_circle);
    mFitBezierView = (DragBezier) findViewById(R.id.drag_circle);

    mFitBezierView.setOnTouchListener(new View.OnTouchListener() {
      @Override public boolean onTouch(View v, MotionEvent event) {



        return false;
      }
    });
  }


}
