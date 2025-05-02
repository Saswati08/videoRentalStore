package com.casumo.videorental.repository;

import com.casumo.videorental.model.VideoDb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoDbRepository extends JpaRepository<VideoDb, Integer> {
//    void addVideo(VideoDb videoDb);
}
