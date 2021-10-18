package com.interfaces;

import com.game.Util;
import com.units.Unit;

//атакующий в ближнем бою
public interface Attackable {
    char CHAR_ATTACK = '↯';

    int CODE_TOO_FAR = -1;   // слишком далеко, пехотинец не может атаковать дистанционно (как стрелок)
    int CODE_IS_KILLED = -2;   //нельзя атаковать убитого

    //атака
    default int attack(Unit enemy) {

        Unit unit = (Unit)this;

        int damageMin = getDamageMin();
        int damageMax = getDamageMax();
        int damage = Util.random(damageMin, damageMax);

        if(unit.getPosition() != enemy.getPosition()) {
            return CODE_TOO_FAR;
        }
        return attack(enemy, damage);
    }

    default int attack(Unit enemy, int damage) {
        //нельзя атаковать убитого
        if(enemy.isDead()) {
            return CODE_IS_KILLED;
        }

        enemy.subtractionHitPoint(damage);
        return damage;
    }

    //информация об уроне
    default String infoDamage() {
        return String.format("%c%d-%d", CHAR_ATTACK, getDamageMin(), getDamageMax());
    }

    int getDamageMin();
    int getDamageMax();
}
