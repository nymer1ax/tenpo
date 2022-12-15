package co.com.tempo.model.gateway;

import co.com.tempo.model.Historical;

public interface HistoricalRepository {
    Historical saveHistorical(Historical historical);
}
