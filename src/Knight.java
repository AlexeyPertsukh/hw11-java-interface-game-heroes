public class Knight extends Soldier {

    private static final String NAME = "Рыцарь";
    private static final char COAT = '◈';
    private static final int DAMAGE_MIN = 30;
    private static final int DAMAGE_MAX = 50;
    private static final int HIT_POINTS = 75;

    public Knight(int position) {
        super(NAME, HIT_POINTS, position, COAT, DAMAGE_MIN, DAMAGE_MAX);
    }

    @Override
    public String shortInfo() {
        String info= String.format("%s (%s, %s)", infoName(), infoHP(), infoDamage());
        return String.format(MASK_INFO, info);
    }
}
