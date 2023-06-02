package game;

import gui.game.StartGUI;

class AlertManager {
    void tooShort(){
        StartGUI.startGui.gameGui.errorLabel.setText("Parola troppo corta");
        popUp();
    }

    void notExists(){
        StartGUI.startGui.gameGui.errorLabel.setText("Parola non esistente");
        popUp();
    }

    void winAlert(){
        StartGUI.startGui.gameGui.errorLabel.setText("Hai vinto! Premi un tasto per continuare");
        StartGUI.startGui.gameGui.errorLabel.setVisible(true);
    }

    void lostAlert(){
        StartGUI.startGui.gameGui.errorLabel.setText("<html>Hai perso! Premi un tasto per continuare <br> La parola era: " + GameManager.getInstance().getWordToGuess() + "<html>");
        StartGUI.startGui.gameGui.errorLabel.setVisible(true);
    }

    private void popUp() {
        new Thread(() -> {
            try {
                StartGUI.startGui.gameGui.errorLabel.setVisible(true);
                Thread.sleep(2000);
                StartGUI.startGui.gameGui.errorLabel.setVisible(false);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
