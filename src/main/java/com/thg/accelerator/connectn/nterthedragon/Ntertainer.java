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
                if (isMoveValid(i, BoardThief.stealCounters(board))) {
                    Board boardCopy = new Board(board, i, getCounter());
//                    System.out.printf("--depth: %d --column: %d",depth,i);
                    int score = minimax(boardCopy, depth + 1, false, counters);
                    maxScore = Math.max(maxScore, score);
                }
            }
            return maxScore;
        } else {
            int minScore = Integer.MAX_VALUE;
            for (int i = 0; i < 10; i++) {
                if (isMoveValid(i, BoardThief.stealCounters(board))) {
                    Board boardCopy = new Board(board, i, getCounter().getOther());
//                    System.out.printf("--depth: %d --column: %d",depth,i);

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
//                System.out.println("\nwin");
                return 100000;
            } else {
//                System.out.println("\nloss");
                return -100000;
            }
        }
        return 0;
    }

    public boolean isMoveValid(int move,Counter[][] counters){
        return counters[move][7] == null;
    }

    public int takeBetterMove(Counter[][] counters, Board board) throws InvalidMoveException {
        int bestMove = -1;
        int maxScore = Integer.MIN_VALUE;

        for (int i = 0; i < 10; i++) {
            if (isMoveValid(i, counters)) {
                Board boardCopy = new Board(board, i, getCounter());
                int score = minimax(boardCopy, 0, false, counters);
                if (i == 4 || i == 5){
                    score += 2;
                }
                System.out.printf("Score at the %dth column is %d%n", i, score);
                if (score > maxScore) {
                    maxScore = score;
                    bestMove = i;
                }
            }
        }
        System.out.println(bestMove);
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
            System.out.println("----");
            System.out.println(move);
            System.out.println("----");

        } catch (InvalidMoveException e) {
            System.out.println("-jwnbfjwefkwbfownfpwnfpwfpw");
            System.out.println(e);
            throw new RuntimeException(e);
        }
        return move;
    }
}

