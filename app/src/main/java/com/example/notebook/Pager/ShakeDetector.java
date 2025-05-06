package com.example.notebook.Pager;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ShakeDetector implements SensorEventListener {
    private static final float SHAKE_THRESHOLD = 3.25f;
    private static final int MIN_TIME_BETWEEN_SHAKES = 1000;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private long lastShakeTime;
    private ShakeListener listener;

    public interface ShakeListener {
        void onShake();
    }

    public ShakeDetector(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lastShakeTime = 0;
    }

    public void setShakeListener(ShakeListener listener) {
        this.listener = listener;
    }

    public void start() {
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        }
    }

    public void stop() {
        sensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long currentTime = System.currentTimeMillis();
            
            if ((currentTime - lastShakeTime) > MIN_TIME_BETWEEN_SHAKES) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                
                double acceleration = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2)) - SensorManager.GRAVITY_EARTH;
                
                if (acceleration > SHAKE_THRESHOLD) {
                    lastShakeTime = currentTime;
                    if (listener != null) {
                        listener.onShake();
                    }
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
