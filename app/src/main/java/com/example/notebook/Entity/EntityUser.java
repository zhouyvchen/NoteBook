package com.example.notebook.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.example.notebook.Util.SecurityUtil;

@Entity(tableName = "table_user")
public class EntityUser {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    long userId;

    @ColumnInfo(name = "user_name")
    String username;

    @ColumnInfo(name = "password")
    String password;

    @ColumnInfo(name = "user_avatar")
    String userAvatar;

    @ColumnInfo(name = "user_slogan")
    String userSlogan;

    public EntityUser() {
    }

    public EntityUser(long userId, String username, String password,
                      String userAvatar, String userSlogan) {
        this.userId = userId;
        this.username = username;
        this.password = SecurityUtil.hashPassword(password);
        this.userAvatar = userAvatar;
        this.userSlogan = userSlogan;
    }

    public EntityUser(String username, String password) {
        this.username = username;
        this.password = SecurityUtil.hashPassword(password);
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPlainPassword(String password) {
        this.password = SecurityUtil.hashPassword(password);
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserSlogan() {
        return userSlogan;
    }

    public void setUserSlogan(String userSlogan) {
        this.userSlogan = userSlogan;
    }

    public boolean verifyPassword(String inputPassword) {
        return SecurityUtil.verifyPassword(inputPassword, this.password);
    }
}