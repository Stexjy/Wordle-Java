package game;

import database.DatabaseManager;
import database.User;
import gui.components.InputPanel;
import gui.game.StartGUI;

import java.awt.*;
import java.util.HashMap;

public class GameManager {
    private static GameManager instance;
    private AlertManager alertManager;

    private GameStatus status;
    private String wordToGuess;
    private int row;
    public GameManager(){
        instance = this;
        alertManager = new AlertManager();
        setupGame();
    }

    private void setupGame() {
        wordToGuess = Casual_word.GetWord();
        status = GameStatus.RUNNING;
        row = 0;

        // Conta i caratteri ripetuti nella parola corretta
        for (int i = 0; i < wordToGuess.length(); i++) {
            for (int j = i + 1; j < wordToGuess.length(); j++) {
                final char c = wordToGuess.charAt(i);
                if (c == wordToGuess.charAt(j) && c != ' ') {
                    int r = ripetizioni.get(c) == null ? 0 : ripetizioni.get(c);
                    ripetizioni.put(c, r + 1);
                    //corretta.setCharAt(j, ' ');
                }
            }
        }

    }

    //Returns whether the input was valid or not
    public boolean guess(String guess){
        final InputCheck inputCheck = new InputCheck(guess);

        guess = guess.toLowerCase();

        if(inputCheck.tooShort()) {
            alertManager.tooShort();
            return false;
        }
        if(guess.equals(wordToGuess)){
            status = GameStatus.WAIT;

            alertManager.winAlert();

            final User user = User.getLoggedUser();
            if(user != null)
                DatabaseManager.getInstance().addWin(User.getLoggedUser().getId());
        }
        if(!inputCheck.exists()){
            alertManager.notExists();
            return false;
        }

        checkCorrectlyPlacedLetters(guess);
        checkIncorrectlyPlacedLetters(guess);

        row++;

        if(row == 6){
            alertManager.lostAlert();
            status = GameStatus.WAIT;
        }

        return true;
    }

    private StringBuilder corretta;
    private HashMap<Character, Integer> ripetizioni = new HashMap<>();

    private void checkCorrectlyPlacedLetters(String guess){
        corretta = new StringBuilder(wordToGuess);

        for (int i = 0; i < wordToGuess.length(); i++) {
            if (guess.charAt(i) == wordToGuess.charAt(i)) {
                StartGUI.startGui.gameGui.inputPanel.setCellColor(i, Color.GREEN);
                corretta.setCharAt(i, ' ');
            }
        }
    }

    private void checkIncorrectlyPlacedLetters(String guess) {
        int x = 0;
        for (int i = 0; i < wordToGuess.length(); i++) {
            for (int j = 0; j < wordToGuess.length(); j++) {
                if (ripetizioni.containsKey(corretta.charAt(j)) && corretta.charAt(i) != ' ' && corretta.charAt(j) == guess.charAt(i) && x < ripetizioni.get(corretta.charAt(j))) {
                    StartGUI.startGui.gameGui.inputPanel.setCellColor(i, Color.YELLOW);
                    x++;
                }
            }
            x = 0;
        }
    }

    public GameStatus getStatus(){
        return status;
    }

    public String getWordToGuess(){
        return wordToGuess;
    }

    public int getRow(){
        return row;
    }

    public static GameManager getInstance(){
        return instance;
    }
}
