package com.example.cuberotation.util;

import static android.opengl.Matrix.invertM;
import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.multiplyMV;
import static android.opengl.Matrix.rotateM;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.transposeM;

/**
 * Matrix utility for use in Android.
 * We have to transpose all of these because
 * matrices are column-major order instead of row-major.
 */
public class MatrixUtil {
  private static final float DEG_TO_RAD = 0.0174532925f;
  private static final float[] AXIS_X = new float[] {1f, 0f, 0f};
  private static final float[] AXIS_Y = new float[] {0f, 1f, 0f};
  private static final float[] AXIS_Z = new float[] {0f, 0f, 1f};

  /**
   * Holds position, rotation, and scale
   * of an object in world space.
   */
  public static class Transform {
    public float[] position = new float[]{0f, 0f, 0f};
    public float[] rotation = new float[]{0f, 0f, 0f};
    public float[] scale = new float[]{1f, 1f, 1f};

    private float[] modelMatrix = new float[16];
    private float[] currentRotation = new float[16];

    public static enum Space {
      SELF, WORLD
    }

    public void rotate(float[] axis, float angle, Space relativeTo) {

    }

    public float[] localToWorldMatrix() {
      float[] scaleMatrix = scaleMatrix(scale);
      float[] rotationMatrix = rotationMatrixFromQuaternions(rotation);
      float[] positionMatrix = translationMatrix(position);

      float[] worldMatrix = identityMatrix();
      worldMatrix = multiplyMatrices(worldMatrix, positionMatrix);
      worldMatrix = multiplyMatrices(worldMatrix, rotationMatrix);
      worldMatrix = multiplyMatrices(worldMatrix, scaleMatrix);
      return worldMatrix;
    }
  }

  public static float[] identityMatrix() {
    float[] identity = new float[16];
    setIdentityM(identity, 0);
    return identity;
  }

  public static float[] scaleMatrix(float[] scale) {
    return transpose(new float[] {
      scale[0], 0f,       0f,       0f,
      0f,       scale[1], 0f,       0f,
      0f,       0f,       scale[2], 0f,
      0f,       0f,       0f,       1f
    });
  }

  public static float[] translationMatrix(float[] translate) {
    return transpose(new float[] {
      1f, 0f, 0f, translate[0],
      0f, 1f, 0f, translate[1],
      0f, 0f, 1f, translate[2],
      0f, 0f, 0f, 1f
    });
  }

  public static float[] rotationMatrix(float[] rotation) {
    float xRad = rotation[0] * DEG_TO_RAD;
    float yRad = rotation[1] * DEG_TO_RAD;
    float zRad = rotation[2] * DEG_TO_RAD;

    float[] rotationX = rotationX(xRad);
    float[] rotationY = rotationY(yRad);
    float[] rotationZ = rotationZ(zRad);

    return transpose(multiplyMatrices(multiplyMatrices(rotationX, rotationY), rotationZ));
  }

  public static float[] rotationMatrixFromQuaternions(float[] rotation) {
    float xRad = rotation[0] * DEG_TO_RAD;
    float yRad = rotation[1] * DEG_TO_RAD;

    Quaternion rotationX = Quaternion.fromAxisAngle(AXIS_X, xRad);
    Quaternion rotationY = Quaternion.fromAxisAngle(AXIS_Y, yRad);

    return rotationY.times(rotationX).normalize().toMatrix();
  }

  static float[] rotationX(float rad) {
    float cosTheta = (float) Math.cos(rad);
    float sinTheta = (float) Math.sin(rad);

    return new float[] {
      1f, 0f,       0f,        0f,
      0f, cosTheta, -sinTheta, 0f,
      0f, sinTheta, cosTheta,  0f,
      0f, 0f,       0f,        1f
    };
  }

  static float[] rotationY(float rad) {
    float cosTheta = (float) Math.cos(rad);
    float sinTheta = (float) Math.sin(rad);

    return new float[] {
      cosTheta,  0f, sinTheta, 0f,
      0f,        1f, 0f,       0f,
      -sinTheta, 0f, cosTheta, 0f,
      0f,        0f, 0f,       1f
    };
  }

  static float[] rotationZ(float rad) {
    float cosTheta = (float) Math.cos(rad);
    float sinTheta = (float) Math.sin(rad);

    return new float[] {
      cosTheta,  -sinTheta, 0f, 0f,
      sinTheta,  cosTheta,  0f, 0f,
      0f,        0f,        1f, 0f,
      0f,        0f,        0f, 1f
    };
  }

  public static float[] transpose(float[] matrix) {
    float[] transposeM = new float[16];
    transposeM(transposeM, 0, matrix, 0);
    return transposeM;
  }

  public static float[] multiplyMatrices(float[] lhs, float[] rhs) {
    float[] result = new float[16];
    multiplyMM(result, 0, lhs, 0, rhs, 0);
    return result;
  }

  public float[] rotate(float[] matrix, float angle, float[] axis) {
    float[] resultMatrix = new float[16];
    rotateM(resultMatrix, 0, matrix, 0, angle, axis[0], axis[1], axis[2]);
    return resultMatrix;
  }

  public static float[] multiplyMatrixVector(float[] matrix, float[] vector) {
    float[] resultVector = new float[4];
    multiplyMV(resultVector, 0, matrix, 0, vector, 0);
    return resultVector;
  }

  public static float[] invert(float[] matrix) {
    float[] invertedMatrix = new float[16];
    boolean success = invertM(invertedMatrix, 0, matrix, 0);
    return invertedMatrix;
  }

  public static float[] normalize(float[] vector) {
    double x = vector[0];
    double y = vector[1];
    double z = vector[2];
    double magnitude = Math.sqrt(x * x + y * y + z * z);
    return new float[]{
      (float) (x / magnitude), (float) (y / magnitude), (float) (z / magnitude), vector[3]
    };
  }
}
