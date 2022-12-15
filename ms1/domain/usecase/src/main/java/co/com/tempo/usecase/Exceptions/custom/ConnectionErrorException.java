package co.com.tempo.usecase.Exceptions.custom;

public class ConnectionErrorException extends RuntimeException{
    public ConnectionErrorException(String message) {
        super(message);
    }
}
