package gui.game;

import game.GameManager;
import game.GameStatus;
import gui.components.InputPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameGUI extends JFrame {
    private JPanel mainPanel;
    public InputPanel inputPanel;
    public JLabel errorLabel;

    public GameGUI() {
        new GameManager();

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(mainPanel);
        setSize(1600,900);
        setLocationRelativeTo(null);

        //Error Label
        try{
            errorLabel = new JLabel("");
            errorLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 50));
            errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
            errorLabel.setVisible(false);
            errorLabel.setBounds(0, 0, 1600, 200);
            mainPanel.add(errorLabel);
        }catch(NullPointerException exc){

        }

        addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if(GameManager.getInstance().getStatus() == GameStatus.WAIT){
                    dispose();
                    new StartGUI();
                    return;
                }

                if (Character.isLetter(e.getKeyChar())) {
                    inputPanel.insertText(e.getKeyChar());
                } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    inputPanel.removeText();
                } else if (e.getKeyCode() == 10) {
                    try {
                        inputPanel.nextRow();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}

        });

    }
}