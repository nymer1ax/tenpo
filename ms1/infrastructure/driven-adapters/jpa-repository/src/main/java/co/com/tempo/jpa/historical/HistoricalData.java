package co.com.tempo.jpa.historical;
import javax.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Table(name = "historical_data")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class HistoricalData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "endpoint")
    private String endpoint;

    @Lob
    @Column(name = "message")
    private String message;

    @Column(name = "status_code")
    private String statusCode;

    @Lob
    @Column(name = "result")
    private String result;

    @Column(name = "created_at")
    private Date createdAt;
}
