package com.example.tictactoe;

import java.util.ArrayList;
import java.util.Random;

public class AILogic {

    private final int human = 1;
    private final int ai = 2;
    Random rand = new Random();


    static public class Move {
        int row;
        int col;

        Move() {
        }

        Move(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    AILogic() {
    }


    public Boolean isMovesLeft(int[][] gameBoard) {
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 3; c++)
                if (gameBoard[r][c] == 0)
                    return true;
        return false;
    }

    public int evaluate(int[][] gameBoard) {
        // Checking for Rows for victory.
        for (int r = 0; r < 3; r++) {
            if (gameBoard[r][0] == gameBoard[r][1] && gameBoard[r][1] == gameBoard[r][2]) {
                if (gameBoard[r][0] == ai)
                    return +10;
                else if (gameBoard[r][0] == human)
                    return -10;
            }
        }
        // Checking for Columns for victory.
        for (int c = 0; c < 3; c++) {
            if (gameBoard[0][c] == gameBoard[1][c] && gameBoard[1][c] == gameBoard[2][c]) {
                //gameLogic.getWinType();
                if (gameBoard[0][c] == ai)
                    return +10;
                else if (gameBoard[0][c] == human)
                    return -10;
            }
        }
        // Checking for Diagonals for victory.
        if (gameBoard[0][0] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][2]) {
            if (gameBoard[0][0] == ai)
                return +10;
            else if (gameBoard[0][0] == human)
                return -10;
        }
        if (gameBoard[0][2] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][0]) {
            if (gameBoard[0][2] == ai)
                return +10;
            else if (gameBoard[0][2] == human)
                return -10;
        }
        // Else if none of them have won then return 0
        return 0;
    }

    public int minimax(int[][] b, int depth, Boolean isMax) {
        int score = evaluate(b);
        // If Maximizer has won the game return evaluated score
        if (score == 10)
            return +1;
        // If Minimizer has won the game return evaluated score
        if (score == -10)
            return -1;
        // If there are no more moves and no winner then it is a tie
        if (!isMovesLeft(b))
            return 0;
        // If this maximizer's move
        int best; // = 0
        if (isMax) {
            best = -1000;
            // Traverse all cells
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    // Check if cell is empty
                    if (b[r][c] == 0) {
                        // Make the move
                        b[r][c] = ai;
                        // Call minimax recursively and choose the maximum value
                        best = Math.max(best, minimax(b, depth + 1, false));
                        // Undo the move
                        b[r][c] = 0;
                    }
                }
            }
        }
        // If this minimizer's move
        else {
            best = 1000;
            // Traverse all cells
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    // Check if cell is empty
                    if (b[r][c] == 0) {
                        // Make the move
                        b[r][c] = human;
                        // Call minimax recursively and choose the minimum value
                        best = Math.min(best, minimax(b, depth + 1, true));
                        // Undo the move
                        b[r][c] = 0;
                    }
                }
            }
        }
        return best;
    }


    public Move findBestMove(int[][] gameBoard) {
        int bestVal = -1000;
        Move bestMove = new Move();
        // Traverse all cells, evaluate minimax function
        // for all empty cells. And return the cell
        // with optimal value.
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Check if cell is empty
                if (gameBoard[i][j] == 0) {
                    // Make the move
                    gameBoard[i][j] = ai;
                    // compute evaluation function for this
                    // move.
                    int moveVal = minimax(gameBoard, 0, false);
                    // Undo the move
                    gameBoard[i][j] = 0;
                    // If the value of the current move is
                    // more than the best value, then update
                    // best
                    if (moveVal > bestVal) {
                        bestMove.row = i;
                        bestMove.col = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        return bestMove;
    }


    public Move findRandomMove(int[][] gameBoard) {
        ArrayList<Move> emptyCells = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Check if cell is empty
                if (gameBoard[i][j] == 0) {
                    //add to an array of empty cells
                    emptyCells.add(new Move(i, j));
                }
            }
        }
        if (isMovesLeft(gameBoard)) {
            int randCell = rand.nextInt(emptyCells.size());
            return emptyCells.get(randCell);
        } else {
            return null;
        }
    }

    public Move findMove(int[][] gameBoard, boolean flag) {
        Move move;
        if (flag) {
            move = findBestMove(gameBoard);

        } else {
            move = findRandomMove(gameBoard);
        }
        return move;
    }
}

