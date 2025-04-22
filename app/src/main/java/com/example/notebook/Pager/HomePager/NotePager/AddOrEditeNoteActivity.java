package com.example.notebook.Pager.HomePager.NotePager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.notebook.Dao.NoteDao;
import com.example.notebook.Dao.UserDao;
import com.example.notebook.Entity.EntityNote;
import com.example.notebook.Entity.EntityUser;
import com.example.notebook.InitDataBase.InitDataBase;
import com.example.notebook.R;
import com.example.notebook.Util.UtilMethod;
import com.example.notebook.databinding.ActivityAddOrEditeNotePagerBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddOrEditeNoteActivity extends AppCompatActivity {
    ActivityAddOrEditeNotePagerBinding binding;
    InitDataBase initDataBase;

    NoteDao noteDao;
    UserDao userDao;
    String createTime;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    boolean isAdd = true;
    EntityNote note;
    EntityUser user;
    ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    String imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =
                ActivityAddOrEditeNotePagerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initMethod(); // 初始化方法

        Intent intent = getIntent();
        // 检查是否为添加模式
        if (!(intent != null && intent.getBooleanExtra("isAdd", true))) {
            isAdd = false;
            long noteId = intent.getLongExtra("noteId", -1); // 获取笔记 ID
            note = noteDao.getNoteById(noteId);

            binding.noteTitle.setText(note.getNoteTitle().length() > 0 ?
                    note.getNoteTitle() : "");
            binding.noteContent.setText(note.getNoteContent());

            if (note.getNoteImageUrl() != null && !note.getNoteImageUrl().isEmpty()) {
                imageUri = note.getNoteImageUrl();
                Glide.with(getApplicationContext()).load(imageUri).into(binding.noteImage);
            }
        }

        binding.cancelButton.setOnClickListener(view -> finish());

        binding.checkButton.setOnClickListener(view -> saveNote());

        binding.noteContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i,
                                          int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1
                    , int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                binding.noteWords.setText(getString(R.string.noteLength,
                        editable.toString().trim().length()));
            }
        });

        binding.checkImageButton.setOnClickListener(view -> {
            pickMedia.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageAndVideo.INSTANCE)
                    .build());
        });

        binding.noteWords.setText(getString(R.string.noteLength,
                binding.noteContent.getText().toString().length()));
    }

    public void initMethod() {
        createTime =
                simpleDateFormat.format(new Date(System.currentTimeMillis()));
        binding.noteCreateTime.setText(createTime);

        initDataBase = UtilMethod.getInstance(getApplicationContext());
        noteDao = initDataBase.noteDao();

        binding.noteWords.setText(getString(R.string.noteLength, 0));

        pickMedia =
                registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                    if (uri != null) {
                        Log.d("PhotoPicker", "Selected URI: " + uri);
                        imageUri = UtilMethod.getPath(getApplicationContext(),
                                uri);

                        Glide.with(getApplicationContext()).load(uri).into(binding.noteImage); // 加载图片
                    } else {
                        Log.d("PhotoPicker", "No media selected");
                    }
                });
    }

    private void saveNote() {
        // 根据模式保存笔记
        if (isAdd) {
            // 添加模式
            if (binding.noteContent.getText().toString().trim().isEmpty()) {
                UtilMethod.ShowToast(getApplicationContext(), "Content is " +
                        "empty");
            } else {
                getCurrentNote();
                noteDao.insertNote(getCurrentNote());
                UtilMethod.ShowToast(getApplicationContext(), "Save note " +
                        "success!");
                finish();
            }
        } else {
            // 编辑模式
            if (binding.noteContent.getText().toString().trim().isEmpty()) {
                UtilMethod.ShowToast(getApplicationContext(), "Content is " +
                        "empty!!!");
            } else {
                String content = binding.noteContent.getText().toString().trim();
                note.setNoteContent(content);
                note.setNoteTitle(binding.noteTitle.getText().toString().trim().length() > 0 ? binding.noteTitle.getText().toString().trim() : ""); // 更新标题
                note.setNoteImageUrl(imageUri == null ? null : imageUri); //
                noteDao.updateNote(note);
                UtilMethod.ShowToast(getApplicationContext(), "保存成功");
                finish();
            }
        }
    }

    private EntityNote getCurrentNote() {
        // 获取当前笔记的内容、标题和图片 URL
        String content = binding.noteContent.getText().toString().trim();
        String title = binding.noteTitle.getText().toString().trim();
        return new EntityNote(6, title, content, imageUri == null ? null :
                imageUri, createTime);
    }
}
