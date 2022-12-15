package co.com.tempo.model.gateway;

import co.com.tempo.model.Percentage;

import java.io.IOException;

public interface PercentageConsumerRepository {
     Percentage getPercentageValue() throws IOException;

}
