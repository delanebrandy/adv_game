public class Boss extends Mobs  {
    public static int counter = 0; //counter for the boss number
    public final int bossNumber;
    private final Item itemDrop;

    public Boss(String name, int damage, int health, String itemDrop, int itemDamage){
        super(name, damage, health);

        this.bossNumber = counter++;
        this.itemDrop = new Item(itemDrop, itemDamage);
    }

    public Item getItemDrop() {
        return itemDrop;
    }

    public int getBossNumber() {
        return bossNumber;
    }
}
