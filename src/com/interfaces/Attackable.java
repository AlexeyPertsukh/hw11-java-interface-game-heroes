package com.interfaces;

import com.game.Util;
import com.units.Unit;

//атакующий в ближнем бою
public interface Attackable {
    char CHAR_ATTACK = '↯';

    int CODE_TOO_FAR = -1;   // слишком далеко, пехотинец не может атаковать дистанционно (как стрелок)
    int CODE_ENEMY_DEAD = -2;   //нельзя атаковать убитого

    //атака
    default int attack(Unit enemy, int myPosition, int enemyPosition) {
        Unit unit = (Unit) this;

        if (myPosition != enemyPosition) {
            return CODE_TOO_FAR;
        }

        return inputRandomDamage(enemy, getDamageMin(), getDamageMax());
    }

    default int inputRandomDamage(Unit enemy, int damageMin, int damageMax) {
        int damagePoint = Util.random(damageMin, damageMax);

        //нельзя атаковать убитого
        if (enemy.isDead()) {
            return CODE_ENEMY_DEAD;
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
