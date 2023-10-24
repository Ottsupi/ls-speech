package com.ott.speech.repository;

import com.ott.speech.exception.SpeechNotFoundException;
import com.ott.speech.model.Speech;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SpeechRepositoryTest {
    private final SpeechRepository underTest;

    @Autowired
    public SpeechRepositoryTest(SpeechRepository speechRepo) {
        this.underTest = speechRepo;
    }

    @BeforeAll
    void setup() {
        String author = "Automated Tester";
        String speech = "Test Speech";
        String keywords = "test speech";
        Date date = new Date();

        Speech firstRecord = new Speech(author, speech, keywords, date);
        underTest.save(firstRecord);

        author = "Automated Tester 2";
        speech = "Test Speech 2";
        keywords = "test speech second";
        date = new Date();
        Speech secondRecord = new Speech(author, speech, keywords, date);
        underTest.save(secondRecord);
    }

    @Test
    void FindSpeechById1() {
        // must be able to find speech

        //given
        Long id = 1L;

        //when
        List<Speech> allSpeeches = underTest.findAll();
        Speech expectedSpeech = underTest.findSpeechById(id)
                .orElseThrow(() -> new SpeechNotFoundException(id));

        //then
        assertThat(expectedSpeech.getId()).isEqualTo(id);
    }

    @Test
    void FindSpeechThatDoesNotExist() {
        // if speech with given id is not found,
        // it must throw SpeechNotFoundException

        //given
        Long id = 0L;

        //that
        assertThrows(SpeechNotFoundException.class, () -> {
            //when
            Speech expectedSpeech = underTest.findSpeechById(id)
                    .orElseThrow(() -> new SpeechNotFoundException(id));
        });
    }

    @Test
    void DeleteSpeechById2() {
        // must be able to delete speech

        //given
        Long id = 2L;

        //when
        underTest.deleteSpeechById(id);

        //then
        assertThrows(SpeechNotFoundException.class, () -> {
            Speech expectedSpeech = underTest.findSpeechById(id)
                    .orElseThrow(() -> new SpeechNotFoundException(id));
        });
    }
}