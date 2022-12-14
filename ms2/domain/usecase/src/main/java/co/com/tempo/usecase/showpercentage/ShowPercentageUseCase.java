package co.com.tempo.usecase.showpercentage;

import co.com.tempo.model.Percentage;
import co.com.tempo.model.gateway.PercentageRepository;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
public class ShowPercentageUseCase {

    private final PercentageRepository percentageRepository;

    public Double getLastPercentageSaved(){

        Optional<Percentage> lastE = percentageRepository.getLastPercentageInsert();

        if(lastE.isEmpty()){
            return Double.valueOf(0.10);
        }

        return lastE.get().getValue();
    }
}
