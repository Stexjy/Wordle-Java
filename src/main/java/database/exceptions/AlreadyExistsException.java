package database.exceptions;

public class AlreadyExistsException extends Exception {
    private String column, value;

    public AlreadyExistsException(String column, String value){
        this.column = column;
        this.value = value;
    }

    public String getColumn(){
        return column;
    }

    public String getValue(){
        return value;
    }
}
