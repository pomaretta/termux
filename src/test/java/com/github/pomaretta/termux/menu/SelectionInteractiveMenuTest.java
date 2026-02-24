package com.github.pomaretta.termux.menu;

import com.github.pomaretta.termux.command.DefaultCommandParser;
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

class SelectionInteractiveMenuTest {

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

    private DefaultCommandParser parserReturning(int... returnValues) {
        return new DefaultCommandParser() {
            private int callIndex = 0;

            @Override
            protected int handleCommand(String command) {
                if (callIndex < returnValues.length) {
                    return returnValues[callIndex++];
                }
                return -1;
            }

            @Override
            protected int handleDefault(String command) {
                return 0;
            }
        };
    }

    private SelectionMenu createSelectionMenu() {
        ArrayList<Object> items = new ArrayList<>();
        items.add("Item A");
        items.add("Item B");
        return new SelectionMenu("", items, "\nItems:\n") {
            @Override
            protected void showItem(Object item, boolean selected) {
                System.out.printf("\n%s%s", selected ? ">" : " ", item);
            }
        };
    }

    private OptionMenu simpleOptionMenu() {
        return new OptionMenu(new String[]{"Select", "Exit"}, "", "Actions", "%s");
    }

    @Test
    void loopBlockShowsSelectionMenuThenOptionMenu() {
        SelectionInteractiveMenu menu = new SelectionInteractiveMenu(
                errorLog, simpleOptionMenu(), parserReturning(-1),
                readerOf("cmd\n"), "> ", createSelectionMenu()) {
            @Override
            protected void outsideLoop() {}
        };

        menu.show();

        String output = outputStream.toString();
        int selectionPos = output.indexOf("Item A");
        int optionPos = output.indexOf("Actions");
        assertTrue(selectionPos >= 0, "Selection menu output should appear");
        assertTrue(optionPos >= 0, "Option menu output should appear");
        assertTrue(selectionPos < optionPos, "Selection menu should appear before option menu");
    }

    @Test
    void nullSelectionMenuThrowsNPE() {
        assertThrows(NullPointerException.class, () ->
                new SelectionInteractiveMenu(errorLog, simpleOptionMenu(),
                        parserReturning(-1), readerOf("a\n"), "> ", null) {
                    @Override protected void outsideLoop() {}
                });
    }

    @Test
    void inheritsInteractiveLoopBehavior() {
        int[] outsideLoopCount = {0};

        SelectionInteractiveMenu menu = new SelectionInteractiveMenu(
                errorLog, simpleOptionMenu(), parserReturning(-1),
                readerOf("cmd\n"), "> ", createSelectionMenu()) {
            @Override
            protected void outsideLoop() {
                outsideLoopCount[0]++;
            }
        };

        menu.show();

        assertEquals(1, outsideLoopCount[0]);
    }
}
