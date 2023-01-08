package com.game;

import com.units.Unit;

import java.util.HashMap;
import java.util.Map;

public class Board {
    private static final int COLUMNS = 5;
    private static final int FIRST_POSITION = 0;
    private static final int SECOND_POSITION = COLUMNS - 1;

    private final Player first;
    private final Player second;

    private final Map<Unit, Integer> map = new HashMap<>();

    public Board(Player first, Player second) {
        this.first = first;
        this.second = second;

        mapUpdate(first.getUnits(), FIRST_POSITION);
        mapUpdate(second.getUnits(), SECOND_POSITION);
    }

    public int length() {
        return Math.max(first.getUnitsSize(), second.getUnitsSize());
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
        int position = map.get(unit);
        if(newPosition < 0 || newPosition >= COLUMNS) {
            return false;
        }
        map.put(unit, newPosition);
        return true;
    }

    public Unit[] line(int index) {
        return new Unit[] {first.getUnit(index),  second.getUnit(index)};
    }

}
