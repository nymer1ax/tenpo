package co.com.tempo.jpa.percentage;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "percentage")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class PercentageData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="valuePct")
    private Double valuePct;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;
}