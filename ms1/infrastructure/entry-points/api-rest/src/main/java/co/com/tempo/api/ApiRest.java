package co.com.tempo.api;
import co.com.tempo.model.Percentage;
import co.com.tempo.usecase.getpercentage.GetPercentageUseCase;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ApiRest {

    private final GetPercentageUseCase getPercentageUseCase;
    @GetMapping(path = "/percentage")
    public Percentage commandName() throws IOException {
        return getPercentageUseCase.getPercentageValue();

    }
}
