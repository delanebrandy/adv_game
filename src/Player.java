import java.util.ArrayList;
import java.util.Arrays;

public class Player{
    private final String name;
    private Health health;
    private ArrayList<Item> inventory;
    private int damageBoost;
    private boolean shieldActive;
    private double damageReduction =1;

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
        if (this.shieldActive){
            System.out.println(this.inventory.get(1).getDamage());
            return this.inventory.get(1).getDamage();
        }else {
            return this.inventory.get(0).getDamage() * damageBoost;
        }
    }

    public void setShield(){
        if (getShieldName().equals("Shield")){
            this.damageReduction = 2;
        } else if (getShieldName().equals("Great Shield")){
            this.damageReduction = 2.5;
        }
    }

    public void shieldChange(){
        this.shieldActive = !this.shieldActive;


    }

    public String getActiveShield(){
        if (this.shieldActive){
            return "Shield";
        }else {
            return "None";
        }
    }



    public void useItem(String item){
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

    public void takeDamage(double value){
        if (this.shieldActive){
            this.health.damage(value/this.damageReduction);
        }
        else {this.health.damage(value);
        }
    }

    public double getHealth(){
        return this.health.getHealth();
    }

    public int getItemDamage(String name) {
        Item item = new Item(name);
        return item.getDamage();
    }

    public void replaceWeapon(String itemName){
        if (this.inventory.get(0).getDamage()<(getItemDamage(itemName))){
            this.inventory.set(0, new Item(itemName));
        }else {
            System.out.println("You can't replace your weapon with a weaker one");
        }
            //throw new Exception("You can't replace your weapon with a weaker one");}
    }

    public void replaceWeapon(Item item){
        if (this.inventory.get(0).getDamage()<(item.getDamage())){
            this.inventory.set(0, item);
        }else {
            System.out.println("You can't replace your weapon with a weaker one");
        }
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
