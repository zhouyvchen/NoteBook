package com.example.notebook.InitDataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.notebook.Dao.NoteDao;
import com.example.notebook.Dao.UserDao;
import com.example.notebook.Entity.EntityNote;
import com.example.notebook.Entity.EntityUser;

@Database(entities = {EntityNote.class, EntityUser.class}, version = 1,
        exportSchema = false)
public abstract class InitDataBase extends RoomDatabase {
    public abstract NoteDao noteDao();
    public abstract UserDao userDao();

}
