package com.units;
//базовый юнит воин
public abstract class Unit {
    public static final char CHAR_HP = '♥';
    public static final String STR_DEAD_SYMBOL = "💀";
    public static final String MASK_INFO = "%-35s";

    public final int HP_MAX;

    private final String name;
    private final char coat;              //тактический знак для отображения на карте

    private int hitPoint;
    private int position;            //позиция по горизонтали

    public Unit(String name, int hitPoint, int position, char coat) {
        this.name = name;
        this.coat = coat;
        this.position = position;
        this.hitPoint = hitPoint;
        HP_MAX = this.hitPoint;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public char getCoat() {
        return coat;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

    public void addHitPoint(int point) {
        hitPoint += point;
        if(hitPoint > HP_MAX) {
            hitPoint = HP_MAX;
        }
    }


    public int getPosition() {
        return position;
    }

    public void kill() {
        hitPoint = 0;
    }

    //убит?
    public boolean isDead() {
        return (hitPoint <= 0);
    }

    //жизнь на максимум?
    public boolean isHitPointMax() {
        return (hitPoint >= HP_MAX);
    }

    public String infoHitPoint() {
        return String.format("%c%d", CHAR_HP, hitPoint);
    }

    public String infoName() {
        return String.format("%c %-9s", coat, getName());
    }

    public String shortInfoDead() {
        String str = String.format("%s %s ", STR_DEAD_SYMBOL, getName());
        return String.format(MASK_INFO, str) + " ";
    }

    @Override
    public String toString() {
        if(isDead()) {
            return shortInfoDead();
        } else {
            return shortInfo();
        }
    }

    //абстрактные методы
    abstract public String shortInfo();

}
