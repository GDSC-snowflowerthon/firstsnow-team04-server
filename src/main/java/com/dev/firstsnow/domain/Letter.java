package com.dev.firstsnow.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Table(name = "letters")
public class Letter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "is_sent")
    private Boolean isSent;

    @Column(name = "is_read")
    private Boolean isRead;

    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDate createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id")
    private User recipient;

    public void updateIsSent(Boolean isSent){
        this.isSent = isSent;
    }

    public void updateIsRead(Boolean isRead){
        this.isRead = isRead;
    }

    @Builder
    public Letter(String title, String content, Boolean isSent, Boolean isRead, User sender, User recipient) {
        this.title = title;
        this.content = content;
        this.isSent = isSent;
        this.isRead = isRead;
        this.sender = sender;
        this.recipient = recipient;
        this.createdDate = LocalDate.now();
    }
}