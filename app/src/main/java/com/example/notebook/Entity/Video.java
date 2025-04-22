package com.example.notebook.Entity;

public class Video {
    private String title;
    private String path;
    private long duration;
    private String thumbnailPath;

    public Video(String title, String path, long duration, String thumbnailPath) {
        this.title = title;
        this.path = path;
        this.duration = duration;
        this.thumbnailPath = thumbnailPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }
} 