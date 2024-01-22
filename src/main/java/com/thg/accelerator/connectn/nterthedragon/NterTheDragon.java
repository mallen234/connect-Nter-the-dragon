package com.thg.accelerator.connectn.nterthedragon;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.GameConfig;
import com.thehutgroup.accelerator.connectn.player.Player;

import java.util.Random;


public class NterTheDragon extends Player {
  public NterTheDragon(Counter counter) {
    //TODO: fill in your name here
    super(counter, NterTheDragon.class.getName());
  }

  @Override
  public int makeMove(Board board) {
    //TODO: some crazy analysis
    //TODO: make sure said analysis uses less than 2G of heap and returns within 10 seconds on whichever machine is running it

    Random rand = new Random();

    GameConfig currboard = board.getConfig();
    return 4;
  }
}
