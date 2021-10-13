package com.interfaces;

import com.game.Util;
import com.units.Unit;

//атакующий дистанционно
public interface DistanceAttackable extends Attackable {
    @Override
    default int attack(Unit enemy) {
        int damageMin = getDamageMin();
        int damageMax = getDamageMax();
        int damage = Util.random(damageMin, damageMax);

        return attack(enemy, damage);
    }
}
