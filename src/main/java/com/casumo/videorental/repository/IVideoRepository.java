package com.casumo.videorental.repository;

import com.casumo.videorental.model.Video;

import java.util.List;
import java.util.UUID;

public interface IVideoRepository {
    void addVideo(Video video);
    List<Video> getVideos();
    Video getVideo(UUID videoId);
}
