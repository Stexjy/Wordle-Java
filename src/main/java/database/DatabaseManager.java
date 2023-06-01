package database;

import database.exceptions.AlreadyExistsException;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.UUID;

public class DatabaseManager {
    private static DatabaseManager instance;
    Connection connection = null;

    public DatabaseManager(){
        instance = this;
        setup();
    }

    private void setup(){
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/wordle?" +
                    "user=root");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createUser(String username, String email, String password) throws AlreadyExistsException {
         alreadyExists("username", username);
         alreadyExists("email", email);

         final String id = UUID.randomUUID().toString();

         try{
             final String sql = "INSERT INTO users (id, username, email, password) VALUES (?, ?, ?, ?)";
             final PreparedStatement preparedStatement = connection.prepareStatement(sql);
             preparedStatement.setString(1, id);
             preparedStatement.setString(2, username);
             preparedStatement.setString(3, email);
             preparedStatement.setString(4, password);

             preparedStatement.execute();

             new User(id, username, email);
         }catch(SQLException e){
             throw  new RuntimeException(e);
         }
    }

    public boolean login(String email, String password){
        String sql = "SELECT * FROM users WHERE email= '" + email + "'";
        ResultSet result;

        try {
            result = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(sql);

            result.last();
            if(result.getRow() == 0)
                return false;

            if(!password.equals(result.getString("password")))
                return false;

            final String id = result.getString("id");
            final String username = result.getString("username");

            new User(id, username, email);

            return true;
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void alreadyExists(String column, String value) throws AlreadyExistsException {
        String sql = "SELECT id FROM users WHERE " + column + "= '" + value + "'";
        ResultSet result;

        try {
            result = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(sql);

            result.last();
            if(result.getRow() != 0){
                throw new AlreadyExistsException(column, value);
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public String getUsername(String id){
        String sql = "SELECT username FROM users WHERE id= '" + id + "'";
        ResultSet result;

        try {
            result = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(sql);

            result.last();
            if(result.getRow() == 0)
                return null;

            return result.getString("username");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getWins(String id){
        String sql = "SELECT wins FROM wins WHERE id= '" + id + "'";
        ResultSet result;

        try {
            result = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(sql);

            result.last();
            if(result.getRow() == 0)
                return 0;

            return result.getInt("wins");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addWin(String id){
        final int wins = getWins(id) + 1;

        final String sql = "INSERT INTO wins (id, wins) VALUES (?, ?) ON DUPLICATE KEY UPDATE id = ?, wins = ?";

        try {
            final PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setInt(2, wins);
            preparedStatement.setString(3, id);
            preparedStatement.setInt(4, wins);

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public LinkedHashMap<String, Integer> getLeaderboard(){
        final LinkedHashMap<String, Integer> leaderboard = new LinkedHashMap<>();

        String sql = "SELECT * FROM wins ORDER BY wins DESC";
        ResultSet result;
        try{
            result = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(sql);

            while(result.next()){
                final String id = result.getString("id");
                final String username = getUsername(id);
                final int wins = result.getInt("wins");

                leaderboard.put(username, wins);
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }

        return leaderboard;
    }

    public static DatabaseManager getInstance() {
        return instance;
    }
}
