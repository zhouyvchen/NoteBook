package com.example.notebook.Pager;

import android.content.SharedPreferences;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notebook.CreateAccountActivity;
import com.example.notebook.Dao.UserDao;
import com.example.notebook.Entity.EntityUser;
import com.example.notebook.InitDataBase.InitDataBase;
import com.example.notebook.Pager.HomePager.NotePager.HomeActivity;
import com.example.notebook.Util.UtilMethod;
import com.example.notebook.databinding.LoginActivityBinding;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {
    private LoginActivityBinding binding;
    private InitDataBase initDataBase;
    private UserDao userDao;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initDataBase = UtilMethod.getInstance(getApplicationContext());
        userDao = initDataBase.userDao();
        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        initMeth();
        checkRememberedUser();
    }

    private void checkRememberedUser() {
        String rememberedUsername = sharedPreferences.getString("username", null);
        if (rememberedUsername != null) {
            binding.userNameInputText.setText(rememberedUsername);
            binding.rememberMeCheckbox.setChecked(true);
        }
    }

    private void initMeth() {
        binding.loginButton.setOnClickListener(view -> {
            String username = binding.userNameInputText.getText().toString().trim();
            String password = binding.passwordInputText.getText().toString().trim();

            if (!validateInput(username, password)) {
                return;
            }

            EntityUser user = userDao.getUserByUsername(username);
            if (user == null) {
                showError("用户不存在");
                return;
            }

            binding.progressBar.setVisibility(View.VISIBLE);
            binding.loginButton.setEnabled(false);

            if (user.verifyPassword(password)) {
                handleSuccessfulLogin(user);
            } else {
                handleFailedLogin();
            }
        });

        binding.registerButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class);
            startActivity(intent);
        });

        binding.forgetButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ForgetPasswordActivity.class);
            startActivity(intent);
        });
    }

    private boolean validateInput(String username, String password) {
        boolean isValid = true;

        if (username.isEmpty()) {
            binding.userNameInputTextLayout.setError("用户名不能为空");
            isValid = false;
        } else {
            binding.userNameInputTextLayout.setErrorEnabled(false);
        }

        if (password.isEmpty()) {
            binding.passwordInputTextLayout.setError("密码不能为空");
            isValid = false;
        } else {
            binding.passwordInputTextLayout.setErrorEnabled(false);
        }

        return isValid;
    }

    private void handleSuccessfulLogin(EntityUser user) {
        // 保存登录状态
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (binding.rememberMeCheckbox.isChecked()) {
            editor.putString("username", user.getUsername());
        } else {
            editor.remove("username");
        }
        editor.apply();

        // 显示成功消息
        Snackbar.make(binding.getRoot(), "登录成功", Snackbar.LENGTH_SHORT).show();

        // 跳转到主页
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void handleFailedLogin() {
        binding.progressBar.setVisibility(View.GONE);
        binding.loginButton.setEnabled(true);
        showError("密码错误");
    }

    private void showError(String message) {
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
    }
}