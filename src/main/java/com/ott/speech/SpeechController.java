package com.ott.speech;

import com.ott.speech.model.Speech;
import com.ott.speech.service.SpeechService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("find/{id}")
    public ResponseEntity<Speech> getSpeechById (@PathVariable("id") Long id) {
        Speech speech = speechService.findSpeechById(id);
        return new ResponseEntity<>(speech, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Speech> addSpeech (@RequestBody Speech speech) {
        Speech newSpeech = speechService.addSpeech(speech);
        return new ResponseEntity<>(newSpeech, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Speech> updateSpeech (@RequestBody Speech speech) {
        Speech updatedSpeech = speechService.updateSpeech(speech);
        return new ResponseEntity<>(updatedSpeech, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSpeech (@PathVariable("id") Long id) {
        speechService.deleteSpeech(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
