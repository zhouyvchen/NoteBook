package com.example.notebook.Util;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.notebook.InitDataBase.InitDataBase;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UtilMethod {
    public static InitDataBase baseRoomDataBase;

    public static void ShowToast(Context context, String info) {
        Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
    }

    public static InitDataBase getInstance(Context context) {
        if (baseRoomDataBase == null) {
            baseRoomDataBase = Room.databaseBuilder(context,
                    InitDataBase.class, "NoteDataBase").allowMainThreadQueries().build();
        }
        return baseRoomDataBase;
    }
    public static String getPath(Context context, Uri srcUri) {
        String path = context.getCacheDir() + "/" + System.currentTimeMillis() + ".png";//获取本地目录
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(srcUri);//context的方法获取URI文件输入流
            if (inputStream == null) return "null";
            OutputStream outputStream = Files.newOutputStream(Paths.get(path));
            copyStream(inputStream, outputStream);//调用下面的方法存储
            inputStream.close();
            outputStream.close();
            return path;
        } catch (Exception e) {
            e.printStackTrace();
            return "null";
        }
    }

    private static void copyStream(InputStream input, OutputStream output) {//文件存储
        final int BUFFER_SIZE = 1024 * 2;
        byte[] buffer = new byte[BUFFER_SIZE];
        BufferedInputStream in = new BufferedInputStream(input, BUFFER_SIZE);
        BufferedOutputStream out = new BufferedOutputStream(output, BUFFER_SIZE);
        int n;
        try {
            while ((n = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
                out.write(buffer, 0, n);
            }
            out.flush();
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
