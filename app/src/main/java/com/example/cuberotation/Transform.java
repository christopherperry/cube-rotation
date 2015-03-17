package com.example.cuberotation;

import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.rotateM;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.translateM;
import static java.lang.System.arraycopy;

/**
 * Holds position, rotation, and scale
 * of an object in world space.
 *
 * TODO: scale (not needed at the moment)
 */
public class Transform {
  public float[] position = new float[]{0f, 0f, 0f};
  public float[] rotation = new float[]{0f, 0f, 0f};
  public float[] scale = new float[]{1f, 1f, 1f};

  private float[] modelMatrix = new float[16];
  private float[] currentRotation = new float[16];
  private float[] accumulatedRotation = new float[16];
  private float[] temporaryMatrix = new float[16];

  public static enum Space {
    SELF, WORLD
  }

  public Transform() {
    setIdentityM(accumulatedRotation, 0);
  }

  public void rotate(float[] axis, float angle, Space relativeTo) {
    if (relativeTo == Space.WORLD) {
      // Set a matrix that contains the current rotation.
      setIdentityM(currentRotation, 0);
      rotateM(currentRotation, 0, angle, axis[0], axis[1], axis[2]);

      // Multiply the current rotation by the accumulated rotation,
      // and then set the accumulated rotation to the result.
      multiplyMM(temporaryMatrix, 0, currentRotation, 0, accumulatedRotation, 0);
      arraycopy(temporaryMatrix, 0, accumulatedRotation, 0, 16);
    } else {
      // Set a matrix that contains the current rotation.
      setIdentityM(currentRotation, 0);

      if (isRotationXAxis(axis)) {
        rotation[0] += angle;
      } else if (isRotationYAxis(axis)) {
        rotation[1] += angle;
      } else if (isRotationZAxis(axis)) {
        rotation[2] += angle;
      }

      rotateM(currentRotation, 0, rotation[0], 1, 0, 0);
      rotateM(currentRotation, 0, rotation[1], 0, 1, 0);
      rotateM(currentRotation, 0, rotation[2], 0, 0, 1);

      arraycopy(currentRotation, 0, accumulatedRotation, 0, 16);
    }
  }

  private boolean isRotationXAxis(float[] axis) {
    return axis[0] == 1 && axis[1] == 0 && axis[2] == 0;
  }

  private boolean isRotationYAxis(float[] axis) {
    return axis[0] == 0 && axis[1] == 1 && axis[2] == 0;
  }

  private boolean isRotationZAxis(float[] axis) {
    return axis[0] == 0 && axis[1] == 0 && axis[2] == 1;
  }

  public float[] localToWorldMatrix() {
    // Translation
    setIdentityM(modelMatrix, 0);
    translateM(modelMatrix, 0, position[0], position[1], position[2]);

    // Rotation
    multiplyMM(temporaryMatrix, 0, modelMatrix, 0, accumulatedRotation, 0);
    arraycopy(temporaryMatrix, 0, modelMatrix, 0, 16);

    return modelMatrix;
  }
}
