package com.casumo.videorental.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Setter
public class ReturnVideo {
    private String videoId;
    private Integer days;
    private Double surcharge;
}
