package com.casumo.videorental.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RentReturnVideoRequest {
    private UUID requestId;
    private RentInfo rentInfo;
    private ReturnInfo returnInfo;
}
