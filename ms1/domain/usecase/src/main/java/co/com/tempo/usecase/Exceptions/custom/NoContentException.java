package co.com.tempo.usecase.Exceptions.custom;

public class NoContentException extends RuntimeException{
    public NoContentException(String message) {
        super(message);
    }
}
