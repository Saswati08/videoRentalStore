package com.casumo.videorental.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RentVideoResponse {
    private List<RentVideoInfo> rentVideoInfoList;
    private Double totalRentcharge;
}
