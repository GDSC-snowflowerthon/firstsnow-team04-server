package com.dev.firstsnow.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Dashboard {
    @Id
    private Long id;

    private String user;

    private String password;

    private String title;

    private String contents;

    private LocalDateTime writtenTime;
}