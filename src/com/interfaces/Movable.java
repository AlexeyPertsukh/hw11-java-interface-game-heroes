package com.interfaces;

import com.game.Game;
import com.units.Unit;

//ходок, перемещается в пространстве
public interface Movable {

    default boolean go(int step) {
        Unit unit = (Unit)this;
        int nextPosition = unit.getPosition() + step;

        if( nextPosition > Game.RIGHT_POSITION) { //двигаться вправо некуда
            return false;
        }
        else if(nextPosition < 0) {   //двигаться влево некуда
            return false;
        }
        unit.setPosition(nextPosition);
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
