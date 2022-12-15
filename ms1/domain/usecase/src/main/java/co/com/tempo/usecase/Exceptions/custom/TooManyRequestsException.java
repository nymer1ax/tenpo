package co.com.tempo.usecase.Exceptions.custom;

public class TooManyRequestsException extends RuntimeException{
    public TooManyRequestsException(String message){
        super(message);
    }
}
