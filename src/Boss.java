public class Boss extends Mobs {
    public static int counter = 0; //counter for the boss number
    public final int bossNumber;
    private final String itemDrop;

    public Boss(String name, int damage, int health, String itemDrop) throws TooManyBosses{
        super(name, damage, health);

        if (counter > 2) {
            throw new TooManyBosses();
        }

        this.bossNumber = counter++;
        this.itemDrop = itemDrop;

    }
}
