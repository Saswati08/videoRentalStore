package com.casumo.videorental.service;

import com.casumo.videorental.dto.*;
import com.casumo.videorental.enums.VideoType;
import com.casumo.videorental.model.Rent;
import com.casumo.videorental.repository.IRentVideoRepository;
import com.casumo.videorental.repository.IVideoRepository;
import com.casumo.videorental.service.calculation.RegularRentalPriceCalculationStrategy;
import com.casumo.videorental.service.calculation.VideoRentalCalculationFactory;
import com.casumo.videorental.service.validation.RentVideoRequestValidation;
import com.casumo.videorental.service.validation.ReturnVideoRequestValidation;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Log4j2
public class ReturnVideoServiceTest {
    @Mock
    private ReturnVideoRequestValidation returnVideoRequestValidation;

    @Mock
    private IVideoRepository videoRepository;

    @Mock
    private IRentVideoRepository rentVideoRepository;

    @Mock
    private VideoRentalCalculationFactory videoRentalCalculationFactory;

    @InjectMocks
    private ReturnVideoService returnVideoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void calculateReturn_shouldReturnZeroSurcharge_whenReturnedOnTime() {
        UUID rentIdentifier = UUID.randomUUID();
        UUID videoIdentifier = UUID.randomUUID();

        RentVideoInfo rentVideoInfo = RentVideoInfo.builder()
                .videoId(videoIdentifier.toString())
                .days(3)
                .build();

        Map<UUID, RentVideoInfo> rentVideoInfoMap = new HashMap<>();
        rentVideoInfoMap.put(videoIdentifier, rentVideoInfo);
        Rent rent = Rent.builder()
                .rentIdentifier(rentIdentifier)
                .rentVideoInfoMap(rentVideoInfoMap)
                .build();
        com.casumo.videorental.model.Video video = com.casumo.videorental.model.Video.builder()
                .videoIdentifier(videoIdentifier)
                .videoType(VideoType.REGULAR)
                .build();

        when(videoRentalCalculationFactory.getVideoRentalCalculationService(any())).thenReturn(new RegularRentalPriceCalculationStrategy());
        when(rentVideoRepository.getRent(rentIdentifier)).thenReturn(rent);
        when(videoRepository.getVideo(any())).thenReturn(video);

        RentReturnVideoRequest rentReturnVideoRequest = new RentReturnVideoRequest();
        ReturnInfo returnInfo = new ReturnInfo(rentIdentifier.toString(), Collections.singletonList(new ReturnVideo(videoIdentifier.toString(), 10, null)));
        rentReturnVideoRequest.setReturnInfo(returnInfo);

        ReturnVideoResponse response = returnVideoService.calculateReturn(rentReturnVideoRequest);

        assertEquals(150, response.getTotalSurcharge());
        assertEquals(150, response.getReturnInfo().getVideoList().get(0).getSurcharge());
    }

    @Test
    void calculateReturn_shouldReturnEmptyResponse_whenNoReturnVideoInfosProvided() {
        RentReturnVideoRequest rentReturnVideoRequest = new RentReturnVideoRequest();
        rentReturnVideoRequest.setReturnInfo(null);

        ReturnVideoResponse response = returnVideoService.calculateReturn(rentReturnVideoRequest);

        assertEquals(null, response.getTotalSurcharge());
        assertEquals(null, response.getReturnInfo());
    }
}

