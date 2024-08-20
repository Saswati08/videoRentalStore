package com.casumo.videorental.service.validation;

import com.casumo.videorental.dto.RentReturnVideoRequest;
import com.casumo.videorental.exception.InvalidInputException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RentVideoRequestValidation {
    public void validate(RentReturnVideoRequest rentReturnVideoRequest) throws InvalidInputException {
        if(rentReturnVideoRequest.getRequestId() == null) {
            throw new InvalidInputException("Request id not given");
        }
        if(rentReturnVideoRequest.getRentInfo() != null) {
            if(rentReturnVideoRequest.getRentInfo().getVideoList() == null || rentReturnVideoRequest.getRentInfo().getVideoList().isEmpty()) {
                throw new InvalidInputException("Rent information list is empty");
            }
            rentReturnVideoRequest.getRentInfo().getVideoList().stream().forEach(
                    rentVideoInfo -> {
                        if(rentVideoInfo.getVideoId() == null) {
                            throw new InvalidInputException("Video identifier not given in rent video list");
                        }
                        try {
                            UUID videoIdentifier = UUID.fromString(rentVideoInfo.getVideoId());
                        } catch (IllegalArgumentException e) {
                            throw new InvalidInputException("Video identifier should be an UUID");
                        }
                        if(rentVideoInfo.getDays() == null) {
                            throw new InvalidInputException("Number of days in rent information not given");
                        }
                        if(rentVideoInfo.getDays() <= 0) {
                            throw new InvalidInputException("Invalid number of days for rent");
                        }
                    }
            );
        }
    }
}
