package com.ott.speech.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ott.speech.exception.SpeechNotFoundException;
import com.ott.speech.model.Speech;
import com.ott.speech.repository.SpeechRepository;
import com.ott.speech.utility.SpeechSearchSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SpeechService {
    private final SpeechRepository speechRepo;

    @Autowired
    public SpeechService(SpeechRepository speechRepo) {
        this.speechRepo = speechRepo;
    }

    public Speech addSpeech(Speech speech) {
        return speechRepo.save(speech);
    }

    public List<Speech> findAllSpeech() {
        return speechRepo.findAll();
    }

    public Speech findSpeechById(Long id) {
        return speechRepo.findSpeechById(id)
                .orElseThrow(() -> new SpeechNotFoundException(id));
    }

    public Speech updateSpeech(Speech speech) {
        findSpeechById(speech.getId());
        return speechRepo.save(speech);
    }

    public void deleteSpeech(Long id) {
        findSpeechById(id);
        speechRepo.deleteSpeechById(id);
    }

    public List<Speech> searchSpeechLike(ObjectNode json) throws ParseException {
        String author = json.has("author") ? json.get("author").asText() : "";
        String speechText = json.has("speech") ? json.get("speech").asText() : "";
        String keywords = json.has("keywords") ?  json.get("keywords").asText() : "";
        String startDateString = json.has("startDate") ? json.get("startDate").asText() : "";
        String endDateString = json.has("endDate") ? json.get("endDate").asText() : "";

        if (author.isBlank() &&
                speechText.isBlank() &&
                keywords.isBlank() &&
                startDateString.isBlank() &&
                endDateString.isBlank()
        ) {
            return speechRepo.findAll();
        }

        Specification<Speech> spec = Specification.where(null);
        if (!author.isBlank()) {
            spec = spec.or(SpeechSearchSpecifications.hasAuthor(author));
        }
        if (!speechText.isBlank()) {
            spec = spec.or(SpeechSearchSpecifications.hasTextInSpeech(speechText));
        }
        if (!keywords.isBlank()) {
            String[] keywordsArray = keywords.split(" ", 0);
            for (String keyword : keywordsArray) {
                spec = spec.or(SpeechSearchSpecifications.hasKeyword(keyword));
            }
        }
        if (!startDateString.isBlank() && !endDateString.isBlank()) {
            Date startDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(startDateString);
            Date endDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(endDateString);
            spec = spec.or(SpeechSearchSpecifications.isBetweenDates(startDate, endDate));
        }

        List<Speech> foundSpeeches = speechRepo.findAll(spec);
        return foundSpeeches;
    }
}
