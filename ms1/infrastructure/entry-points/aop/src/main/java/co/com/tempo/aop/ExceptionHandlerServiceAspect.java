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

import java.util.Date;

@Aspect
@Component
@Slf4j
public class ExceptionHandlerServiceAspect {

    @Autowired
    private HistoricalRepository historicalRepository;
    @Pointcut("execution(* co.com.tempo.api.handlingexception.GlobalExceptionHandler.*(..))")
    public void globalExceptionHandlerMethods() {}

    @Before(value= "globalExceptionHandlerMethods()")
    public void beforeGetHandlerValues(){
        log.info("Global Handler Exception: va a ser invocada: ");
    }

    @AfterReturning(value = "globalExceptionHandlerMethods()", returning = "result")
    public void afterExecutionHandlerException(JoinPoint joinPoint, ResponseEntity<Object> result){
        if(result.getBody()==null){
            log.info("Global Handler no tiene cuerpo, no se puede guardar al historico " + result);
        }

        if(result.getBody() != null){
            Object body = result.getBody();

            Historical historical = Historical.builder()
                    .statusCode(String.valueOf(result.getStatusCodeValue()))
                    .createdAt(new Date())
                    .result(body.toString())
                    .build();
            historicalRepository.saveHistorical(historical);
            log.info("Elemento insertado en la BD: Se ha guardado un al historico");

        }
    }

    @AfterReturning(value = "globalExceptionHandlerMethods()", returning = "result")
    public void afterExecutionHandlerExceptionWithResponse(JoinPoint joinPoint, ResponseEntity<Response> result){
        if(result.getBody()==null){
            log.info("Global Handler no tiene cuerpo, no se puede guardar al historico " + result);
        }

        if(result.getBody() != null){

            Response r = result.getBody();

            Historical historical = Historical.builder()
                    .message(r.getDescripcionRespuesta())
                    .endpoint(r.getEndpoint())
                    .statusCode(r.getCodigoResultado())
                    .createdAt(new Date())
                    .result(r.getResult().toString())
                    .build();

            historicalRepository.saveHistorical(historical);
            log.info("Elemento insertado en la BD: Se ha guardado un al historico: El servicio " + r.getEndpoint() + "con su resultado:  " + r.getResult());
        }
    }

}
