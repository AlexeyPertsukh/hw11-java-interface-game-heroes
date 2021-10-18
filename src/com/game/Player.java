package com.game;

import com.units.*;

import java.util.Scanner;

public class Player {
    private static final int ERR_CODE = -1;

    private final String name;
    private Unit[] units;
    private Unit unitCurrent;

    public Player(String name, int position) {
        this.name = name;
        units = new Unit[0];
        addUnit(new Tower(position));
        addUnit(new Knight(position));
        addUnit(new Archer(position));
        addUnit(new Dangler(position));
        addUnit(new Magic(position));

        focusFirstLivingUnit();
    }

    public String getUnitShortInfo(int num) {
        if (num < 0 || num >= units.length) { //некорректный запрос
            return null;
        }

        return units[num].toString();
    }

    //добавляем юнита
    private void addUnit(Unit unit) {
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
        int num = ERR_CODE;
        for (int i = 0; i < units.length; i++) {
            if (unitCurrent == units[i]) {
                num = i;
                break;
            }
        }

        //такого юнита вообще не нашли - выходим
        if (num == ERR_CODE) {
            return false;
        }

        //все убиты? выходим
        if (isAllUnitsDead()) {
            return false;
        }

        //фокусируемся на следующем живом юните
        do {
            num++;
            if (num >= units.length) {
                num = 0;
            }
        } while (units[num].isDead());

        return focusUnit(units[num]);
    }

    //все юниты сыграли?
    public boolean currentUnitIsFirstAmongLiving() {
        for (Unit unit : units) {
            if (!unit.isDead()) {
                return (unit == unitCurrent);
            }
        }
        return false;
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

    public String nextCmd(Scanner sc) {
        return sc.next();
    }

    public String getName() {
        return name;
    }

    public int getUnitsSize() {
        return units.length;
    }

    //возвращяет порядковый номер юнита (начинается с 1), или код ошибки
    public int getNumUnits(Unit unit) {
        for (int i = 0; i < units.length; i++) {
            if (units[i] == unit) {
                return i + 1;
            }
        }
        return ERR_CODE;
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
