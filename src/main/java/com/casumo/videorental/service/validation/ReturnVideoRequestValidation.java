package com.casumo.videorental.service.validation;

import com.casumo.videorental.dto.RentReturnVideoRequest;
import com.casumo.videorental.exception.InvalidInputException;
import com.casumo.videorental.model.Rent;
import com.casumo.videorental.repository.IRentVideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReturnVideoRequestValidation {
    private final IRentVideoRepository rentVideoRepository;
    public void validate(RentReturnVideoRequest request) {
        if (request.getRequestId() == null) {
            throw new InvalidInputException("Request id not present for the given request");
        }
        if (request.getReturnInfo() != null && request.getReturnInfo().getRentId() == null) {
            throw new InvalidInputException("Rent id is mandatory to return the videos");
        }
        if (request.getReturnInfo() != null) {
        try {
                UUID rentId = UUID.fromString(request.getReturnInfo().getRentId());
                if (Boolean.FALSE.equals(rentVideoRepository.getRentInfoList().containsKey(rentId))) {
                    throw new InvalidInputException("Rent id is not present in the system " + rentId.toString());
                }
            } catch(IllegalArgumentException e){

                throw new InvalidInputException("Rent id is not a valid UUID");
            }
            if(request.getReturnInfo().getVideoList() == null || request.getReturnInfo().getVideoList().size() == 0) {
                throw new InvalidInputException("Return video list is empty");
            }
            UUID rentId = UUID.fromString(request.getReturnInfo().getRentId());
            request.getReturnInfo().getVideoList().stream().forEach(
                    returnVideo -> {
                        try {
                            if(returnVideo.getDays() == null) {
                                throw new InvalidInputException("Return days cannot be null");
                            }
                            UUID video = UUID.fromString(returnVideo.getVideoId());
                            Rent rent = rentVideoRepository.getRentInfoList().get(rentId);
                            if (rent.getRentVideoInfoMap() == null) {
                                throw new InvalidInputException("Rent id given has no video rented");
                            }
                            if (Boolean.FALSE.equals(rent.getRentVideoInfoMap().containsKey(video))) {
                                throw new InvalidInputException("Video id does not correspond to the rent id given");
                            }

                        } catch (IllegalArgumentException e) {
                            throw new InvalidInputException("Video id is not a valid UUID " + returnVideo.getVideoId());
                        }
                    }
            );
        }
    }

}


