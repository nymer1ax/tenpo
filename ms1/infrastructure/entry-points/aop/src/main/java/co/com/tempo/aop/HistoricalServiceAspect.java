package co.com.tempo.aop;


import co.com.tempo.model.Historical;
import co.com.tempo.model.gateway.HistoricalRepository;
import co.com.tempo.usecase.Exceptions.Response;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Aspect
@Component
@Slf4j
public class HistoricalServiceAspect {

    @Autowired
    private HistoricalRepository historicalRepository;

    @Pointcut("execution(* co.com.tempo.api.ApiRest.*(..))")
    public void apiRestMethods() {
    }


    @Before(value = "apiRestMethods()")
    public void beforeGetPercentageValue() {
        log.info("La Api rest va a ser invocada: ");
    }


    @AfterReturning(value = "apiRestMethods()", returning = "result")
    public void afterGetPercentageValue(JoinPoint joinPoint, ResponseEntity<Response> result) throws IOException,
            ParseException {

        if (result.getBody() == null) {
            log.info("La petición no tiene cuerpo, no se puede guardar al historico " + result);
        }
        if (result.getBody() != null) {

            Response r = result.getBody();

            if(!r.getEndpoint().equals("/api/historical")){
                Historical historical = Historical.builder()
                        .message(r.getDescripcionRespuesta())
                        .endpoint(r.getEndpoint())
                        .statusCode(r.getCodigoResultado())
                        .createdAt(new Date())
                        .result(r.getResult().toString())
                        .build();

                historicalRepository.saveHistorical(historical);
            }

            if(r.getEndpoint().equals("/api/historical")) {
                Historical historical = Historical.builder()
                        .message(r.getDescripcionRespuesta())
                        .endpoint(r.getEndpoint())
                        .statusCode(r.getCodigoResultado())
                        .createdAt(new Date())
                        .result(r.getDescripcionRespuesta())
                        .build();
                historicalRepository.saveHistorical(historical);
            }

            log.info("Elemento insertado en la BD: Se ha guardado un al historico: El servicio " + r.getEndpoint() + "con su resultado:  " + r.getResult());
        }

    }


}



