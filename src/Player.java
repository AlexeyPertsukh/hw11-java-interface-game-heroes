import java.util.Scanner;

public class Player {
    private static final String DEFAULT_NAME = "noname";

    private final String name;
    private Unit[] units;
    private Unit unitCurrent;

    public Player(int position) {
        this(DEFAULT_NAME, position);
    }

    public Player(String name, int position) {
        this.name = name;
        units = new Unit[0];
        addUnit(new Tower(position));
        addUnit(new Knight(position));
        addUnit(new Archer(position));
        addUnit(new Dangler(position));
        addUnit(new Magic(position));

        focusFirstUnit();
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
        for (int i = 0; i < units.length; i++) {
            tmp[i] = units[i];
        }

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
    public boolean focusFirstUnit() {
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
        int num = -1;
        for (int i = 0; i < units.length; i++) {
            if (unitCurrent == units[i]) {
                num = i;
                break;
            }
        }

        //такого юнита вообще не нашли - выходим
        if (num == -1) {
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
    public boolean isAllUnitsPlayed() {
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

    public int getUnitsArrLength() {
        return units.length;
    }

    //возвращяет порядковый номер юнита (начинается с 1), или ошибка -1
    public int getNumUnits(Unit unit) {
        for (int i = 0; i < units.length; i++) {
            if (units[i] == unit) {
                return i + 1;
            }
        }
        return -1;
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
