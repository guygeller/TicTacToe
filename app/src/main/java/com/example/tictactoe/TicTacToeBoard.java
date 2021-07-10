package com.example.tictactoe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class TicTacToeBoard extends View {
    private final int boardColor;
    private final int xColor;
    private final int oColor;
    private final int lineColor;
    private final Paint paint = new Paint();
    private int cellSize = getWidth() / 3;
    private final GameLogic game;
    private boolean winningLine = false;
    private final AILogic aiGame;


    public TicTacToeBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        game = new GameLogic(context);
        aiGame = new AILogic();


        TypedArray arr = context.getTheme().
                obtainStyledAttributes(attrs, R.styleable.TicTacToeBoard, 0, 0);

        try {
            boardColor = arr.getInteger(R.styleable.TicTacToeBoard_boardColor, 0);
            xColor = arr.getInteger(R.styleable.TicTacToeBoard_xColor, 0);
            oColor = arr.getInteger(R.styleable.TicTacToeBoard_oColor, 0);
            lineColor = arr.getInteger(R.styleable.TicTacToeBoard_lineColor, 0);
        } finally {
            arr.recycle();
        }
    }

    @Override
    protected void onMeasure(int width, int height) {
        super.onMeasure(width, height);

        int dimension = Math.min(getMeasuredWidth(), getMeasuredHeight());
        cellSize = dimension / 3;
        setMeasuredDimension(dimension, dimension);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        drawGameBoard(canvas);
        drawMarkers(canvas);
        if (winningLine) {
            paint.setColor(lineColor);
            drawWinningLine(canvas);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            int row = (int) Math.ceil(y / cellSize);
            int col = (int) Math.ceil(x / cellSize);

            if (!winningLine) { //no winner
                if (game.updateGameBoard(row, col)) {
                    invalidate(); // redraws board

                    if (game.winnerCheck()) {
                        winningLine = true;
                    } else {
                        game.switchPlayer();
                    }
                    invalidate();

                    if (game.isFlagAI() && !winningLine) {
                        AILogic.Move move = new AILogic.Move();
                        switch (game.getDiffOptionIndex()) {
                            case 0: // random moves
                                Log.e("case 0", "case 0 - easy");
                                move = aiGame.findRandomMove(game.getGameBoard());
                                break;
                            case 1: // 2 minimax moves then random moves
                                Log.e("case 1", "case 1 - normal");
                                if (game.getMoveNum() < 5) {
                                    Log.e("case 1", "true");
                                    move = aiGame.findMove(game.getGameBoard(), true);
                                } else {
                                    Log.e("case 1", "false");
                                    move = aiGame.findMove(game.getGameBoard(), false);
                                }
                                break;
                            case 2: // minimax moves
                                Log.e("case 2", "case 2 - hard");
                                move = aiGame.findBestMove(game.getGameBoard());
                                break;
                        }
                        if (move != null) {
                            if (!game.updateGameBoard(move.row + 1, move.col + 1)) {
                                Log.e("TicTacToeBoard", "ai can't make the move r:" + move.row + " c:" + move.col);
                            } else {
                                Log.e("TicTacToeBoard", "ai moved r:" + move.row + " c:" + move.col);
                            }

                            if (game.winnerCheck()) {
                                winningLine = true;
                            } else {
                                game.switchPlayer();
                            }
                            invalidate();
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    private void drawGameBoard(Canvas canvas) {
        paint.setColor(boardColor);
        paint.setStrokeWidth(12);

        for (int c = 1; c < 3; c++) {
            canvas.drawLine(cellSize * c, 0, cellSize * c, canvas.getWidth(), paint);
        }
        for (int r = 1; r < 3; r++) {
            canvas.drawLine(0, cellSize * r, canvas.getWidth(), cellSize * r, paint);
        }
    }

    private void drawMarkers(Canvas canvas) {
        //will draw an X or O onto the board
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (game.getGameBoard()[r][c] == 1) {
                    drawX(canvas, r, c);
                }
                if (game.getGameBoard()[r][c] == 2) {
                    drawO(canvas, r, c);
                }
            }
        }
    }

    private void drawX(Canvas canvas, int row, int col) {
        paint.setColor(xColor);

        canvas.drawLine((col + 1) * cellSize - cellSize * 0.2f,
                row * cellSize + cellSize * 0.2f,
                col * cellSize + cellSize * 0.2f,
                (row + 1) * cellSize - cellSize * 0.2f,
                paint);

        canvas.drawLine(col * cellSize + cellSize * 0.2f,
                row * cellSize + cellSize * 0.2f,
                (col + 1) * cellSize - cellSize * 0.2f,
                (row + 1) * cellSize - cellSize * 0.2f,
                paint);
    }

    private void drawO(Canvas canvas, int row, int col) {
        paint.setColor(oColor);

        canvas.drawOval(col * cellSize + cellSize * 0.2f,
                row * cellSize + cellSize * 0.2f,
                col * cellSize + cellSize - cellSize * 0.2f,
                row * cellSize + cellSize - cellSize * 0.2f,
                paint);
    }

    private void drawHorizontalLine(Canvas canvas, int row, int col) {
        canvas.drawLine(col,
                row * cellSize + cellSize / 2.f,
                cellSize * 3,
                row * cellSize + cellSize / 2.f,
                paint);
    }

    private void drawVerticalLine(Canvas canvas, int row, int col) {
        canvas.drawLine(col * cellSize + cellSize / 2.f, row,
                col * cellSize + cellSize / 2.f,
                cellSize * 3,
                paint);
    }

    private void drawDiagonalLinePos(Canvas canvas) {
        canvas.drawLine(0, cellSize * 3,
                cellSize * 3,
                0,
                paint);
    }

    private void drawDiagonalLineNeg(Canvas canvas) {
        canvas.drawLine(0, 0,
                cellSize * 3,
                cellSize * 3,
                paint);
    }

    private void drawWinningLine(Canvas canvas) {
        paint.setStrokeWidth(8);
        int row = game.getWinType()[0];
        int col = game.getWinType()[1];

        switch (game.getWinType()[2]) {
            case 1:
                drawHorizontalLine(canvas, row, col);
                break;
            case 2:
                drawVerticalLine(canvas, row, col);
                break;
            case 3:
                drawDiagonalLineNeg(canvas);
                break;
            case 4:
                drawDiagonalLinePos(canvas);
                break;
        }
    }


    public void setUpGame(Button playAgain, Button home, TextView playerDisplay, String[] names) {
        game.setPlayAgainBtn(playAgain);
        game.setHomeBtn(home);
        game.setPlayerTurn(playerDisplay);
        game.setPlayerNames(names);
    }

    public void setUpGameAI(Button playAgain, Button home, TextView playerDisplay, String[] names, int diffIndex, boolean flag) {
        game.setPlayAgainBtn(playAgain);
        game.setHomeBtn(home);
        game.setPlayerTurn(playerDisplay);
        game.setPlayerNames(names);
        game.setDiffOptionIndex(diffIndex);
        game.setFlagAI(flag);
    }

    public void resetGame() {
        game.resetGame();
        winningLine = false;
    }

    public GameLogic getGame() {
        return game;
    }

}
