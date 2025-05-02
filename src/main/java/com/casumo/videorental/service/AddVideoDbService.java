package com.casumo.videorental.service;

import com.casumo.videorental.model.VideoDb;
import com.casumo.videorental.repository.VideoDbRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddVideoDbService {
    private final VideoDbRepository  videoDbRepository;
    public void addVideo(String title, String genre, Boolean available) {
        videoDbRepository.save(
                VideoDb.builder()
                        .title(title)
                        .genre(genre)
                        .available(available)
                        .build()
        );
    }

}
