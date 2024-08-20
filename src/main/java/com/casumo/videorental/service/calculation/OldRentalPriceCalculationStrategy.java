package com.casumo.videorental.service.calculation;

import com.casumo.videorental.service.calculation.IVideoRentalCalcutationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.casumo.videorental.constant.Constants.basePriceForOldVideosValid;
import static com.casumo.videorental.constant.Constants.basicPrice;

@Component
@RequiredArgsConstructor
public class OldRentalPriceCalculationStrategy implements IVideoRentalCalcutationStrategy {
    @Override
    public Double calculateVideoRentalCost(Integer days) {
       if(days == 0D) {
           return 0D;
       }
       else if(days <= basePriceForOldVideosValid) {
           return basicPrice;
       }
       return basicPrice + ((days - basePriceForOldVideosValid) * basicPrice);
    }
}
