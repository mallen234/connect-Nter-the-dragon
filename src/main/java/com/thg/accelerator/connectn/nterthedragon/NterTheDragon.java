package com.thg.accelerator.connectn.nterthedragon;
import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.GameConfig;
import com.thehutgroup.accelerator.connectn.player.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;


public class NterTheDragon extends Player {
  public NterTheDragon(Counter counter) {
    //TODO: fill in your name here
    super(counter, NterTheDragon.class.getName());
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

  public Counter[][] getCounters(Board board){

    Method[] boardMethods = board.getClass().getDeclaredMethods();
    Counter[][] counters = new Counter[0][];
    for (Method method : boardMethods){
      if (method.getName().equals("getCounterPlacements")){
        try {
          method.setAccessible(true);
          counters = (Counter[][]) method.invoke(board);
          System.out.println("=======");

          System.out.println(counters);
          System.out.println("=======");

        } catch (IllegalAccessException e) {
          throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
          throw new RuntimeException(e);
        }
      }
    }
    return counters;
  }

  @Override
  public int makeMove(Board board) {
    //TODO: some crazy analysis
    //TODO: make sure said analysis uses less than 2G of heap and returns within 10 seconds on whichever machine is running it

    Counter[][] counters =  getCounters(board);
    int move = takeRandomMove(counters);
    return move;
  }
}
