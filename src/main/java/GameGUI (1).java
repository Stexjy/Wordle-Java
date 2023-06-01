import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;

import static javax.swing.SwingConstants.CENTER;

public class GameGUI extends JFrame {
    private JPanel mainPanel;
    private inputPanel inputPanel;
    public JLabel errorLabel;

    public JLabel instructions;

    public GameGUI() {
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
            errorLabel.setBounds(0, 10, 800, 100);
            mainPanel.add(errorLabel);
        }catch(NullPointerException exc){

        }

        addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
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