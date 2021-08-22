//ходок, перемещается в пространстве
public interface Movable {
    String MSG_NO_WAY = "туда ходить нельзя";

    //идти
    default boolean go(int step) {
        Unit unit = (Unit)this;

        if(unit.getPosition() + step > Game.RIGHT_MAP_MAX_POSITION) { //двигаться вправо некуда
            return false;
        }
        else if(unit.getPosition() + step < 0) {   //двигаться влевоЮ некуда
            return false;
        }
        unit.setPosition(unit.getPosition() + step);
        return true;
    }

    //идти вправо
    default boolean goRightOneStep() {
        return go(1);
    }

    //идти влево
    default boolean goLeftOneStep() {
        return go(-1);
    }
}
