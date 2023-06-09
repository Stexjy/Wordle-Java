package gui.game;

import database.DatabaseManager;
import gui.LeaderboardFrame;
import gui.components.GameButton;
import gui.login.LoginFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class StartGUI extends JFrame {
    private JLabel title;
    public GameGUI gameGui;
    private JPanel mainPanel;
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

        log.addActionListener((ActionEvent e) -> {
            dispose();
            new LoginFrame();
        });

        leaderboard.addActionListener((ActionEvent e) -> {
            dispose();
            new LeaderboardFrame();
        });
    }

    public static StartGUI startGui;
    public static void main(String[] args) {
        new DatabaseManager();
        startGui = new StartGUI();
    }
}