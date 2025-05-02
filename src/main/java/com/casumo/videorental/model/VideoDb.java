package com.casumo.videorental.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;

@Builder
@Entity
@Data
@Table(name = "video")
public class VideoDb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String genre;
    private Boolean available;
}
