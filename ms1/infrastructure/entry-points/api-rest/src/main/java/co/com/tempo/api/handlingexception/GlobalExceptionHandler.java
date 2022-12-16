package co.com.tempo.api.handlingexception;

import co.com.tempo.usecase.Exceptions.Response;
import co.com.tempo.usecase.Exceptions.custom.CustomException;
import co.com.tempo.usecase.Exceptions.custom.NoContentException;
import co.com.tempo.usecase.Exceptions.custom.TooManyRequestsException;
import io.github.bucket4j.Bucket;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.net.ConnectException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    private static final int MAX_REQUESTS_PER_MINUTE = 3;
    private final Bucket bucket;

    public GlobalExceptionHandler(Bucket bucket) {
        this.bucket = bucket;
    }
    @ModelAttribute
    public void rateLimit(HttpServletRequest request) throws TooManyRequestsException {
        // Check if the rate limit has been exceeded
        if (!bucket.tryConsume(MAX_REQUESTS_PER_MINUTE)) {
            throw new TooManyRequestsException("Has superado los 3 request per minute. Intentalo más tarde");
        }
    }

    @ExceptionHandler(TooManyRequestsException.class)
    public ResponseEntity<Response> handleTooManyRequests(TooManyRequestsException ex) {

        Response response = Response.builder()
                .fecha(LocalDateTime.now().toString())
                .codigoResultado("TOO_MANY_REQUEST")
                .descripcionRespuesta(ex.getMessage())
                .endpoint(ex.getClass().getSimpleName())
                .result(Collections.emptyList())
                .build();
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(response);
    }

    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<Response> custom(CustomException ex){
        Response response = Response.builder()
                .fecha(LocalDateTime.now().toString())
                .codigoResultado("BAD_REQUEST")
                .descripcionRespuesta(ex.getMessage())
                .endpoint(ex.getClass().getSimpleName())
                .result(Collections.emptyList())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<Response> custom(RuntimeException ex){

        Response response = Response.builder()
                .fecha(LocalDateTime.now().toString())
                .codigoResultado("BAD_REQUEST")
                .descripcionRespuesta(ex.getMessage())
                .result(Collections.emptyList())
                .endpoint(ex.getClass().getSimpleName())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(value = {NoContentException.class})
    public ResponseEntity<Response> connection(NoContentException ex){
        Response response = Response.builder()
                .fecha(LocalDateTime.now().toString())
                .codigoResultado("NO_CONTENT")
                .descripcionRespuesta(ex.getMessage())
                .endpoint(ex.getClass().getSimpleName())
                .result(Collections.emptyList())
                .build();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }


    @ExceptionHandler(value = {ConnectException.class})
    public ResponseEntity<Response> connectionFail(ConnectException ex){
        Response response = Response.builder()
                .fecha(LocalDateTime.now().toString())
                .codigoResultado("SERVICE_UNAVAILABLE")
                .descripcionRespuesta(ex.getMessage())
                .endpoint(ex.getClass().getSimpleName())
                .result(Collections.emptyList())
                .build();
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        Response response = Response.builder()
                .fecha(LocalDateTime.now().toString())
                .codigoResultado("UNPROCESSABLE_ENTITY")
                .descripcionRespuesta(errors.stream().reduce(" ", String::concat))
                .endpoint(ex.getClass().getSimpleName())
                .result(Collections.emptyList())
                .build();

        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> constraintViolationException(ConstraintViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();

        ex.getConstraintViolations().forEach(cv -> errors.add(cv.getMessage()));

        Response response = Response.builder()
                .fecha(LocalDateTime.now().toString())
                .codigoResultado("BAD_REQUEST")
                .descripcionRespuesta(errors.stream().reduce(" ", String::concat))
                .endpoint(ex.getClass().getSimpleName())
                .result(Collections.emptyList())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
