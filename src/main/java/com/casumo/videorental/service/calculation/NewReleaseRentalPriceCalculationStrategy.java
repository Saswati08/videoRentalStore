package com.casumo.videorental.service.calculation;

import com.casumo.videorental.service.calculation.IVideoRentalCalcutationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.casumo.videorental.constant.Constants.premiumPrice;

@Component
@RequiredArgsConstructor
public class NewReleaseRentalPriceCalculationStrategy implements IVideoRentalCalcutationStrategy {

    @Override
    public Double calculateVideoRentalCost(Integer days) {
        return days * premiumPrice;
    }
}
