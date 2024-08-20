package com.casumo.videorental.controller;

import com.casumo.videorental.dto.*;
import com.casumo.videorental.service.*;
import com.casumo.videorental.service.inventory.AddVideoService;
import com.casumo.videorental.service.inventory.GetRentInfoService;
import com.casumo.videorental.service.inventory.GetVideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class VideoController {
    private final AddVideoService addVideoService;
    private final GetVideoService getVideoService;
    private final RentReturnVideoService rentReturnVideoService;
    private final GetRentInfoService getRentInfoService;

   @PostMapping("/videos")
    public void addMovies(@RequestBody AddVideoRequest addVideoRequest) {
       addVideoService.addVideos(addVideoRequest);
   }

   @GetMapping("/videos")
   public ResponseEntity<List<Video>> getVideos() {
       return ResponseEntity.ok(getVideoService.getVideos());
   }

   @PostMapping(value = "/videos/rental", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> rentVideos(@RequestBody RentReturnVideoRequest rentReturnVideoRequest)  {
           return ResponseEntity.ok(rentReturnVideoService.calculateRentReturn(rentReturnVideoRequest));
   }

   @GetMapping(value = "/videos/rent", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getRentInfo() {
       return ResponseEntity.ok(getRentInfoService.getRentInfos());
   }

}
