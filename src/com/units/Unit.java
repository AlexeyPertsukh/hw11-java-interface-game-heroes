package com.units;

//базовый юнит воин
public abstract class Unit {
    public static final char CHAR_HP = '♥';
    public static final String STR_DEAD_SYMBOL = "💀";
    public static final String MASK_INFO = "%-35s";

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

    protected String infoName() {
        return String.format("%c %-9s", coat, getName());
    }

    protected String shortInfoDead() {
        String info = String.format("%s %s ", STR_DEAD_SYMBOL, getName());
        return String.format(MASK_INFO, info) + " "; //не убирать пробел! он нужен для ровного цветного вывода убитого юнита
    }

    public String shortInfo() {
        if(isDead()) {
            return shortInfoDead();
        } else {
            return shortInfoAlive();
        }
    }

    //абстрактные методы
    abstract protected String shortInfoAlive();

}
