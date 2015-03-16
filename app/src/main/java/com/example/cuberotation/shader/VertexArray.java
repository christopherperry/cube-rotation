package com.example.cuberotation.shader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES30.GL_FLOAT;
import static android.opengl.GLES30.glEnableVertexAttribArray;
import static android.opengl.GLES30.glVertexAttribPointer;

public class VertexArray {
  private static final int BYTES_PER_FLOAT = 4;
  private final FloatBuffer floatBuffer;
  private final int length;

  public VertexArray(float[] vertexData) {
    length = vertexData.length;

    floatBuffer = ByteBuffer
        .allocateDirect(vertexData.length * BYTES_PER_FLOAT)
        .order(ByteOrder.nativeOrder())
        .asFloatBuffer()
        .put(vertexData);
  }

  public void setVertexAttribPointer(int dataOffset, int attributeLocation,
                                     int componentCount, int stride) {
    floatBuffer.position(dataOffset);
    glVertexAttribPointer(attributeLocation, componentCount, GL_FLOAT,
        false, stride, floatBuffer);
    glEnableVertexAttribArray(attributeLocation);

    floatBuffer.position(0);
  }

  public int length() {
    return length;
  }

}
