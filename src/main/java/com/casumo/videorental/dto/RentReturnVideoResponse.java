package com.casumo.videorental.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RentReturnVideoResponse {
    private RentVideoResponse rentVideoResponse;
    private ReturnVideoResponse returnVideoResponse;
}
