package com.example.cuberotation;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class CubeSurfaceView extends GLSurfaceView {

  private static final float TOUCH_SCALE_FACTOR = 0.25f;

  private CubeRenderer mRenderer;
  private float mPreviousX;
  private float mPreviousY;
  private long downTime;

  public CubeSurfaceView(Context context) {
    super(context);
    setEGLContextClientVersion(2);
  }

  public void setRenderer(CubeRenderer renderer) {
    mRenderer = renderer;
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
      case MotionEvent.ACTION_DOWN:
        downTime = event.getEventTime();
        break;
      case MotionEvent.ACTION_MOVE:
        float dx = x - mPreviousX;
        float dy = y - mPreviousY;

        if (Math.abs(dx) > Math.abs(dy)) {
          mRenderer.setHorizontal(mRenderer.getHorizontal() + (int) ((dx) * TOUCH_SCALE_FACTOR));
        } else {
          mRenderer.setVertical(mRenderer.getVertical() + (int) ((dy) * TOUCH_SCALE_FACTOR));
        }
        requestRender();
        break;

      case MotionEvent.ACTION_UP:
        if (event.getEventTime() - downTime <= 250) {
//          mRenderer.onClick(x, y);
          requestRender();

          callOnClick();
        }
        break;
    }
    mPreviousX = x;
    mPreviousY = y;

    return true;
  }
}
