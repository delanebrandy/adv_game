import java.io.*;
import java.util.*;
import java.util.ArrayList;


public class Main {

    public static void main(String[] args) throws Exception {
        // Initialize player -- name, health, damage, item
        //String playerName = intro(); // Get player name & game into
        Player player = new Player("delane"); // create a new player
        //randomEvent(player);


        //Bosses
        ArrayList<Boss> bosses = new ArrayList<Boss>();
        Boss b1 = new Boss("Dragon", 25, 1000, "Health Potion");
        Boss b2 = new Boss("Ogre", 30, 1000, "Energy Sword");
        Boss b3 = new Boss("Titan", 40, 1000, "Dragonbane");
        bosses.add(b1);
        bosses.add(b2);
        bosses.add(b3);

        //dungeon(player, bosses);


    }

    public static String intro() {
        System.out.println("Welcome to the game!");
        System.out.println("You have been summoned to a land far away where you must fight your way through a dungeon of monsters.");
        System.out.println("You have been given a big stick lol");
        System.out.println("You must defeat the monsters (2 mini bosses) and kill the final boss to win the game.");
        System.out.println("Good luck!\n");

        System.out.println("What is your name?:");
        Scanner nameInput = new Scanner(System.in);
        return nameInput.nextLine();
    }

    public static void dungeon(Player p, ArrayList<Boss> bosses) throws Exception {
        final String lineBreak = "\n----------------------------------------------------\n";

        System.out.println("You enter the dungeon.");
        int dungeonPercent = 0;

        while (p.getHealth() > 0) {
                //System.out.println("You have " + p.getHealth() + " health.");
/*            if (dungeonPercent == 30) {
                bossFight(p, bosses.get(0));
            }
            else if (dungeonPercent == 60) {
                bossFight(p, bosses.get(1));
            }
            else if (dungeonPercent == 90) {
                bossFight(p, bosses.get(2));
            }*/

            Mobs mob = randomEvent(p);
            if (mob != null) {
                System.out.println(lineBreak);
                System.out.println("You encounter a " + mob.getName() + "!");
                System.out.println("You have " + p.getHealth() + " health.");
                System.out.println("You have a " + p.currentItem() + ".");
                System.out.println(lineBreak);

                System.out.println("What do you do? (Run or fight)");

                Scanner input = new Scanner(System.in);
                String choice = input.nextLine();

                if (choice.equals("1") || choice.equals("fight")) {
                    fight(p, mob, dungeonPercent);
                } else if (choice.equals("2") || choice.equals("run")) {
                    continue;
                }

            }


        }

        endGame("death");
    }

   /* public static void bossFight(Player p, Boss b) {
        System.out.println("You encounter a " + b.getName() + "!");
        System.out.println("The " + b.getName() + " has " + b.getHealth() + " health.");
        System.out.println("The " + b.getName() + " has " + b.getDamage() + " damage.");
        System.out.println("The " + b.getName() + " has " + b.getItem() + ".");

        while (b.getHealth() > 0) {
            System.out.println("What do you want to do? (attack, use item, run)");
            Scanner input = new Scanner(System.in);
            String choice = input.nextLine();

            if (choice == "attack") {
                b.takeDamage(p.attack());
                System.out.println("You attack the " + b.getName() + "!");
                System.out.println("The " + b.getName() + " has " + b.getHealth() + " health.");
            } else if (choice == "use item") {
                System.out.println("What item do you want to use?");
                String item = input.nextLine();
                if (p.getItem(item) == true) {
                    b.takeDamage(p.attack());
                    System.out.println("You attack the " + b.getName() + "!");
                    System.out.println("The " + b.getName() + " has " + b.getHealth() + " health.");
                } else {
                    System.out.println("You don't have that item.");
                }
            } else if (choice == "run") {
                System.out.println("You run away.");
                break;
            } else {
                System.out.println("Invalid input.");
            }
        }
    }*/


    public static void fight(Player p, Mobs mob, int dungeonPercent) throws Exception {
        final String lineBreak = "\n----------------------------------------------------\n";
        final String lineBreak2 = "----------------------------------------------------";
       //System.out.println(lineBreak);

        while (mob.getHealth() > 0 && p.getHealth() > 0) {

            System.out.println("What do you want to do? (attack, use item, run)");
            Scanner input = new Scanner(System.in);
            String choice = input.nextLine();

            if (choice.equals("attack")) {
                mob.takeDamage(p.attack());
                System.out.println("You attack the " + mob.getName() + "!");
                System.out.println(p.attack() + " damage dealt.");
                System.out.println("The " + mob.getName() + " has " + mob.getHealth() + " health left.");
                System.out.println(lineBreak2);

                if (mob.getHealth() > 0) {
                    p.takeDamage(mob.attack());
                    System.out.println("The " + mob.getName() + " attacks you!");
                    System.out.println(mob.attack() + " damage taken.");
                    System.out.println("You have " + p.getHealth() + " health left.");
                }
                else {
                    System.out.println("You killed the " + mob.getName() + "!");
                    dungeonPercent++;
                }
            }

            else if (choice.equals("use item")) {
                System.out.println("What item do you want to use?");
                System.out.println(p.getItems());
                String item = input.nextLine();
                if (p.getItems().contains(item)) {
                    p.changeSlot(p.getItems().indexOf(item));
                    p.useItem(item);
                } else {
                    System.out.println("You don't have that item.");
                }
            }

            else if (choice.equals("run")) {
                    System.out.println("You run away.");
                    break;
                    }
            else {
                System.out.println("Invalid input.");
            }
        }

        if (p.getHealth() <= 0) {
            endGame("death");
        }
        //System.out.println(lineBreak);
    }

    public static void endGame(String mode) {
        if (mode == "death") {
            System.out.println("You died.");
            System.out.println("Game Over.");
            System.exit(0);
        } else if (mode == "win") {
            System.out.println("You win!");
            System.exit(0);
        } else {
            throw new IllegalArgumentException("Invalid mode");
        }
    }

    public static <E> Mobs randomEvent(Player p) throws Exception{

        int random = (int)(Math.random() * 100);

        if (random < 5) {
            if (p.getItems().contains("Health Potion")) {
                randomEvent(p);}
            else {
                System.out.println("You found a health potion!");
                p.addItem("Health Potion");}
        }

        else if (random < 10) {
            if (p.getItems().contains("Axe")) {
                randomEvent(p);}
            else{
                System.out.println("You found an Axe!");
                p.addItem("Axe");}
        }

        else if (random < 75) {
            System.out.println("A monster has appeared!");
            return new Mobs(1);
        }

        else if (random < 80) {
            if (p.getItems().contains("Long Sword")) {
                randomEvent(p);}
            else{
                System.out.println("You found a Long Sword!");
                p.addItem("Long Sword");}
        }
        //3% chance of getting potion (-10 HP)
        else if (random < 83) {
            System.out.println("You found a Potion :)!\n You drank it :)");
            p.takeDamage(10);
        }

        else {
            System.out.println("You found nothing lol.");
        }

        return null;
    }
}
