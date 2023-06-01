import javax.swing.*;
import java.awt.*;

public class StartGUI extends JFrame {
    private JLabel title;
    GameGUI gameGui;
    private JPanel mainPanel;

    private GameButton startGame;
    private JButton log;
    private JPanel buttonsPanel;
    private GameButton startButton;
    private JButton leaderboard;

    public StartGUI() {
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1600,900);
        add(mainPanel);
        buttonsPanel.setLayout(new GridLayout(3,1,20,20));
        setPreferredSize(new Dimension(50,100));
        setLocationRelativeTo(null);
    }

    static StartGUI startGui;
    public static void main(String[] args) {
        startGui = new StartGUI();
    }
}