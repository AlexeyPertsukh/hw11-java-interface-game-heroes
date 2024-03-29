package com.units;

import com.interfaces.Medicinable;

/*
Маг - колдует фаерболы издалека(стрелок), и еще лечит
Реализует больше интерфейсов, чем кто-либо другой
*/

public class Magic extends Shooter implements Medicinable {

    private static final String NAME = "Маг";
    private static final char COAT = '▣';
    private static final int DAMAGE_MIN = 5;
    private static final int DAMAGE_MAX = 10;
    private static final int HIT_POINT = 75;

    public Magic() {
        super(NAME, HIT_POINT, COAT, DAMAGE_MIN, DAMAGE_MAX);
    }

    @Override
    public String infoSkills() {
        return String.format("%s, %s, %s", infoHitPoint(), infoDamage(), infoCure());
    }

}
