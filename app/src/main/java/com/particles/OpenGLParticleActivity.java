package com.particles;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class OpenGLParticleActivity extends AppCompatActivity {

    private OpenGLView mOpenGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opengl_particle);
        mOpenGLView = findViewById(R.id.opengl_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mOpenGLView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mOpenGLView.onPause();
    }
}
