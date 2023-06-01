package gui;

import database.DatabaseManager;
import gui.components.BackButton;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;

public class LeaderboardFrame extends JFrame {
    private final int WIDTH = 500, HEIGHT = 500;

    private JPanel mainPanel;
    private JButton backButton;

    public LeaderboardFrame(){
        setupPanel();

        setVisible(true);
        setSize(WIDTH, HEIGHT);
        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width  - getSize().width) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);
        setContentPane(mainPanel);
    }

    private void setupPanel(){
        mainPanel = new JPanel();
        mainPanel.setLayout(null);

        //Back Button
        mainPanel.add(new BackButton(this));

        //Carica giocatori e vittorie
        final LinkedHashMap<String, Integer> leaderboard = DatabaseManager.getInstance().getLeaderboard();
        for(int i = 0; i < leaderboard.size(); i++){
            //Name Label
            final JLabel nameLabel = new JLabel((String) leaderboard.keySet().toArray()[i]);
            nameLabel.setBounds(50, 50 + i * 50, 500, 50);
            nameLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 25));
            mainPanel.add(nameLabel);

            //Wins Label
            final JLabel winsLabel = new JLabel(leaderboard.values().toArray()[i].toString());
            winsLabel.setBounds(400, 50 + i * 50, 500, 50);
            winsLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 25));
            mainPanel.add(winsLabel);
        }
    }
}
