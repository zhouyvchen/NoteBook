package com.example.notebook.Pager;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notebook.databinding.ActivityVideoPlayerBinding;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.common.PlaybackException;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

public class VideoPlayerActivity extends AppCompatActivity {
    private ActivityVideoPlayerBinding binding;
    private ExoPlayer player;
    private PlayerView playerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVideoPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        playerView = binding.playerView;
        progressBar = binding.progressBar;
        ImageButton backButton = binding.backButton;

        // 初始化播放器
        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        // 设置视频源
        String videoPath = getIntent().getStringExtra("video_path");
        String videoTitle = getIntent().getStringExtra("video_title");
        
        if (videoTitle != null) {
            binding.videoTitle.setText(videoTitle);
        }

        if (videoPath != null) {
            Uri videoUri = Uri.parse(videoPath);
            MediaItem mediaItem = MediaItem.fromUri(videoUri);
            player.setMediaItem(mediaItem);
            player.prepare();
            player.play();

            // 添加播放器监听器
            player.addListener(new Player.Listener() {
                @Override
                public void onPlayerError(PlaybackException error) {
                    Toast.makeText(VideoPlayerActivity.this, 
                        "播放错误: " + error.getMessage(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onPlaybackStateChanged(int playbackState) {
                    if (playbackState == Player.STATE_BUFFERING) {
                        progressBar.setVisibility(View.VISIBLE);
                    } else {
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
        } else {
            Toast.makeText(this, "无效的视频路径", Toast.LENGTH_SHORT).show();
            finish();
        }

        // 返回按钮点击事件
        backButton.setOnClickListener(v -> finish());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
        binding = null;
    }
} 