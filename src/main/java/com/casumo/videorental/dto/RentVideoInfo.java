package com.casumo.videorental.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Setter
public class RentVideoInfo {
    private String videoId;
    private Integer days;
    private Double price;
    private String videoName;
    private String videoType;
    private LocalDate createdOn;
    private LocalDate updatedOn;
}
