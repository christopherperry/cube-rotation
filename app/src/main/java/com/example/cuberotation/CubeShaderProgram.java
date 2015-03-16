package com.example.cuberotation;

import android.content.Context;
import android.graphics.Color;

import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUniformMatrix4fv;

public class CubeShaderProgram extends ColorShaderProgram {
  protected static final int POSITION_COMPONENT_COUNT = 3;

  // Uniforms
  private final int uMatrixLocation;
  private final int uColorLocation;

  // Attributes
  private final int aPositionLocation;
  private final float[] red;
  private final float[] green;
  private final float[] blue;
  private final float[] yellow;
  private final float[] cyan;
  private final float[] gray;
  private final VertexArray vertexArray;

  public CubeShaderProgram(Context context) {
    super(context);

    // Retrieve uniform locations for the shader program.
    uMatrixLocation = glGetUniformLocation(program, U_MATRIX);
    uColorLocation = glGetUniformLocation(program, U_COLOR);

    // Retrieve attribute locations for the shader program.
    aPositionLocation = glGetAttribLocation(program, A_POSITION);

    red = red();
    green = green();
    blue = blue();
    yellow = yellow();
    cyan = cyan();
    gray = gray();

    float size = 0.2f;

    float[] cubeVertices = new float[]{
      ////////////////////////////////////////////////////////////////////
      // FRONT
      ////////////////////////////////////////////////////////////////////
      // Triangle 1
      -size, size, size, // top-left
      -size, -size, size, // bottom-left
      size, -size, size, // bottom-right
      // Triangle 2
      size, -size, size, // bottom-right
      size, size, size, // top-right
      -size, size, size, // top-left
      ////////////////////////////////////////////////////////////////////
      // BACK
      ////////////////////////////////////////////////////////////////////
      // Triangle 1
      -size, size, -size, // top-left
      -size, -size, -size, // bottom-left
      size, -size, -size, // bottom-right
      // Triangle 2
      size, -size, -size, // bottom-right
      size, size, -size, // top-right
      -size, size, -size, // top-left

      ////////////////////////////////////////////////////////////////////
      // LEFT
      ////////////////////////////////////////////////////////////////////
      // Triangle 1
      -size, size, -size, // top-left
      -size, -size, -size, // bottom-left
      -size, -size, size, // bottom-right
      // Triangle 2
      -size, -size, size, // bottom-right
      -size, size, size, // top-right
      -size, size, -size, // top-left
      ////////////////////////////////////////////////////////////////////
      // RIGHT
      ////////////////////////////////////////////////////////////////////
      // Triangle 1
      size, size, -size, // top-left
      size, -size, -size, // bottom-left
      size, -size, size, // bottom-right
      // Triangle 2
      size, -size, size, // bottom-right
      size, size, size, // top-right
      size, size, -size, // top-left

      ////////////////////////////////////////////////////////////////////
      // TOP
      ////////////////////////////////////////////////////////////////////
      // Triangle 1
      -size, size, -size, // top-left
      -size, size, size, // bottom-left
      size, size, size, // bottom-right
      // Triangle 2
      size, size, size, // bottom-right
      size, size, -size, // top-right
      -size, size, -size, // top-left
      ////////////////////////////////////////////////////////////////////
      // BOTTOM
      ////////////////////////////////////////////////////////////////////
      // Triangle 1
      -size, -size, -size, // top-left
      -size, -size, size, // bottom-left
      size, -size, size, // bottom-right
      // Triangle 2
      size, -size, size, // bottom-right
      size, -size, -size, // top-right
      -size, -size, -size // top-left
    };

    vertexArray = new VertexArray(cubeVertices);
  }

  public void draw(float[] matrix) {
    useProgram();

    vertexArray.setVertexAttribPointer(
      0,
      aPositionLocation,
      POSITION_COMPONENT_COUNT,
      0);

    // Pass the matrix into the shader program.
    glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);

    int startPosition = 0;
    int verticesPerTriangle = 6;

    // Draw FRONT face RED
    glUniform4f(uColorLocation, red[0], red[1], red[2], 1f);
    glDrawArrays(GL_TRIANGLES, startPosition, verticesPerTriangle);
    startPosition += verticesPerTriangle;

    // Draw BACK face GREEN
    glUniform4f(uColorLocation, green[0], green[1], green[2], 1f);
    glDrawArrays(GL_TRIANGLES, startPosition, verticesPerTriangle);
    startPosition += verticesPerTriangle;

    // Draw LEFT face BLUE
    glUniform4f(uColorLocation, blue[0], blue[1], blue[2], 1f);
    glDrawArrays(GL_TRIANGLES, startPosition, verticesPerTriangle);
    startPosition += verticesPerTriangle;

    // Draw RIGHT face YELLOW
    glUniform4f(uColorLocation, yellow[0], yellow[1], yellow[2], 1f);
    glDrawArrays(GL_TRIANGLES, startPosition, verticesPerTriangle);
    startPosition += verticesPerTriangle;

    // Draw TOP face CYAN
    glUniform4f(uColorLocation, cyan[0], cyan[1], cyan[2], 1f);
    glDrawArrays(GL_TRIANGLES, startPosition, verticesPerTriangle);
    startPosition += verticesPerTriangle;

    // Draw BOTTOM face GRAY
    glUniform4f(uColorLocation, gray[0], gray[1], gray[2], 1f);
    glDrawArrays(GL_TRIANGLES, startPosition, verticesPerTriangle);
  }

  float[] red() {
    return new float[]{
      Color.red(Color.RED) / 255f,
      Color.green(Color.RED) / 255f,
      Color.blue(Color.RED) / 255f
    };
  }

  float[] green() {
    return new float[]{
      Color.red(Color.GREEN) / 255f,
      Color.green(Color.GREEN) / 255f,
      Color.blue(Color.GREEN) / 255f
    };
  }

  float[] blue() {
    return new float[]{
      Color.red(Color.BLUE) / 255f,
      Color.green(Color.BLUE) / 255f,
      Color.blue(Color.BLUE) / 255f
    };
  }

  float[] yellow() {
    return new float[]{
      Color.red(Color.YELLOW) / 255f,
      Color.green(Color.YELLOW) / 255f,
      Color.blue(Color.YELLOW) / 255f
    };
  }

  float[] cyan() {
    return new float[]{
      Color.red(Color.CYAN) / 255f,
      Color.green(Color.CYAN) / 255f,
      Color.blue(Color.CYAN) / 255f
    };
  }

  float[] gray() {
    return new float[]{
      Color.red(Color.GRAY) / 255f,
      Color.green(Color.GRAY) / 255f,
      Color.blue(Color.GRAY) / 255f
    };
  }
}
