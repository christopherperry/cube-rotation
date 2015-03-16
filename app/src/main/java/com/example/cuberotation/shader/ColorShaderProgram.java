package com.example.cuberotation.shader;

import android.content.Context;


import com.example.cuberotation.R;
import com.example.cuberotation.shader.ShaderHelper;
import com.example.cuberotation.shader.TextResourceReader;

import static android.opengl.GLES20.glUseProgram;

public class ColorShaderProgram {
  // Uniform constants
  protected static final String U_MATRIX = "u_Matrix";
  protected static final String U_COLOR = "u_Color";

  // Attribute constants
  protected static final String A_POSITION = "a_Position";

  // Shader program
  protected final int program;

  protected ColorShaderProgram(Context context) {
    // Compile the shaders and link the program.
    program = ShaderHelper.buildProgram(
      TextResourceReader.readTextFileFromResource(
        context, R.raw.simple_vertex_shader),
      TextResourceReader.readTextFileFromResource(
        context, R.raw.simple_fragment_shader));
  }

  public void useProgram() {
    // Set the current OpenGL shader program to this program.
    glUseProgram(program);
  }
}
