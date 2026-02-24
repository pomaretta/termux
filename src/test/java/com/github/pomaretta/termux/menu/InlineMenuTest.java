package com.github.pomaretta.termux.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InlineMenuTest {

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
    void showDisplaysOptionsHorizontally() {
        String[] options = {"A", "B", "C"};
        InlineMenu menu = new InlineMenu(options, "", 1);
        menu.show();
        String output = outputStream.toString();
        assertTrue(output.contains("1 A"));
        assertTrue(output.contains("2 B"));
        assertTrue(output.contains("3 C"));
    }

    @Test
    void wrapsAtMaxItemsPerRow() {
        String[] options = {"A", "B", "C", "D", "E", "F"};
        InlineMenu menu = new InlineMenu(options, "", 1, 3);
        menu.show();
        String output = outputStream.toString();
        String[] lines = output.trim().split("\n");
        assertTrue(lines.length >= 2);
    }

    @Test
    void customMaxItemsDoesNotAffectOtherInstances() {
        String[] opts = {"A", "B", "C", "D"};
        InlineMenu narrow = new InlineMenu(opts, "", 1, 2);
        InlineMenu wide = new InlineMenu(opts, "", 1, 10);

        narrow.show();
        String narrowOutput = outputStream.toString();
        outputStream.reset();
        wide.show();
        String wideOutput = outputStream.toString();

        assertNotEquals(narrowOutput, wideOutput);
    }

    @Test
    void constructorAcceptsArrayList() {
        ArrayList<String> options = new ArrayList<>();
        options.add("X");
        InlineMenu menu = new InlineMenu(options, "", 1);
        menu.show();
        assertTrue(outputStream.toString().contains("X"));
    }
}
