package co.com.tempo.usecase.getpercentage;

import co.com.tempo.model.Percentage;
import co.com.tempo.model.gateway.PercentageConsumerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.IOException;
import java.net.ConnectException;

@Slf4j
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
 class GetPercentageUseCaseTest {

    @Mock
    private PercentageConsumerRepository percentageRepository;

    @InjectMocks
    private GetPercentageUseCase getPercentageUseCase;

    @Test
    public void testGetPercentageValue_validInput_validOutput() throws IOException {

        Percentage percentage = Percentage.builder().value(Double.valueOf(0.10)).build();

        // Configurar el mock de PercentageConsumerRepository para que devuelva un valor válido
        Mockito.when(percentageRepository.getPercentageValue()).thenReturn(percentage);

        // Invocar al método getPercentageValue de la clase GetPercentageUseCase
        Percentage result = getPercentageUseCase.getPercentageValue();

        Assertions.assertEquals(percentage.getValue(), result.getValue());

    }

    @Test
    public void testExceptions() throws IOException {
        
        Mockito.when(percentageRepository.getPercentageValue()).thenReturn(null);

        Exception ex = Assertions.assertThrows(
                ConnectException.class,
                () -> {
                    getPercentageUseCase.getPercentageValue();
                });
        Assertions.assertNotNull(ex.getMessage());
        Assertions.assertEquals("No se pudo obtener el valor del procentaje. Ha ocurrido un error en la conexión.", ex.getMessage());
    }
}