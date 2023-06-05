package gui.components;

import gui.game.GameGUI;
import gui.game.StartGUI;

import javax.swing.*;
import java.awt.*;

public class GameButton extends JButton {
    public GameButton() {
        setForeground(Color.WHITE);
        addActionListener(e -> startGame());
    }

    public void startGame() {
        StartGUI.startGui.setVisible(false);
        StartGUI.startGui.gameGui = new GameGUI();
    }

}
