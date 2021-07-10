package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class PlayerSetup extends AppCompatActivity {
    private EditText player1;
    private EditText player2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_setup);
        player1 = findViewById(R.id.enter_name1_id);
        player2 = findViewById(R.id.enter_name2_id);
    }

    public void submitClick(View view) {
        String player1Name = player1.getText().toString();
        if (TextUtils.isEmpty(player1Name)) { // no name
            player1Name = getString(R.string.player1_txt_view);
        }
        String player2Name = player2.getText().toString();
        if (TextUtils.isEmpty(player2Name)) { // no name
            player2Name = getString(R.string.player2_txt_view);
        }

        Intent intent = new Intent(this, GameDisplay.class);
        intent.putExtra("PLAYER_NAMES", new String[]{player1Name, player2Name});
        startActivity(intent);
    }
}

