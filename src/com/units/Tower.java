package com.units;

import com.interfaces.DistanceAttackable;

public class Tower extends Bastion implements DistanceAttackable {

    private static final String NAME = "Башня";
    private static final char COAT = '▲';
    private static final int DAMAGE_MIN = 15;
    private static final int DAMAGE_MAX = 30;
    private static final int HIT_POINT = 80;

    public Tower(int position) {
        super(NAME, HIT_POINT, position, COAT, DAMAGE_MIN, DAMAGE_MAX);
    }

    @Override
    public String shortInfoAlive() {
        String info = String.format("%s (%s, %s)", infoName(), infoHitPoint(), infoDamage());
        return String.format(MASK_INFO, info);
    }
}
