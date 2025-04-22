package com.example.notebook.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_note")
public class EntityNote {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    long noteId;
    @ColumnInfo(name = "note_user_id")
    long userId;
    @ColumnInfo(name = "note_title")
    String noteTitle;
    @ColumnInfo(name = "note_content")
    String noteContent;
    @ColumnInfo(name = "note_image_url")
    String noteImageUrl;
    @ColumnInfo(name = "create_time")
    String createTime;

    public EntityNote(long userId, String noteTitle, String noteContent,
                      String noteImageUrl, String createTime) {
        this.userId = userId;
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.noteImageUrl = noteImageUrl;
        this.createTime = createTime;
    }

    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public String getNoteImageUrl() {
        return noteImageUrl;
    }

    public void setNoteImageUrl(String noteImageUrl) {
        this.noteImageUrl = noteImageUrl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public EntityNote() {
    }

    @Override
    public String toString() {
        return "EntityNote{" +
                "noteId=" + noteId +
                ", userId=" + userId +
                ", noteTitle='" + noteTitle + '\'' +
                ", noteContent='" + noteContent + '\'' +
                ", noteImageUrl='" + noteImageUrl + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }

}
