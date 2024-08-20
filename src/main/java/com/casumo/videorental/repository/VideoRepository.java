package com.casumo.videorental.repository;

import com.casumo.videorental.exception.InvalidInputException;
import com.casumo.videorental.model.Video;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Component
public class VideoRepository implements IVideoRepository {
    private HashMap<UUID, Video> videoHashMap;
    VideoRepository() {
        videoHashMap = new HashMap<>();
    }

    public void addVideo(Video video) {
        videoHashMap.put(video.getVideoIdentifier(), video);
    }

    @Override
    public List<Video> getVideos() {
        List<Video> videoList = new ArrayList<>();
        videoHashMap.keySet().stream().forEach(
                videoId -> {
                   videoList.add(videoHashMap.get(videoId));
                }
        );
        return videoList;
    }

    @Override
    public Video getVideo(UUID videoId) {
        if(Boolean.FALSE.equals(videoHashMap.containsKey(videoId))) {
            throw new InvalidInputException("Given video identifier does not exist in system");
        }
        return videoHashMap.get(videoId);
    }

}
