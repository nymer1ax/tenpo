package co.com.tempo.model.gateway;

import co.com.tempo.model.Percentage;

import java.util.Optional;

public interface PercentageRepository {

    Percentage savePercentage(Percentage percentage);

    Optional<Percentage> getLastPercentageInsert();

}
