package com.example.notebook.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notebook.Entity.EntityNote;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert
    long insertNote(EntityNote note);

    @Delete
    void delete(EntityNote note);

    @Query("Select * from table_note where note_user_id=:userid")
    List<EntityNote> getAllNoteByUserId(long userid);

    @Query("Select * from table_note")
    List<EntityNote> getAllNote();

    @Query("Select * from table_note where note_id=:noteId")
    EntityNote getNoteById(long noteId);

    @Update
    void updateNote(EntityNote note);

    @Query("DELETE FROM table_note where note_id=:noteId")
    void deleteNoteById(long noteId);
}
