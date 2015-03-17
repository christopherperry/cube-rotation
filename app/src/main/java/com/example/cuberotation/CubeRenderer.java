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
import static android.opengl.Matrix.rotateM;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.translateM;
import static com.example.cuberotation.util.MatrixUtil.Transform;
import static com.example.cuberotation.util.MatrixUtil.multiplyMatrices;
import static java.lang.System.arraycopy;

public class CubeRenderer implements GLSurfaceView.Renderer {
  private static final int ALL_BUFFERS = GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT;
  private static final float Z_NEAR = 1f;
  private static final float Z_FAR = 40f;

  //////////////////////////////////////////////////////////////////////////////////////////////////
  // State variables
  //////////////////////////////////////////////////////////////////////////////////////////////////

  private final Context context;
  private final float[] projectionMatrix = new float[16];

  private CubeShaderProgram cubeShaderProgram;
  private Transform transform = new Transform();

  private float[] accumulatedRotation = new float[16];
  private float[] currentRotation = new float[16];
  private float[] modelMatrix = new float[16];
  private float[] temporaryMatrix = new float[16];

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
    setIdentityM(accumulatedRotation, 0);
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

    float[] mvpMatrix = multiplyMatrices(projectionMatrix, getModelMatrix());

    cubeShaderProgram.draw(mvpMatrix);
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////
  // Private Methods
  //////////////////////////////////////////////////////////////////////////////////////////////////

//  private float[] getMvpMatrix() {
//    transform.position = new float[]{0f, 0f, -2f};
//    transform.rotation = new float[]{vertical, horizontal, 0f};
//
//    return multiplyMatrices(projectionMatrix, transform.localToWorldMatrix());
//  }

  private float[] getModelMatrix() {
    // Draw a cube.
    // Translate the cube into the screen.
    setIdentityM(modelMatrix, 0);
    translateM(modelMatrix, 0, 0.0f, 0f, -2f);

    // Set a matrix that contains the current rotation.
    setIdentityM(currentRotation, 0);
    rotateM(currentRotation, 0, dx, 0.0f, 1.0f, 0.0f);
    rotateM(currentRotation, 0, dy, 1.0f, 0.0f, 0.0f);
    dx = 0.0f;
    dy = 0.0f;

    // Multiply the current rotation by the accumulated rotation, and then set the accumulated
    // rotation to the result.
    multiplyMM(temporaryMatrix, 0, currentRotation, 0, accumulatedRotation, 0);
    arraycopy(temporaryMatrix, 0, accumulatedRotation, 0, 16);

    // Rotate the cube taking the overall rotation into account.
    multiplyMM(temporaryMatrix, 0, modelMatrix, 0, accumulatedRotation, 0);
    arraycopy(temporaryMatrix, 0, modelMatrix, 0, 16);

    return modelMatrix;
  }
}
