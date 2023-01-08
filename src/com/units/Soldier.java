package com.units;

import com.interfaces.Attackable;

public abstract class Soldier extends Man implements Attackable {
    private final int damageMin;
    private final int damageMax;

    public Soldier(String name, int hintPoint, char coat, int damageMin, int damageMax) {
        super(name, hintPoint, coat);
        this.damageMin = damageMin;
        this.damageMax = damageMax;
    }

    @Override
    public int getDamageMin() {
        return damageMin;
    }

    @Override
    public int getDamageMax() {
        return damageMax;
    }

}
