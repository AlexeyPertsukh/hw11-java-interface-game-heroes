package com.units;

public abstract class Build extends Unit {
    public static final char SYMBOL_HP = '⚒';

    public Build(String name, int hintPoint, char coat) {
        super(name, hintPoint, coat);
    }

    @Override
    protected String infoHitPoint() {
        return String.format("%c%d", SYMBOL_HP, getHitPoint());
    }

}
