package com.ott.speech.repository;

import com.ott.speech.model.Speech;
import com.ott.speech.model.Speech_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface SpeechRepository extends
        JpaRepository<Speech, Long>,
        JpaSpecificationExecutor<Speech> {
    @Transactional
    void deleteSpeechById(Long id);

    Optional<Speech> findSpeechById(Long id);

    List<Speech> findAllByAuthorLike(String author);
}
