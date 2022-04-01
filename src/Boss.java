public class Boss extends Mobs {
    private int bossNumber;
    private String itemDrop;

    public Boss(String name, int damage, int health, int bossNumber, String itemDrop) {
        super(name, damage, health);
        this.bossNumber = bossNumber;
        this.itemDrop = itemDrop;
    }
}
