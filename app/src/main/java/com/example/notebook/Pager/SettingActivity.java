package com.example.notebook.Pager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.notebook.Pager.HomePager.NotePager.HomeActivity;

import com.example.notebook.databinding.ActivitySettingBinding;

public class SettingActivity extends AppCompatActivity {
    private ActivitySettingBinding binding;
    private AutoBrightness autoLight;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        // 初始化自动调节亮度功能
        autoLight = new AutoBrightness(this);
        
        initMeth();
    }

    private void initMeth() {
        SharedPreferences sharedPreferences = getSharedPreferences("app_settings", MODE_PRIVATE);

        binding.btnBack.setOnClickListener(view -> {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        });


        boolean isShakeEnabled = sharedPreferences.getBoolean("shake_enabled", true);
        binding.switchShake.setChecked(isShakeEnabled);
        

        binding.switchShake.setOnCheckedChangeListener((buttonView, isChecked) -> {

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("shake_enabled", isChecked);
            editor.apply();
        });
        
        // 设置开启自动调节亮度
        // 读取保存的设置，默认为false（关闭）
        boolean isAutoBrightnessEnabled = sharedPreferences.getBoolean("auto_brightness_enabled", false);
        binding.switchAutoBrightness.setChecked(isAutoBrightnessEnabled);
        
        // 设置初始状态
        autoLight.setAutoLightEnabled(isAutoBrightnessEnabled);
        
        // 监听开关状态变化
        binding.switchAutoBrightness.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // 保存设置到SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("auto_brightness_enabled", isChecked);
            editor.apply();
            
            // 设置自动调节亮度功能的状态
            autoLight.setAutoLightEnabled(isChecked);
        });
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        // 如果自动调节亮度功能已启用，则启动传感器监听
        if (autoLight.isAutoLightEnabled()) {
            autoLight.start();
        }
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        autoLight.stop();
    }
}