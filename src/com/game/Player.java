package com.game;

import com.units.Unit;

public class Player {
    private static final int CODE_ERR = -1;

    private final String name;
    private Unit[] units;
    private Unit unitCurrent;

    public Player(String name) {
        this.name = name;
        units = new Unit[0];
    }

    //добавляем юнита
    public void addUnit(Unit unit) {
        Unit[] tmp = new Unit[units.length + 1];
        System.arraycopy(units, 0, tmp, 0, units.length);

        tmp[tmp.length - 1] = unit;
        units = tmp;
    }

    //фокус на юнита
    public boolean focusUnit(Unit unit) {
        if (unit.isDead()) {
            return false;
        } else {
            unitCurrent = unit;
            return true;
        }
    }

    //фокус на первого (живого) юнита
    public boolean focusFirstLivingUnit() {
        for (Unit unit : units) {
            if (!unit.isDead()) {
                return focusUnit(unit);
            }
        }
        return false;
    }

    //фокус на следующего юнита
    public boolean focusNextUnit() {

        //находим позицию в массиве текущего юнита
        int num = CODE_ERR;
        for (int i = 0; i < units.length; i++) {
            if (unitCurrent == units[i]) {
                num = i;
                break;
            }
        }

        //такого юнита вообще не нашли - выходим
        if (num == CODE_ERR) {
            return false;
        }

        //все убиты? выходим
        if (isAllUnitsDead()) {
            return false;
        }

        //фокусируемся на следующем живом юните
        for (int i = num + 1; i < units.length; i++) {
            if(!units[i].isDead()) {
                num = i;
                break;
            }
        }

        return focusUnit(units[num]);
    }

    public boolean currentUnitIsLastInLine() {
        boolean isLast = false;
        for (Unit unit : units) {
            if (unit == unitCurrent) {
                isLast = true;
            } else if(!unit.isDead()) {
                isLast = false;
            }
        }
        return isLast;
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

    public int getUnitsSize() {
        return units.length;
    }

    public Unit getUnitByNum(int num) {
        if (num < 0 || num >= units.length) {
            return null;
        }
        return units[num];
    }

    public Unit getUnitCurrent() {
        return unitCurrent;
    }


}
