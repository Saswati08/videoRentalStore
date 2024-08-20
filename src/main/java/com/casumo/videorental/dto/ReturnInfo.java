package com.casumo.videorental.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ReturnInfo {
    private String rentId;
    private List<ReturnVideo> videoList;
}
