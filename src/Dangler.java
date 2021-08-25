//тунеядец-шутник ᙢ ☋
public class Dangler extends Man implements Jokable {

    private static final String NAME = "Тунеядец";
    private static final char COAT = '⬦';
    private static final int HIT_POINTS = 15;

    public Dangler(int position) {
        super(NAME, HIT_POINTS, position, COAT);
    }

    @Override
    public String shortInfo() {
        String info= String.format("%s (%s, %s)", infoUnit(), infoHP(), infoJoke());
        return String.format(MASK_INFO, info);
    }
}
