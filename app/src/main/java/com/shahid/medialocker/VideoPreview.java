package com.shahid.medialocker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;

public class VideoPreview extends AppCompatActivity {
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_preview);
        videoView = findViewById(R.id.videoView);
        if(getIntent().hasExtra("video")){
            File video = (File) getIntent().getExtras().get("video");
            videoView.setVideoPath(video.getAbsolutePath());

            MediaController mediaController = new MediaController(this);

            videoView.setMediaController(mediaController);

            mediaController.setMediaPlayer(videoView);

            videoView.setVisibility(View.VISIBLE);

            videoView.start();
        }
    }
}
