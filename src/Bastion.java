public abstract class Bastion extends Build implements Attackable {
    private final int damageMin;
    private final int damageMax;


    public Bastion(String name, int hintPoint, int position, char coat, int damageMin, int damageMax) {
        super(name, hintPoint, position, coat);
        this.damageMin = damageMin;
        this.damageMax = damageMax;
    }

    @Override
    public int getDamageMin() {
        return damageMin;
    }

    @Override
    public int getDamageMax() {
        return damageMax;
    }

    abstract public String shortInfo(); //абстрактный метод

}
