package co.com.tempo.consumer.percentage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PercentageResponse {

    @JsonProperty("codigoResultado")
    private String codigoResultado;

    @JsonProperty("descripcionRespuesta")
    private String descripcionRespuesta;

    @JsonProperty("fecha")
    private Date fecha;

    @JsonProperty("result")
    private Double result;
}
