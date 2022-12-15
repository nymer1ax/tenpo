package co.com.tempo.usecase.getpercentage;

import co.com.tempo.model.Percentage;
import co.com.tempo.model.gateway.PercentageConsumerRepository;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class GetPercentageUseCase {
    private final PercentageConsumerRepository percentageRepository;
    public Percentage getPercentageValue() throws IOException {
        return percentageRepository.getPercentageValue();
    }
}
