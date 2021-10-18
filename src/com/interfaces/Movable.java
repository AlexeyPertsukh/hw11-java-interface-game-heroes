package com.interfaces;

import com.game.Game;
import com.units.Unit;

//ходок, перемещается в пространстве
public interface Movable {
    String MSG_NO_WAY = "туда ходить нельзя";

    //идти
    default boolean go(int step) {
        Unit unit = (Unit)this;
        int newPosition = unit.getPosition() + step;

        if( newPosition > Game.RIGHT_MAP_MAX_POSITION) { //двигаться вправо некуда
            return false;
        }
        else if(newPosition < 0) {   //двигаться влево некуда
            return false;
        }
        unit.setPosition(newPosition);
        return true;
    }

    //идти вправо
    default boolean goRightOneStep() {
        return go(1);
    }

    //идти влево
    default boolean goLeftOneStep() {
        return go(-1);
    }
}
