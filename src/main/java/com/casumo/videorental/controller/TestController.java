package com.casumo.videorental.controller;

import com.casumo.videorental.dto.AddVideoRequest;
import com.casumo.videorental.service.AddVideoDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final AddVideoDbService addVideoService;

    @PostMapping("/video")
    public void addMovies(@RequestParam String title, @RequestParam String genre, @RequestParam Boolean available) {
        addVideoService.addVideo(
                title, genre, available
        );
    }

}
