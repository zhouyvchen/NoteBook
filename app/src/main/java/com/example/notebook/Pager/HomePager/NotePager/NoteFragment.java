package com.example.notebook.Pager.HomePager.NotePager;

import android.content.Intent;
import android.os.Bundle;

import com.example.notebook.Dao.UserDao;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;

import com.example.notebook.Adapter.NoteAdapter;
import com.example.notebook.Dao.NoteDao;
import com.example.notebook.Entity.EntityNote;
import com.example.notebook.Entity.EntityNoteCard;
import com.example.notebook.Entity.EntityUser;
import com.example.notebook.InitDataBase.InitDataBase;
import com.example.notebook.R;
import com.example.notebook.Util.UtilMethod;
import com.example.notebook.databinding.FragmentNoteBinding;

import java.util.ArrayList;
import java.util.List;


public class NoteFragment extends Fragment {
    FragmentNoteBinding binding;
    InitDataBase initDataBase;
    NoteDao noteDao;
    UserDao userDao; // 添加 UserDao
    String currentUsername; // 保存当前登录的用户名
    String currentSlogan;
    String currentAvatarUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNoteBinding.inflate(getLayoutInflater());

        initMethod();
        initList();

        binding.floatingActionButton.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(),
                    AddOrEditeNoteActivity.class));
        });

        return binding.getRoot();
    }

    private void initList() {
        List<EntityNote> localNote = getLocalNote();
        if (localNote != null && localNote.size() > 0) {
            ArrayList<EntityNoteCard> list = noteToCard(localNote);
            binding.noNote.setVisibility(View.GONE);
            binding.noteAlter.setVisibility(View.VISIBLE);
            NoteAdapter noteAdapter = new NoteAdapter(getContext(), list,
                    currentUsername, currentSlogan, currentAvatarUri, count -> {
                if (count == 0) {
                    binding.noNote.setVisibility(View.VISIBLE);
                }
                binding.noteAlter.setText(getString(R.string.note_alter, count));
            }
            );
            binding.noteList.setAdapter(noteAdapter);
            binding.noteList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            binding.noteAlter.setText(getString(R.string.note_alter,
                    list.size()));

        } else {
            binding.noNote.setVisibility(View.VISIBLE);
            binding.noteAlter.setVisibility(View.GONE);
        }
    }

    private void initMethod() {
        initDataBase = UtilMethod.getInstance(getContext());
        noteDao = initDataBase.noteDao();
        userDao = initDataBase.userDao();

        currentUsername = getCurrentUsername();
        currentSlogan = getCurrentSlogan(currentUsername);
        currentAvatarUri = getCurrentAvatarUri(currentUsername);
    }


    private List<EntityNote> getLocalNote() {
        List<EntityNote> allNote = noteDao.getAllNote();
        if (allNote.size() > 0) {
            return allNote;
        } else {
            return null;
        }
    }

    private ArrayList<EntityNoteCard> noteToCard(List<EntityNote> list) {
        ArrayList<EntityNoteCard> cards = new ArrayList<>();
        for (EntityNote note : list) {
            EntityNoteCard entityNoteCard = new EntityNoteCard();
            entityNoteCard.setNoteCardId(note.getNoteId());
            entityNoteCard.setContent(note.getNoteContent());
            entityNoteCard.setTitle(note.getNoteTitle());
            entityNoteCard.setCreate_time(note.getCreateTime());
            entityNoteCard.setCover(note.getNoteImageUrl());
            entityNoteCard.setUsername(currentUsername);
            entityNoteCard.setSlogan(currentSlogan);
            cards.add(entityNoteCard);
        }
        return cards;
    }

    @Override
    public void onResume() {
        initList();
        super.onResume();
    }

    private String getCurrentUsername() {
        SharedPreferences sharedPreferences =
                requireContext().getSharedPreferences("user_prefs",
                        Context.MODE_PRIVATE);
        return sharedPreferences.getString("username", "DefaultUser"); //
    }

    public String getCurrentSlogan(String currentUsername) {
        EntityUser user = userDao.getUserByUsername(currentUsername);
        currentSlogan = user.getUserSlogan();
        return currentSlogan;
    }

    public String getCurrentAvatarUri(String currentUsername) {
        EntityUser user = userDao.getUserByUsername(currentUsername);
        currentAvatarUri = user.getUserAvatar();
        return currentAvatarUri;
    }
}