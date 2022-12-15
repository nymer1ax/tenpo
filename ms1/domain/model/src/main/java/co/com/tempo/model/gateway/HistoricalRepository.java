package co.com.tempo.model.gateway;

import co.com.tempo.model.Historical;

import java.util.List;

public interface HistoricalRepository {
    Historical saveHistorical(Historical historical);
    List<Historical> getHistoricalList();
}
