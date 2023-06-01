package gui.components;

import gui.game.StartGUI;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class InputPanel extends JPanel {
    String wordToGuess;
    GridLayout gridLayout = new GridLayout(6,5,10,10);
    int currentRow = 0; int currentColumn = 0;
    JLabel[][] cells = new JLabel[6][5];
    StringBuilder word = new StringBuilder("     ");

    private static final File file;

    static {
        try {
            file = Paths.get(InputPanel.class.getClassLoader().getResource("WordListFive.txt").toURI()).toFile();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void addToWord() {
        for (int i = 0; i < 5; i++) {
            word.setCharAt(i, cells[currentRow][i].getText().charAt(0));
        }
    }

    public InputPanel() {
        super();
        setLayout(gridLayout);
        int panelWidth = gridLayout.getColumns() * 75 + (gridLayout.getColumns() - 1) * gridLayout.getHgap();
        int panelHeight = gridLayout.getRows() * 75 + (gridLayout.getRows() - 1) * gridLayout.getVgap();
        setPreferredSize(new Dimension(panelWidth, panelHeight));

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                cells[i][j] = new JLabel(" ");
                cells[i][j].setOpaque(true);
                cells[i][j].setBackground(Color.GRAY);
                cells[i][j].setFont(new Font(Font.SERIF,Font.BOLD,50));
                cells[i][j].setForeground(Color.WHITE);
                cells[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                add(cells[i][j]);
            }
        }

        List<String> l;
        try {
            l = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Random random = new Random();
        wordToGuess = l.get(random.nextInt(l.size()));
    }

    public void insertText(char c) {
        if (currentColumn <= 4) {
            cells[currentRow] [currentColumn].setText(String.valueOf(Character.toUpperCase(c)));
            currentColumn++;
        }
    }

    public void removeText() {
        if (currentColumn > 0) {
            currentColumn--;
        }
        cells[currentRow][currentColumn].setText(" ");
    }
    public void nextRow() throws InterruptedException /*throws FileNotFoundException*/ {
        addToWord();

        for (int i = 0; i < 5; i++) {
            if (word.charAt(i) == ' ') {
                //parola corta
                newThread(0);
                break;
            }
        }

        if(wordToGuess.equalsIgnoreCase(word.toString())){
            System.out.println("Vinto");
        }

        if (currentRow < 5 && currentColumn == 5) {
            BufferedReader reader;

            try {
                reader = new BufferedReader(new FileReader(file));

                String readWord = reader.readLine();
                while(readWord != null){
                    if(readWord.equalsIgnoreCase(word.toString())){
                        currentRow++;
                        currentColumn = 0;
                        return;
                    }

                    readWord = reader.readLine();
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            newThread(1);
        }else if(currentRow == 5){
            //todo HAI PERSO
        }
    }

    public void newThread(int i) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(i == 0)
                        tooShort();
                    else
                        notExists();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        thread.start();
    }

    public void tooShort() throws InterruptedException {
        StartGUI.startGui.gameGui.errorLabel.setText("Parola troppo corta");
        popUp();
    }

    public void notExists() throws InterruptedException{
        StartGUI.startGui.gameGui.errorLabel.setText("Parola non esistente");
        popUp();
    }

    public void popUp() throws InterruptedException {
        StartGUI.startGui.gameGui.errorLabel.setVisible(true);
        Thread.sleep(2000);
        StartGUI.startGui.gameGui.errorLabel.setVisible(false);
    }

    public void changeColor() {
        for (int i = 0; i < 5; i++) {

        }
    }
}