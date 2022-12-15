package co.com.tempo.model;

import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Historical {
    private String endpoint;
    private String message;
    private String statusCode;
    private String result;
    private Date createdAt;
}
