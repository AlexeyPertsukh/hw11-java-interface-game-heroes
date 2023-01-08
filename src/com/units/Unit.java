package com.units;

//базовый юнит, прародитель для всех остальных персонажей
public abstract class Unit {
    public static final String DEAD_SYMBOL = "💀";

    public final int maxHitPoint;

    private final String name;
    private final char icon;              //тактический знак для отображения на карте

    private int hitPoint;

    public Unit(String name, int hitPoint, char icon) {
        this.name = name;
        this.icon = icon;
        this.hitPoint = hitPoint;
        this.maxHitPoint = this.hitPoint;
    }


    public String getName() {
        return name;
    }

    public String getNameLowerCase() {
        return name.toLowerCase();
    }

    public char getIcon() {
        return icon;
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

    public String shortInfo() {
        if(isDead()) {
            return shortInfoDead();
        } else {
            return shortInfoAlive();
        }
    }

    protected String shortInfoAlive() {
     return String.format("%c %s", icon, name);
    }

    protected String shortInfoDead() {
        return String.format("%s %s", DEAD_SYMBOL, name);
    }

    //абстрактные методы
    abstract public String infoSkills();

    abstract protected String infoHitPoint();

}
