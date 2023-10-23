package com.ott.speech.service;

import com.ott.speech.exception.SpeechNotFoundException;
import com.ott.speech.model.Speech;
import com.ott.speech.repository.SpeechRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        return speechRepo.save(speech);
    }

    public void deleteSpeech(Long id) {
        speechRepo.deleteSpeechById(id);
    }

    public List<Speech> searchSpeech(Speech speech) {
        return Collections.singletonList(speech);
    }
}
