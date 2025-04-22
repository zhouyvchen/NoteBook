package com.example.notebook.Pager.HomePager.NotePager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.notebook.Dao.UserDao;
import com.example.notebook.Entity.EntityUser;
import com.example.notebook.InitDataBase.InitDataBase;
import com.example.notebook.Util.UtilMethod;
import com.example.notebook.databinding.FragmentProfielBinding;

import java.util.Objects;

public class ProfileFragment extends Fragment {
    FragmentProfielBinding binding;
    InitDataBase initDataBase;
    ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    String avatarUri;
    UserDao userDao;
    String currentUsername;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pickMedia =
                registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                    if (uri != null) {
                        Log.d("PhotoPicker", "Selected URI: " + uri);
                        avatarUri =
                                UtilMethod.getPath(Objects.requireNonNull(getContext()), uri);
                        // 获取图片路径
                        Glide.with(getContext()).load(uri).into(binding.userAvatar); // 加载图片
                        EntityUser user =
                                userDao.getUserByUsername(binding.username.getText().toString());
                        user.setUserAvatar(avatarUri);
                        userDao.updataUser(user);
                    } else {
                        Log.d("PhotoPicker", "No media selected");
                    }
                });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initDataBase = UtilMethod.getInstance(getContext());
        userDao = initDataBase.userDao();
        binding = FragmentProfielBinding.inflate(getLayoutInflater());
        SharedPreferences sharedPreferences =
                getActivity().getSharedPreferences("user_prefs",
                        Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        EntityUser user = userDao.getUserByUsername(username);
        binding.username.setText(username);
        String avatarUri = user.getUserAvatar();
        if (avatarUri != null) {
            Glide.with(getContext()).load(avatarUri).into(binding.userAvatar);
        }
        initMeth();
        return binding.getRoot();
    }

    private void initMeth() {
        binding.confirmLogin.setOnClickListener(view -> {
            String slogan = binding.inputSlogan.getText().toString().trim();
            String username = binding.username.getText().toString().trim();
            String password = binding.inputPassword.getText().toString().trim();
            String newPassword =
                    binding.inputNewPassword.getText().toString().trim();

            EntityUser user = userDao.getUserByUsername(username);
            if (user == null) {
                UtilMethod.ShowToast(getContext(), "用户名不存在");
                return;
            }

            boolean updated = false; // 用于跟踪是否有更新

            if (!password.isEmpty()) {
                if (password.equals(user.getPassword())) {
                    if (!newPassword.isEmpty()) {
                        user.setPassword(newPassword);
                        updated = true;
                    } else {
                        binding.inputLayoutPassword.setError("新密码不能为空!");
                    }
                } else {
                    binding.inputLayoutPassword.setError("密码错误!");
                }
            }

            if (!slogan.isEmpty()) {
                user.setUserSlogan(slogan);
                updated = true;
            }

            if (updated) {
                userDao.updataUser(user);
                UtilMethod.ShowToast(getContext(), "更新成功");
            }
        });
        binding.userAvatar.setOnClickListener(view -> {
            PickVisualMediaRequest request = new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                    .build();
            pickMedia.launch(request);
        });
    }
}