import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.rmi.NotBoundException;

import static java.awt.Color.WHITE;

public class inputPanel extends JPanel {
    GridLayout gridLayout = new GridLayout(6,5,10,10);
    int currentRow = 0; int currentColumn = 0;
    JLabel[][] cells = new JLabel[6][5];
    StringBuilder word = new StringBuilder("     ");

    public void addToWord() {
        for (int i = 0; i < 5; i++) {
            word.setCharAt(i, cells[currentRow][i].getText().charAt(0));
        }
    }

    public inputPanel() {
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
    }

    public void insertText(char c) {
        if (currentColumn <= 4) {
            cells[currentRow] [currentColumn].setText(String.valueOf(Character.toUpperCase(c)));
            currentColumn++;
        } else {
            if (!(currentRow <= 5)) {
                //Fine partita
            }
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
                newThread();
                break;
            }
        }

        if (currentRow < 5 && currentColumn == 5) {
            //BufferedReader fileReader = new BufferedReader(new FileReader("resources\\WordListFive.txt"));
                currentRow++;
                currentColumn = 0;
        }
    }

    public void newThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    tooShort();
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