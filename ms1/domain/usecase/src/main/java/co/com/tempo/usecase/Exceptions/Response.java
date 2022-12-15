package co.com.tempo.usecase.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class Response {
    private String endpoint;
    private String descripcionRespuesta;
    private String codigoResultado;
    private String fecha;
    public Object result;
}
