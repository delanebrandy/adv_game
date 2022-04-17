/**
 * The Entity class is the base class for all character objects in the game.#
 * The Entity class contains all the basic properties of an entity, such as health and name.
 * It also contains the basic methods for taking damage and attacking.
 */

public class Entity {

    private Health health;
    private String name;

    public Entity(String name, int health) {
        this.name = name;
        this.health = new Health(health);
    }

    public Entity(String name) {
        this.name = name;
        this.health = new Health();
    }


    //Setters and getters
    public void setEntity(String name, int health) {
        this.name = name;
        this.health.setHealth(health);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHealth(int health) {
        this.health.setHealth(health);
    }

    public String getName() {
        return name;
    }

    //returns the health of the entity, if the entity is dead, returns 0
    public double getHealth() {
        if (this.health.getHealth() > 0) {
            return this.health.getHealth();
        } else {
            return 0;
        }
    }

    public void takeDamage(double value) {
        this.health.damage(value);
    }

    public void damage(double value) {
        this.health.damage(value);
    }

    public void heal(int heal) {
        this.health.heal(heal);
    }


}
