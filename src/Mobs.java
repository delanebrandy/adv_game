public class Mobs {
    private Health health;
    private final String name;
    private int damage;


    public Mobs(String name, int damage, int health) {
        this.name = name;
        this.damage = damage;
        this.health = new Health(health);
    }
    public Mobs(int level) throws Exception {
        if (level == 1) {
            this.name = "Goblin";
            this.damage = 20;
            this.health = new Health(40);
        }
        else if (level == 2) {
            this.name = "Orc";
            this.damage = 30;
            this.health = new Health(60);
        }
        else if (level == 3) {
            this.name = "Troll";
            this.damage = 40;
            this.health = new Health(90);
        }
        else{
            throw new Exception("Invalid level");
        }
    }

    public String getName() {
        return name;
    }

    public int attack() {
        return damage;
    }

    public int getHealth() {
        return health.getHealth();
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void takeDamage(int damage) {
        this.health.damage(damage);
    }
}
