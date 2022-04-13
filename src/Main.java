import java.io.*;
import java.util.*;
import java.util.ArrayList;
import static java.lang.Thread.sleep;


public class Main {
    public static int dungeonPercent = 0;

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
        Boss b1 = new Boss("Dragon", 25, 100, "Energy Sword", 35);
        Boss b2 = new Boss("Ogre", 40, 200, "Dragonbane", 50);

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

        while (p.getHealth() > 0) {

            if (dungeonPercent == 0) {
                bossFight(p, bosses.get(0));
            }
            else if (dungeonPercent == 90) {
                bossFight(p, bosses.get(1));
            }

            randomEvent(p, difficulty);
            System.out.println("\n");
            sleep(1000);

        }
    }


    //Boss fight
    public static void bossFight(Player p, Boss b) {

        fight(p, b);
        p.replaceWeapon(b.getItemDrop());
    }

    public static void fight(Player p, Mobs mob) {
        System.out.println("You encounter a " + mob.getName() + "!");
        System.out.println("You have " + p.getHealth() + " health.");
        System.out.println("You have a " + p.getWeaponName() + ".\n");

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
                        dungeonPercent += 10;
                        System.out.println("You killed the " + mob.getName() + "!");
                        System.out.println("You are now " + dungeonPercent + "% though the dungeon.");
                    }
                    break;
                case "shield":
                    if (p.hasShield()) {
                        System.out.println("You use your shield!");
                        p.setShield();
                        p.shieldChange();

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
            printToFile(p);
            endGame("death");
        }
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

    public static Mobs randomEvent(Player p, int difficulty){
        int BOUND = 101;

        //if the player has a boss drop weapon, the chance of getting a weapon drop is 0%
        if (p.getWeaponName().equals("Energy Sword") || p.getWeaponName().equals("Energy Bow")) {
            BOUND = 91;
        }

        Random rand = new Random();
        int random = rand.nextInt(BOUND);


        //30% chance of finding a health potion
        if (random < 30) {
            p.useItem("Health Potion");
            System.out.println("You found a health potion!\nYou drank it :)\nYou have " + p.getHealth() + " health left.");
        }
        //5% chance of finding a poison potion (-10 HP)
        else if (random < 35) {
            System.out.println("You found a Potion :)!\nYou drank it :)");
            p.useItem("Poison Potion");
            System.out.println("You have " + p.getHealth() + " health left lol.");
        }

        //5% chance of finding a Strength Potion (+0.5 damage boost)
        else if (random < 40) {
            System.out.println("You found a Strength Potion!");
            p.useItem("Strength Potion");
        }
        //45% change of finding a monster
        else if (random < 85) {
            System.out.println("A monster has appeared!");
            fight(p,new Mobs(difficulty));
        }
        //5% change of finding nothing
        else if (random < 90) {
            System.out.println("You found nothing lol.");
        }

        //Weapon chances

        //5% chance of finding an Axe
        else if (random < 95) {
            if ((p.getWeaponName().equals("Axe"))) {
                randomEvent(p, difficulty);}
            else{
                System.out.println("You found an Axe!");
                p.replaceWeapon("Axe");}
                System.out.println("You now do " + p.attack() + " damage.");
        }

        //5% chance of finding a Long Sword
        else if (random < 100) {
        if (p.getWeaponName().equals("Long Sword")) {
                randomEvent(p, difficulty);}
            else{
                System.out.println("You found a Long Sword!");
                p.replaceWeapon("Long Sword");}
        }

        //5% change of finding nothing
        else {
            System.out.println("You found nothing lol.");
        }
        return null;
    }


    //Prints the player's stats to a file
    public static void printToFile(Player p) {
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

    //Reads the player's stats from a file and converts them to a String
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

