package com.ott.speech.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
public class Speech implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @NotNull
    private String author;
    @Column(columnDefinition="TEXT")
    @NotNull
    private String speech;
    @NotNull
    private String keywords;
    @NotNull
    private Date date;

    public Speech(Long id, String author, String speech, String keywords, Date date) {
        this.id = id;
        this.author = author;
        this.speech = speech;
        this.keywords = keywords;
        this.date = date;
    }
    public Speech(String author, String speech, String keywords, Date date) {
        this.author = author;
        this.speech = speech;
        this.keywords = keywords;
        this.date = date;
    }

    public Speech() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSpeech() {
        return speech;
    }

    public void setSpeech(String speech) {
        this.speech = speech;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Speech{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", speech='" + speech + '\'' +
                ", keywords='" + keywords + '\'' +
                ", date=" + date +
                '}';
    }

}
