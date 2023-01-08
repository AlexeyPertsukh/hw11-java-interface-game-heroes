package com.units;

import com.interfaces.Movable;

public abstract class Man extends Unit implements Movable {
    public static final char SYMBOL_HP = '♥';

    public Man(String name, int hintPoint, char coat) {
        super(name, hintPoint,coat);
    }

    protected String infoHitPoint() {
        return String.format("%c%d", SYMBOL_HP, getHitPoint());
    }

}
