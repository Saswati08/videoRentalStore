package com.casumo.videorental.model;

import com.casumo.videorental.dto.RentVideoInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
public class Rent {
    private UUID rentIdentifier;
    private Map<UUID, RentVideoInfo> rentVideoInfoMap;
}
