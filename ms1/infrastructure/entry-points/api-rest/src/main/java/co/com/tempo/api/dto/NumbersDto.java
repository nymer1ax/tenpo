package co.com.tempo.api.dto;

import lombok.*;
import org.wildfly.common.annotation.NotNull;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NumbersDto {
    @NotNull
    Integer num1;
    @NotNull
    Integer num2;
}
