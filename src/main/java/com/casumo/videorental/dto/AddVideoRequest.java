package com.casumo.videorental.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class AddVideoRequest {
    private List<Video> videoList;
}
