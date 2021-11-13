package com.interfaces;

import com.game.Util;
import com.units.Unit;

//атакующий в ближнем бою
public interface Attackable {
    char CHAR_ATTACK = '↯';

    int CODE_TOO_FAR = -1;   // слишком далеко, пехотинец не может атаковать дистанционно (как стрелок)
    int CODE_ATTACK_ON_DEAD = -2;   //нельзя атаковать убитого

    //атака
    default int attack(Unit enemy) {
        Unit unit = (Unit) this;

        if (unit.getPosition() != enemy.getPosition()) {
            return CODE_TOO_FAR;
        }

        return inputRandomDamage(enemy, getDamageMin(), getDamageMax());
    }

    default int inputRandomDamage(Unit enemy, int damageMin, int damageMax) {

        int damagePoint = Util.random(damageMin, damageMax);

        //нельзя атаковать убитого
        if (enemy.isDead()) {
            return CODE_ATTACK_ON_DEAD;
        }

        enemy.subtractionHitPoint(damagePoint);
        return damagePoint;
    }

    //информация об уроне
    default String infoDamage() {
        return String.format("%c%d-%d", CHAR_ATTACK, getDamageMin(), getDamageMax());
    }

    int getDamageMin();
    int getDamageMax();
}
