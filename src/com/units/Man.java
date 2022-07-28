package com.units;

import com.interfaces.Movable;

public abstract class Man extends Unit implements Movable {
    public static final char CHAR_HP = '♥';

    public Man(String name, int hintPoint, int position, char coat) {
        super(name, hintPoint, position, coat);
    }

    protected String infoHitPoint() {
        return String.format("%c%d", CHAR_HP, getHitPoint());
    }

}
