/**
 * A simple adventure game where the player fights
 * though a dungeon until they kill the final boss
 *
 * @author  Delane Brandy
 * @version 1.0
 * @since   2022-04-17
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Game extends JFrame {

    private JTextArea infoArea = new JTextArea(12,40);
    JPanel bottomPanel = new JPanel();
    private int gameMode;
    private int eventCounter = 0;
    private static ArrayList<Boss> bosses = new ArrayList<>();
    private Player player;
    private int dungeonPercent = 0;
    private String buttonText = "Use Shield";


    //Sends any text to the infoArea
    public void print(String text){
        infoArea.setText("\n\n"+text);
    }

    //appends ant text to the infoArea
    public void addPrint(String text){
        infoArea.append(text);
    }

    //Prints the player's stats to a file
    public void printToFile() {
        try {
            //prints data to .csv file -- appending or creating a new file if none is found.
            FileWriter fw = new FileWriter("stats.csv", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            out.println(player.toString()+","+dungeonPercent);
            out.close();
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

    //Reads the player's stats from a file and converts them to a String
    public String scoreboardToString(java.util.List<java.util.List<String>> scoreboard){
        StringBuilder sb = new StringBuilder();
        for (java.util.List<String> row : scoreboard) {
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

    //Changes the text on the shield button depending on the player's current status
    public void setButtonText(){
        if (this.buttonText.equals("Use Shield")){
            this.buttonText = "Disable Shield";
        } else {
            this.buttonText = "Use Shield";
        }
    }


    //Creates the GUI and starts the game
    public Game(){
        super("Dungeon Battle");

        ImageIcon icon = new ImageIcon("icon.jpg");
        this.setIconImage(icon.getImage());

        this.setLayout(new BorderLayout());
        this.setSize(700,500);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);


        //Confirm on close
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                String[] ObjButtons = {"Yes","No"};
                int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to exit?","Quit",
                        JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
                if(PromptResult==JOptionPane.YES_OPTION) {
                    try {
                        player.setCompleted(false);
                        printToFile();
                        System.exit(0);
                    }
                    //if player has not yet been created, exit without saving
                    catch (Exception e1) {
                        System.exit(0);
                    }
                }
            }
        });

        this.setResizable(false);
        this.setLocationRelativeTo(null);


        //Sets up bottom panel
        JPanel panel = new JPanel();
        this.infoArea.setBackground(this.getBackground());
        this.infoArea.setEditable(false);
        this.infoArea.setLineWrap(true);
        this.infoArea.setWrapStyleWord(true);

        this.infoArea.setFont(new Font(null, Font.BOLD, 17));
        panel.add(this.infoArea);
        this.add(panel, BorderLayout.NORTH);
        setVisible(true);

        scoreboardDisplay();

    }

    //Creates the scoreboard display and displays it for 20 seconds
    public void scoreboardDisplay(){
        java.util.List<java.util.List<String>> records = new ArrayList<>();
        java.util.List<java.util.List<String>> records2 = new ArrayList<>();

        String display = "Scoreboard:\n\nName, Weapon, Shield, Damage Boost, Completed?, Dungeon %\n\n";
        try {
            BufferedReader br = new BufferedReader(new FileReader("stats.csv")) ;
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(Arrays.asList(values));
            }
            br.close();

            if (records.size() > 3) {
                for(int i = 0; i < 3; i++){
                    records2.add(records.get(records.size() - 1 - i));
                }
                print(display+scoreboardToString(records2));
            }
            else{
                print(display+scoreboardToString(records));
            }
        }
        catch (IOException e) {
            print("No scores yet!");
        }

        Timer timer = new Timer(2000, e -> intro());
        timer.setRepeats(false);
        timer.start();
    }

    //sends intro message to display with name input box.
    public void intro(){
        print(
                "Welcome to the Dungeon Battle!\n\n" +
                        "You have been summoned to a land far away where you must fight your way through a dungeon of monsters.\n" +
                        "You have been given a big stick lol\n" +
                        "You must defeat the monsters, 1 mini boss, and kill the final boss to win the game.\n\nGood luck!!"
        );

        JLabel label = new JLabel("Whats your name? :)");
        label.setFont(new Font("Courier", Font.BOLD, 18));
        this.bottomPanel.add(label);

        JPanel inputPanel = new JPanel();

        JTextField input = new JTextField(25);
        input.setFont(new Font("Courier", Font.PLAIN, 17));
        inputPanel.add(input);

        JButton button = new JButton("Enter");

        button.addActionListener(e -> {
            String name = input.getText();
            //error checking -- if blank then has error pop up
            if(name.equals("")){
                JOptionPane.showMessageDialog(new JFrame(), "You must enter a name!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                print("\nYou must enter a name!");
            }
            else{
                this.player = new Player(name);
                this.bottomPanel.setVisible(false);
                modeSelect(); //moves onto next method if valid input
            }
        });

        inputPanel.add(button);

        this.bottomPanel.add(inputPanel);

        this.add(this.bottomPanel, BorderLayout.CENTER);
    }

    //Game difficulty selector -- sets the class variable gameMode
    public void modeSelect(){
        this.bottomPanel.removeAll();

        print("\nHi, " + this.player.getName() +"\n\nWhat difficulty do you want to play on? (Easy, Medium, Hard)");
        JRadioButton r1 = new JRadioButton("Easy");
        JRadioButton r2 = new JRadioButton("Medium");
        JRadioButton r3 = new JRadioButton("Hard");
        ButtonGroup group = new ButtonGroup();

        group.add(r1);group.add(r2);group.add(r3);

        JButton button = new JButton("Enter");

        button.addActionListener(e -> {
            this.bottomPanel.setVisible(false);
            if(r1.isSelected()){
                this.gameMode = 1;
                print("\nYou have chosen Easy mode.\n\nGood luck!");
                addBosses();
            }
            else if(r2.isSelected()){
                this.gameMode = 2;
                print("\nYou have chosen Medium mode.\n\nGood luck!");
                addBosses();
            }
            else if(r3.isSelected()){
                this.gameMode = 3;
                print("\nYou have chosen Hard mode.\n\nGood luck!");
                addBosses();
            }
            //error checking
            else {
                this.bottomPanel.setVisible(true);
                JOptionPane.showMessageDialog(new JFrame(), "Please select a difficulty!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                print("\nYou must select a difficulty!");
            }

        });

        this.bottomPanel.add(r1);this.bottomPanel.add(r2);this.bottomPanel.add(r3);
        this.bottomPanel.add(button);
        this.bottomPanel.setVisible(true);

    }

    //add bosses to class array
    public void addBosses(){
        Boss b1 = new Boss("Dragon", 25, 100, "Energy Sword", 35);
        Boss b2 = new Boss("Ogre", 40, 200, "Dragonbane", 50);

        bosses.add(b1);
        bosses.add(b2);
        //send game to main dungeon loop -- game setup complete
        dungeon();
    }

    //Sets the display to wait for 1500 milliseconds then moves onto dungeon loop
    public void dungeonWait(String statement){
        print(statement);

        Timer timer = new Timer(1500, e -> dungeon());
        timer.setRepeats(false);
        timer.start();
    }

    //simple display iteration & game progress check
    public void dungeon(){

        if (dungeonPercent >= 100){
            endGame(false);
        }

        if (this.eventCounter == 0){
            print("\nYou enter the dungeon.\n");
        } else {
            print("You proceed deeper into the dungeon");
        }
        this.eventCounter++;

        //Sets the display to wait for 1000 milliseconds then moves onto dungeon loop
        Timer timer = new Timer(1000, e ->
                event());
        timer.setRepeats(false);
        timer.start();
    }


    //Fight event, does not end until player or the mob dies -- recursive loop
    public void fight(Mobs mob) {
        this.bottomPanel.removeAll();

        print("You encounter a " + mob.getName() + "!"
        +"\nYou have " + this.player.getHealth() + " health."
        +"\nYou have a " + this.player.getWeaponName() + ".\n"
        +"\nThe " + mob.getName() + " has " + mob.getHealth() + " health.");

        if (this.player.getHealth() <= 0 ) {
            endGame(true);
        } else if (mob.getHealth() <= 0) {
            print("\nYou defeated the " + mob.getName() + "!");
            this.dungeonPercent += 10;
        }

        addPrint("\nWhat do you want to do? (attack, use shield, run)");
        JButton b1 = new JButton("Attack");
        JButton b2 = new JButton(buttonText);
        JButton b3 = new JButton("Run");

        b1.addActionListener(e -> {

            mob.takeDamage(this.player.attack());
            print("You attack the " + mob.getName() + "!\n"
                    +this.player.attack() + " damage dealt.");

            if (mob.getHealth() > 0) {

                print("The " + mob.getName() + " has " + mob.getHealth() + " health left.");
                this.player.takeDamage(mob.attack());

                print("The " + mob.getName() + " attacks you!\n"
                        +mob.attack() + " damage taken.\n"
                        +"You have " + this.player.getHealth() + " health left.");
                this.bottomPanel.removeAll();
                fight(mob);
            } else {
                this.dungeonPercent += 10;
                dungeonWait("You killed the " + mob.getName() + "!"
                        +"\nYou are now " + this.dungeonPercent  + "% though the dungeon.");

                //if the mob is a boss
                if (mob instanceof Boss) {
                    Boss boss = (Boss) mob; //convert mob to boss
                    this.player.replaceWeapon(boss.getItemDrop());
                    this.player.addShield(new Item("Shield"));
                    print("You found a " + boss.getItemDrop().getName() + " and a shield!");
                    this.bottomPanel.setVisible(false);

                    //if it is the final boss
                    if (boss.getBossNumber() == 2) {
                        this.player.addShield(new Item("Great Shield"));
                        this.player.replaceWeapon(new Item("Dragonbane",50)); //create a new item
                    }

                }
            }
        });

        b2.addActionListener(e -> {
            try {
                this.player.setShield();
                this.player.shieldChange();
                setButtonText();
                fight(mob);
            }
            //if the player has no shield, display error message
            catch (Exception e1) {
                JOptionPane.showMessageDialog(new JFrame(), "You dont have a shield!", "lol",
                        JOptionPane.ERROR_MESSAGE);
            }
            //refresh display so the button text changes
            SwingUtilities.updateComponentTreeUI(this.bottomPanel);
        });

        b3.addActionListener(e -> {
            this.bottomPanel.setVisible(false);
            dungeonWait("You run away from the " + mob.getName() + "!");

        });

        this.bottomPanel.add(b1);
        this.bottomPanel.add(b2);
        this.bottomPanel.add(b3);
        //id mob is boss, then don't allow run function
        if (mob instanceof Boss) {
            this.bottomPanel.remove(b3);
            SwingUtilities.updateComponentTreeUI(this.bottomPanel);
        }
        this.bottomPanel.setVisible(true);
    }

    //Generates a random event and displays it
    public void event(){
        int BOUND = 101;

        //if the player has a boss drop weapon, the chance of getting a weapon drop is 0%
        if (this.player.getWeaponName().equals("Energy Sword") || this.player.getWeaponName().equals("Energy Bow")) {
            BOUND = 91;
        }

        Random rand = new Random();
        int random = rand.nextInt(BOUND);

        //if the dungeonPercent is 50% or 90%  the event will be a boss fight
        if (this.dungeonPercent == 50) {fight(bosses.get(0));}
        else if (this.dungeonPercent == 90) {fight(bosses.get(1));}

        //30% chance of finding a health potion
        else if (random < 25) {
            this.player.useItem("Health Potion");
            dungeonWait("You found a health potion!\nYou drank it :)\nYou have " + this.player.getHealth() + " health left.");
        }
        //5% chance of finding a poison potion (-10 HP)
        else if (random < 30) {
            this.player.useItem("Poison Potion");
            if (player.getHealth()>0) {
                dungeonWait("You found a Potion :)!\nYou drank it :)" + "\nYou have " + this.player.getHealth() + " health left lol.");
            } else {
                endGame(true);}
            }

        //5% chance of finding a Strength Potion (+0.5 damage boost)
        else if (random < 35) {
            this.player.useItem("Strength Potion");
            dungeonWait("You found a Strength Potion!");
        }
        //45% change of finding a monster
        //Generates a mob (with a difficulty level) and sends it to the fight method
        else if (random < 85) {
            print("A monster has appeared!");
            fight(new Mobs(this.gameMode));
        }
        //5% change of finding nothing
        else if (random < 90) {
            dungeonWait("You found nothing lol.");
        }

        //Weapon chances

        //5% chance of finding an Axe
        else if (random < 95) {
            if ((this.player.getWeaponName().equals("Axe"))) {
                event();}
            else{
                try {
                    this.player.replaceWeapon("Axe");
                    dungeonWait("You found an Axe!"+"\nYou now do " + this.player.attack() + " damage.");
                } catch (Exception e) {
                    dungeonWait("You found an Axe!\nBut you cant replace your weapon with this one.");}
                }
        }

        //5% chance of finding a Long Sword
        else if (random < 100) {
            if (this.player.getWeaponName().equals("Long Sword")) {
                event();}
            else{
                try {
                    this.player.replaceWeapon("Long Sword");
                    dungeonWait("You found a Long Sword!"+"\nYou now do " + this.player.attack() + " damage.");
                }
                catch (Exception e) {
                    dungeonWait("You found a Long Sword\nBut you cant replace your weapon with this one.");}
            }
        }
    }

    //ends the game and gives different options depending on the type of ending
    public void endGame(boolean mode) {
        if (mode) {
            JOptionPane.showMessageDialog(new JFrame(), "You have died\nGame Over :)", "You Died",
                    JOptionPane.INFORMATION_MESSAGE);
            printToFile();
            System.exit(0);
        }else {
            String[] ObjButtons = {"Keep Playing","New Game", "Quit"};
            int PromptResult = JOptionPane.showOptionDialog(null,"You got through the dungeon\nDo you want to play again :)","You Won",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
            if(PromptResult==0) {
                JOptionPane.getRootFrame().dispose();
            } else if (PromptResult==1) {
                printToFile();
                dispose();
                new Game();
            } else if (PromptResult==2) {
                printToFile();
                System.exit(0);
            }
        }
    }

    public static void main(String[] args){
        new Game();
    }
}
