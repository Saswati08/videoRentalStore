package com.casumo.videorental.model;

import com.casumo.videorental.enums.VideoType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
public class Video {
    private UUID videoIdentifier;
    private String videoName;
    private VideoType videoType;
}
