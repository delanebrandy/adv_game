public class Mobs {
    private Health health;
    private String name;
    private int damage;


    public Mobs(String name, int damage, int health) {
        this.name = name;
        this.damage = damage;
        this.health = new Health(health);
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public int getHealth() {
        return health.getHealth();
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void takeDamage(int damage) {
        this.health.damage(damage);
    }
}
