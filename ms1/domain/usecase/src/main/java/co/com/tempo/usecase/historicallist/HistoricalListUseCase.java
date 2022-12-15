package co.com.tempo.usecase.historicallist;

import co.com.tempo.model.Historical;
import co.com.tempo.model.gateway.HistoricalRepository;
import co.com.tempo.usecase.Exceptions.custom.NoContentException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class HistoricalListUseCase {

    private final HistoricalRepository historicalRepository;

    public List<Historical> historicalListOfCalls(){

        List<Historical> historicals = historicalRepository.getHistoricalList();

       if(historicals.isEmpty() || historicals == null){
           throw new NoContentException("Error: No existe información historica la cual mostrar.");
       }

       return  historicals;
    }

}
