package co.com.tempo.usecase.savepercentage;

import co.com.tempo.model.Percentage;
import co.com.tempo.model.gateway.PercentageRepository;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class SavePercentageUseCase {

   private final PercentageRepository percentageRepository;

   public Percentage savePct(Percentage percentage){
     return percentageRepository.savePercentage(percentage);
   }


}
