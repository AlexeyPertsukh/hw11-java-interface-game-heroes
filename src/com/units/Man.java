package com.units;

import com.interfaces.Movable;

public abstract class Man extends Unit implements Movable {

    public Man(String name, int hintPoint, int position, char coat) {
        super(name, hintPoint, position, coat);
    }

//    abstract public String shortInfo(); //абстрактный метод
}
