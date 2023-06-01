import database.DatabaseManager;
import database.User;
import database.exceptions.AlreadyExistsException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;

public class Main {
    public static void main(String[] args) {
        final InputStreamReader inputStream = new InputStreamReader(System.in);
        final BufferedReader reader = new BufferedReader(inputStream);
        new DatabaseManager();

        while(true){
            final User loggedUser = User.getLoggedUser();
            char c;

            try {
                System.out.println("Vuoi registrarti,  loggare, sapere se sei loggato, o aggiungere una vittoria? 1, 2, 3 o 4");
                c = reader.readLine().charAt(0);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if(c == '1'){
                registra(reader);
            }else if(c == '2'){
                login(reader);
            }else if(c == '3'){
                if(loggedUser == null)
                    System.out.println("Non loggati");
                else
                    System.out.println("Loggati come: " + loggedUser.getUsername() + " e hai: " +
                            DatabaseManager.getInstance().getWins(loggedUser.getId()) + " vittorie");
            }else if(c == '4'){
                DatabaseManager.getInstance().addWin(loggedUser.getId());
            }else{
                LinkedHashMap<String, Integer> leaderboard = DatabaseManager.getInstance().getLeaderboard();
                for(String key : leaderboard.keySet()){
                    System.out.println(key + "    " + leaderboard.get(key));
                }
            }
        }
    }

    private static void registra(BufferedReader reader){
        final String username, email, password;

        try{
            username = reader.readLine();
            email = reader.readLine();
            password = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            DatabaseManager.getInstance().createUser(username, email, password);
        } catch (AlreadyExistsException e) {
            System.out.println("This " + e.getColumn() + " has already been used");
        }
    }

    private static void login(BufferedReader reader){
        final String email, password;

        try{
            email = reader.readLine();
            password = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(!DatabaseManager.getInstance().login(email, password)){
            System.out.println("informazioni inserite non valide");
        }
    }
}
