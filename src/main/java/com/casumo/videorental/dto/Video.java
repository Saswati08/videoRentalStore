package com.casumo.videorental.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Video {
    private String videoId;
    private String videoName;
    private String videoType;
}
