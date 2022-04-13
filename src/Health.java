public class Health {

    private double health;

    public Health(){
        this.health = 100;
    }

    public Health(int healthValue){
        this.health = healthValue;
    }

    public double getHealth() {
        return this.health;
    }

    public void damage(double value){
        this.health -= value;
    }

    public void heal(int value){
        this.health += value;
    }
    public void heal(){
        this.health += 10;
    }
}
