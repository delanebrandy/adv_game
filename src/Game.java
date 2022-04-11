import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Game extends JFrame {
    private static JTextArea infoArea = new JTextArea(12,43);
    private static int gameMode;
    ArrayList<Boss> bosses = new ArrayList<>();

    public void print(String text){
        infoArea.setText(text);
    }

    public Game() throws Exception{
        super("Dungeon Battle");
        setLayout(new BorderLayout());
        setSize(700,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        infoArea.setBackground(this.getBackground());
        infoArea.setEditable(false);
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);
        infoArea.setFont(new Font("Courier", Font.BOLD, 17));
        panel.add(infoArea);



        this.add(panel, BorderLayout.NORTH);


        print(
            "\nWelcome to the Dungeon Battle!\n\n" +
            "You have been summoned to a land far away where you must fight your way through a dungeon of monsters.\n" +
            "You have been given a big stick lol\n" +
            "You must defeat the monsters, 1 mini boss, and kill the final boss to win the game.\n\nGood luck!!"
        );

        JPanel bottomPanel = new JPanel();

            JLabel label = new JLabel("Whats your name? :)");
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setFont(new Font("Courier", Font.BOLD, 18));
            bottomPanel.add(label);

            JPanel inputPanel = new JPanel();

                JTextField input = new JTextField(25);
                input.setFont(new Font("Courier", Font.PLAIN, 17));
                inputPanel.add(input);

                JButton button = new JButton("Enter");

                button.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        String name = input.getText();
                        if(name.equals("")){
                            print("\nYou must enter a name!");
                        }
                        else{
                            Player player = new Player(name);
                            bottomPanel.setVisible(false);
                            modeSelect(player, bottomPanel);
                        }
                    }
                });

                inputPanel.add(button);

            bottomPanel.add(inputPanel);

        this.add(bottomPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public void modeSelect(Player player, JPanel bottomPanel){
        bottomPanel.removeAll();
        print("\nHi, " + player.getName() +"\n\nWhat difficulty do you want to play on? (Easy, Medium, Hard)");
        JRadioButton r1=new JRadioButton("Easy");
        JRadioButton r2=new JRadioButton("Medium");
        JRadioButton r3 = new JRadioButton("Hard");
        ButtonGroup group = new ButtonGroup();
        group.add(r1);
        group.add(r2);
        group.add(r3);
        bottomPanel.add(r1);
        bottomPanel.add(r2);
        bottomPanel.add(r3);
        JButton button = new JButton("Enter");
        button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(r1.isSelected()){
                    gameMode = 1;
                    print("\nYou have chosen Easy mode.\n\nGood luck!");
                    addBosses();
                }
                else if(r2.isSelected()){
                    gameMode = 2;
                    print("\nYou have chosen Medium mode.\n\nGood luck!");
                    addBosses();
                }
                else if(r3.isSelected()){
                    gameMode = 3;
                    print("\nYou have chosen Hard mode.\n\nGood luck!");
                    addBosses();
                }
                else{
                    print("\nYou must select a difficulty!");
                }
            }
         });

        bottomPanel.add(button);
        bottomPanel.setVisible(true);
    }

    public void addBosses(){

        try {
            Boss b1 = new Boss("Dragon", 25, 200, "Energy Sword");
            Boss b2 = new Boss("Ogre", 40, 200, "Dragonbane");

            bosses.add(b1);
            bosses.add(b2);
        }
        catch(TooManyBosses e){
            System.out.println("Too many bosses");
        }


    }

    public static void main(String[] args) throws Exception{
        new Game();
    }
}
