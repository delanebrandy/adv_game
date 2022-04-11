import javax.swing.*;
import java.awt.*;

public class App extends JFrame{//inheriting JFrame
    private JLabel label;
    private JButton button;
    private JTextField textField;
    private JTextArea mainText;
    private static JTextArea infoArea = new JTextArea(10,30);

    public App(){
        super("Hello World");
        setLayout(new FlowLayout());

        infoArea.setText("Welcome to the Game");
        add(infoArea);

        label = new JLabel("Enter your name: ");
        add(label);
        textField = new JTextField(10);
        add(textField);
        button = new JButton("Click me");
        add(button);


        infoArea.setText("This is an info area");
        add(infoArea);

        setSize(400,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new App();

    }

}