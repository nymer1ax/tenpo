package co.com.tempo.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class BucketConfig {
    private static final int MAX_REQUESTS_PER_MINUTE = 3;

    @Bean
    public Bucket bucket(){
       return Bucket.builder()
                .addLimit(Bandwidth.classic(MAX_REQUESTS_PER_MINUTE, Refill.greedy(MAX_REQUESTS_PER_MINUTE, Duration.ofMinutes(1))))
                .build();
    }
}
