package com.example.group14_vaccinationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Audio extends AppCompatActivity {

    MediaPlayer audio1, audio2, audio3, currentPlaying;
    ImageView icon1, icon2, currentPlayImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        Toolbar toolbar = findViewById(R.id.toolbar_audio);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        audio1 = MediaPlayer.create(this, R.raw.audiocovid1);
        audio2 = MediaPlayer.create(this, R.raw.audiocovid2);
        audio3 = MediaPlayer.create(this, R.raw.audiocovid3);
    }

    @Override
    protected void onStop() {
        super.onStop();
        currentPlaying.stop();
    }

    @Override //when back button clicked
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void audioOperation(View view) {
        ImageView imgToAction = (ImageView)view;

        switch (view.getId()){
            case R.id.img_audio_audio1:
                if(currentPlaying == audio1 && currentPlaying.isPlaying()){
                    audio1.pause();
                    imgToAction.setImageResource(R.drawable.playicon);
                }
                else{
                    playAudio(audio1, imgToAction);
                    imgToAction.setImageResource(R.drawable.pauseicon);
                }
                break;

            case R.id.img_audio_audio2:
                if(currentPlaying == audio2 && currentPlaying.isPlaying()) {
                    audio2.pause();
                    imgToAction.setImageResource(R.drawable.playicon);
                }
                else {
                    playAudio(audio2, imgToAction);
                    imgToAction.setImageResource(R.drawable.pauseicon);
                }
                break;

            case R.id.img_audio_audio3:
                if(currentPlaying == audio3 && currentPlaying.isPlaying()) {
                    audio3.pause();
                    imgToAction.setImageResource(R.drawable.playicon);
                }
                else {
                    playAudio(audio3, imgToAction);
                    imgToAction.setImageResource(R.drawable.pauseicon);
                }
                break;
        }
    }

    public void playAudio(MediaPlayer audio, ImageView img){
        if(currentPlaying != null){
            currentPlaying.pause();
            currentPlayImage.setImageResource(R.drawable.playicon);
        }
        audio.start();
        currentPlaying = audio;
        currentPlayImage = img;
    }
}