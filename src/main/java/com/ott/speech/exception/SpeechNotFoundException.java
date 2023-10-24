package com.ott.speech.exception;

public class SpeechNotFoundException extends RuntimeException {
    public SpeechNotFoundException(Long id) {
        super("Speech with id " + id + " not found.");
    }
}
