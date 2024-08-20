package com.casumo.videorental.service;

import com.casumo.videorental.dto.RentInfo;
import com.casumo.videorental.dto.RentReturnVideoRequest;
import com.casumo.videorental.dto.RentVideoInfo;
import com.casumo.videorental.dto.RentVideoResponse;
import com.casumo.videorental.enums.VideoType;
import com.casumo.videorental.exception.InvalidInputException;
import com.casumo.videorental.model.Video;
import com.casumo.videorental.repository.IRentVideoRepository;
import com.casumo.videorental.repository.IVideoRepository;
import com.casumo.videorental.service.calculation.IVideoRentalCalcutationStrategy;
import com.casumo.videorental.service.calculation.VideoRentalCalculationFactory;
import com.casumo.videorental.service.validation.RentVideoRequestValidation;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@Log4j2
public class RentVideoServiceTest {

    @Mock
    private RentVideoRequestValidation rentVideoRequestValidation;

    @Mock
    private IVideoRepository videoRepository;

    @Mock
    private VideoRentalCalculationFactory videoRentalCalculationFactory;

    @Mock
    private IRentVideoRepository iRentVideoRepository;

    @InjectMocks
    private RentVideoService rentVideoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void calculateRent_shouldReturnValidResponse_whenRentInfoIsProvided() throws InvalidInputException {
        RentReturnVideoRequest rentReturnVideoRequest = new RentReturnVideoRequest();
        rentReturnVideoRequest.setRentInfo(new RentInfo());
        rentReturnVideoRequest.setRequestId(UUID.randomUUID());
        RentVideoInfo rentVideoInfo = RentVideoInfo.builder()
                .videoId("dd8da6e6-1916-48d1-b0f4-9d702d68c705")
                .days(5)
                .build();

        rentReturnVideoRequest.getRentInfo().setVideoList(Collections.singletonList(rentVideoInfo));

        Video video = new Video(UUID.fromString(rentVideoInfo.getVideoId()), "Video Name", VideoType.REGULAR);
        when(videoRepository.getVideo(UUID.fromString(rentVideoInfo.getVideoId()))).thenReturn(video);

        IVideoRentalCalcutationStrategy strategy = mock(IVideoRentalCalcutationStrategy.class);
        when(videoRentalCalculationFactory.getVideoRentalCalculationService(video.getVideoType())).thenReturn(strategy);
        when(strategy.calculateVideoRentalCost(rentVideoInfo.getDays())).thenReturn(100.0);

        RentVideoResponse response = rentVideoService.calculateRent(rentReturnVideoRequest);

        assertEquals(1, response.getRentVideoInfoList().size());
        assertEquals(100.0, response.getTotalRentcharge());
        verify(iRentVideoRepository, times(1)).addRent(any());
    }

    @Test
    void calculateRent_shouldThrowException_whenValidationFails() throws InvalidInputException {
        RentReturnVideoRequest rentReturnVideoRequest = new RentReturnVideoRequest();
        rentReturnVideoRequest.setRequestId(UUID.randomUUID());
        RentVideoInfo rentVideoInfo = RentVideoInfo.builder()
                .videoId("dd8da6e6-1916-48d1-b0f4-9d702d68c705")
                .days(5)
                .build();
        rentReturnVideoRequest.setRentInfo(new RentInfo());
        rentReturnVideoRequest.getRentInfo().setVideoList(Collections.singletonList(rentVideoInfo));

        doThrow(new InvalidInputException("Invalid input")).when(rentVideoRequestValidation).validate(rentReturnVideoRequest);

        assertThrows(InvalidInputException.class, () -> rentVideoService.calculateRent(rentReturnVideoRequest));
        verify(iRentVideoRepository, never()).addRent(any());
    }

    @Test
    void calculateRent_shouldReturnEmptyResponse_whenRentVideoInfosIsEmpty() throws InvalidInputException {
        RentReturnVideoRequest rentReturnVideoRequest = new RentReturnVideoRequest();
        rentReturnVideoRequest.setRentInfo(new RentInfo());
        rentReturnVideoRequest.getRentInfo().setVideoList(Collections.emptyList());

        RentVideoResponse response = rentVideoService.calculateRent(rentReturnVideoRequest);

        assertEquals(null, response.getRentVideoInfoList());
        assertEquals(null, response.getTotalRentcharge());
        verify(iRentVideoRepository, never()).addRent(any());
    }
}

