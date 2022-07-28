package com.units;

public abstract class Build extends Unit {
    public static final char BUILD_HP = '⚒';

    public Build(String name, int hintPoint, int position, char coat) {
        super(name, hintPoint, position, coat);
    }

    @Override
    protected String infoHitPoint() {
        return String.format("%c%d", BUILD_HP, getHitPoint());
    }

}
