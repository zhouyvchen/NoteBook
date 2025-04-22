package com.example.notebook.Pager.HomePager.VideoPager;

public class VideoItem {
    private String title;
    private String videoUrl;
    private String duration;

    public VideoItem(String title, String videoUrl, String duration) {
        this.title = title;
        this.videoUrl = videoUrl;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
} 