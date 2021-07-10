package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class GameDisplay extends AppCompatActivity {

    private TicTacToeBoard board;
    private TextView player1Score;
    private TextView player2Score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_display);


        Button playAgain = findViewById(R.id.play_again_id);
        Button home = findViewById(R.id.home_id);
        TextView playerTurn = findViewById(R.id.game_dis_title_txt_view);

        player1Score = findViewById(R.id.score_left_id);
        player2Score = findViewById(R.id.score_right_id);

        playAgain.setVisibility(View.GONE);
        home.setVisibility(View.GONE);

        String[] playerNames = getIntent().getStringArrayExtra("PLAYER_NAMES");
        String[] aiNames = getIntent().getStringArrayExtra("PLAYER_AI_NAMES");
        int optionIndex = getIntent().getIntExtra("DIFF_NUM", 0);
        boolean flagAI = getIntent().getExtras().getBoolean("FLAG");

        board = findViewById(R.id.ticTacToeBoard);


        if (playerNames != null) {
            playerTurn.setText(getString(R.string.turn_string, playerNames[0]));
        }

        if (aiNames != null) {
            playerTurn.setText(getString(R.string.turn_string, aiNames[0]));
        }


        if (flagAI) {
            board.setUpGameAI(playAgain, home, playerTurn, aiNames, optionIndex, true);
        } else {
            board.setUpGame(playAgain, home, playerTurn, playerNames);
        }

    }


    public void playAgainClick(View view) {
        updateScore();
        board.resetGame();
        board.invalidate();
    }

    public void updateScore() {
        player1Score.setText(String.format("  %s:  %s  ", board.getGame().getPlayerNames(0),
                board.getGame().getPlayer1ScoreCount()));
        player2Score.setText(String.format("  %s:  %s  ", board.getGame().getPlayerNames(1),
                board.getGame().getPlayer2ScoreCount()));
    }

    public void homeClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}