package com.units;

//базовый юнит, прародитель для всех остальных персонажей
public abstract class Unit {
    public static final char CHAR_HP = '♥';
    public static final String STR_DEAD_SYMBOL = "💀";

    public final int maxHitPoint;

    private final String name;
    private final char coat;              //тактический знак для отображения на карте

    private int hitPoint;
    private int position;            //позиция по горизонтали

    public Unit(String name, int hitPoint, int position, char coat) {
        this.name = name;
        this.coat = coat;
        this.position = position;
        this.hitPoint = hitPoint;
        this.maxHitPoint = this.hitPoint;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public String getNameLowerCase() {
        return name.toLowerCase();
    }

    public char getCoat() {
        return coat;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    private void setHitPoint(int hitPoint) {
        if(hitPoint > maxHitPoint) {
            hitPoint = maxHitPoint;
        } else if (hitPoint < 0){
            hitPoint = 0;
        }
        this.hitPoint = hitPoint;
    }

    public void additionHitPoint(int point) {
        setHitPoint(hitPoint + point);
    }

    public void subtractionHitPoint(int point) {
        setHitPoint(hitPoint - point);
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
        return (hitPoint >= maxHitPoint);
    }

    protected String infoHitPoint() {
        return String.format("%c%d", CHAR_HP, hitPoint);
    }

    public String shortInfo() {
        if(isDead()) {
            return shortInfoDead();
        } else {
            return shortInfoAlive();
        }
    }

    protected String shortInfoAlive() {
     return String.format("%c %s", coat, name);
    }

    protected String shortInfoDead() {
        return String.format("%s %s", STR_DEAD_SYMBOL, name);
    }

    //абстрактные методы
    abstract public String infoSkills();

}
