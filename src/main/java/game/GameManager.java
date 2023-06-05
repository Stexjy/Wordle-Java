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

    private StringBuilder corretta;
    private HashMap<Character, Integer> presenze = new HashMap<>();
    private HashMap<Character, Integer> tempRip;

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
            final char c = wordToGuess.charAt(i);
            int r = presenze.get(c) == null ? 0 : presenze.get(c);
            presenze.put(c, r + 1);
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

    private void checkCorrectlyPlacedLetters(String guess){
        corretta = new StringBuilder(wordToGuess);
        tempRip = new HashMap<>();

        for (int i = 0; i < wordToGuess.length(); i++) {
            if (guess.charAt(i) == wordToGuess.charAt(i)) {
                final char c = wordToGuess.charAt(i);
                int rip = tempRip.get(c) == null ? 0 : tempRip.get(c);

                StartGUI.startGui.gameGui.inputPanel.setCellColor(i, Color.GREEN);
                corretta.setCharAt(i, ' ');
                tempRip.put(c, rip + 1);
            }
        }
    }

    private void checkIncorrectlyPlacedLetters(String guess) {
        for(int i = 0; i < wordToGuess.length(); i++){
            if(corretta.charAt(i) == ' ') continue;
            if(corretta.toString().indexOf(guess.charAt(i)) == -1) continue;

            final char c = guess.charAt(i);
            int rip = tempRip.get(c) == null ? 0 : tempRip.get(c);
            
            if(rip < presenze.get(c)){
                StartGUI.startGui.gameGui.inputPanel.setCellColor(i, Color.YELLOW);
                tempRip.put(c, rip + 1);
            }
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
