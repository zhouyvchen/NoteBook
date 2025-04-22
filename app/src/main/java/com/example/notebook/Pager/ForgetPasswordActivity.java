package com.example.notebook.Pager;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notebook.Dao.UserDao;
import com.example.notebook.Entity.EntityUser;
import com.example.notebook.InitDataBase.InitDataBase;
import com.example.notebook.Util.UtilMethod;
import com.example.notebook.databinding.ActivityForgetPasswordBinding;

public class ForgetPasswordActivity extends AppCompatActivity {
    ActivityForgetPasswordBinding binding;
    InitDataBase initDataBase;
    UserDao userDao;
    EntityUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initDataBase = UtilMethod.getInstance(getApplicationContext());
        userDao = initDataBase.userDao();
        initMeth();
    }

    private void initMeth() {
        binding.savaNewPasswordButton.setOnClickListener(view -> {
            String username = binding.usernameInput.getText().toString();
            String newPassword = binding.newPassswordInput.getText().toString();
            if (username.isEmpty()) {
                binding.usernameInputLayout.setError("用户名为空");
                return;
            } else {
                binding.usernameInputLayout.setErrorEnabled(false);
            }
            if (newPassword.isEmpty()) {
                binding.newPassswordInputLayout.setError("新密码为空");
                return;
            } else {
                binding.newPassswordInputLayout.setErrorEnabled(false);
            }
            user = userDao.getUserByUsername(username);
            if (user == null) {
                binding.usernameInputLayout.setError("用户名不存在");
                return;
            }

            // 更新密码
            user.setPassword(newPassword);
            userDao.updataUser(user);

            // 提供反馈
            UtilMethod.ShowToast(getApplicationContext(), "更新成功");
            finish();

        });
    }
}