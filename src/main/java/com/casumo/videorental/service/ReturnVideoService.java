package com.casumo.videorental.service;

import com.casumo.videorental.dto.RentVideoInfo;
import com.casumo.videorental.dto.RentReturnVideoRequest;
import com.casumo.videorental.dto.ReturnVideoResponse;
import com.casumo.videorental.model.Rent;
import com.casumo.videorental.model.Video;
import com.casumo.videorental.repository.IRentVideoRepository;
import com.casumo.videorental.repository.IVideoRepository;
import com.casumo.videorental.service.calculation.IVideoRentalCalcutationStrategy;
import com.casumo.videorental.service.calculation.VideoRentalCalculationFactory;
import com.casumo.videorental.service.validation.ReturnVideoRequestValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReturnVideoService {
    private final ReturnVideoRequestValidation returnVideoRequestValidation;
    private final IVideoRepository videoRepository;
    private final IRentVideoRepository rentVideoRepository;
    private final VideoRentalCalculationFactory videoRentalCalculationFactory;
    public ReturnVideoResponse calculateReturn(RentReturnVideoRequest rentReturnVideoRequest) {

        returnVideoRequestValidation.validate(rentReturnVideoRequest);
        log.info("return_video_calculation_for_request_id " + rentReturnVideoRequest.getRequestId());
        AtomicReference<Double> totalSurcharge = new AtomicReference<>(0D);
        if(rentReturnVideoRequest.getReturnInfo() != null) {
            UUID rentIdentifier = UUID.fromString(rentReturnVideoRequest.getReturnInfo().getRentId());
            rentReturnVideoRequest.getReturnInfo().getVideoList().stream().forEach(
                   returnVideo -> {
                       Rent rent = rentVideoRepository.getRent(rentIdentifier);
                       UUID videoIdentifier = UUID.fromString(returnVideo.getVideoId());
                       RentVideoInfo rentVideoInfo = rent.getRentVideoInfoMap().get(videoIdentifier);
                       if(returnVideo.getDays() <= rentVideoInfo.getDays()) {
                           returnVideo.setSurcharge(0D);
                       }
                       else {
                           Integer surchargeDays = returnVideo.getDays() - rentVideoInfo.getDays();
                           Video video = videoRepository.getVideo(UUID.fromString(returnVideo.getVideoId()));
                           IVideoRentalCalcutationStrategy rentalCalcutationStrategy = videoRentalCalculationFactory.getVideoRentalCalculationService(video.getVideoType());
                           Double surcharge = rentalCalcutationStrategy.calculateVideoRentalCost(surchargeDays);
                           returnVideo.setSurcharge(surcharge);
                           totalSurcharge.updateAndGet(v -> v + surcharge);
                       }
                       // video is returned so should not be an entry in rent anymore
                       rent.getRentVideoInfoMap().remove(videoIdentifier);
                   }
           );
            return ReturnVideoResponse.builder()
                    .returnInfo(rentReturnVideoRequest.getReturnInfo())
                    .totalSurcharge(totalSurcharge.get())
                    .build();
            }
        return ReturnVideoResponse.builder().build();
        }
}

