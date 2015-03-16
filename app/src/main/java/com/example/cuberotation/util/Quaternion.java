package com.example.cuberotation.util;

public class Quaternion {

  private float w, x, y, z;

  private Quaternion(float w, float x, float y, float z) {
    this.w = w;
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public static Quaternion identity() {
    return new Quaternion(0f, 0f, 0f, 1f);
  }

  public static Quaternion fromAxisAngle(float[] axis, float radians) {
    float sinAngleTwo = (float) Math.sin(radians / 2f);
    return new Quaternion(
      (float) Math.cos(radians / 2f),
      axis[0] * sinAngleTwo,
      axis[1] * sinAngleTwo,
      axis[2] * sinAngleTwo
    );
  }

  public Quaternion times(final Quaternion other) {
    final float newX = this.w * other.x + this.x * other.w + this.y * other.z - this.z * other.y;
    final float newY = this.w * other.y + this.y * other.w + this.z * other.x - this.x * other.z;
    final float newZ = this.w * other.z + this.z * other.w + this.x * other.y - this.y * other.x;
    final float newW = this.w * other.w - this.x * other.x - this.y * other.y - this.z * other.z;
    return new Quaternion(newW, newX, newY, newZ);
  }

  public Quaternion normalize() {
    float mag = (float) Math.sqrt(x * x + y * y + z * z + w * w);
    return new Quaternion(w/mag, x/mag, y/mag, z/mag);
  }

  public float[] toMatrix() {
    final float[] matrix = new float[16];

    final float xx = x * x;
    final float xy = x * y;
    final float xz = x * z;
    final float xw = x * w;
    final float yy = y * y;
    final float yz = y * z;
    final float yw = y * w;
    final float zz = z * z;
    final float zw = z * w;

    // Set matrix from quaternion
    matrix[0] = 1f - 2f * (yy + zz);
    matrix[1] = 2f * (xy - zw);
    matrix[2] = 2f * (xz + yw);
    matrix[3] = 0f;

    matrix[4] = 2f * (xy + zw);
    matrix[5] = 1f - 2f * (xx + zz);
    matrix[6] = 2f * (yz - xw);
    matrix[7] = 0f;

    matrix[8] = 2f * (xz - yw);
    matrix[9] = 2f * (yz + xw);
    matrix[10] = 1f - 2f * (xx + yy);
    matrix[11] = 0f;

    matrix[12] = 0f;
    matrix[13] = 0f;
    matrix[14] = 0f;
    matrix[15] = 1f;

    return matrix;
  }

}
