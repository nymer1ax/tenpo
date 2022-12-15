package co.com.tempo.jpa.historical;

import co.com.tempo.jpa.helper.AdapterOperations;
import co.com.tempo.jpa.percentage.PercentageData;
import co.com.tempo.jpa.percentage.PercentageDataRepository;
import co.com.tempo.model.Historical;
import co.com.tempo.model.Percentage;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.util.function.Function;

@Repository
public class HistoricalDataAdapter extends AdapterOperations<Historical, HistoricalData, Long, HistoricalDataRepository> {

    public HistoricalDataAdapter(HistoricalDataRepository repository,  ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Historical.class/* change for domain model */));
    }
}
