package com.neopi.bezier.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.neopi.bezier.R;
import com.neopi.bezier.widget.DoubleOrderBezier;

public class MainActivity extends AppCompatActivity {

  private DoubleOrderBezier mBezier ;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mBezier = (DoubleOrderBezier) findViewById(R.id.bezier_one);
    registerForContextMenu(mBezier);
  }

  @Override
  public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
    switch (v.getId()){
      case R.id.bezier_one:
        menu.setHeaderTitle("页面跳转");
        menu.add(0,Menu.FIRST,0,"三阶贝塞尔曲线");
        menu.add(0,Menu.FIRST + 1,0,"曲线变形动画");
        menu.add(0,Menu.FIRST + 2,0,"波浪动画");
        menu.add(0,Menu.FIRST + 3,0,"曲线动画");
        menu.add(0,Menu.FIRST + 4,0,"拟合动画");
        menu.add(0,Menu.FIRST + 5,0,"拟合动画实现QQ未读消息小红点");
        menu.add(0,Menu.FIRST + 6,0,"拟合动画实现加载动画");
        break;
    }
  }

  @Override public boolean onContextItemSelected(MenuItem item) {
    switch (item.getItemId()){
      case Menu.FIRST:
        startActivity(new Intent(MainActivity.this,ThreeOrderActivity.class));
        break ;
      case Menu.FIRST + 1:
        startActivity(new Intent(MainActivity.this,PathMorphAnimertorActivity.class));
        break;
      case Menu.FIRST + 2:
        startActivity(new Intent(MainActivity.this,WaveBezierActivity.class));
        break;
      case Menu.FIRST + 3:
        startActivity(new Intent(MainActivity.this,PathAnimationActivity.class));
        break;
      case Menu.FIRST + 4:
        startActivity(new Intent(MainActivity.this,FitBezierActivity.class));
        break;
      case Menu.FIRST + 5:
        startActivity(new Intent(MainActivity.this,DragCircleActivity.class));
        break;
      case Menu.FIRST + 6:
        startActivity(new Intent(MainActivity.this,BezierLoadingActivity.class));
        break;

    }
    return true;
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    unregisterForContextMenu(mBezier);
  }
}
