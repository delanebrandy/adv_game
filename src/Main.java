import java.io.*;
import java.util.*;
import java.util.ArrayList;

import static java.lang.Thread.sleep;


public class Main {

    public static void main(String[] args) throws Exception {
        // Initialize player -- name, health, damage, item
        String playerName = intro(); // Get player name & game into
        Player player = new Player(playerName); // create a new player
        //randomEvent(player);


        //Bosses
        ArrayList<Boss> bosses = new ArrayList<>();
        Boss b1 = new Boss("Dragon", 25, 200, "Energy Sword");
        Boss b2 = new Boss("Ogre", 40, 200, "Dragonbane");

        bosses.add(b1);
        bosses.add(b2);

        dungeon(player, bosses);


    }

    public static String intro() {
        System.out.println("\n----------------------------------------------------");
        System.out.println("Welcome to the game!");
        System.out.println("You have been summoned to a land far away where you must fight your way through a dungeon of monsters.");
        System.out.println("You have been given a big stick lol");
        System.out.println("You must defeat the monsters (2 mini bosses) and kill the final boss to win the game.");
        System.out.println("Good luck!");
        System.out.println("----------------------------------------------------\n");

        System.out.println("What is your name?:");
        Scanner nameInput = new Scanner(System.in);
        return nameInput.nextLine();
    }

    public static void dungeon(Player p, ArrayList<Boss> bosses) throws Exception {
        final String lineBreak = "\n----------------------------------------------------\n";

        System.out.println("You enter the dungeon.");
        int dungeonPercent = 0;

        while (p.getHealth() > 0) {

            if (dungeonPercent == 50) {
                dungeonPercent += bossFight(p, bosses.get(0), dungeonPercent);
            }
            else if (dungeonPercent == 90) {
                dungeonPercent += bossFight(p, bosses.get(1), dungeonPercent);
            }

            System.out.println("\n~~~~~~~~~~~~~~Event Start~~~~~~~~~~~~~~");
            Mobs mob = randomEvent(p);
            sleep(1000);


            if (mob != null) {
                System.out.println(lineBreak);
                System.out.println("You encounter a " + mob.getName() + "!");
                System.out.println("You have " + p.getHealth() + " health.");
                System.out.println("You have a " + p.getWeaponName() + ".");
                System.out.println(lineBreak);

                System.out.println("What do you do? (Run or fight)");

                Scanner input = new Scanner(System.in);
                String choice = input.nextLine();

                if (choice.equals("fight") || choice.equals("attack")) {
                    dungeonPercent += fight(p, mob, dungeonPercent);
                } else if (choice.equals("run")) {
                    System.out.println("~~~~~~~~~~~~~~~Event End~~~~~~~~~~~~~~~\n");
                } else {
                    System.out.println("Invalid input. Input taken as run");
                }
            }
        }
    }

    public static int bossFight(Player p, Boss b, int dungeonPercent) {
        System.out.println("You encounter a " + b.getName() + "!");
        System.out.println("The " + b.getName() + " has " + b.getHealth() + " health.");
        System.out.println("The " + b.getName() + " has " + b.getDamage() + " damage.");

        dungeonPercent += (5 + fight(p, b, dungeonPercent));

        return dungeonPercent;
    }


    public static int fight(Player p, Mobs mob, int dungeonPercent) {
        final String lineBreak = "\n----------------------------------------------------\n";
        final String lineBreak2 = "----------------------------------------------------";

        System.out.println("The " + mob.getName() + " has " + mob.getHealth() + " health.");
        while (mob.getHealth() > 0 && p.getHealth() > 0) {

            System.out.println("What do you want to do? (attack, use shield, run)");
            Scanner input = new Scanner(System.in);
            String choice = input.nextLine();

            if (choice.equals("attack")) {
                mob.takeDamage(p.attack());
                System.out.println("You attack the " + mob.getName() + "!");
                System.out.println(p.attack() + " damage dealt.");
                System.out.println(lineBreak2);

                if (mob.getHealth() > 0) {
                    System.out.println("The " + mob.getName() + " has " + mob.getHealth() + " health left.");
                    p.takeDamage(mob.attack());
                    System.out.println("The " + mob.getName() + " attacks you!");
                    System.out.println(mob.attack() + " damage taken.");
                    System.out.println("You have " + p.getHealth() + " health left.");
                }
                else {
                    System.out.println("You killed the " + mob.getName() + "!");
                    System.out.println("You are now " + (dungeonPercent + 5) + "% though the dungeon.");
                    return 5;
                }
            }

            else if (choice.equals("use shield")) {
                if (p.hasShield()) {
                    p.useShield();
                } else {
                    System.out.println("You don't have a shield.");
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
            printToFile(p, dungeonPercent);
            endGame("death");
        }
        return 0;
    }

    public static void endGame(String mode) {
        if (mode.equals("death")) {
            System.out.println("You died.");
            System.out.println("Game Over.");
            System.exit(0);
        } else if (mode.equals("win")) {
            System.out.println("You win!");
            System.exit(0);
        } else {
            throw new IllegalArgumentException("Invalid mode");
        }
    }

    public static Mobs randomEvent(Player p) throws Exception{
        Random rand = new Random();
        int random = rand.nextInt(101);

        //30% chance of finding a health potion
        if (random < 30) {
            p.useItem("Health Potion");
            System.out.println("You found a health potion!\nYou drank it :)\nYou have " + p.getHealth() + " health left.");
        }

        //5% chance of finding an Axe
        else if (random < 35) {
            if ((p.getWeaponName().equals("Axe"))) {
                randomEvent(p);}
            else{
                System.out.println("You found an Axe!");
                p.replaceWeapon("Axe");}
                System.out.println("You now do " + p.attack() + " damage.");
        }
        //45% change of finding a monster
        else if (random < 80) {
            System.out.println("A monster has appeared!");
            return new Mobs(1);
        }

        //5% chance of finding a Long Sword
        else if (random < 85) {
        if (p.getWeaponName().equals("Long Sword")) {
                randomEvent(p);}
            else{
                System.out.println("You found a Long Sword!");
                p.replaceWeapon("Long Sword");}
        }

        //5% chance of finding a poison potion (-10 HP)
        else if (random < 90) {
            System.out.println("You found a Potion :)!\nYou drank it :)");
            p.useItem("Poison Potion");
            System.out.println("You have " + p.getHealth() + " health left lol.");
        }

        //4% chance of finding a Strength Potion (+0.5 damage boost)
        else if (random < 94) {
            System.out.println("You found a Strength Potion!");
            p.useItem("Strength Potion");
        }

        //6% change of finding nothing
        else {
            System.out.println("You found nothing lol.");
        }
        return null;
    }

    public static void printToFile(Player p, int dungeonPercent) {
        System.out.println("Saving game...");
        try {
            FileWriter fw = new FileWriter("stats.csv", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            out.println(p.toString()+","+dungeonPercent);

            out.close();
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }
}
