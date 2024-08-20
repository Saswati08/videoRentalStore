package com.casumo.videorental.service.calculation;

import com.casumo.videorental.service.calculation.IVideoRentalCalcutationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.casumo.videorental.constant.Constants.basePriceForRegularVideosValid;
import static com.casumo.videorental.constant.Constants.basicPrice;

@Component
@RequiredArgsConstructor
public class RegularRentalPriceCalculationStrategy implements IVideoRentalCalcutationStrategy {
    @Override
    public Double calculateVideoRentalCost(Integer days) {
        if(days == 0) {
            return 0D;
        }
        else if(days <= basePriceForRegularVideosValid) {
            return basicPrice ;
        }
        else {
            return basicPrice + (days - basePriceForRegularVideosValid) * basicPrice ;
        }
    }
}
