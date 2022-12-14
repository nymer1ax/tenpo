package co.com.tempo.usecase.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class Response {
    private String codigoResultado;
    private String descripcionRespuesta;
    private String fecha;
    public Object result;
}
