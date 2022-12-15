package co.com.tempo.aop;

import co.com.tempo.model.Percentage;
import co.com.tempo.model.gateway.PercentageRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Aspect
@Component
@Slf4j
public class PercentageServiceAspect {

    @Autowired
    private PercentageRepository percentageRepository;

//    @Before("execution(* ms1.infrastructure.driven.adapters.rest.consumer.co.com.tempo.consumer.percentage.PercentageConsumerRepository.getPercentageValue())")
//    public void beforeGetPercentageValue() {
//        log.info("Se va a invocar GetPercentageValue()");
//    }

@Pointcut("execution(* co.com.tempo.consumer.percentage.RestPercentageAdapter.getPercentageValue())")
public void getPercentageValuePointcut() {}


    //@AfterReturning(value = "execution(* ms1.infrastructure.driven.adapters.rest.consumer.co.com.tempo.consumer.percentage.PercentageConsumerRepository.getPercentageValue())" ,returning = "result")
    @AfterReturning(value = "getPercentageValuePointcut()" ,returning = "result")
    public void afterGetPercentageValue(JoinPoint joinPoint, Percentage result) throws IOException {

        if(result != null){
            // Guardamos el resultado en el repositorio de porcentajes.
            percentageRepository.savePercentage(result);
            log.info("Elemento insertado en la BD: Se ha guardado un elemento Percentage: " + result);
        }

        if(result==null) {
            log.info("El resultado de getValue es Null");
        }

    }
}
