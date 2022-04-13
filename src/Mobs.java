public class Mobs {
    private final Health health;
    private final String name;
    private final int damage;


    public Mobs(String name, int damage, int health) {
        this.name = name;
        this.damage = damage;
        this.health = new Health(health);
    }
    public Mobs(int level) {
        if (level == 1) {
            this.name = "Goblin";
            this.damage = 20;
            this.health = new Health(40);
        }
        else if (level == 2) {
            this.name = "Orc";
            this.damage = 25;
            this.health = new Health(60);
        }
        else if (level == 3) {
            this.name = "Troll";
            this.damage = 30;
            this.health = new Health(80);
        }
        else {
            this.name = "Giant";
            this.damage = 40;
            this.health = new Health(100);
        }
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return this.damage;
    }

    public int attack() {
        return this.damage;
    }

    public double getHealth() {
        if (this.health.getHealth() > 0) {
        return this.health.getHealth();
        } else {
            return 0;
        }
    }

    public void takeDamage(int damage) {
        this.health.damage(damage);
    }
}
