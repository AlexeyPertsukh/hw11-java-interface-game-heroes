//Маг - колдует фаерболы издалека(стрелок), и еще лечит
public class Magic extends Shooter implements Medicinable {

    private static final String NAME = "Маг";
    private static final char COAT = '✶';
    private static final int DAMAGE_MIN = 5;
    private static final int DAMAGE_MAX = 10;
    private static final int HIT_POINTS = 75;

    public Magic(int position) {
        super(NAME, HIT_POINTS, position, COAT, DAMAGE_MIN, DAMAGE_MAX);
    }


    @Override
    public String shortInfo() {
        String info= String.format("%s (%s, %s, %s)", infoName(), infoHP(), infoDamage(), infoCure());
        return String.format(MASK_INFO, info);
    }

}
