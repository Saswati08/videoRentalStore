package com.casumo.videorental.service.inventory;

import com.casumo.videorental.dto.AddVideoRequest;
import com.casumo.videorental.enums.VideoType;
import com.casumo.videorental.model.Video;
import com.casumo.videorental.repository.IVideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddVideoService {
    private final IVideoRepository videoRepository;
    public void addVideos(AddVideoRequest addVideoRequest) {
        for(int i = 0; i < addVideoRequest.getVideoList().size(); i++) {
            videoRepository.addVideo(
                    Video.builder()
                        .videoIdentifier(UUID.randomUUID())
                            .videoName(addVideoRequest.getVideoList().get(i).getVideoName())
                            .videoType(VideoType.fromValue(addVideoRequest.getVideoList().get(i).getVideoType()))
                        .build()
            );
        }
    }
}
