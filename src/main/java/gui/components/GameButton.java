package gui.components;

import gui.game.GameGUI;
import gui.game.StartGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameButton extends JButton {
    public GameButton() {
        setForeground(Color.WHITE);
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
    }

    public void startGame() {
        StartGUI.startGui.setVisible(false);
        StartGUI.startGui.gameGui = new GameGUI();
    }

}
