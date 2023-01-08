package com.units;

import com.interfaces.DistanceAttackable;

//Стрелок - атакует дистанционно, с любого расстояния
public abstract class Shooter extends Soldier implements DistanceAttackable {

    public Shooter(String name, int hintPoint, char coat, int damageMin, int damageMax) {
        super(name, hintPoint, coat, damageMin, damageMax);
    }

}
