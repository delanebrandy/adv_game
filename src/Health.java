public class Health {

    private int health;

    public Health(){
        this.health = 100;
    }

    public Health(int healthValue){
        this.health = healthValue;
    }

    public int getHealth() {
        return this.health;
    }

    public void damage(int value){
        this.health -= value;
    }

    public void heal(int value){
        this.health += value;
    }
    public void heal(){
        this.health += 10;
    }
}
