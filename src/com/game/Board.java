package com.game;

import com.units.Unit;

import java.util.HashMap;
import java.util.Map;

public class Board {
    public static final int COLUMNS = 4;
    private static final int FIRST_POSITION = 0;
    private static final int SECOND_POSITION = COLUMNS - 1;

    private final Unit[] firstTeam;
    private final Unit[] secondTeam;

    private final Map<Unit, Integer> map = new HashMap<>();

    public Board(Unit[] firstTeam, Unit[] secondTeam) {
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;

        mapUpdate(firstTeam, FIRST_POSITION);
        mapUpdate(secondTeam, SECOND_POSITION);
    }

    public int length() {
        return Math.max(firstTeam.length, secondTeam.length);
    }

    public int getPosition(Unit unit) {
        return map.get(unit);
    }

    private void mapUpdate(Unit[] units, int position) {
        for (Unit u : units) {
            map.put(u, position);
        }
    }

    public boolean updatePosition(Unit unit, int newPosition) {
        map.get(unit);  //если такого юнита нет- выбьет ошибку
        if(newPosition < 0 || newPosition >= COLUMNS) {
            return false;
        }
        map.put(unit, newPosition);
        return true;
    }

    public Unit[] line(int index) {
        return new Unit[] {getUnit(firstTeam, index),  getUnit(secondTeam, index)};
    }

    private Unit getUnit(Unit[] units, int index) {
        if(index < 0 || index >= units.length) {
            return null;
        }
        return units[index];
    }
}
