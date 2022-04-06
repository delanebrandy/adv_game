import java.util.ArrayList;

public class Player{
    private final String name;
    private Health health;
    private ItemBar itemBar;
    private int damageBoost;
    //private Items items;

    public Player(){
        this.itemBar = new ItemBar();
        this.health = new Health();
        this.name = "Player";
        this.damageBoost = 1;
    }

    public Player(String name){
        this.name = name;
        this.itemBar = new ItemBar();
        this.health = new Health();
        this.damageBoost = 1;
    }

    public int attack(){
        return this.itemBar.attack();
    }
    public int useItem(){
        return  this.itemBar.useItem();

    }

    public String currentItem(){
        return this.items.getCurrentItem();
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

    public void addItem(String item){
        this.items.setItem(item);
    }
    public ArrayList<String> getItems(){
        return this.items.getPlayerItems();
    }

    public void removeItem(int slot){
        this.items.removeItem(slot);
    }
    public void changeSlot(int slot){
        this.items.setSlot(slot);
    }
    public void nextSlot(){
        this.items.incSlot();
    }
    public int getSlot(){
        return this.items.getSlot();
    }


}
