package com.example.tictactoe;


import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;

public class GameLogic {
    private final int[][] gameBoard;
    private int player = 1;
    private Button playAgainBtn;
    private Button homeBtn;
    private TextView playerTurn;
    private String[] playerNames;
    //1st element-->row, 2nd element-->col, 3rd element--> line type
    private int[] winType = {-1, -1, -1};
    private Context context;
    private int player1ScoreCount;
    private int player2ScoreCount;


    GameLogic(Context context) {
        this.context = context;
        gameBoard = new int[3][3];
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                gameBoard[r][c] = 0;
            }
        }
    }


    public boolean updateGameBoard(int row, int col) {
        // X = 1, O = 2
        //Player will be a 1 or 2. if 1 --> X. if 2 --> O
        if (gameBoard[row - 1][col - 1] == 0) {
            gameBoard[row - 1][col - 1] = player;

            //happens in the opposite order because the turn is flipped after updateGameBoard
            if (player == 1) {
                playerTurn.setText(context.getString(R.string.turn_string, playerNames[1]));
            } else {
                playerTurn.setText(context.getString(R.string.turn_string, playerNames[0]));
            }
            return true;
        } else {
            return false;
        }
    }

    public void fadeIn(View view) {
        //0.0f makes view invisible
        //1.0f makes view fully visible
        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(1000); // 1 second
        view.startAnimation(animation);
        view.setVisibility(View.VISIBLE);
    }


    public boolean winnerCheck() {
        boolean isWinner = false;

        for (int r = 0; r < 3; r++) {
            // Horizontal check, winType == 1
            if (gameBoard[r][0] == gameBoard[r][1] && gameBoard[r][0] == gameBoard[r][2] &&
                    gameBoard[r][0] != 0) {
                winType = new int[]{r, 0, 1};
                isWinner = true;
            }
        }
        // Vertical check, winType == 2
        for (int c = 0; c < 3; c++) {
            if (gameBoard[0][c] == gameBoard[1][c] && gameBoard[2][c] == gameBoard[0][c] &&
                    gameBoard[0][c] != 0) {
                winType = new int[]{0, c, 2};
                isWinner = true;
            }
        }
        // Negative diagonal check, winType == 3
        if (gameBoard[0][0] == gameBoard[1][1] && gameBoard[0][0] == gameBoard[2][2] &&
                gameBoard[0][0] != 0) {
            winType = new int[]{0, 2, 3};
            isWinner = true;
        }
        // Positive diagonal check, winType == 4
        if (gameBoard[2][0] == gameBoard[1][1] && gameBoard[2][0] == gameBoard[0][2] &&
                gameBoard[2][0] != 0) {
            winType = new int[]{2, 2, 4};
            isWinner = true;
        }
        int boardFilled = 0;

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (gameBoard[r][c] != 0) {
                    boardFilled++;
                }
            }
        }
        if (isWinner) {
            fadeIn(playAgainBtn);
            fadeIn(homeBtn);
            playerTurn.setText(context.getString(R.string.won_string, playerNames[player - 1]));
            if (getPlayer() % 2 == 0) {
                player2ScoreCount++;
            } else {
                player1ScoreCount++;
            }
            return true;
        } else if (boardFilled == 9) {
            fadeIn(playAgainBtn);
            fadeIn(homeBtn);
            playerTurn.setText(R.string.tie_string);
            return true;
        } else {
            return false;
        }
    }


    public void resetGame() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                gameBoard[r][c] = 0;
            }
        }

        for (int i = 0; i < 3; i++) {
            winType[i] = -1;
        }

        player = 1;
        playAgainBtn.setVisibility(View.GONE);
        homeBtn.setVisibility(View.GONE);
        playerTurn.setText(context.getString(R.string.turn_string, playerNames[0]));
    }

    public int[][] getGameBoard() {
        return gameBoard;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getPlayer() {
        return player;
    }

    public String getCurrentPlayerName() {
        return playerNames[player - 1];
    }

    public String getOtherPlayerName() {
        int player = this.player == 1 ? 2 : 1;
        return playerNames[player - 1];
    }

    public void switchPlayer() {
        this.player = this.player == 1 ? 2 : 1;
    }

    public void setPlayAgainBtn(Button playAgainBtn) {
        this.playAgainBtn = playAgainBtn;
    }

    public void setHomeBtn(Button homeBtn) {
        this.homeBtn = homeBtn;
    }

    public void setPlayerTurn(TextView playerTurn) {
        this.playerTurn = playerTurn;
    }

    public void setPlayerNames(String[] playerNames) {
        this.playerNames = playerNames;
    }

    public String getPlayerNames(int i) {
        return playerNames[i];
    }

    public int[] getWinType() {
        return winType;
    }

    public int getPlayer1ScoreCount() {
        return player1ScoreCount;
    }

    public int getPlayer2ScoreCount() {
        return player2ScoreCount;
    }

}
