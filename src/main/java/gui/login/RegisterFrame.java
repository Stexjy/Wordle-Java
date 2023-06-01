package gui.login;

import database.DatabaseManager;
import database.exceptions.AlreadyExistsException;
import gui.components.BackButton;
import gui.game.StartGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterFrame extends JFrame {
    private int WIDTH, HEIGHT;

    private JPanel mainPanel;
    private JLabel errorText;
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton submitButton;
    private JButton switchButton;

    public RegisterFrame(){
        WIDTH = 500;
        HEIGHT = 500;

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
        errorText.setBounds(125, 75, 250, 25);
        mainPanel.add(errorText);

        //Username Field
        usernameField = new JTextField();
        usernameField.setBounds(150, 100, 200, 25);
        usernameField.setToolTipText("Username");
        mainPanel.add(usernameField);

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
            try{
                DatabaseManager.getInstance().createUser(usernameField.getText(), emailField.getText(), passwordField.getText());
                dispose();
                new StartGUI();
            }catch(AlreadyExistsException exception){
                errorText.setText("Questo " + exception.getColumn() + " è già in uso");
            }
        });
        mainPanel.add(submitButton);

        //Switch Button
        switchButton = new JButton();
        switchButton.setBounds(250 - 200 / 2, 270, 200, 35);
        switchButton.setText("Switch to Login");
        switchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginFrame();
            }
        });
        mainPanel.add(switchButton);
    }
}