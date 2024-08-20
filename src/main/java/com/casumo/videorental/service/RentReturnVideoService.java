package com.casumo.videorental.service;


import com.casumo.videorental.dto.RentReturnVideoRequest;
import com.casumo.videorental.dto.RentReturnVideoResponse;
import com.casumo.videorental.dto.RentVideoResponse;
import com.casumo.videorental.dto.ReturnVideoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RentReturnVideoService {
    private final RentVideoService rentVideoService;
    private final ReturnVideoService returnVideoService;

    public RentReturnVideoResponse calculateRentReturn(RentReturnVideoRequest request) {
        RentVideoResponse rentVideoResponse = rentVideoService.calculateRent(request);
        ReturnVideoResponse returnVideoResponse = returnVideoService.calculateReturn(request);
        return RentReturnVideoResponse.builder()
                .rentVideoResponse(rentVideoResponse)
                .returnVideoResponse(returnVideoResponse)
                .build();
    }

}
