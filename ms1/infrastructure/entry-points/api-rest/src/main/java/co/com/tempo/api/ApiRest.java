package co.com.tempo.api;

import co.com.tempo.model.Historical;
import co.com.tempo.usecase.Exceptions.Response;
import co.com.tempo.usecase.addtwonumbers.AddTwoNumbersUseCase;
import co.com.tempo.usecase.historicallist.HistoricalListUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ApiRest {

    private final AddTwoNumbersUseCase addTwoNumbersUseCase;

    private final HistoricalListUseCase historicalListUseCase;

    @GetMapping(path = "/sum")
    public ResponseEntity<Response> sum(@RequestParam(required = true) Integer num1, @RequestParam(required = true) Integer num2 ) throws IOException {
        Double sumResult  =  addTwoNumbersUseCase.addTwoNumbers(num1, num2);

        Response response = Response.builder()
                .codigoResultado("200")
                .descripcionRespuesta("OK")
                .fecha(LocalDateTime.now().toString())
                .endpoint("/api/sum")
                .result(sumResult).build();

        return ResponseEntity.status(HttpStatus.OK).body(response);


    }

    @GetMapping(path = "/historical")
    public ResponseEntity<Response> historical(){

        List<Historical> lista = historicalListUseCase.historicalListOfCalls();

        Response response = Response.builder()
                .codigoResultado("200")
                .descripcionRespuesta("OK")
                .fecha(LocalDateTime.now().toString())
                .endpoint("/api/historical")
                .result(lista).build();

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
}
