package co.com.tempo.usecase.getpercentage;

import co.com.tempo.model.Percentage;
import co.com.tempo.model.gateway.PercentageConsumerRepository;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.ConnectException;

@RequiredArgsConstructor
public class GetPercentageUseCase {
    private final PercentageConsumerRepository percentageRepository;
    public Percentage getPercentageValue() throws IOException {

        if(percentageRepository.getPercentageValue() == null){
            throw new ConnectException("No se pudo obtener el valor del procentaje. Ha ocurrido un error en la conexión.");
        }
        return percentageRepository.getPercentageValue();
    }
}
