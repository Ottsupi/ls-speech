package com.ott.speech;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ott.speech.exception.SpeechNotFoundException;
import com.ott.speech.model.Speech;
import com.ott.speech.service.SpeechService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/speech")
public class SpeechController {
    private final SpeechService speechService;

    public SpeechController(SpeechService speechService) {
        this.speechService = speechService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Speech>> getAllSpeeches() {
        List<Speech> speechList = speechService.findAllSpeech();
        return new ResponseEntity<>(speechList, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Speech> getSpeechById (@PathVariable("id") Long id) {
        Speech speech = speechService.findSpeechById(id);
        return new ResponseEntity<>(speech, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Speech>> searchSpeech (@RequestBody ObjectNode json) {
        try {
            List<Speech> foundSpeech = speechService.searchSpeechLike(json);
            return new ResponseEntity<>(foundSpeech, HttpStatus.OK);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Speech> addSpeech (@RequestBody @Validated Speech speech) {
        Speech newSpeech = speechService.addSpeech(speech);
        return new ResponseEntity<>(newSpeech, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Speech> updateSpeech (@RequestBody @Validated Speech speech) {
        Speech updatedSpeech = speechService.updateSpeech(speech);
        return new ResponseEntity<>(updatedSpeech, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSpeech (@PathVariable("id") Long id) {
        speechService.deleteSpeech(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<String> handle(SpeechNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
