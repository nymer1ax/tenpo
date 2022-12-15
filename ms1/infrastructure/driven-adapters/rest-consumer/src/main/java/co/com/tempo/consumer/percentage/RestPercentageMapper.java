package co.com.tempo.consumer.percentage;


import co.com.tempo.model.Percentage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RestPercentageMapper {
    private final ObjectMapper mapper;

    public PercentageResponse jsonObjectToPercetangeResponse(JSONObject jsonObject) throws JsonProcessingException {
        return mapper.readValue(jsonObject.toString(), PercentageResponse.class);
    }

    public Percentage percentageResponseToPercentage(PercentageResponse percentageResponse){
        return Percentage.builder().createdAt(percentageResponse.getFecha()).value(percentageResponse.getResult()).build();
    }
}
