package com.interfaces;

import com.game.Util;
import com.units.Man;
import com.units.Unit;

//интерфейс лекаря
public interface Medicinable {
    char CHAR_CURE = '+';

    int HEALTH_RESTORE_MIN = 10;
    int HEALTH_RESTORE_MAX = 30;

    int CODE_PATIENT_NO_MAN = -1;   //нельзя лечить не_людей
    int CODE_PATIENT_DEAD = -2;   //нельзя лечить убитого
    int CODE_PATIENT_HP_MAX = -3;   //нельзя лечить при полном здоровье
//    int CODE_IS_THIS = -4;   //нельзя лечить самого себя

    //Лечение
    //возвращает:
    // >0  количество очков здоровья, добавленных юниту
    // <0  код ошибки
    default int cure(Unit patient) {
        //нельзя лечить не живые юниты (напр. здания)
        boolean isMan = patient instanceof Man;
        if (!isMan) {
            return CODE_PATIENT_NO_MAN;

        } else if (patient.isDead()) {    //нельзя лечить убитого
            return CODE_PATIENT_DEAD;

        } else if (patient.isHitPointMax()) {    //если максимум здоровья- больше не добавляем
            return CODE_PATIENT_HP_MAX;
        }

//        if(patient == ((Unit)this)) { //нельзя лечить самого себя
//            return CODE_IS_THIS;
//        }

        int curePoint = Util.random(HEALTH_RESTORE_MIN, HEALTH_RESTORE_MAX);
        patient.additionHitPoint(curePoint);
        return curePoint;
    }

    default String infoCure() {
        return String.format("%c%d-%d", CHAR_CURE, HEALTH_RESTORE_MIN, HEALTH_RESTORE_MAX);
    }
}
