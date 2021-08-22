public class Tower extends Bastion implements DistanceAttackable {

    private static final String NAME = "Башня";
    private static final char COAT = '▲';
    private static final int DAMAGE_MIN = 15;
    private static final int DAMAGE_MAX = 30;
    private static final int HIT_POINTS = 80;

    public Tower(int position) {
        super(NAME, HIT_POINTS, position, COAT, DAMAGE_MIN, DAMAGE_MAX);
    }


    @Override
    public String shortInfo() {
        if (isDead()) {
            return shortInfoDead();
        }
        String info= String.format("%s (%s, %s)", infoUnit(), infoHP(), infoDamage());
        return String.format(MASK_INFO, info);
    }
}
