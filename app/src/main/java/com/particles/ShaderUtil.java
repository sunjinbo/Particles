package com.particles;

import android.content.Context;
import android.opengl.GLES20;

import java.io.IOException;
import java.io.InputStream;

public class ShaderUtil {
    public static int compileVertexShader(Context context, String fileName) {
        final String shaderCode = loadAssets(context, fileName);
        return compileShader(GLES20.GL_VERTEX_SHADER, shaderCode);
    }

    public static int compileFragmentShader(Context context, String fileName) {
        final String shaderCode = loadAssets(context, fileName);
        return compileShader(GLES20.GL_FRAGMENT_SHADER, shaderCode);
    }

    public static int linkProgram(int vertexShaderId, int fragmentShaderId) {
        final int programId = GLES20.glCreateProgram();
        if (programId != 0) {
            //将顶点着色器加入到程序
            GLES20.glAttachShader(programId, vertexShaderId);
            //将片元着色器加入到程序中
            GLES20.glAttachShader(programId, fragmentShaderId);
            //链接着色器程序
            GLES20.glLinkProgram(programId);
            final int[] linkStatus = new int[1];

            GLES20.glGetProgramiv(programId, GLES20.GL_LINK_STATUS, linkStatus, 0);
            if (linkStatus[0] == 0) {
                String logInfo = GLES20.glGetProgramInfoLog(programId);
                System.err.println(logInfo);
                GLES20.glDeleteProgram(programId);
                return 0;
            }
            return programId;
        } else {
            //创建失败
            return 0;
        }
    }

    private static int compileShader(int type, String shaderCode) {
        final int shaderId = GLES20.glCreateShader(type); // 创建一个着色器
        if (shaderId != 0) {
            GLES20.glShaderSource(shaderId, shaderCode);
            GLES20.glCompileShader(shaderId);
            //检测状态
            final int[] compileStatus = new int[1];
            GLES20.glGetShaderiv(shaderId, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
            if (compileStatus[0] == 0) {
                String logInfo = GLES20.glGetShaderInfoLog(shaderId);
                System.err.println(logInfo);
                //创建失败
                GLES20.glDeleteShader(shaderId);
                return 0;
            }
            return shaderId;
        } else {
            //创建失败
            return 0;
        }
    }

    private static String loadAssets(Context context, String fileName) {
        String result = null;
        try {
            InputStream is = context.getResources().getAssets()
                    .open(fileName);
            int length = is.available();
            byte[] buffer = new byte[length];
            is.read(buffer);
            result = new String(buffer, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
