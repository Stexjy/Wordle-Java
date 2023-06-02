package gui.components;

import game.GameManager;
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
    GridLayout gridLayout = new GridLayout(6,5,10,10);
    int currentRow = 0; int currentColumn = 0;
    JLabel[][] cells = new JLabel[6][5];
    StringBuilder word = new StringBuilder("     ");

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
    }

    public void addToWord() {
        for (int i = 0; i < 5; i++) {
            word.setCharAt(i, cells[currentRow][i].getText().charAt(0));
        }
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

        if(GameManager.getInstance().guess(word.toString())){
            currentRow++;
            currentColumn = 0;
        }
    }

    public void setCellColor(int index, Color color){
        cells[GameManager.getInstance().getRow()][index].setBackground(color);
    }
}