package gui.components;

import gui.game.StartGUI;

import javax.swing.*;

public class BackButton extends JButton {
    private JFrame frame;
    public BackButton(JFrame frame){
        super("", new ImageIcon(BackButton.class.getClassLoader().getResource("backButton.png")));

        setBounds(0, 0, 35, 35);
        addActionListener(e -> {
            frame.dispose();
            new StartGUI();
        });
    }
}
