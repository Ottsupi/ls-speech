package com.ott.speech.utility;

import com.ott.speech.model.Speech;
import com.ott.speech.model.Speech_;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public final class SpeechSearchSpecifications {
    public static Specification<Speech> hasAuthor(String author) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .like(root.get(Speech_.AUTHOR), "%"+author+"%");
    }

    public static Specification<Speech> hasTextInSpeech(String text) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .like(root.get(Speech_.SPEECH), "%"+text+"%");
    }

    public static Specification<Speech> hasKeyword(String keyword) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .like(root.get(Speech_.KEYWORDS), "%"+keyword+"%");
    }

    public static Specification<Speech> isBetweenDates(Date start, Date end) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .between(root.get(Speech_.DATE),
                            start,
                            end
                        );
    }
}
