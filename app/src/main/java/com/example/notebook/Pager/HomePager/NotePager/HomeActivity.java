package com.example.notebook.Pager.HomePager.NotePager;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.notebook.Dao.NoteDao;
import com.example.notebook.InitDataBase.InitDataBase;
import com.example.notebook.Pager.VideoFragment;
import com.example.notebook.R;
import com.example.notebook.Util.UtilMethod;
import com.example.notebook.databinding.HomeActivityBinding;

public class HomeActivity extends AppCompatActivity {
    HomeActivityBinding binding;
    NoteFragment noteFragment;
    ProfileFragment profileFragment;
    VideoFragment videoFragment;
    Fragment current;
    InitDataBase initDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = HomeActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initMethod();
        initDataBase = UtilMethod.getInstance(getApplicationContext());
        NoteDao noteDao = initDataBase.noteDao();
    }

    private void initMethod() {
        current = noteFragment;
        noteFragment = new NoteFragment();
        profileFragment = new ProfileFragment();
        videoFragment = new VideoFragment();
        changeFragment(null, noteFragment);
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.note) {
                changeFragment(noteFragment);
            } else if (itemId == R.id.profile) {
                changeFragment(profileFragment);
            } else if (itemId == R.id.video) {
                changeFragment(videoFragment);
            }
            item.setChecked(true);
            return false;
        });
    }

    private void changeFragment(Fragment from, Fragment to) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        if (to.isAdded()) {
            if (from != null) {
                fragmentTransaction.hide(from);
            }
            fragmentTransaction.show(to);
        } else {
            if (from != null) {
                fragmentTransaction.hide(from);
            }
            fragmentTransaction.add(binding.navFragment.getId(), to);
        }
        fragmentTransaction.commit();
        current = to;
    }

    private void changeFragment(Fragment to) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.replace(binding.navFragment.getId(), to);
        fragmentTransaction.commit();
        current = to;
    }
}