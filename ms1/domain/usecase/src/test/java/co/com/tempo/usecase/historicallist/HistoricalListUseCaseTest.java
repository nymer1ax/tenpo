package co.com.tempo.usecase.historicallist;

import co.com.tempo.model.Historical;
import co.com.tempo.model.gateway.HistoricalRepository;
import co.com.tempo.model.gateway.PercentageConsumerRepository;
import co.com.tempo.usecase.Exceptions.custom.NoContentException;
import co.com.tempo.usecase.getpercentage.GetPercentageUseCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
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

import java.net.ConnectException;
import java.util.Collections;
import java.util.List;


@Slf4j
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class HistoricalListUseCaseTest {

    @Mock
    private HistoricalRepository historicalRepository;

    @InjectMocks
    private HistoricalListUseCase historicalListUseCase;

    @Test
    public void testlist(){

        Historical h1 = Historical.builder().build();
        Historical h2 = Historical.builder().build();
        Historical h3 = Historical.builder().build();
        List<Historical> list = List.of(h1, h2, h3);

        Mockito.when(historicalRepository.getHistoricalList()).thenReturn(list);
        List<Historical> historicals = historicalListUseCase.historicalListOfCalls();

        Assertions.assertEquals(historicals.size(), list.size());
    }

    @Test
    public void testException(){

        Mockito.when(historicalRepository.getHistoricalList()).thenReturn(Collections.emptyList());

        Exception ex = Assertions.assertThrows(
                NoContentException.class,
                () -> {
                    historicalListUseCase.historicalListOfCalls();
                });
        Assertions.assertNotNull(ex.getMessage());
        Assertions.assertEquals("Error: No existe información historica la cual mostrar.", ex.getMessage());
    }

}