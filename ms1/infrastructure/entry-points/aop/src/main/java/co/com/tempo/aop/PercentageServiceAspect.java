package co.com.tempo.aop;

import co.com.tempo.model.Percentage;
import co.com.tempo.model.gateway.PercentageRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
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


    @Pointcut("execution(* co.com.tempo.consumer.percentage.RestPercentageAdapter.getPercentageValue())")
    public void getPercentageValuePointcut() {}


    @Before(value = "getPercentageValuePointcut()")
    public void beforeGetPercentageValue() {
        log.info("La función GetPercentageValue(): va a ser invocada:");
    }


    @AfterReturning(value = "getPercentageValuePointcut()" ,returning = "result")
    public void afterGetPercentageValue(JoinPoint joinPoint, Percentage result) throws IOException {

        if(result != null){
            // Guardamos el resultado en el repositorio de porcentajes.
            percentageRepository.savePercentage(result);
            log.info("Elemento insertado en la BD: Se ha guardado un elemento Percentage: " + result);
        }

        if(result==null) {
            log.info("La función GetPercentageValue(): su valor es Nulo. ");
        }

    }
}
