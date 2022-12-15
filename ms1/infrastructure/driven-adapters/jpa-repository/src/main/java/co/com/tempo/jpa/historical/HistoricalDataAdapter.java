package co.com.tempo.jpa.historical;

import co.com.tempo.jpa.helper.AdapterOperations;
import co.com.tempo.jpa.percentage.PercentageData;
import co.com.tempo.jpa.percentage.PercentageDataRepository;
import co.com.tempo.model.Historical;
import co.com.tempo.model.Percentage;
import co.com.tempo.model.gateway.HistoricalRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class HistoricalDataAdapter extends AdapterOperations<Historical, HistoricalData, Long, HistoricalDataRepository> implements HistoricalRepository {

    @Autowired
    HistoricalDataMapper historicalDataMapper;
    public HistoricalDataAdapter(HistoricalDataRepository repository,  ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Historical.class/* change for domain model */));
    }

    @Override
    @Transactional
    public Historical saveHistorical(Historical historical) {
         HistoricalData historicalData = this.repository.saveAndFlush(historicalDataMapper.historicalToHistoricalData(historical));
        return historicalDataMapper.historicalDataToHistorical(historicalData);
    }

    @Override
    public List<Historical> getHistoricalList() {
        List<HistoricalData> historicalDataList = this.repository.findAll();
        return historicalDataList.stream().map(historicalDataMapper::historicalDataToHistorical).collect(Collectors.toList());
    }
}
