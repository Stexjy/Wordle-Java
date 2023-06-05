package game;

import gui.components.InputPanel;

import java.io.*;

class InputCheck {
    private File wordsFile;
    private String wordToGuess, guess;

    public InputCheck(String guess){
        this.wordsFile = new File(InputCheck.class.getClassLoader().getResource("WordListFive.txt").getPath());

        this.wordToGuess = GameManager.getInstance().getWordToGuess();
        this.guess = guess;
    }
    boolean tooShort(){
        return guess.indexOf(' ') != -1;
    }

    boolean exists(){
        final BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(wordsFile));

            String readWord = reader.readLine();
            while(readWord != null){
                if(readWord.equalsIgnoreCase(guess.toString())){
                    return true;
                }

                readWord = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
}
