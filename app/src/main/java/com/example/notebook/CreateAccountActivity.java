package com.example.notebook;

import android.os.Bundle;
import android.telephony.SmsManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notebook.Dao.UserDao;
import com.example.notebook.InitDataBase.InitDataBase;
import com.example.notebook.Util.UtilMethod;
import com.example.notebook.databinding.ActivityCreateAccountBinding;
import com.example.notebook.Entity.EntityUser; // 导入 EntityUser

public class CreateAccountActivity extends AppCompatActivity {
    ActivityCreateAccountBinding binding;
    InitDataBase initDataBase;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initDataBase = UtilMethod.getInstance(getApplicationContext());
        userDao = initDataBase.userDao();

        binding.createAccount.setOnClickListener(view -> createAccount());
    }

    private void createAccount() {
        String username = binding.createUserNameInputText.getText().toString();
        String password = binding.createPasswordInputText.getText().toString();
        String confirmPassword =
                binding.confirmPasswordInputText.getText().toString();

        // 检查用户名
        if (username.isEmpty()) {
            binding.createUserNameInputTextLayout.setError("账号不能为空");
            return;
        } else {
            binding.createUserNameInputTextLayout.setErrorEnabled(false);
        }

        // 检查密码
        if (password.isEmpty()) {
            binding.createPasswordInputTextLayout.setError("密码不能为空");
            return;
        } else {
            binding.createPasswordInputTextLayout.setErrorEnabled(false);
        }

        // 检查确认密码
        if (confirmPassword.isEmpty()) {
            binding.confirmPasswordInputTextLayout.setError("确认密码不能为空");
            return;
        } else {
            binding.confirmPasswordInputTextLayout.setErrorEnabled(false);
        }

        // 检查密码一致性
        if (!password.equals(confirmPassword)) {
            binding.confirmPasswordInputTextLayout.setError("两次密码不一致");
            return;
        } else {
            binding.confirmPasswordInputTextLayout.setErrorEnabled(false);
        }

        // 创建用户
        EntityUser newUser = new EntityUser(username, password);
        userDao.insertUser(newUser);
        UtilMethod.ShowToast(getApplicationContext(), "账号创建成功");
        finish();
    }
}
