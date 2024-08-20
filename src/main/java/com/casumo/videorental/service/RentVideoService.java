package com.casumo.videorental.service;

import com.casumo.videorental.dto.RentVideoInfo;
import com.casumo.videorental.dto.RentReturnVideoRequest;
import com.casumo.videorental.dto.RentVideoResponse;
import com.casumo.videorental.exception.InvalidInputException;
import com.casumo.videorental.model.Video;
import com.casumo.videorental.model.Rent;
import com.casumo.videorental.repository.IVideoRepository;
import com.casumo.videorental.repository.IRentVideoRepository;
import com.casumo.videorental.service.calculation.IVideoRentalCalcutationStrategy;
import com.casumo.videorental.service.calculation.VideoRentalCalculationFactory;
import com.casumo.videorental.service.validation.RentVideoRequestValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
@Log4j2
public class RentVideoService {
    private final RentVideoRequestValidation rentVideoRequestValidation;
    private final IVideoRepository videoRepository;
    private final VideoRentalCalculationFactory videoRentalCalculationFactory;
    private final IRentVideoRepository iRentVideoRepository;
    public RentVideoResponse calculateRent(RentReturnVideoRequest rentReturnVideoRequest) throws InvalidInputException {
        rentVideoRequestValidation.validate(rentReturnVideoRequest);
        if(rentReturnVideoRequest.getRentInfo().getVideoList() != null && !rentReturnVideoRequest.getRentInfo().getVideoList().isEmpty()) {
            log.info("rent_info_to_be_calculated_request_id " + rentReturnVideoRequest.getRequestId());


            final List<RentVideoInfo> rentVideoInfoResponseList = new ArrayList<>();
            final Map<UUID, RentVideoInfo> rentVideoInfoHashMap = new HashMap<>();
            AtomicReference<Double> totalRentCharge = new AtomicReference<>(0D);
            rentReturnVideoRequest.getRentInfo().getVideoList().stream().forEach(
                    rentVideoInfo -> {
                        Video video = videoRepository.getVideo(UUID.fromString(rentVideoInfo.getVideoId()));
                        IVideoRentalCalcutationStrategy videoRentalCalculationService = videoRentalCalculationFactory.getVideoRentalCalculationService(video.getVideoType());
                        Double price = videoRentalCalculationService.calculateVideoRentalCost(rentVideoInfo.getDays());
                        RentVideoInfo rentVideoInfoResponse = RentVideoInfo.builder()
                                .videoId(video.getVideoIdentifier().toString())
                                .videoName(video.getVideoName())
                                .videoType(video.getVideoType().toString())
                                .days(rentVideoInfo.getDays())
                                .price(price)
                                .createdOn(LocalDate.now())
                                .updatedOn(LocalDate.now())
                                .build();
                        rentVideoInfoResponseList.add(
                          rentVideoInfoResponse
                        );
                        totalRentCharge.updateAndGet(v -> v + price);
                        rentVideoInfoHashMap.put(video.getVideoIdentifier(), rentVideoInfoResponse);
                    }
            );
            iRentVideoRepository.addRent(
                    Rent.builder()
                            .rentIdentifier(UUID.randomUUID())
                            .rentVideoInfoMap(rentVideoInfoHashMap)
                            .build()
            );
            return RentVideoResponse.builder()
                    .rentVideoInfoList(rentVideoInfoResponseList)
                    .totalRentcharge(totalRentCharge.get())
                    .build();
        }
        else {
            return RentVideoResponse.builder().build();
        }
    }

}
