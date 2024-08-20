package com.casumo.videorental.dto;

import lombok.Builder;
import lombok.Getter;
@Getter
@Builder
public class ReturnVideoResponse {
    private ReturnInfo returnInfo;
    private Double totalSurcharge;
}
