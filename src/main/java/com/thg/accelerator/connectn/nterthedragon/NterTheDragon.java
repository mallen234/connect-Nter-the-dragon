package com.thg.accelerator.connectn.nterthedragon;
import com.thehutgroup.accelerator.connectn.player.*;
import com.thg.accelerator.connectn.nterthedragon.helpers.BoardAnalyser;
import com.thg.accelerator.connectn.nterthedragon.helpers.GameState;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class NterTheDragon extends Player {
  List<List<Integer>> winStates;
  BoardAnalyser boardAnalyser;
  public NterTheDragon(Counter counter) {
    //TODO: fill in your name here
    super(counter, NterTheDragon.class.getName());
    boardAnalyser = new BoardAnalyser(new GameConfig(10,8,4));
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

  public boolean isMoveWinner(int move, Board board) throws InvalidMoveException {
    Board boardCheck = new Board(board,move,getCounter());
    GameState gameState =  boardAnalyser.calculateGameState(boardCheck);
    return gameState.isWin();
  }

  public int takeBetterMove(Counter[][] counters, Board board) {
    for (int i = 0; i < 10; i++) {
      try {
        if (isMoveWinner(i, board)) {
          return i;
        }
      } catch (InvalidMoveException e) {
        throw new RuntimeException(e);
      }

    }
    return takeRandomMove(counters);
  }

  public Counter[][] getCounters(Board board) throws NoSuchMethodException {
    Method method = board.getClass().getDeclaredMethod("getCounterPlacements");
    Counter[][] counters = new Counter[0][];

    if (method.getName().equals("getCounterPlacements")){
      try {
        method.setAccessible(true);
        counters = (Counter[][]) method.invoke(  board);
        System.out.println("=======");

        System.out.println(counters);
        System.out.println("=======");

      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      } catch (InvocationTargetException e) {
        throw new RuntimeException(e);
      }
    }
    return counters;
  }

  @Override
  public int makeMove(Board board) {
    //TODO: some crazy analysis
    //TODO: make sure said analysis uses less than 2G of heap and returns within 10 seconds on whichever machine is running it

    Counter[][] counters =  BoardThief.stealCounters(board);
    int move = takeBetterMove(counters,board);
    return move;
  }
}
