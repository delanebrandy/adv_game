import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        Player p = new Player();

        p.addItem("Energy Sword");
        p.nextSlot();

        Mobs m = new Mobs("Goblin", 10, 100);
        m.takeDamage(p.attack());
        System.out.println(m.getHealth());

    }
}
