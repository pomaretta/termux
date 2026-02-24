package com.github.pomaretta.termux.menu;

import com.github.pomaretta.termux.error.ErrorLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SequentialMenuTest {

    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;
    private ErrorLog errorLog;

    @BeforeEach
    void setUp() {
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        errorLog = new ErrorLog();
    }

    @AfterEach
    void restoreOutput() {
        System.setOut(originalOut);
    }

    private BufferedReader readerOf(String input) {
        return new BufferedReader(new InputStreamReader(
                new ByteArrayInputStream(input.getBytes())));
    }

    @Test
    void collectsAnswersWithoutValidation() {
        String[] questions = {"Name?", "Age?"};
        BufferedReader reader = readerOf("Alice\n30\n");
        SequentialMenu menu = new SequentialMenu(questions, reader, "", errorLog);
        menu.show();
        ArrayList<String> output = menu.getOutput();
        assertEquals(2, output.size());
        assertEquals("Alice", output.get(0));
        assertEquals("30", output.get(1));
    }

    @Test
    void collectsAnswersWithValidation() {
        String[] questions = {"Number?"};
        String[] validation = {"\\d+"};
        BufferedReader reader = readerOf("42\n");
        SequentialMenu menu = new SequentialMenu(questions, reader, "", errorLog, validation);
        menu.show();
        assertEquals("42", menu.getOutput().get(0));
    }

    @Test
    void retriesOnValidationFailure() {
        String[] questions = {"Number?"};
        String[] validation = {"\\d+"};
        BufferedReader reader = readerOf("abc\n42\n");
        SequentialMenu menu = new SequentialMenu(questions, reader, "", errorLog, validation);
        menu.show();
        assertEquals(1, menu.getOutput().size());
        assertEquals("42", menu.getOutput().get(0));
        assertTrue(errorLog.size() > 0);
    }

    @Test
    void mismatchedArrayLengthsThrowsIllegalArgument() {
        String[] questions = {"Q1?", "Q2?"};
        String[] validation = {".*"};
        BufferedReader reader = readerOf("a\nb\n");
        assertThrows(IllegalArgumentException.class,
            () -> new SequentialMenu(questions, reader, "", errorLog, validation));
    }

    @Test
    void ioExceptionSkipsQuestionAndLogsError() {
        String[] questions = {"Q1?", "Q2?"};
        BufferedReader failingReader = new BufferedReader(new InputStreamReader(
                new ByteArrayInputStream("".getBytes()))) {
            private int callCount = 0;

            @Override
            public String readLine() throws java.io.IOException {
                if (callCount++ == 0) {
                    throw new java.io.IOException("read failure");
                }
                return "answer";
            }
        };

        SequentialMenu menu = new SequentialMenu(questions, failingReader, "", errorLog);
        menu.show();

        assertEquals(2, menu.getOutput().size());
        assertTrue(errorLog.size() >= 1);
    }
}
