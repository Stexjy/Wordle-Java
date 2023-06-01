package gui;

import database.DatabaseManager;
import gui.login.LoginFrame;

public class Main {
    public static void main(String[] args) {
        new DatabaseManager();
        new LoginFrame();
    }
}
