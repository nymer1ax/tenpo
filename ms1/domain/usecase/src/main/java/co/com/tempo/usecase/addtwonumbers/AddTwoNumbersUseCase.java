package co.com.tempo.usecase.addtwonumbers;

import co.com.tempo.model.Percentage;
import co.com.tempo.model.gateway.PercentageRepository;
import co.com.tempo.usecase.getpercentage.GetPercentageUseCase;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.ConnectException;
import java.util.Optional;

@RequiredArgsConstructor
public class AddTwoNumbersUseCase {

    private final GetPercentageUseCase getPercentageUseCase;
    private final PercentageRepository percentageRepository;

    public Double  addTwoNumbers(int num1, int num2) throws IOException {

        Double percentage = null;

        try {
            percentage = getPercentageUseCase.getPercentageValue().getValue();
        }catch (ConnectException ex){
            percentage = 0.0;
        }

       Optional<Percentage> lasInsert = percentageRepository.getLastPercentageInsert();

        Double pct = 0.0;

        if(percentage == 0.0){

            pct = lasInsert.map(Percentage::getValue).orElse(0.0);

            if(pct.equals(0.0)){
                throw new RuntimeException("No se ha encontrado el dato de pct");
            }

            return addNumbersAndPercentage(num1, num2, pct);
        }

        return addNumbersAndPercentage(num1, num2, percentage);
    }

    private double addNumbersAndPercentage(int num1, int num2, double percentage) {
        return num1 + num2 + (num1 + num2) * percentage / 100;
    }
}
