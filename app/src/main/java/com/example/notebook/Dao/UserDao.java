package com.example.notebook.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notebook.Entity.EntityUser;

@Dao
public interface UserDao {
    @Query("SELECT * FROM table_user WHERE user_name = :username")
    EntityUser getUserByUsername(String username);

    // 更新密码
    @Query("UPDATE table_user SET password = :newPassword WHERE user_name = " +
            ":username")
    void updateUserPassword(String username, String newPassword);

    // 更新用户名
    @Query("UPDATE table_user SET user_name = :newUsername WHERE user_id = " +
            ":userId")
    void updateUsername(long userId, String newUsername);

    @Query("SELECT * FROM table_user WHERE user_name = :username AND password " +
            "= :password")
    EntityUser validateUserLogin(String username, String password);

    @Query("select user_name from table_user")
    String getUsername();

    // 插入新用户
    @Insert
    void insertUser(EntityUser user);

    @Update
    void updataUser(EntityUser user);
}
