package com.thg.accelerator.connectn.nterthedragon;

package com.thg.accelerator.connectn.nterthedragon;
import com.thehutgroup.accelerator.connectn.player.*;
import com.thg.accelerator.connectn.nterthedragon.helpers.BoardAnalyser;
import com.thg.accelerator.connectn.nterthedragon.helpers.GameState;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Ntertainer extends Player {
    List<List<Integer>> winStates;
    private BoardAnalyser boardAnalyser;
    private Integer MAX_DEPTH = 4;
    public Ntertainer(Counter counter) {
        //TODO: fill in your name here
        super(counter, Ntertainer.class.getName());
        boardAnalyser = new BoardAnalyser(new GameConfig(10,8,4));

    }

    private int minimax(Board board, int depth, boolean maximizingPlayer, Counter[][] counters) throws InvalidMoveException {
        if (depth == MAX_DEPTH || boardAnalyser.calculateGameState(board).isEnd()) {
            return evaluateBoard(board);
        }

        if (maximizingPlayer) {
            int maxScore = Integer.MIN_VALUE;
            for (int i = 0; i < 10; i++) {
                if (isMoveValid(i, counters)) {
                    Board boardCopy = new Board(board, i, getCounter());
                    int score = minimax(boardCopy, depth + 1, false, counters);
                    maxScore = Math.max(maxScore, score);
                }
            }
            return maxScore;
        } else {
            int minScore = Integer.MAX_VALUE;
            for (int i = 0; i < 10; i++) {
                if (isMoveValid(i, counters)) {
                    Board boardCopy = new Board(board, i, getCounter().getOther());
                    int score = minimax(boardCopy, depth + 1, true,counters);
                    minScore = Math.min(minScore, score);
                }
            }
            return minScore;
        }
    }

    private int evaluateBoard(Board board) {
        GameState gameState =  boardAnalyser.calculateGameState(board);
        if (gameState.isWin()){
            Counter counter = gameState.getWinner();
            if (counter == getCounter()){
                return 1;
            } else {
                return -1;
            }
        }
        return 0;
    }

    public boolean isMoveValid(int move,Counter[][] counters){
        return counters[move][7] == null;
    }

    public int takeRandomMove(Counter[][] counters){
        Random rand = new Random();

        while (true){
            int move = rand.nextInt(9);
            System.out.println(counters[move][7]);
            if (isMoveValid(move,counters)){
                return move;
            }
        }
    }
//
//    public boolean isMoveBlocker(int move, Board board) throws InvalidMoveException {
//        Board boardCheck = new Board(board,move,getCounter().getOther());
//        GameState gameState =  boardAnalyser.calculateGameState(boardCheck);
//        return gameState.isWin();
//    }
//
//    public boolean isMoveWinner(int move, Board board) throws InvalidMoveException {
//        Board boardCheck = new Board(board,move,getCounter());
//        GameState gameState =  boardAnalyser.calculateGameState(boardCheck);
//        return gameState.isWin();
//    }

    public int takeBetterMove(Counter[][] counters, Board board) throws InvalidMoveException {
        int bestMove = -1;
        int maxScore = Integer.MIN_VALUE;

        for (int i = 0; i < 10; i++) {
            if (isMoveValid(i, counters)) {
                Board boardCopy = new Board(board, i, getCounter());
                int score = minimax(boardCopy, 0, false, counters);

                if (score > maxScore) {
                    maxScore = score;
                    bestMove = i;
                }
            }
        }
        return bestMove;
    }

    @Override
    public int makeMove(Board board) {
        //TODO: some crazy analysis
        //TODO: make sure said analysis uses less than 2G of heap and returns within 10 seconds on whichever machine is running it

        Counter[][] counters =  BoardThief.stealCounters(board);
        int move = 0;
        try {
            move = takeBetterMove(counters,board);
        } catch (InvalidMoveException e) {
            throw new RuntimeException(e);
        }
        return move;
    }
}

