package com.github.pomaretta.termux.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OptionMenuTest {

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
    void showDisplaysTitleAndOptions() {
        String[] options = {"Option A", "Option B"};
        OptionMenu menu = new OptionMenu(options, "", "Test Menu", "%s");
        menu.show();
        String output = outputStream.toString();
        assertTrue(output.contains("Test Menu"));
        assertTrue(output.contains("Option A"));
        assertTrue(output.contains("Option B"));
    }

    @Test
    void showWithNumbersDisplaysNumberedOptions() {
        String[] options = {"First", "Second"};
        OptionMenu menu = new OptionMenu(options, "", "Menu", "%s", 1, true);
        menu.show();
        String output = outputStream.toString();
        assertTrue(output.contains("1"));
        assertTrue(output.contains("First"));
        assertTrue(output.contains("2"));
        assertTrue(output.contains("Second"));
    }

    @Test
    void showWithIndentIncludesIndent() {
        String[] options = {"A"};
        OptionMenu menu = new OptionMenu(options, "\t", "Menu", "%s");
        menu.show();
        String output = outputStream.toString();
        assertTrue(output.contains("\t"));
    }

    @Test
    void constructorAcceptsArrayList() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Option 1");
        OptionMenu menu = new OptionMenu(options, "", "Menu", "%s");
        menu.show();
        String output = outputStream.toString();
        assertTrue(output.contains("Option 1"));
    }

    @Test
    void customTitleLengthAffectsOnlyThisInstance() {
        String[] options = {"A"};
        OptionMenu menu1 = new OptionMenu(options, "", "Short", "%s", 0, false, 20, 1);
        OptionMenu menu2 = new OptionMenu(options, "", "Default", "%s");

        menu1.show();
        String output1 = outputStream.toString();
        outputStream.reset();
        menu2.show();
        String output2 = outputStream.toString();

        assertNotEquals(output1, output2);
    }

    @Test
    void nullOptionsThrowsNullPointer() {
        assertThrows(NullPointerException.class,
            () -> new OptionMenu((String[]) null, "", "Menu", "%s"));
    }

    @Test
    void nullIndentThrowsNullPointer() {
        assertThrows(NullPointerException.class,
            () -> new OptionMenu(new String[]{"A"}, null, "Menu", "%s"));
    }

    @Test
    void emptyOptionsArrayShowsOnlyTitle() {
        OptionMenu menu = new OptionMenu(new String[]{}, "", "Title", "%s");
        menu.show();
        String output = outputStream.toString();
        assertTrue(output.contains("Title"));
    }

    @Test
    void indentWithPercentDoesNotThrow() {
        String[] options = {"A", "B"};
        OptionMenu menu = new OptionMenu(options, "100%", "Menu", "%s", 1, true);
        assertDoesNotThrow(() -> menu.show());
    }

    @Test
    void titleLongerThanTitleLengthThrowsIllegalArgument() {
        assertThrows(IllegalArgumentException.class,
            () -> new OptionMenu(new String[]{"A"}, "", "Very Long Title Here", "%s",
                0, false, 10, 2));
    }
}
