package com.example.group14_vaccinationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.VideoView;



public class Videos extends AppCompatActivity {
    Toolbar toolbar;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

        toolbar=findViewById(R.id.toolbar_Video);
        scrollView=findViewById(R.id.scrollView_video);
        //to make screen scroll smoothly
        scrollView.setSmoothScrollingEnabled(true);
        // set back button
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        VideoView videoView1 = findViewById(R.id.videoView);
        VideoView videoView2 = findViewById(R.id.videoView2);
        VideoView videoView3 = findViewById(R.id.videoView3);

        //to set the video source for videoView
        videoView1.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.video1);
        videoView2.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.video2);
        videoView3.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.video3);

        //video control use MediaController
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView1);//pass the video object
        videoView1.setMediaController(mediaController);
        videoView1.start();//start automatically

        MediaController mediaController2 = new MediaController(this);
        mediaController2.setAnchorView(videoView2);//pass the video object
        videoView2.setMediaController(mediaController2);
        videoView2.start();

        MediaController mediaController3 = new MediaController(this);
        mediaController3.setAnchorView(videoView3);
        videoView3.setMediaController(mediaController3);
        videoView3.start();

    }

    @Override //when back button clicked
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}