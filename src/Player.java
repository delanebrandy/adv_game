import java.util.ArrayList;

public class Player{
    private final String name;
    private Health health;
    private Item items;

    public Player(){
        this.items = new Items();
        this.health = new Health();
        this.name = "Player";
    }

    public Player(String name){
        this.name = name;
        this.items = new Items();
        this.health = new Health();
    }

    public int attack(){
        return this.items.useItem();
    }
    public void useItem(String item){
        this.items.useItem();
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
