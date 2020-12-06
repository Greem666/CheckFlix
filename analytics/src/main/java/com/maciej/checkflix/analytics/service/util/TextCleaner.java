package com.maciej.checkflix.analytics.service.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class TextCleaner {

    private static final List<String> englishStopWords = Arrays.asList(
            "i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your", "yours", "yourself",
            "yourselves", "he", "him", "his", "himself", "she", "her", "hers", "herself", "it", "its", "itself",
            "they", "them", "their", "theirs", "themselves", "what", "which", "who", "whom", "this", "that",
            "these", "those", "am", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had",
            "having", "do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or", "because", "as",
            "until", "while", "of", "at", "by", "for", "with", "about", "against", "between", "into", "through",
            "during", "before", "after", "above", "below", "to", "from", "up", "down", "in", "out", "on", "off",
            "over", "under", "again", "further", "then", "once", "here", "there", "when", "where", "why", "how",
            "all", "any", "both", "each", "few", "more", "most", "other", "some", "such", "no", "nor", "not",
            "only", "own", "same", "so", "than", "too", "very", "s", "t", "can", "will", "just", "don", "should",
            "now");
    private static final String pointlessCharsRegex = "[\\d[^\\w\\s]]+";

    public String cleanText(String text) {
        //TODO: It could really use stemming or (better!) lemmatization as well!
        text = text.toLowerCase().replaceAll(pointlessCharsRegex, " ");
        text = text.trim();
        text = text.replaceAll("  ", " ");

        ArrayList<String> allWords = Stream.of(text.split(" "))
                .collect(Collectors.toCollection(ArrayList<String>::new));
        allWords.removeAll(englishStopWords);
        text = String.join(" ", allWords);

        return text;
    }
}
