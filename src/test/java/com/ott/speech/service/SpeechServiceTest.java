package com.ott.speech.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ott.speech.exception.SpeechNotFoundException;
import com.ott.speech.model.Speech;
import com.ott.speech.repository.SpeechRepository;
import com.ott.speech.utility.SpeechSearchSpecifications;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SpeechServiceTest {

    @Mock
    private SpeechRepository speechRepository;
    private SpeechService underTest;

    @BeforeEach
    void setup() {
        underTest = new SpeechService(speechRepository);
    }

    @Test
    void canAddSpeech() {
        String author = "Automated Tester";
        String speechText = "Test Speech";
        String keywords = "test speech";
        Date date = new Date();
        Speech speech = new Speech(author, speechText, keywords, date);

        //when
        underTest.addSpeech(speech);

        //then
        ArgumentCaptor<Speech> speechArgumentCaptor = ArgumentCaptor.forClass(Speech.class);
        verify(speechRepository).save(speechArgumentCaptor.capture());

        Speech capturedSpeech = speechArgumentCaptor.getValue();

        assertThat(capturedSpeech).isEqualTo(speech);
    }

    @Test
    void canFindAllSpeech() {
        // when
        underTest.findAllSpeech();
        // then
        verify(speechRepository).findAll();
    }

    @Test
    void canFindSpeechById() {
        String author = "Automated Tester";
        String speechText = "Test Speech";
        String keywords = "test speech";
        Date date = new Date();
        Speech speech = new Speech(author, speechText, keywords, date);

        //given
        Long id = 1L;
        given(speechRepository.findSpeechById(id)).willReturn(Optional.of(speech));

        //when
        underTest.findSpeechById(id);

        //then
        verify(speechRepository).findSpeechById(id);
    }

    @Test
    void findSpeechByIdCanThrowSpeechNotFoundException() {
        //given
        Long id = 1L;

        //that
        assertThrows(SpeechNotFoundException.class, () -> {
            //when
            underTest.findSpeechById(id);
        });
    }

    @Test
    void canUpdateSpeech() {
        Long id = 1L;
        String author = "Automated Tester";
        String speechText = "Test Speech";
        String keywords = "test speech";
        Date date = new Date();
        Speech speech = new Speech(id, author, speechText, keywords, date);

        //given
        given(speechRepository.findSpeechById(id)).willReturn(Optional.of(speech));

        //when
        underTest.updateSpeech(speech);

        //then
        verify(speechRepository).save(speech);
    }

    @Test
    void updateSpeechCanThrowSpeechNotFoundException() {
        Long id = 1L;
        String author = "Automated Tester";
        String speechText = "Test Speech";
        String keywords = "test speech";
        Date date = new Date();

        //given
        Speech speech = new Speech(id, author, speechText, keywords, date);

        //that
        assertThrows(SpeechNotFoundException.class, () -> {
            //when
            underTest.updateSpeech(speech);
        });
    }

    @Test
    void canDeleteSpeech() {
        Long id = 1L;
        String author = "Automated Tester";
        String speechText = "Test Speech";
        String keywords = "test speech";
        Date date = new Date();
        Speech speech = new Speech(id, author, speechText, keywords, date);

        //given
        given(speechRepository.findSpeechById(id)).willReturn(Optional.of(speech));

        //when
        underTest.deleteSpeech(id);

        //then
        verify(speechRepository).deleteSpeechById(id);
    }

    @Test
    void deleteSpeechCanThrowSpeechNotFoundException() {
        //given
        Long id = 1L;

        //that
        assertThrows(SpeechNotFoundException.class, () -> {
            //when
            underTest.deleteSpeech(id);
        });
    }

    @Test
    void searchSpeechCanReturnAllSpeechesIfParametersAreEmpty() {
        // search feature must return all speeches
        // if parameters are empty

        //given
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();

        //when
        try {
            underTest.searchSpeechLike(json);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        //then
        verify(speechRepository).findAll();
    }

    @Test
    void searchSpeechCanFindBasedOnSpec() {
        //given
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();
        json.put("author", "Automated Tester");
        json.put("speech", "Test Speech");
        json.put("keywords", "test speech");
        json.put("startDate", "2020-01-01");
        json.put("endDate", "2023-12-31");

        String author = "Automated Tester";
        String speechText = "Test Speech";
        String keywords = "test speech";
        String startDateString = "2020-01-01";
        String endDateString = "2023-12-31";

        Specification<Speech> spec = Specification.where(null);
        spec = spec.or(SpeechSearchSpecifications.hasAuthor(author));
        spec = spec.or(SpeechSearchSpecifications.hasTextInSpeech(speechText));
        String[] keywordsArray = keywords.split(" ", 0);

        for (String keyword : keywordsArray) {
            spec = spec.or(SpeechSearchSpecifications.hasKeyword(keyword));
        }
        try {
            Date startDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(startDateString);
            Date endDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(endDateString);
            spec = spec.or(SpeechSearchSpecifications.isBetweenDates(startDate, endDate));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        //when
        try {
            underTest.searchSpeechLike(json);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        //then
        verify(speechRepository).findAll(spec);
    }
}