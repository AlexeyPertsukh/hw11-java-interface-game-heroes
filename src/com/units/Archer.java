package com.units;

public class Archer extends Shooter {

    private static final String NAME = "Лучник";
    private static final char COAT = '◉';
    private static final int DAMAGE_MIN = 10;
    private static final int DAMAGE_MAX = 30;
    private static final int HIT_POINT = 55;

    public Archer(int position) {
        super(NAME, HIT_POINT, position, COAT, DAMAGE_MIN, DAMAGE_MAX);
    }

    @Override
    public String infoSkills() {
        return String.format("%s, %s", infoHitPoint(), infoDamage());
    }

}
