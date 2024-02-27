package com.adaptavist.wordcounter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Application {

    private static final Logger log = LoggerFactory.getLogger(WordCounter.class);

    public static void main(String[] args) {
        if (args.length < 1 || args.length > 2) {
            System.out.println("Usage: java Application <input file> <optional: output file>");
            log.error("Usage: java Application <input file> <optional: output file>");
            return;
        }

        Arguments arguments = Arguments.parse(args);
        String inputFile = arguments.inputFile();
        log.info("Counting words in file: " + inputFile);

        try {
            WordCounter.countWordFrequency(inputFile, arguments.outputFile());
        } catch (FileNotFoundException e) {
            System.out.println("Error - File not found: " + inputFile);
            log.error("File not found: " + inputFile);
        } catch (IOException e) {
            System.out.println("Error - Could not read file: " + inputFile);
            log.error("Could not read file: " + inputFile);
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            log.error("An error occurred: ", e);
        }
    }
}
