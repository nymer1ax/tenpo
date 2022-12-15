package co.com.tempo.jpa.percentage;

import co.com.tempo.model.Percentage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PercentageMapper {
    public PercentageData percentageToPercentageData(Percentage percentage){
        return PercentageData.builder()
                .createdAt(percentage.getCreatedAt())
                .valuePct(percentage.getValue())
                .build();
    }

    public Percentage percentageDataToPercentage(PercentageData percentageData){
        return Percentage.builder()
                .createdAt(percentageData.getCreatedAt())
                .value(percentageData.getValuePct())
                .build();
    }
}
