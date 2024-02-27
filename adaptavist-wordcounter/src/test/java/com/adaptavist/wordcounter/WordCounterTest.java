package com.adaptavist.wordcounter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class WordCounterTest {

    private static final PrintStream standardOut = System.out;
    private static final ByteArrayOutputStream customOutputCaptor = new ByteArrayOutputStream();

    @BeforeAll
    public static void setup() {
        System.setOut(new PrintStream(customOutputCaptor));

    }

    @AfterAll
    public static void tearDown() throws IOException {
        System.setOut(standardOut);
        Files.deleteIfExists(Paths.get("src/test/resources/output.txt"));
        Files.deleteIfExists(Paths.get("logs/wordcounter.log"));
    }

    @BeforeEach
    public void reset() {
        customOutputCaptor.reset();
    }

    @Test
    void main_printToStandardOutput() throws URISyntaxException, IOException {
        WordCounter.countWordFrequency("src/test/resources/data/input/text.txt", null);
        String expectedOutput = Files.readString(Paths.get(getClass().getClassLoader().getResource("data/output.txt").toURI()));
        assertEquals(expectedOutput, customOutputCaptor.toString().trim());
    }

    @Test
    void main_printToOutputFile() throws URISyntaxException, IOException {
        WordCounter.countWordFrequency("src/test/resources/data/input/text.txt", "src/test/resources/output.txt");
        String output = Files.readString(Paths.get("src/test/resources/output.txt"));
        String expectedOutput = Files.readString(Paths.get(getClass().getClassLoader().getResource("data/output.txt").toURI()));
        assertEquals(expectedOutput, output.trim());
    }

    @Test
    void main_emptyFile() throws IOException {
        WordCounter.countWordFrequency("src/test/resources/data/input/empty.txt", null);
        assertEquals("", customOutputCaptor.toString().trim());
    }

    @Test
    void main_nonAlphanumericFile() throws IOException {
        WordCounter.countWordFrequency("src/test/resources/data/input/non-alphanumeric.txt", null);
        assertEquals("", customOutputCaptor.toString().trim());
    }

    @Test
    void main_invalidInputFile_throwException() {
        assertThrows(FileNotFoundException.class, () -> WordCounter.countWordFrequency("src/test/resources/invalid.txt", null));
    }
}