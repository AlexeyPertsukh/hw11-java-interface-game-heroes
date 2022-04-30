package com.interfaces;

import com.units.Unit;

//ходок, перемещается в пространстве
public interface Movable {

    int ONE_STEP_LEFT = -1;
    int ONE_STEP_RIGHT = 1;

    default boolean go(int minPosition, int maxPosition, int step) {
        Unit unit = (Unit)this;
        int nextPosition = unit.getPosition() + step;

        if( nextPosition > maxPosition) { //двигаться вправо некуда
            return false;
        }
        else if(nextPosition < minPosition) {   //двигаться влево некуда
            return false;
        }
        unit.setPosition(nextPosition);
        return true;
    }

    //идти вправо
    default boolean goRightOneStep(int minPosition, int maxPosition) {
        return go(minPosition, maxPosition, ONE_STEP_RIGHT);
    }

    //идти влево
    default boolean goLeftOneStep(int minPosition, int maxPosition) {
        return go(minPosition, maxPosition, ONE_STEP_LEFT);
    }
}
