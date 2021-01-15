package com.particles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onCanvasParticleClick(View view) {
        Intent intent = new Intent();
        intent.setClass(this, CanvasParticleActivity.class);
        startActivity(intent);
    }

    public void onOpenGLParticleClick(View view) {
        Intent intent = new Intent();
        intent.setClass(this, OpenGLParticleActivity.class);
        startActivity(intent);
    }
}
