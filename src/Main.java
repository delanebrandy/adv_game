import java.io.*;
import java.util.*;
import javax.swing.*;

import static java.lang.Thread.sleep;


public class Main{

    public static void main(String[] args) throws Exception {

        printScoreboard(); //Prints score board
        String playerName = intro(); // Get player name & game into
        Player player = new Player(playerName); // create a new player

        System.out.println("\nWhat difficulty do you want to play on? (Easy, Medium, Hard)");

        Scanner input = new Scanner(System.in);
        String difficulty = input.nextLine();

        int difficultyInt;

        if (difficulty.equalsIgnoreCase("Easy")) {difficultyInt = 1;}
        else if (difficulty.equalsIgnoreCase("Medium")) {difficultyInt = 2;}
        else if (difficulty.equalsIgnoreCase("Hard")) {difficultyInt = 3;}
        else {difficultyInt = 1;}

        //Bosses
        ArrayList<Boss> bosses = new ArrayList<>();
        Boss b1 = new Boss("Dragon", 25, 200, "Energy Sword");
        Boss b2 = new Boss("Ogre", 40, 200, "Dragonbane");

        bosses.add(b1);
        bosses.add(b2);

        dungeon(player, bosses, difficultyInt);

    }

    public static String intro() {
        String welcomeMessage =
        ("\n----------------------------------------------------"
        +"\nWelcome to the game!"
        +"\nYou have been summoned to a land far away where you must fight your way through a dungeon of monsters."
        +"\nYou have been given a big stick lol"
        +"\nYou must defeat the monsters (2 mini bosses) and kill the final boss to win the game."
        +"\nGood luck!"
        +"\n----------------------------------------------------\n"
        +"\nWhat is your name? ");

        System.out.println(welcomeMessage);

        Scanner nameInput = new Scanner(System.in);
        return nameInput.nextLine();
    }

    public static void printScoreboard() {
        System.out.println("\n----------------------------------------------------");
        System.out.println("Scoreboard:");

        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("stats.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(Arrays.asList(values));
            }
            System.out.println(scoreboardToString(records));
        }
        catch (IOException e) {
            System.out.println("No scores yet!");
        }

        System.out.println("----------------------------------------------------\n");
    }

    public static void dungeon(Player p, ArrayList<Boss> bosses, int difficulty) throws Exception {

        System.out.println("You enter the dungeon.\n");
        int dungeonPercent = 0;

        while (p.getHealth() > 0) {

            if (dungeonPercent == 50) {
                dungeonPercent += bossFight(p, bosses.get(0), dungeonPercent);
            }
            else if (dungeonPercent == 90) {
                dungeonPercent += bossFight(p, bosses.get(1), dungeonPercent);
            }

            Mobs mob = randomEvent(p, difficulty);
            System.out.println("\n");
            sleep(1000);


            if (mob != null) {
                System.out.println("You encounter a " + mob.getName() + "!");
                System.out.println("You have " + p.getHealth() + " health.");
                System.out.println("You have a " + p.getWeaponName() + ".\n");

                System.out.println("What do you do? (Run or fight)");

                Scanner input = new Scanner(System.in);
                String choice = input.nextLine();

                if (choice.equals("fight") || choice.equals("attack")) {
                    dungeonPercent += fight(p, mob, dungeonPercent);
                } else if (choice.equals("run")) {
                    System.out.println("You run away from the " + mob.getName() + ".\n");
                } else {
                    System.out.println("Invalid input. Input taken as run\n");
                }
            }
        }
    }


    //Boss fight
    public static int bossFight(Player p, Boss b, int dungeonPercent) {
        System.out.println("\nYou encounter a " + b.getName() + "!");
        System.out.println("The " + b.getName() + " has " + b.getHealth() + " health.");
        System.out.println("The " + b.getName() + " has " + b.getDamage() + " damage.");


        dungeonPercent += (fight(p, b, dungeonPercent));
        //returns not different dungeon percent than usual (10)
        return dungeonPercent;
    }

    public static int fight(Player p, Mobs mob, int dungeonPercent) {

        System.out.println("\nThe " + mob.getName() + " has " + mob.getHealth() + " health.");
        label:
        while (mob.getHealth() > 0 && p.getHealth() > 0) {

            System.out.println("What do you want to do? (attack, use shield, run)");
            Scanner input = new Scanner(System.in);
            String choice = input.nextLine();

            switch (choice) {
                case "attack":
                    mob.takeDamage(p.attack());
                    System.out.println("You attack the " + mob.getName() + "!");
                    System.out.println(p.attack() + " damage dealt.");

                    if (mob.getHealth() > 0) {
                        System.out.println("The " + mob.getName() + " has " + mob.getHealth() + " health left.");
                        p.takeDamage(mob.attack());
                        System.out.println("The " + mob.getName() + " attacks you!");
                        System.out.println(mob.attack() + " damage taken.");
                        System.out.println("You have " + p.getHealth() + " health left.\n");
                    } else {
                        System.out.println("You killed the " + mob.getName() + "!");
                        System.out.println("You are now " + (dungeonPercent + 10) + "% though the dungeon.");
                        return 10;
                    }
                    break;
                case "use shield":
                    if (p.hasShield()) {
                        p.useShield();
                    } else {
                        System.out.println("You don't have a shield.");
                    }
                    break;
                case "run":
                    System.out.println("You run away.");
                    break label;
                default:
                    System.out.println("Invalid input.");
                    break;
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
            System.out.println("You died.\nGame Over.");
        } else if (mode.equals("win")) {
            System.out.println("You Won!");
        } else {
            throw new IllegalArgumentException("Invalid mode");
        }
        System.exit(0);
    }

    public static Mobs randomEvent(Player p, int difficulty) throws Exception{
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
                randomEvent(p, difficulty);}
            else{
                System.out.println("You found an Axe!");
                p.replaceWeapon("Axe");}
                System.out.println("You now do " + p.attack() + " damage.");
        }
        //45% change of finding a monster
        else if (random < 80) {
            System.out.println("A monster has appeared!");
            return new Mobs(difficulty);
        }

        //5% chance of finding a Long Sword
        else if (random < 85) {
        if (p.getWeaponName().equals("Long Sword")) {
                randomEvent(p, difficulty);}
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
        System.out.println("Saving game...\n");
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

    public static String scoreboardToString(List<List<String>> scoreboard){
               StringBuilder sb = new StringBuilder();
               for (List<String> row : scoreboard) {
                   for (String col : row) {
                       if (row.indexOf(col) == row.size() - 1) {
                           sb.append(col);
                       } else {
                           sb.append(col).append(", ");
                       }
                   }
                   sb.append("\n");
               }
               return sb.toString();
    }
}
