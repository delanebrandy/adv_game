public class Player{

    private Health health;
    private Items items;


    public Player(){
        this.items = new Items();
        this.health = new Health();
    }

    public int attack(){
        return this.items.useItem();
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
