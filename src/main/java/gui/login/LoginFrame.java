package gui.login;

import database.DatabaseManager;
import gui.components.BackButton;
import gui.game.StartGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private final int WIDTH = 500, HEIGHT = 500;

    private JPanel mainPanel;

    private JLabel errorText;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton submitButton;
    private JButton switchButton;

    public LoginFrame(){
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

        //Error Label
        errorText = new JLabel("", SwingConstants.CENTER);
        errorText.setBounds(125, 100, 250, 25);
        mainPanel.add(errorText);

        //Email Field
        emailField = new JTextField();
        emailField.setBounds(150, 140, 200, 25);
        emailField.setToolTipText("Email");
        mainPanel.add(emailField);

        //Password Field
        passwordField = new JPasswordField();
        passwordField.setBounds(150, 185, 200, 25);
        passwordField.setToolTipText("Password");
        mainPanel.add(passwordField);

        //Submit Button
        submitButton = new JButton();
        submitButton.setBounds(250 - 100 / 2, 220, 100, 35);
        submitButton.setText("Submit");
        submitButton.addActionListener(e -> {
            if(!DatabaseManager.getInstance().login(emailField.getText(), passwordField.getText()))
                errorText.setText("Le credenziali inserite sono invalide");
            else {
                dispose();
                new StartGUI();
            }
        });
        mainPanel.add(submitButton);

        //Switch Button
        switchButton = new JButton();
        switchButton.setBounds(250 - 200 / 2, 270, 200, 35);
        switchButton.setText("Switch to Register");
        switchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new RegisterFrame();
            }
        });
        mainPanel.add(switchButton);
    }
}
