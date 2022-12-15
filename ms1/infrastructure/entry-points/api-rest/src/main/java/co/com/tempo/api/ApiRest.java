package co.com.tempo.api;
import co.com.tempo.api.dto.NumbersDto;
import co.com.tempo.model.Historical;
import co.com.tempo.model.Percentage;
import co.com.tempo.usecase.addtwonumbers.AddTwoNumbersUseCase;
import co.com.tempo.usecase.getpercentage.GetPercentageUseCase;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ApiRest {

    private final GetPercentageUseCase getPercentageUseCase;

    private final AddTwoNumbersUseCase addTwoNumbersUseCase;

    @GetMapping(path = "/percentage")
    public Percentage getPercentage() throws IOException {
        return getPercentageUseCase.getPercentageValue();
    }

    @GetMapping(path = "/sum")
    public Double sum(@RequestBody NumbersDto numbers) throws IOException {
        return addTwoNumbersUseCase.addTwoNumbers(numbers.getNum1(), numbers.getNum2());
    }

    @GetMapping(path = "/historical")
    public List<Historical> historical(){
        return Collections.EMPTY_LIST;
    }
}
