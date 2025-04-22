package com.example.notebook.Entity;

public class EntityNoteCard {
    long noteCardId;
    String username;
    String slogan;
    String userAvater;
    String cover;
    String title;
    String content;
    String create_time;

    public EntityNoteCard(long noteCardId, String username, String slogan,
                          String userAvater, String cover, String title,
                          String content, String create_time) {
        this.noteCardId = noteCardId;
        this.username = username;
        this.slogan = slogan;
        this.userAvater = userAvater;
        this.cover = cover;
        this.title = title;
        this.content = content;
        this.create_time = create_time;
    }

    public EntityNoteCard() {
    }

    public long getNoteCardId() {
        return noteCardId;
    }

    public void setNoteCardId(long noteCardId) {
        this.noteCardId = noteCardId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getUserAvater() {
        return userAvater;
    }

    public void setUserAvater(String userAvater) {
        this.userAvater = userAvater;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "EntityNoteCard{" +
                "noteCardId=" + noteCardId +
                ", username='" + username + '\'' +
                ", slogan='" + slogan + '\'' +
                ", userAvater='" + userAvater + '\'' +
                ", cover='" + cover + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", create_time='" + create_time + '\'' +
                '}';
    }
}
