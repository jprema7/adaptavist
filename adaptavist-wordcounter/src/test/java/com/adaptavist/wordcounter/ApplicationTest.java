package com.adaptavist.wordcounter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ApplicationTest {

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
        String[] args = {"src/test/resources/data/input/text.txt"};
        assertDoesNotThrow(() -> Application.main(args));
        String expectedOutput = Files.readString(Paths.get(getClass().getClassLoader().getResource("data/output.txt").toURI()));
        assertEquals(expectedOutput, customOutputCaptor.toString().trim());
    }

    @Test
    void main_printToOutputFile() throws URISyntaxException, IOException {
        String[] args = {"src/test/resources/data/input/text.txt", "src/test/resources/output.txt"};
        assertDoesNotThrow(() -> Application.main(args));
        String output = Files.readString(Paths.get("src/test/resources/output.txt"));
        String expectedOutput = Files.readString(Paths.get(getClass().getClassLoader().getResource("data/output.txt").toURI()));
        assertEquals(expectedOutput, output.trim());
    }

    @Test
    void main_noArguments() {
        assertDoesNotThrow(() -> Application.main(new String[]{}));
        assertEquals("Usage: java Application <input file> <optional: output file>", customOutputCaptor.toString().trim());
    }

    @Test
    void main_invalidInputFile_catchException() {
        assertDoesNotThrow(() -> Application.main(new String[]{"src/test/resources/invalid.txt"}));
        assertEquals("Error - File not found: src/test/resources/invalid.txt", customOutputCaptor.toString().trim());
    }

}