package com.example.cuberotation;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class CubeActivity extends Activity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    CubeSurfaceView cubeSurfaceView = new CubeSurfaceView(this);
    cubeSurfaceView.setLayoutParams(
      new FrameLayout.LayoutParams(
        FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
    );
    cubeSurfaceView.setRenderer(new CubeRenderer(this));
    setContentView(cubeSurfaceView);
  }
}
