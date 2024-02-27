package com.adaptavist.wordcounter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class WordCounter {
    private static final Logger log = LoggerFactory.getLogger(WordCounter.class);
    public static final String REGEX_NON_ALPHANUMERIC = "[^a-zA-Z\\s]";
    public static final String REGEX_WHITESPACE = "\\s+";

    public static void countWordFrequency(String inputFile, String outputFile) throws IOException {
        log.info("Starting word frequency counter...");
        ConcurrentHashMap<String, Integer> wordCount = getWordFrequencyCount(inputFile);

        log.info("Printing word frequency count...");
        printWordFrequencyMap(wordCount, getOutputStream(outputFile));
        log.info("Successfully completed word frequency counter!");
    }

    /*
    * Read the input file and count the frequency of each word
    */
    private static ConcurrentHashMap<String, Integer> getWordFrequencyCount(String inputFile) throws IOException {
        ConcurrentHashMap<String, Integer> wordCount = new ConcurrentHashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))){
            reader.lines()
                    .flatMap(line -> Arrays.stream(line.replaceAll(REGEX_NON_ALPHANUMERIC, "").toLowerCase().split(REGEX_WHITESPACE)))
                    .filter(word -> !word.isEmpty())
                    .forEach(word -> wordCount.put(word, wordCount.getOrDefault(word, 0) + 1));
        }
        log.info("{} unique words found in file {}", wordCount.size(), inputFile);
        return wordCount;
    }

    /*
    * Sort the word frequency map by key (asc) and value (desc) and print the result to the output stream
    */
    private static void printWordFrequencyMap(ConcurrentHashMap<String, Integer> wordCount, PrintStream printStream) {
         wordCount.entrySet()
                 .stream()
                 .sorted(Map.Entry.comparingByKey())
                 .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                 .forEach(entry -> printStream.println(entry.getKey() + ": " + entry.getValue()));
    }

    private static PrintStream getOutputStream(String outputFile) {
        PrintStream outputStream = System.out;

        if (outputFile != null) {
            try {
                outputStream = new PrintStream(new FileOutputStream(outputFile));
            } catch (FileNotFoundException e) {
                System.out.println("Error - Could not write to file: " + outputFile);
                log.error("Could not write to file: " + outputFile);
            }
        }
        return outputStream;
    }
}