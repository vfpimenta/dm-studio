package vfpimenta.dungeonmasterstudio.exceptions;

public class MissingFieldException extends Exception {
    public MissingFieldException(String field){
        super("Field "+field+" is required!");
    }
}
