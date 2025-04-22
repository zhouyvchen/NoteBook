package com.example.notebook.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.notebook.Dao.NoteDao;
import com.example.notebook.Entity.EntityNoteCard;
import com.example.notebook.InitDataBase.InitDataBase;
import com.example.notebook.Pager.HomePager.NotePager.AddOrEditeNoteActivity;
import com.example.notebook.R;
import com.example.notebook.Util.UtilMethod;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    ArrayList<EntityNoteCard> list;
    Context context;
    String currentUsername; // 当前登录的用户名
    String currentSlogan;
    String currentAvatarUri;
    int deletePosition;
    InitDataBase initDataBase;
    NoteDao noteDao;
    CountListen countListen;

    public NoteAdapter(Context context, ArrayList<EntityNoteCard> list,
                       String currentUsername, String currentSlogan,
                       String currentAvatarUri,
                       CountListen countListen) {
        this.context = context;
        this.list = list;
        this.currentSlogan = currentSlogan;// 当前用户的slogan
        this.currentUsername = currentUsername; // 传递当前登录的用户名
        this.currentAvatarUri = currentAvatarUri;
        this.countListen = countListen;
        initDataBase = UtilMethod.getInstance(context);
        noteDao = initDataBase.noteDao();
    }


    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent
            , int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_note,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder,
                                 int position) {
        if (position == holder.getLayoutPosition()) {
            holder.username.setText(currentUsername);
            holder.title.setText(list.get(position).getTitle());
            // TODO: 2024/12/12 当前用户的slogan 
            holder.slogan.setText(currentSlogan);
            holder.content.setText(list.get(position).getContent());
            if (list.get(position) != null && !list.get(position).getTitle().isEmpty()) {
                holder.title.setVisibility(View.VISIBLE);
                holder.title.setText(list.get(position).getTitle());
                holder.content.setTextColor(Color.GRAY);
                holder.content.setTextSize(16);
            } else {
                holder.title.setVisibility(View.GONE);
                holder.content.setTextColor(Color.BLACK);
                holder.content.setTextSize(24);
            }
            // TODO: 2024/12/12 从当前用户名获取头像uri
            Glide.with(context).load(currentAvatarUri).circleCrop().into(holder.userAvatar);
            if (list.get(position).getCover() == null) {
                holder.cover.setVisibility(View.GONE);
            } else {
                holder.cover.setVisibility(View.VISIBLE);
                Glide.with(context).load(list.get(position).getCover()).into(holder.cover);
            }
            holder.create_time.setText(list.get(position).getCreate_time());
            holder.delete.setOnClickListener(viw -> {
                new MaterialAlertDialogBuilder(context).setTitle(
                        "你确定要删除这个笔记吗？").setPositiveButton("确定",
                        (dialogInterface, i) -> {
                            deletePosition = holder.getAdapterPosition();
                            deleteNote();
                        }).setNegativeButton("取消", null).show();
            });
            holder.itemCard.setOnClickListener(view -> {
                Intent intent = new Intent(context,
                        AddOrEditeNoteActivity.class);
                intent.putExtra("isAdd", false);
                intent.putExtra("noteId", list.get(position).getNoteCardId());
                context.startActivity(intent);
            });
        }
    }

    private void deleteNote() {
        noteDao.deleteNoteById(list.get(deletePosition).getNoteCardId());
        notifyItemRemoved(deletePosition);
        list.remove(deletePosition);
        countListen.CountListen(list.size());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView itemCard;
        TextView username;
        TextView slogan;
        TextView title;
        TextView content;
        Button delete;
        TextView create_time;
        ImageView userAvatar;
        ImageView cover;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemCard = itemView.findViewById(R.id.item_card);
            username = itemView.findViewById(R.id.note_item_username);
            slogan = itemView.findViewById(R.id.slogan);
            title = itemView.findViewById(R.id.item_note_title);
            content = itemView.findViewById(R.id.item_note_content);
            create_time = itemView.findViewById(R.id.note_create_time);
            delete = itemView.findViewById(R.id.item_note_delete_button);
            userAvatar = itemView.findViewById(R.id.userAvater);
            cover = itemView.findViewById(R.id.cover);
        }

    }

    public interface CountListen {
        void CountListen(int count);
    }
}
