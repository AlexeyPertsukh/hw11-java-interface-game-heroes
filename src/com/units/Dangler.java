package com.units;

import com.interfaces.Jokable;

//тунеядец-шутник ᙢ ☋
public class Dangler extends Man implements Jokable {

    private static final String NAME = "Тунеядец";
    private static final char COAT = '⬦';
    private static final int HIT_POINT = 15;

    public Dangler(int position) {
        super(NAME, HIT_POINT, position, COAT);
    }

    @Override
    protected String shortInfoAlive() {
        return String.format("%s (%s, %s)", infoName(), infoHitPoint(), infoJoke());
    }
}
