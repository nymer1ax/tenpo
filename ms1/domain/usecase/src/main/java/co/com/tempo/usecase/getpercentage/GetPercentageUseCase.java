package co.com.tempo.usecase.getpercentage;

import co.com.tempo.model.Percentage;
import co.com.tempo.model.gateway.PercentageRepository;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class GetPercentageUseCase {
    private final PercentageRepository percentageRepository;
    public Percentage getPercentageValue() throws IOException {
        return percentageRepository.getPercentageValue();
    }
}
