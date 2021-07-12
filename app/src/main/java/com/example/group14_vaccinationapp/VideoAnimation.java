package com.example.group14_vaccinationapp;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class VideoAnimation extends YouTubeBaseActivity {

    //Youtube API KEY
    String API_KEY ="AIzaSyCix23rz9_PfxCRXBFlQYTrxOz1cKQMx-4";
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_animation);
        toolbar=findViewById(R.id.toolbar_video);
        //set back button
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_backbtn));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ImageView imageView = findViewById(R.id.imageView_VideoAnimation);
        // the animation list -> background of image frame
        imageView.setBackgroundResource(R.drawable.animation);
        // AnimationDrawable -> animate the images using animation.xml
        AnimationDrawable vaccinationAnimation = (AnimationDrawable) imageView.getBackground();
        vaccinationAnimation.start();

        //YOUTUBE API VIDEO PLAYER
        YouTubePlayerView videoView = findViewById(R.id.ytVideo);
        videoView.initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadPlaylist("PLZ53YLGualfMnPYHPaVZAAt6bmISvml8e");
                youTubePlayer.play();

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(VideoAnimation.this, "Youtube Video player Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}