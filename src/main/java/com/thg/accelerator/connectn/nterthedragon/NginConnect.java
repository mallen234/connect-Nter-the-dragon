package com.thg.accelerator.connectn.nterthedragon;
import com.thehutgroup.accelerator.connectn.player.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class NginConnect extends Player {
    List<List<Integer>> winStates;
    public NginConnect(Counter counter) {
        //TODO: fill in your name here
        super(counter, NginConnect.class.getName());
    }

    public List<List<Integer>> winStates(Counter[][] board){
        List<List<Integer>> winCoordinates = new ArrayList<>();

        // Check horizontal wins
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col <= board[row].length - 4; col++) {
                List<Integer> coordinates = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    coordinates.add(row);
                    coordinates.add(col + i);
                }
                winCoordinates.add(coordinates);
            }
        }

        // Check vertical wins
        for (int col = 0; col < board[0].length; col++) {
            for (int row = 0; row <= board.length - 4; row++) {
                List<Integer> coordinates = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    coordinates.add(row + i);
                    coordinates.add(col);
                }
                winCoordinates.add(coordinates);
            }
        }

        // Check diagonal wins (from top-left to bottom-right)
        for (int row = 0; row <= board.length - 4; row++) {
            for (int col = 0; col <= board[row].length - 4; col++) {
                List<Integer> coordinates = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    coordinates.add(row + i);
                    coordinates.add(col + i);
                }
                winCoordinates.add(coordinates);
            }
        }

        // Check diagonal wins (from top-right to bottom-left)
        for (int row = 0; row <= board.length - 4; row++) {
            for (int col = 3; col < board[row].length; col++) {
                List<Integer> coordinates = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    coordinates.add(row + i);
                    coordinates.add(col - i);
                }
                winCoordinates.add(coordinates);
            }
        }
        winStates = winCoordinates;
        return winCoordinates;
    }

//    public List<Integer> possibleMoves(Counter[][] board){
//
//    }

    public int takeRandomMove(Counter[][] counters){
        Random rand = new Random();

        while (true){
            int move = rand.nextInt(9);
            System.out.println(counters[move][7]);
            if (counters[move][7] == null){
                return move;
            }
        }
    }

    @Override
    public int makeMove(Board board) {
        //TODO: some crazy analysis
        //TODO: make sure said analysis uses less than 2G of heap and returns within 10 seconds on whichever machine is running it

        Counter[][] counters =  BoardThief.stealCounters(board);
        int move = takeRandomMove(counters);
        return 1;
    }
}

