package co.com.tempo.model;

import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Percentage {
    private Double value;
    private Date createdAt;
}
