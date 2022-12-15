package co.com.tempo.jpa.percentage;


import co.com.tempo.jpa.helper.AdapterOperations;
import co.com.tempo.model.Percentage;


import co.com.tempo.model.gateway.PercentageRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public class PercentageDataAdapter extends AdapterOperations<Percentage, PercentageData, Long, PercentageDataRepository>  implements PercentageRepository {

    @Autowired
    PercentageMapper percentageMapper;

    public PercentageDataAdapter(PercentageDataRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Percentage.class/* change for domain model */));
    }

    @Override
    public Percentage savePercentage(Percentage percentage) {
        PercentageData p = this.repository.saveAndFlush(percentageMapper.percentageToPercentageData(percentage));
        return percentageMapper.percentageDataToPercentage(p);
    }

    @Override
    public Optional<Percentage> getLastPercentageInsert() {

        Optional<PercentageData> percentageData =  this.repository.findFirstByOrderByIdDesc();
        if(!percentageData.isPresent()){
            return Optional.empty();
        }
        return Optional.of(percentageMapper.percentageDataToPercentage(percentageData.get()));
    }
}
