package com.ott.speech.repository;

import com.ott.speech.model.Speech;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpeechRepository extends JpaRepository<Speech, Long> {
    @Transactional
    void deleteSpeechById(Long id);

    Optional<Speech> findSpeechById(Long id);

    List<Speech> findAllByAuthorLike(String author);
}
