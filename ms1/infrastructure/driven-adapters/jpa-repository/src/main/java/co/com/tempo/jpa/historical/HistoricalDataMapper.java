package co.com.tempo.jpa.historical;

import co.com.tempo.model.Historical;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class HistoricalDataMapper {

    public HistoricalData historicalToHistoricalData(Historical historical){
        return HistoricalData.builder()
                .createdAt(historical.getCreatedAt())
                .endpoint(historical.getEndpoint())
                .message(historical.getMessage())
                .statusCode(historical.getStatusCode())
                .result(historical.getResult())
                .build();
    }

    public Historical historicalDataToHistorical(HistoricalData historicalData){
        return Historical.builder()
                .createdAt(historicalData.getCreatedAt())
                .endpoint(historicalData.getEndpoint())
                .message(historicalData.getMessage())
                .statusCode(historicalData.getStatusCode())
                .result(historicalData.getResult())
                .build();
    }

}
