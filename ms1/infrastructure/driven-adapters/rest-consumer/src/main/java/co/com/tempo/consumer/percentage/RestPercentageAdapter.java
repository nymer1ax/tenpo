package co.com.tempo.consumer.percentage;

import co.com.tempo.model.Percentage;
import co.com.tempo.model.gateway.PercentageConsumerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
@RequiredArgsConstructor
public class RestPercentageAdapter implements PercentageConsumerRepository {

    private final RestPercentageConsumer restPercentageConsumer;
    private final RestPercentageMapper restPercentageMapper;
    @Override
    public Percentage getPercentageValue() throws IOException {

        PercentageResponse percentageResponse = restPercentageConsumer.getPercentage();

        if(percentageResponse == null){
            return null;
        }

        return restPercentageMapper.percentageResponseToPercentage(percentageResponse);
    }
}
