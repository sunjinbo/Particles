package com.particles;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class OpenGLView extends GLSurfaceView {
    public OpenGLView(Context context) {
        super(context);
        initView(context);
    }

    public OpenGLView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {

    }
}
