package com.example.sierpmat.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.hardware.SensorEvent;


public class Main2Activity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mOrientation;
    private ImageView plan;
    private ImageView arrow;
    private TextView info;
    private float currentDegree = 0f;
    private ScaleGestureDetector mScaleGestureDetectorPlan;
    private ScaleGestureDetector mScaleGestureDetectorArrow;

    private float mScaleFactor = 1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        // Get an instance of the SensorManager
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mOrientation = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        plan = (ImageView) findViewById(R.id.plan);
        arrow = (ImageView) findViewById(R.id.arrow);
        info = (TextView) findViewById(R.id.info);
        turnOnPinchZoom();

    }

    private void turnOnPinchZoom() {
        mScaleGestureDetectorPlan = new ScaleGestureDetector(this, new ScaleListener(this.plan));
        mScaleGestureDetectorArrow = new ScaleGestureDetector(this, new ScaleListener(this.arrow));

    }


    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        mScaleGestureDetectorPlan.onTouchEvent(motionEvent);
        mScaleGestureDetectorArrow.onTouchEvent(motionEvent);
        return true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mOrientation, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        float degree = Math.round(sensorEvent.values[0]);
        currentDegree = -degree;
        plan.setRotation(currentDegree);

        info.setText("value" + degree);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}


