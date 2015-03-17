package com.example.cuberotation;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class CubeSurfaceView extends GLSurfaceView {

  private static final float TOUCH_SCALE_FACTOR = 0.015f;

  private CubeRenderer renderer;
  private float previousX;
  private float previousY;

  public CubeSurfaceView(Context context) {
    super(context);
    setEGLContextClientVersion(2);
  }

  public void setRenderer(CubeRenderer renderer) {
    this.renderer = renderer;
    super.setRenderer(renderer);
    setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    // MotionEvent reports input details from the touch screen
    // and other input controls. In this case, you are only
    // interested in events where the touch position changed.
    float x = event.getX();
    float y = event.getY();

    switch (event.getAction()) {
      case MotionEvent.ACTION_MOVE:
        float dx = x - previousX;
        float dy = y - previousY;

        renderer.dy += (int) ((dy) * TOUCH_SCALE_FACTOR);
        renderer.dx += (int) ((dx) * TOUCH_SCALE_FACTOR);
        requestRender();

        return true;
    }
    previousX = x;
    previousY = y;

    return true;
  }
}
