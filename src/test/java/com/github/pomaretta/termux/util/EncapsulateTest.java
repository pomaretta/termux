package com.github.pomaretta.termux.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class EncapsulateTest {

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

    @Test
    void inlineEncapsulatesCenterText() {
        String result = Encapsulate.inlineEncapsulate("Page 1", 35, 2);
        assertNotNull(result);
        assertTrue(result.contains("Page 1"));
        assertEquals(35, result.length());
    }

    @Test
    void inlineEncapsulateWithExactFitText() {
        String result = Encapsulate.inlineEncapsulate("A", 5, 2);
        assertNotNull(result);
        assertTrue(result.contains("A"));
        assertEquals(5, result.length());
    }

    @Test
    void encapsulateStringPrintsOutput() {
        Encapsulate.encapsulateString("Hello", "");
        String output = outputStream.toString();
        assertTrue(output.contains("Hello"));
        assertTrue(output.contains("-"));
    }

    @Test
    void encapsulateStringWithIndent() {
        Encapsulate.encapsulateString("Test", "\t");
        String output = outputStream.toString();
        assertTrue(output.contains("\t"));
        assertTrue(output.contains("Test"));
    }
}
