package com.github.pomaretta.termux.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.regex.PatternSyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {

    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void captureOutput() {
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
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
    void askReturnsUserInput() throws Exception {
        BufferedReader reader = readerOf("hello\n");
        String result = Question.ask("What?", "", reader);
        assertEquals("hello", result);
    }

    @Test
    void askDisplaysQuestionAndDefaultPrompt() throws Exception {
        BufferedReader reader = readerOf("answer\n");
        Question.ask("Name?", "\t", reader);
        String output = outputStream.toString();
        assertTrue(output.contains("Name?"));
        assertTrue(output.contains("> "));
    }

    @Test
    void askWithCustomPromptDisplaysCustomPrompt() throws Exception {
        BufferedReader reader = readerOf("answer\n");
        Question.ask("Name?", "", ">> ", reader);
        String output = outputStream.toString();
        assertTrue(output.contains(">> "));
    }

    @Test
    void askWithValidationReturnsInputWhenValid() throws Exception {
        BufferedReader reader = readerOf("test@example.com\n");
        String result = Question.askWithValidation("Email?", "", ".*@.*", reader);
        assertEquals("test@example.com", result);
    }

    @Test
    void askWithValidationThrowsOnInvalidInput() {
        BufferedReader reader = readerOf("notanemail\n");
        assertThrows(ValidationException.class,
            () -> Question.askWithValidation("Email?", "", ".*@.*", reader));
    }

    @Test
    void validationExceptionIsUnchecked() {
        BufferedReader reader = readerOf("bad\n");
        assertThrows(RuntimeException.class,
            () -> Question.askWithValidation("Q?", "", "^good$", reader));
    }

    @Test
    void askReturnsNullOnEOF() throws Exception {
        BufferedReader reader = readerOf("");
        String result = Question.ask("Q?", "", reader);
        assertNull(result);
    }

    @Test
    void askWithValidationThrowsNPEOnEOF() {
        BufferedReader reader = readerOf("");
        assertThrows(NullPointerException.class,
            () -> Question.askWithValidation("Q?", "", ".*", reader));
    }

    @Test
    void invalidRegexThrowsPatternSyntaxException() {
        BufferedReader reader = readerOf("input\n");
        assertThrows(PatternSyntaxException.class,
            () -> Question.askWithValidation("Q?", "", "[invalid", reader));
    }

    @Test
    void askWithPercentInIndentDoesNotThrow() throws Exception {
        BufferedReader reader = readerOf("answer\n");
        assertDoesNotThrow(() -> Question.ask("Q?", "%percent", reader));
    }

    @Test
    void matchesRequiresFullStringMatch() {
        BufferedReader reader = readerOf("abc42\n");
        assertThrows(ValidationException.class,
            () -> Question.askWithValidation("Q?", "", "\\d+", reader));
    }
}
