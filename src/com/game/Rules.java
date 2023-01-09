package com.game;

public class Rules {

    private Rules() {
    }

    public static boolean isWin(Player first, Player second) {
        return getWinPlayer(first, second) != null;
    }

    public static Player getWinPlayer(Player first, Player second) {
        if (first.isAllUnitsDead()) {
            return second;
        }

        if (second.isAllUnitsDead()) {
            return first;
        }

        return null;
    }

}
