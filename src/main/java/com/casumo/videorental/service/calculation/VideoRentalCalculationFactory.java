package com.casumo.videorental.service.calculation;

import com.casumo.videorental.enums.VideoType;
import com.casumo.videorental.exception.InternalServerException;
import com.casumo.videorental.service.calculation.IVideoRentalCalcutationStrategy;
import com.casumo.videorental.service.calculation.NewReleaseRentalPriceCalculationStrategy;
import com.casumo.videorental.service.calculation.OldRentalPriceCalculationStrategy;
import com.casumo.videorental.service.calculation.RegularRentalPriceCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VideoRentalCalculationFactory {
    private final NewReleaseRentalPriceCalculationStrategy newReleaseRentalPriceCalculationService;
    private final OldRentalPriceCalculationStrategy oldRentalPriceCalculationService;
    private final RegularRentalPriceCalculationStrategy regularRentalPriceCalculationService;

    public IVideoRentalCalcutationStrategy getVideoRentalCalculationService(VideoType videoType) {
        if(Boolean.TRUE.equals(VideoType.NEW_RELEASES.equals(videoType))) {
            return newReleaseRentalPriceCalculationService;
        }
        else if(Boolean.TRUE.equals(VideoType.OLD_RELEASES.equals(videoType))) {
            return oldRentalPriceCalculationService;
        }
        else if(Boolean.TRUE.equals(VideoType.REGULAR.equals(videoType))) {
            return regularRentalPriceCalculationService;
        }
        else {
            throw new InternalServerException("Video Rental Calculation not possible for this video type");
        }
    }

}
