//интерфейс лекаря
public interface Medicinable {
    char CHAR_CURE = '+';

    int HEALTH_RESTORE_MIN = 10;
    int HEALTH_RESTORE_MAX = 30;

    int CODE_IS_NO_MAN = -1;   //это не живое существо
    int CODE_IS_KILLED = -2;   //нельзя лечить убитого
    int CODE_IS_FULL = -3;   //нельзя лечить при полном здоровье
    int CODE_IS_THIS = -4;   //нельзя лечить самого себя

    //Лечение
    //возвращает:
    // >0  количество очков здоровья, добавленных юниту
    // <0  код ошибки
    default int cure(Unit patient) {
        int cure = My.random(HEALTH_RESTORE_MIN, HEALTH_RESTORE_MAX);
        //нельзя лечить не живые юниты (напр. здания)
        boolean isMan = patient instanceof Man;
        if(!isMan) {
            return CODE_IS_NO_MAN;
        }
        return cure(patient, cure);
    }

    default int cure(Unit patient, int cure) {
        if(patient.isDead()) {    //нельзя лечить убитого
            return CODE_IS_KILLED;
        }

        if(patient.isHitPointMax()) {    //если максимум здоровья- больше не добавляем
            return CODE_IS_FULL;
        }

//        if(patient == ((Unit)this)) { //нельзя лечить самого себя
//            return CODE_IS_THIS;
//        }

        int hitPoint = patient.getHitPoint() + cure;

        if(hitPoint >= patient.HP_MAX) {
            hitPoint = patient.HP_MAX;
        }

        patient.setHitPoint(hitPoint);
        return cure;
    }

    default String infoCure() {
        return String.format("%c%d-%d", CHAR_CURE, HEALTH_RESTORE_MIN, HEALTH_RESTORE_MAX);
    }
}