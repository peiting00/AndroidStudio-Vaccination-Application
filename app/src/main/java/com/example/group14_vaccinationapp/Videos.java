package com.example.group14_vaccinationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class Videos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

        VideoView videoView1 = findViewById(R.id.videoView1);
        VideoView videoView2 = findViewById(R.id.videoView2);
        VideoView videoView3 = findViewById(R.id.videoView3);

        videoView1.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.video1);
        videoView2.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.video2);
        videoView3.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.video3);
        //video control
        MediaController mediaController1 = new MediaController(this);
        mediaController1.setAnchorView(videoView1);//pass the video object
        videoView1.setMediaController(mediaController1);

        MediaController mediaController2 = new MediaController(this);
        mediaController2.setAnchorView(videoView2);//pass the video object 2
        videoView2.setMediaController(mediaController2);

        MediaController mediaController3 = new MediaController(this);
        mediaController3.setAnchorView(videoView3);//pass the video object 3
        videoView3.setMediaController(mediaController3);
    }
}