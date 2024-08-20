package com.casumo.videorental.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Setter
public class RentInfo {
    private List<RentVideoInfo> videoList;
}
