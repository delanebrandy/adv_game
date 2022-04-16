import java.util.ArrayList;
import java.util.Arrays;

public class Player extends Entity{

    private ArrayList<Item> inventory;
    private int damageBoost;
    private boolean shieldActive;
    private double damageReduction =1;
    private boolean completed;

    public Player(){
        super("Player");
        this.inventory = new ArrayList<>(Arrays.asList(new Item("Stick"), new Item("EMPTY")));
        this.damageBoost = 1;
    }

    public Player(String name){
        super(name);
        this.inventory = new ArrayList<>(Arrays.asList(new Item("Stick"), new Item("EMPTY")));
        this.damageBoost = 1;

    }

    public int attack(){
        if (this.shieldActive){
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

    public void addShield(Item shield){
        this.inventory.set(1, shield);
    }

    public void useItem(String item){
        switch (item) {
            case "Health Potion":
                this.heal(20);
                break;
            case "Strength Potion":
                this.damageBoost += 0.5;
                break;
            case "Poison Potion":
                this.takeDamage(10);
                break;
            default:
                throw new IllegalArgumentException("Invalid item");
        }
    }

    public void takeDamage(double value){
        if (this.shieldActive){
            this.damage(value/this.damageReduction);
        }
        else {this.damage(value);
        }
    }

    public int getItemDamage(String name) {
        Item item = new Item(name);
        return item.getDamage();
    }

    public void replaceWeapon(String itemName) throws Exception {
        if (this.inventory.get(0).getDamage()<(getItemDamage(itemName))){
            this.inventory.set(0, new Item(itemName));
        }else {
            throw new Exception("You can't replace your weapon with a weaker one");
        }
    }


    public void replaceWeapon(Item item) {
        if (this.inventory.get(0).getDamage()<(item.getDamage())){
            this.inventory.set(0, item);
        }
    }

    public ArrayList<Item> getItems(){
        return this.inventory;
    }

    public String getWeaponName(){
        return inventory.get(0).getName();
    }
    public String getShieldName(){
        return inventory.get(1).getName();
    }

    public boolean hasShield() {
        return !inventory.get(1).getName().equals("EMPTY");
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String toString(){
        return this.getName() + "," +this.inventory.get(0).getName() + "," + this.inventory.get(1).getName() + "," + this.damageBoost + "," + this.completed;
    }



}
