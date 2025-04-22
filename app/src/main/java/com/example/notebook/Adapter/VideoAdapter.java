package com.example.notebook.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.notebook.Entity.Video;
import com.example.notebook.Pager.VideoPlayerActivity;
import com.example.notebook.R;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private List<Video> videoList;
    private Context context;

    public VideoAdapter(List<Video> videoList, Context context) {
        this.videoList = videoList;
        this.context = context;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Video video = videoList.get(position);
        holder.videoTitle.setText(video.getTitle());
        
        // 格式化视频时长
        long minutes = TimeUnit.MILLISECONDS.toMinutes(video.getDuration());
        long seconds = TimeUnit.MILLISECONDS.toSeconds(video.getDuration()) % 60;
        holder.videoDuration.setText(String.format("%02d:%02d", minutes, seconds));

        // 加载缩略图
        Glide.with(context)
                .load(video.getThumbnailPath())
                .placeholder(R.drawable.ic_video_placeholder)
                .into(holder.videoThumbnail);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, VideoPlayerActivity.class);
            intent.putExtra("video_path", video.getPath());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder {
        ImageView videoThumbnail;
        TextView videoTitle;
        TextView videoDuration;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoThumbnail = itemView.findViewById(R.id.video_thumbnail);
            videoTitle = itemView.findViewById(R.id.video_title);
            videoDuration = itemView.findViewById(R.id.video_duration);
        }
    }
} 