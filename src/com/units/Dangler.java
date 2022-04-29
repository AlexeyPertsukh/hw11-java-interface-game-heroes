package com.units;

import com.interfaces.Jokable;

/*
тунеядец-шутник
в бою бесполезен, нужен для прикола
 */

public class Dangler extends Man implements Jokable {

    private static final String NAME = "Тунеядец";
    private static final char COAT = '⬦';
    private static final int HIT_POINT = 15;

    public Dangler(int position) {
        super(NAME, HIT_POINT, position, COAT);
    }

    @Override
    public String infoSkills() {
        return String.format("%s, %s",infoHitPoint(), infoJoke());
    }
}
