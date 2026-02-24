package com.github.pomaretta.termux.error;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ErrorLogTest {

    private ErrorLog errorLog;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        errorLog = new ErrorLog();
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void restoreOutput() {
        System.setOut(originalOut);
    }

    @Test
    void showDisplaysErrorsAsMenu() {
        errorLog.add("Something went wrong");
        errorLog.show("", "Errors");
        String output = outputStream.toString();
        assertTrue(output.contains("Errors"));
        assertTrue(output.contains("Something went wrong"));
    }

    @Test
    void showWithIndentIncludesIndent() {
        errorLog.add("Error 1");
        errorLog.show("\t", "Error Log");
        String output = outputStream.toString();
        assertTrue(output.contains("\t"));
    }

    @Test
    void showWithNoErrorsProducesOutput() {
        assertDoesNotThrow(() -> errorLog.show("", "Empty"));
        String output = outputStream.toString();
        assertTrue(output.contains("Empty"));
    }

    @Test
    void showWithMultipleErrorsDisplaysAll() {
        errorLog.add("Error A");
        errorLog.add("Error B");
        errorLog.add("Error C");
        errorLog.show("", "Errors");
        String output = outputStream.toString();
        assertTrue(output.contains("Error A"));
        assertTrue(output.contains("Error B"));
        assertTrue(output.contains("Error C"));
    }
}
