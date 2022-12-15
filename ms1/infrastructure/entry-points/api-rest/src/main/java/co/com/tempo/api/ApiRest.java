package co.com.tempo.api;

import co.com.tempo.model.Historical;
import co.com.tempo.usecase.Exceptions.Response;
import co.com.tempo.usecase.addtwonumbers.AddTwoNumbersUseCase;
import co.com.tempo.usecase.getpercentage.GetPercentageUseCase;
import co.com.tempo.usecase.historicallist.HistoricalListUseCase;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.PUT})
@RequiredArgsConstructor
public class ApiRest {




    private final AddTwoNumbersUseCase addTwoNumbersUseCase;

    private final HistoricalListUseCase historicalListUseCase;

    private final GetPercentageUseCase getPercentageUseCase;

    @GetMapping(path="/percentage")
    public ResponseEntity<Response> pct() throws IOException {

        Double pctVal  =  getPercentageUseCase.getPercentageValue().getValue();

        Response response = Response.builder()
                .codigoResultado("200")
                .descripcionRespuesta("OK")
                .fecha(LocalDateTime.now().toString())
                .endpoint("/api/percentage")
                .result(pctVal)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping(path = "/sum")
    public ResponseEntity<Response> sum(@RequestParam(required = true) @Valid Integer num1, @RequestParam(required = true)  @Valid Integer num2 ) throws IOException {

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
    public ResponseEntity<Response> historical(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size){

        List<Historical> lista = historicalListUseCase.historicalListOfCalls();

        Page<Historical> pageList = new PageImpl<Historical>(lista, PageRequest.of(page,
                size), lista.size());

        Response response = Response.builder()
                .codigoResultado("200")
                .descripcionRespuesta("OK")
                .fecha(LocalDateTime.now().toString())
                .endpoint("/api/historical")
                .result(pageList).build();

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
}
