import java.util.Scanner;

public class WordleGame {
    protected static Scanner sc;
    protected static String input;
    protected static String correct;
    protected static StringBuilder corretta;
    protected static String guess;
    protected static int x;
    protected static int index;
    protected static String Nrep;
    protected static int[] Vd;
    protected static String[] vr;

    // Funzione principale del gioco Wordle
    protected static void playWordleGame() {
        corretta = new StringBuilder(correct);
        guess = "";
        x = 0;

        countRepeatedCharacters();

        removeSpaces();

        createNoRepetitionString();

        createRepeatedCharacterCountArray();

        
        // 6 tentativi per indovinare la parola
        for (int round = 0; round < 6; round++) {
            System.out.print("\nInserisci > ");
            guess = sc.nextLine().toLowerCase();

            if (guess.length() != correct.length()) {
                System.out.println("La parola inserita non ha la lunghezza corretta.");
                continue;
            }
	initializeGuessArray();

            checkCorrectlyPlacedLetters();

            checkIncorrectlyPlacedLetters();

            printGuessResult();

            if (guess.equals(correct)) {
                System.out.println("Correct! You win!");
                break;
            }
        }

        if (!guess.equals(correct)) {
            System.out.println("FALLITO! La corretta è: " + correct + ".");
        }
    }

    // Conta i caratteri ripetuti nella parola corretta
    protected static void countRepeatedCharacters() {
        for (int i = 0; i < correct.length(); i++) {
            for (int j = i + 1; j < correct.length(); j++) {
                if (corretta.charAt(i) == corretta.charAt(j) && corretta.charAt(i) != ' ') {
                    corretta.setCharAt(j, ' ');
                    x++;
                }
            }
        }
    }

    // Rimuove gli spazi dalla stringa corretta
    protected static void removeSpaces() {
        index = corretta.indexOf(String.valueOf(' '));
        while (index != -1) {
            corretta.deleteCharAt(index);
            index = corretta.indexOf(String.valueOf(' '));
        }
    }

    // Crea una stringa senza ripetizioni
    protected static void createNoRepetitionString() {
        Nrep = corretta.toString();
    }

    // Crea un array che conta quante volte un carattere viene ripetuto
    protected static void createRepeatedCharacterCountArray() {
        Vd = new int[correct.length() - x];
        x = 0;
        for (int i = 0; i < Nrep.length(); i++) {
            for (int j = 0; j < correct.length(); j++) {
                if (Nrep.charAt(i) == correct.charAt(j)) {
                    x++;
                }
            }
            Vd[i] = x;
            x = 0;
        }
    }

    // Inizializza l'array di indizi
    protected static void initializeGuessArray() {
        corretta.replace(0, corretta.length(), correct);
        vr = new String[correct.length()];
    }

    // Controlla le lettere correttamente posizionate
    protected static void checkCorrectlyPlacedLetters() {
        for (int i = 0; i < correct.length(); i++) {
            if (guess.charAt(i) == correct.charAt(i)) {
                vr[i] = "Si ";
                corretta.setCharAt(i, ' ');
            }
        }
    }

    // Controlla le lettere presenti ma posizionate in modo errato
    protected static void checkIncorrectlyPlacedLetters() {
        x = 0;
        for (int i = 0; i < correct.length(); i++) {
            for (int j = 0; j < correct.length(); j++) {
                if (corretta.charAt(i) != ' ' && corretta.charAt(j) == guess.charAt(i) && x < Vd[Nrep.indexOf(corretta.charAt(j))]) {
                    vr[i] = "Sni ";
                    x++;
                }
            }
            x = 0;
        }
    }

    // Stampa il risultato del tentativo
    protected static void printGuessResult() {
        System.out.println("");
        for (int i = 0; i < correct.length(); i++) {
            System.out.print(vr[i]);
        }
    }
}
