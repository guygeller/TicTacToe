package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    private VideoView videoBackground;
    MediaPlayer mMediaPlayer;
    int currentVideoPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoBackground = (VideoView) findViewById(R.id.video_view);

        Uri uri = Uri.parse("android.resource://"
                + getPackageName()
                + "/" + R.raw.playing_in_the_blackboard);

        videoBackground.setVideoURI(uri);
        videoBackground.start();

        videoBackground.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mMediaPlayer = mp;
                //video is looping
                mMediaPlayer.setLooping(true);
                // seeking the current position if it has been set and play the video
                if (currentVideoPosition != 0) {
                    mMediaPlayer.seekTo(currentVideoPosition);
                    mMediaPlayer.start();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        //capture the current video position and pause the video
        currentVideoPosition = mMediaPlayer.getCurrentPosition();
        videoBackground.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Restart the video when resuming the Activity
        videoBackground.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //when the Activity is destroyed, release MediaPlayer and set it to null
        mMediaPlayer.release();
        mMediaPlayer = null;
    }

    public void PlayClick(View view) {
        //Player Setup activity
        Intent intent = new Intent(this, PlayerSetup.class);
        startActivity(intent);
    }

    public void AIPlayClick(View view) {
        //AI activity
        String you = getString(R.string.you_name);
        String ai = getString(R.string.AI_name);
        Intent intent = new Intent(this, GameDisplay.class);
        intent.putExtra("PLAYER_AI_NAMES", new String[]{ai, you});
        intent.putExtra("button", 1);
        startActivity(intent);

    }
}