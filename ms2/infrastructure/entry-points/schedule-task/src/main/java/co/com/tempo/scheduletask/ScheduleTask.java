package co.com.tempo.scheduletask;

import co.com.tempo.model.Percentage;
import co.com.tempo.model.gateway.PercentageRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduleTask {

    private final PercentageRepository repository;

    
    //@Scheduled(fixedRate = 60000)
    @Scheduled(cron = "0 0/30 * * * ?")
    public void scheduledGenerateRandomPercentage() throws ParseException {

        double random = generateRandomPercentage();

        Date date =  stringToDate(getDate());

        Percentage p = repository.savePercentage(Percentage
                .builder()
                .createdAt(date)
                .value(random)
                .build());

        log.info("Percentage {}", p);
    }

    private double generateRandomPercentage() {

        // Create a Random object
        Random random = new Random();

        // Generate a random percentage value as a decimal between 0 and 1
        double percentage = random.nextDouble();

        // Return the percentage value
        return percentage;
    }

    private Date stringToDate(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyy-mm-dd");
        return formatter.parse(date);
    }

    private String getDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyy-mm-dd");
        Date date = new Date();
        String day = formatter.format(date);
        return day;
    }
}
