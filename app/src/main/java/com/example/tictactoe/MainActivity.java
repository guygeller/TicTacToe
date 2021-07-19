package com.example.tictactoe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.AudioManager;
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
    private int difficultyChosen;
    Button mute;
    private boolean muteBool = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // play background music
        Intent backgroundMusic = new Intent(MainActivity.this, BackgroundSoundService.class);
        startService(backgroundMusic);

        videoBackground = findViewById(R.id.video_view);
        mute = findViewById(R.id.mute_btn_id);

        Uri uri = Uri.parse("android.resource://"
                + getPackageName()
                + "/" + R.raw.playing_in_the_blackboard);

        videoBackground.setVideoURI(uri);
        videoBackground.start();

        videoBackground.setOnPreparedListener(mp -> {
            mMediaPlayer = mp;
            //video is looping
            mMediaPlayer.setLooping(true);
            // seeking the current position if it has been set and play the video
            if (currentVideoPosition != 0) {
                mMediaPlayer.seekTo(currentVideoPosition);
                mMediaPlayer.start();
            }
        });

    }


    public void setDifficultyChosen(int difficultyChosen) {
        this.difficultyChosen = difficultyChosen;
    }


    public int getDifficultyChosen() {
        return difficultyChosen;
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


    public void onClickMute(View view) {
        AudioManager manager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        if (muteBool) {
            manager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
            muteBool = false;
        } else {
            manager.setStreamVolume(AudioManager.STREAM_MUSIC, 20, 0);
            muteBool = true;
        }
    }


    public void PlayClick(View view) {
        //Player Setup activity
        Intent intent = new Intent(this, PlayerSetup.class);
        startActivity(intent);
    }

    public void AIPlayClick(View view) {
        Resources res = getResources();
        final String[] diff = {res.getString(R.string.easy_option), res.getString(R.string.normal_option), res.getString(R.string.hard_option)};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.difficulty_title));
        builder.setSingleChoiceItems(diff, 0, (dialog, which) -> setDifficultyChosen(which));
        builder.setPositiveButton(android.R.string.ok, (dialog, which) -> onYesClick(view));
        // A null listener allows the button to dismiss the dialog and take no further action.
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.setIcon(android.R.drawable.ic_menu_set_as);
        builder.show();
    }

    public void onYesClick(View view) {
        //AI activity
        int difficultyChosen = getDifficultyChosen();
        String player = getString(R.string.you_name);
        String ai = getString(R.string.AI_name);
        Intent intent = new Intent(this, GameDisplay.class);
        intent.putExtra("PLAYER_AI_NAMES", new String[]{player, ai});
        intent.putExtra("DIFF_NUM", difficultyChosen);
        intent.putExtra("FLAG", true);
        startActivity(intent);
    }
}