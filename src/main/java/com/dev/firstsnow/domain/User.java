package com.dev.firstsnow.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "location")
    private String location;

    @Column(name = "phone")
    private String phone;

    @Column(name = "snowman")
    private String snowman;

    @Builder
    public User(String nickname, String phone, String snowman, String location) {
        this.nickname = nickname;
        this.phone = phone;
        this.snowman = snowman;
        this.location = location;
    }
}