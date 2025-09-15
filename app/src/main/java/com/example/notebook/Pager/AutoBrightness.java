package com.example.notebook.Pager;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Window;
import android.view.WindowManager;
import android.util.Log;

public class AutoBrightness implements SensorEventListener {
    private static final String TAG = "AutoBrightness";
    private static final float MAX_LUX = 10000f; // 最大光照值
    private static final float MIN_BRIGHTNESS = 0.1f; // 最小亮度比例
    private static final float MAX_BRIGHTNESS = 1.0f; // 最大亮度比例

    private SensorManager sensorManager;
    private Sensor lightSensor;
    private Activity activity;
    private boolean isAutoLightEnabled = false;
    private float currentLux = 0f;

    public AutoBrightness(Activity activity) {
        this.activity = activity;
        sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    public void start() {
        if (lightSensor != null && isAutoLightEnabled) {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
            Log.d(TAG, "光线传感器监听已启动");
        } else {
            Log.d(TAG, "光线传感器不可用或自动调节功能未启用");
        }
    }

    public void stop() {
        if (lightSensor != null) {
            sensorManager.unregisterListener(this);
            Log.d(TAG, "光线传感器监听已停止");
        }
    }


    public void setAutoLightEnabled(boolean enabled) {
        this.isAutoLightEnabled = enabled;
        if (enabled) {
            start();
        } else {
            stop();
            resetBrightness();
        }
    }


    public boolean isAutoLightEnabled() {
        return isAutoLightEnabled;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            currentLux = event.values[0];
            Log.d(TAG, "当前光照值: " + currentLux);
            adjustBrightness(currentLux);
        }
    }

    private void adjustBrightness(float lux) {
        if (!isAutoLightEnabled) return;

        float brightnessRatio;
        if (lux <= 0) {
            brightnessRatio = MIN_BRIGHTNESS;
        } else if (lux >= MAX_LUX) {
            brightnessRatio = MAX_BRIGHTNESS;
        } else {
            // 使用对数函数使亮度变化更平滑
            brightnessRatio = MIN_BRIGHTNESS + (MAX_BRIGHTNESS - MIN_BRIGHTNESS)
                    * (float) (Math.log10(lux) / Math.log10(MAX_LUX));
        }

        brightnessRatio = Math.max(MIN_BRIGHTNESS, Math.min(MAX_BRIGHTNESS, brightnessRatio));

        setScreenBrightness(brightnessRatio);

        Log.d(TAG, "调整亮度比例为: " + brightnessRatio);
    }


    private void setScreenBrightness(float brightnessRatio) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.screenBrightness = brightnessRatio;
        window.setAttributes(layoutParams);
    }


    private void resetBrightness() {
        Window window = activity.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
        window.setAttributes(layoutParams);
        Log.d(TAG, "已重置为系统亮度");
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}