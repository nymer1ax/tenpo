package co.com.tempo.api;
import co.com.tempo.usecase.exceptions.Response;
import co.com.tempo.usecase.showpercentage.ShowPercentageUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ApiRest {

    private final ShowPercentageUseCase showPercentageUseCase;

    @GetMapping(path = "/percentage")
    public ResponseEntity<Response> showPercentage() {

        Double percentage = showPercentageUseCase.getLastPercentageSaved();

        Response response = Response.builder()
                .codigoResultado("200")
                .descripcionRespuesta("OK")
                .fecha(LocalDateTime.now().toString())
                .result(percentage).build();

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }


}
