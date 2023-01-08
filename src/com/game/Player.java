package com.game;

import com.units.Unit;

public class Player {

    private final String name;
    private Unit[] units;

    public Player(String name, Unit[] units) {
        this.name = name;
        this.units = units;
    }

    //добавляем юнита
    public void addUnit(Unit unit) {
        Unit[] tmp = new Unit[units.length + 1];
        System.arraycopy(units, 0, tmp, 0, units.length);

        tmp[tmp.length - 1] = unit;
        units = tmp;
    }

    //все юниты погибли?
    public boolean isAllUnitsDead() {
        for (Unit unit : units) {
            if (!unit.isDead()) {
                return false;
            }
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public Unit getUnit(int num) {
        if (num < 0 || num >= units.length) {
            return null;
        }
        return units[num];
    }


    public Unit[] getUnits() {
        return units;
    }

    public boolean contain(Unit unit) {
        for (Unit u : units) {
            if (u == unit) {
                return true;
            }
        }
        return false;
    }
}
