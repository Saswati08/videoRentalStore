package com.casumo.videorental.service.validation;

import com.casumo.videorental.dto.RentInfo;
import com.casumo.videorental.dto.RentReturnVideoRequest;
import com.casumo.videorental.dto.RentVideoInfo;
import com.casumo.videorental.exception.InvalidInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class RentVideoRequestValidationTest {
    @InjectMocks
    private RentVideoRequestValidation rentVideoRequestValidation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validate_shouldThrowExceptionWhenRequestIdIsNull() {
        RentReturnVideoRequest request = new RentReturnVideoRequest();

        InvalidInputException exception = assertThrows(InvalidInputException.class, () ->
                rentVideoRequestValidation.validate(request));

        assertEquals("Request id not given", exception.getMessage());
    }

    @Test
    void validate_shouldThrowExceptionWhenRentVideoInfosIsEmpty() {
        RentReturnVideoRequest request = new RentReturnVideoRequest();
        request.setRequestId(UUID.randomUUID());
        request.setRentInfo(new RentInfo());
        request.getRentInfo().setVideoList(Collections.emptyList());

        InvalidInputException exception = assertThrows(InvalidInputException.class, () ->
                rentVideoRequestValidation.validate(request));

        assertEquals("Rent information list is empty", exception.getMessage());
    }

    @Test
    void validate_shouldThrowExceptionWhenVideoIdentifierIsNull() {
        RentReturnVideoRequest request = new RentReturnVideoRequest();
        request.setRequestId(UUID.randomUUID());
        request.setRentInfo(new RentInfo());
        RentVideoInfo videoInfo = new RentVideoInfo();
        videoInfo.setVideoId(null);
        videoInfo.setDays(3);

        request.getRentInfo().setVideoList(Collections.singletonList(videoInfo));

        InvalidInputException exception = assertThrows(InvalidInputException.class, () ->
                rentVideoRequestValidation.validate(request));

        assertEquals("Video identifier not given in rent video list", exception.getMessage());
    }

    @Test
    void validate_shouldThrowExceptionWhenVideoIdentifierIsInvalidUUID() {
        RentReturnVideoRequest request = new RentReturnVideoRequest();
        request.setRequestId(UUID.randomUUID());
        request.setRentInfo(new RentInfo());
        RentVideoInfo videoInfo = new RentVideoInfo();
        videoInfo.setVideoId("invalid-uuid");
        videoInfo.setDays(3);

        request.getRentInfo().setVideoList(Collections.singletonList(videoInfo));

        InvalidInputException exception = assertThrows(InvalidInputException.class, () ->
                rentVideoRequestValidation.validate(request));

        assertEquals("Video identifier should be an UUID", exception.getMessage());
    }

    @Test
    void validate_shouldThrowExceptionWhenDaysIsNull() {
        RentReturnVideoRequest request = new RentReturnVideoRequest();
        request.setRequestId(UUID.randomUUID());
        request.setRentInfo(new RentInfo());
        RentVideoInfo videoInfo = new RentVideoInfo();
        videoInfo.setVideoId(UUID.randomUUID().toString());
        videoInfo.setDays(null);

        request.getRentInfo().setVideoList(Collections.singletonList(videoInfo));

        InvalidInputException exception = assertThrows(InvalidInputException.class, () ->
                rentVideoRequestValidation.validate(request));

        assertEquals("Number of days in rent information not given", exception.getMessage());
    }

    @Test
    void validate_shouldPassWhenRequestIsValid() {
        RentReturnVideoRequest request = new RentReturnVideoRequest();
        request.setRequestId(UUID.randomUUID());
        request.setRentInfo(new RentInfo());
        RentVideoInfo videoInfo = new RentVideoInfo();
        videoInfo.setVideoId(UUID.randomUUID().toString());
        videoInfo.setDays(3);

        request.getRentInfo().setVideoList(Collections.singletonList(videoInfo));

        assertDoesNotThrow(() -> rentVideoRequestValidation.validate(request));
    }
}
