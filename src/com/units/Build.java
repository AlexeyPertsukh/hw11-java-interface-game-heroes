package com.units;

public abstract class Build extends Unit {
    public static final char CHAR_BUILD_HP = '⚒';

    public Build(String name, int hintPoint, int position, char coat) {
        super(name, hintPoint, position, coat);
    }


    @Override
    protected String infoHitPoint() {
        return super.infoHitPoint().replace(CHAR_HP, CHAR_BUILD_HP);  //заменяем сердце на молоточки
    }

}
