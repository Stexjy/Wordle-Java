package database;

public class User {
    private static User loggedUser;
    private final String id, username, email;

    public User(String id, String username, String email){
        loggedUser = this;

        this.id = id;
        this.username = username;
        this.email = email;
    }

    public static User getLoggedUser(){
        return loggedUser;
    }

    public String getId(){
        return id;
    }

    public String getUsername(){
        return username;
    }

    public String getEmail(){
        return email;
    }
}
