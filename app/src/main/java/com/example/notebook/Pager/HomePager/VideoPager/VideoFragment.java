package com.example.notebook.Pager.HomePager.VideoPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notebook.Pager.VideoPlayerActivity;
import com.example.notebook.R;
import com.example.notebook.databinding.FragmentVideoBinding;

import java.util.ArrayList;
import java.util.List;

public class VideoFragment extends Fragment {
    private FragmentVideoBinding binding;
    private List<VideoItem> videoList;
    private VideoAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentVideoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();
        loadVideos();
    }

    private void initRecyclerView() {
        videoList = new ArrayList<>();
        adapter = new VideoAdapter(videoList, this::onVideoClick);
        binding.videoRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.videoRecyclerView.setAdapter(adapter);
    }

    private void loadVideos() {
        // 添加示例视频
        videoList.add(new VideoItem("示例视频1", "https://example.com/video1.mp4", "10:30"));
        videoList.add(new VideoItem("示例视频2", "https://example.com/video2.mp4", "15:45"));
        adapter.notifyDataSetChanged();
    }

    private void onVideoClick(VideoItem video) {
        Intent intent = new Intent(getActivity(), VideoPlayerActivity.class);
        intent.putExtra("video_url", video.getVideoUrl());
        intent.putExtra("video_title", video.getTitle());
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
} 