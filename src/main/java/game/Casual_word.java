package game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Casual_word {
    public static String GetWord() {
        String wordFile = "WordListFive.txt";
        String path = Casual_word.class.getClassLoader().getResource(wordFile).getPath().toString();
        String randomWord = null;
        try {
            randomWord = getRandomWord(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
		return randomWord;
    }
    
    public static String getRandomWord(String path) throws IOException {
        List<String> words = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line);
            }
        }
        
        Random random = new Random();
        double randomIndex = random.nextDouble(words.size());
        
        return words.get((int) randomIndex);
    }
}
