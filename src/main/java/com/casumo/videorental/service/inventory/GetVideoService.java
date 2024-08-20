package com.casumo.videorental.service.inventory;

import com.casumo.videorental.dto.Video;
import com.casumo.videorental.repository.IVideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetVideoService {
    private final IVideoRepository videoRepository;
    public List<Video> getVideos() {
        List<com.casumo.videorental.model.Video> videoList =  videoRepository.getVideos();
        List<Video> videoListResponse = new ArrayList<>();
        videoList.stream().forEach(
                video -> {
                    videoListResponse.add(
                            Video.builder()
                                .videoId(video.getVideoIdentifier().toString())
                                .videoType(video.getVideoType().toString())
                                .videoName(video.getVideoName())
                                .build()
                    );
                }
        );
        return videoListResponse;
    }
}
