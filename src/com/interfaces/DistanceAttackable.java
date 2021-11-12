package com.interfaces;

import com.units.Unit;

//атакующий дистанционно
public interface DistanceAttackable extends Attackable {
    @Override
    default int attack(Unit enemy) {
        return inputRandomDamage(enemy, getDamageMin(), getDamageMax());
    }
}
