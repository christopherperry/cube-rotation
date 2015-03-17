package com.example.cuberotation;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.example.cuberotation.shader.CubeShaderProgram;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_TEST;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glEnable;
import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.perspectiveM;

public class CubeRenderer implements GLSurfaceView.Renderer {
  private static final int ALL_BUFFERS = GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT;
  private static final float Z_NEAR = 1f;
  private static final float Z_FAR = 40f;
  public static final float[] AXIS_X = new float[]{1f, 0f, 0f};
  public static final float[] AXIS_Y = new float[]{0f, 1f, 0f};
  public static final float[] AXIS_Z = new float[]{0f, 0f, 1f};

  //////////////////////////////////////////////////////////////////////////////////////////////////
  // State variables
  //////////////////////////////////////////////////////////////////////////////////////////////////

  private final Context context;
  private final float[] projectionMatrix = new float[16];

  private CubeShaderProgram cubeShaderProgram;
  private Transform transform = new Transform();

  public float dx;
  public float dy;

  public CubeRenderer(Context context) {
    this.context = context;
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////
  // Renderer Implementation
  //////////////////////////////////////////////////////////////////////////////////////////////////

  @Override
  public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
    glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
    cubeShaderProgram = new CubeShaderProgram(context);
  }

  @Override
  public void onSurfaceChanged(GL10 glUnused, int width, int height) {
    // Set the OpenGL viewport to fill the entire surface.
    glViewport(0, 0, width, height);

    final float aspect = (float) width / (float) height;
    perspectiveM(projectionMatrix, 0, 53.13f, aspect, Z_NEAR, Z_FAR);
  }

  @Override
  public void onDrawFrame(GL10 glUnused) {
    glClear(ALL_BUFFERS);
    glEnable(GL_DEPTH_TEST);

    float[] mvpMatrix = new float[16];
    multiplyMM(mvpMatrix, 0, projectionMatrix, 0, getModelMatrix(), 0);

    cubeShaderProgram.draw(mvpMatrix);
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////
  // Private Methods
  //////////////////////////////////////////////////////////////////////////////////////////////////

  private float[] getModelMatrix() {
    transform.position = new float[]{0f, 0f, -2f};
    transform.rotate(AXIS_Y, dx, Transform.Space.WORLD);
    transform.rotate(AXIS_X, dy, Transform.Space.WORLD);

    dx = 0;
    dy = 0;

    return transform.localToWorldMatrix();
  }
}
