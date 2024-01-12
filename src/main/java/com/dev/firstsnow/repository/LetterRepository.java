package com.dev.firstsnow.repository;

import com.dev.firstsnow.domain.Letter;
import com.dev.firstsnow.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LetterRepository extends JpaRepository<Letter, Long> {
    List<Letter> findByRecipientIdAndIsSentTrue(Long recipientId);

    List<Letter> findByRecipient(User recipient);
}
