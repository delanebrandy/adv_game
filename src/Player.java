import java.util.ArrayList;
import java.util.Arrays;

public class Player{
    private final String name;
    private Health health;
    private ArrayList<Item> inventory;
    private int damageBoost;

    public Player(){
        this.inventory = new ArrayList<>(Arrays.asList(new Item("Stick"), new Item("EMPTY")));
        this.health = new Health();
        this.name = "Player";
        this.damageBoost = 1;
    }

    public Player(String name){
        this.name = name;
        this.inventory = new ArrayList<>(Arrays.asList(new Item("Stick"), new Item("EMPTY")));
        this.health = new Health();
        this.damageBoost = 1;
    }

    public int attack(){
            return inventory.get(0).getDamage() * damageBoost;
    }
    public int useShield(){
        return inventory.get(1).getDamage();
        //Mob attack reduction.
    }

    public void useItem(String item) throws Exception{
        switch (item) {
            case "Health Potion":
                this.health.heal(20);
                break;
            case "Strength Potion":
                this.damageBoost += 0.5;
                break;
            case "Poison Potion":
                this.health.damage(10);
                break;
            default:
                throw new IllegalArgumentException("Invalid item");
        }
    }

    public void heal(int value){
        this.health.heal(value);
    }
    public void takeDamage(int value){
        this.health.damage(value);
    }

    public int getHealth(){
        return this.health.getHealth();
    }

    public int getItemDamage(String name) {
        Item item = new Item(name);
        return item.getDamage();
    }

    public void replaceWeapon(String item){
        if (this.inventory.get(0).getDamage()<(getItemDamage(item))){
            this.inventory.set(0, new Item(item));
        }else {System.out.println("You can't replace your weapon with a weaker one");}
    }

    public ArrayList<Item> getItems(){
        return this.inventory;
    }

    public String getName(){
        return this.name;
    }

    public String getWeaponName(){
        return inventory.get(0).getName();
    }
    public String getShieldName(){
        return inventory.get(1).getName();
    }
    public int getDamage(int slot) {
        return inventory.get(slot).getDamage();
    }
    public String getEffect() {
        return inventory.get(1).getEffect();
    }
    public int getDamageBoost() {
        return damageBoost;
    }


    public boolean hasShield() {
        return !inventory.get(1).getName().equals("EMPTY");
    }

    public String toString(){
        return this.name + "," +this.inventory.get(0).getName() + "," +this.inventory.get(0).getDamage() + "," + this.damageBoost;
    }


}
