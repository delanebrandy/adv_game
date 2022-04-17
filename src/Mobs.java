/**
 * The Mobs class is used to create mobs.
 * Mobs are the enemies in the game, and have damage.
 * Mobs extend the Entity class.
 */

public class Mobs extends Entity {
    private final int damage;

    public Mobs(String name, int damage, int health) {
        super(name, health);
        this.damage = damage;
    }
    public Mobs(int level) {
        super(null, 40);
        if (level == 1) {
            setEntity("Goblin", 40);
            this.damage = 20;
        }
        else if (level == 2) {
            setEntity("Orc", 60);
            this.damage = 25;

        }
        else if (level == 3) {
            setEntity("Troll", 80);
            this.damage = 30;
        }
        else {
            this.damage = 40;
        }
    }

    public int attack() {
        return this.damage;
    }

}
