package com.example.notebook.Pager;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * ShakeDetector 类用于检测设备的摇晃动作。
 * 它实现了 SensorEventListener 接口来接收传感器事件。
 */
public class ShakeDetector implements SensorEventListener {
    // 定义摇晃检测的阈值，当加速度变化超过此值时认为是摇晃
    private static final float SHAKE_THRESHOLD = 3.25f;
    // 定义两次摇晃之间的最小时间间隔，单位为毫秒，防止短时间内多次触发
    private static final int MIN_TIME_BETWEEN_SHAKES = 1000;

    private SensorManager sensorManager; // 传感器管理器，用于访问设备传感器
    private Sensor accelerometer; // 加速度传感器，用于检测设备运动
    private long lastShakeTime; // 上一次检测到摇晃的时间戳
    private ShakeListener listener; // 摇晃事件的回调监听器

    /**
     * 摇晃事件监听器接口。
     * 当检测到摇晃时，将调用 onShake 方法。
     */
    public interface ShakeListener {
        /**
         * 当检测到摇晃时调用此方法。
         */
        void onShake();
    }

    /**
     * ShakeDetector 的构造函数。
     *
     * @param context 应用程序上下文，用于获取系统服务。
     */
    public ShakeDetector(Context context) {
        // 获取 SensorManager 服务
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        // 获取默认的加速度传感器
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // 初始化上次摇晃时间
        lastShakeTime = 0;
    }

    /**
     * 设置摇晃事件的监听器。
     *
     * @param listener ShakeListener 的实例。
     */
    public void setShakeListener(ShakeListener listener) {
        this.listener = listener;
    }

    /**
     * 开始监听摇晃事件。
     * 如果加速度传感器可用，则注册监听器。
     */
    public void start() {
        if (accelerometer != null) {
            // 注册传感器监听器，SENSOR_DELAY_UI 表示传感器事件的更新频率适合用户界面
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        }
    }

    /**
     * 停止监听摇晃事件。
     * 注销传感器监听器。
     */
    public void stop() {
        sensorManager.unregisterListener(this);
    }


    /**
     * 当传感器数值发生变化时调用此方法。
     *
     * @param event 包含新的传感器数据的 SensorEvent 对象。
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        // 检查事件是否来自加速度传感器
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long currentTime = System.currentTimeMillis(); // 获取当前时间

            // 检查距离上次摇晃是否已超过最小时间间隔
            if ((currentTime - lastShakeTime) > MIN_TIME_BETWEEN_SHAKES) {
                float x = event.values[0]; // x轴方向的加速度
                float y = event.values[1]; // y轴方向的加速度
                float z = event.values[2]; // z轴方向的加速度

                // 计算总加速度，减去重力加速度 SensorManager.GRAVITY_EARTH 以获得设备本身的运动加速度
                double acceleration = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2)) - SensorManager.GRAVITY_EARTH;

                // 如果计算出的加速度超过了设定的阈值
                if (acceleration > SHAKE_THRESHOLD) {
                    lastShakeTime = currentTime; // 更新上次摇晃的时间
                    // 如果设置了监听器，则调用其 onShake 方法
                    if (listener != null) {
                        listener.onShake();
                    }
                }
            }
        }
    }

    /**
     * 当传感器的精度发生变化时调用此方法。
     * 在这个应用中未使用。
     *
     * @param sensor   发生变化的传感器。
     * @param accuracy 新的精度值。
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // 此方法在此应用中未使用，可以留空
    }
}
