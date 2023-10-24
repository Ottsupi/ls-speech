package com.ott.speech.repository;

import com.ott.speech.exception.SpeechNotFoundException;
import com.ott.speech.model.Speech;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class SpeechRepositoryTest {


    private final SpeechRepository speechRepo;

    @Autowired
    public SpeechRepositoryTest(SpeechRepository speechRepo) {
        this.speechRepo = speechRepo;
    }

    @Test
    void deleteSpeechById() {
    }

    @Test
    void FindSpeechById1() {
        //sanity check
        // must be able to find speech with id: 1
        // in seeded database

        //given
        Long id = 1L;

        //when
        Speech expectedSpeech = speechRepo.findSpeechById(id)
                .orElseThrow(() -> new SpeechNotFoundException(id));

        //then
        assertThat(expectedSpeech.getId()).isEqualTo(id);
    }

    @Test
    void FindSpeechThatDoesNotExist() {
        //if speech with id given does not exist
        // it must throw SpeechNotFoundException

        //given
        Long id = 0L;

        //that
        assertThrows(SpeechNotFoundException.class, () -> {
            //when
            Speech expectedSpeech = speechRepo.findSpeechById(id)
                    .orElseThrow(() -> new SpeechNotFoundException(id));
        });
    }
}