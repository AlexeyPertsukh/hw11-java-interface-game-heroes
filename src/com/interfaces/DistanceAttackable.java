package com.interfaces;

import com.game.My;
import com.units.Unit;

//атакующий дистанционно
public interface DistanceAttackable extends Attackable {
    @Override
    default int attack(Unit enemy) {
        int damageMin = getDamageMin();
        int damageMax = getDamageMax();
        int damage = My.random(damageMin, damageMax);

        return attack(enemy, damage);
    }
}
