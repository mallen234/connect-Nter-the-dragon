package com.thehutgroup.accelerator.connectn.player;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;

public class BoardThief {

    public static Counter[][] stealCounters (Board board) {
        return board.getCounterPlacements();
    }
}
