package com.thg.accelerator.connectn.nterthedragon;

import com.thehutgroup.accelerator.connectn.player.Counter;

public class Connect4Board {
    private static final int ROWS = 10;
    private static final int COLUMNS = 8;

    public static Counter getWinner(Counter[][] board) {
        final int HEIGHT = board.length;
        final int WIDTH = board[0].length;
        final Counter EMPTY_SLOT = null;
        for (int r = 0; r < HEIGHT; r++) {
            for (int c = 0; c < WIDTH; c++) {
                Counter player = board[r][c];
                if (player == null) {
                    continue;}

                if (c + 3 < WIDTH &&
                        player == board[r][c+1] &&
                        player == board[r][c+2] &&
                        player == board[r][c+3]) {
                    return player;
                }
                if (r + 3 < HEIGHT) {
                    if (player == board[r+1][c] &&
                            player == board[r+2][c] &&
                            player == board[r+3][c])
                        return player;
                    if (c + 3 < WIDTH &&
                            player == board[r+1][c+1] &&
                            player == board[r+2][c+2] &&
                            player == board[r+3][c+3])
                        return player;
                    if (c - 3 >= 0 &&
                            player == board[r+1][c-1] &&
                            player == board[r+2][c-2] &&
                            player == board[r+3][c-3])
                        return player;
                }
            }
        }
        return null;
    }

}
