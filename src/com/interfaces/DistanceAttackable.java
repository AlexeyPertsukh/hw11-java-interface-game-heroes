package com.interfaces;

import com.units.Unit;

//атакующий дистанционно
public interface DistanceAttackable extends Attackable {
    @Override
    default int attack(Unit enemy, int myPosition, int enemyPosition) {
        return inputRandomDamage(enemy, getDamageMin(), getDamageMax());
    }
}
