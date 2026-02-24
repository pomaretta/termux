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

import static org.junit.jupiter.api.Assertions.*;

class DefaultInteractiveMenuTest {

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

    private DefaultCommandParser throwingThenReturning() {
        return new DefaultCommandParser() {
            private int callIndex = 0;

            @Override
            protected int handleCommand(String command) throws Exception {
                if (callIndex++ == 0) {
                    throw new RuntimeException("parser error");
                }
                return -1;
            }

            @Override
            protected int handleDefault(String command) {
                return 0;
            }
        };
    }

    private OptionMenu simpleOptionMenu() {
        return new OptionMenu(new String[]{"A"}, "", "Menu", "%s");
    }

    @Test
    void showCallsOutsideLoopThenLoopBlock() {
        int[] outsideLoopCount = {0};
        int[] loopBlockCount = {0};

        DefaultInteractiveMenu menu = new DefaultInteractiveMenu(
                errorLog, simpleOptionMenu(), parserReturning(-1),
                readerOf("cmd\n"), "> ") {
            @Override
            protected void outsideLoop() {
                outsideLoopCount[0]++;
            }

            @Override
            protected void loopBlock() {
                loopBlockCount[0]++;
            }
        };

        menu.show();

        assertEquals(1, outsideLoopCount[0]);
        assertEquals(1, loopBlockCount[0]);
    }

    @Test
    void loopBlockCalledEachIteration() {
        int[] loopBlockCount = {0};

        DefaultInteractiveMenu menu = new DefaultInteractiveMenu(
                errorLog, simpleOptionMenu(), parserReturning(0, 0, -1),
                readerOf("a\nb\nc\n"), "> ") {
            @Override
            protected void outsideLoop() {}

            @Override
            protected void loopBlock() {
                loopBlockCount[0]++;
            }
        };

        menu.show();

        assertEquals(3, loopBlockCount[0]);
    }

    @Test
    void promptIsPrintedEachIteration() {
        DefaultInteractiveMenu menu = new DefaultInteractiveMenu(
                errorLog, simpleOptionMenu(), parserReturning(0, -1),
                readerOf("a\nb\n"), "myPrompt> ") {
            @Override
            protected void outsideLoop() {}

            @Override
            protected void loopBlock() {}
        };

        menu.show();

        String output = outputStream.toString();
        int count = 0;
        int idx = 0;
        while ((idx = output.indexOf("myPrompt> ", idx)) != -1) {
            count++;
            idx += "myPrompt> ".length();
        }
        assertEquals(2, count);
    }

    @Test
    void exceptionInParserIsLoggedAndLoopContinues() {
        DefaultInteractiveMenu menu = new DefaultInteractiveMenu(
                errorLog, simpleOptionMenu(), throwingThenReturning(),
                readerOf("a\nb\n"), "> ") {
            @Override
            protected void outsideLoop() {}

            @Override
            protected void loopBlock() {}
        };

        menu.show();

        assertEquals(1, errorLog.size());
    }

    @Test
    void eofOnReaderExitsLoop() {
        DefaultInteractiveMenu menu = new DefaultInteractiveMenu(
                errorLog, simpleOptionMenu(), parserReturning(0),
                readerOf(""), "> ") {
            @Override
            protected void outsideLoop() {}

            @Override
            protected void loopBlock() {}
        };

        menu.show();

        assertEquals(0, errorLog.size());
    }

    @Test
    void nullErrorLogThrowsNPE() {
        assertThrows(NullPointerException.class, () ->
                new DefaultInteractiveMenu(null, simpleOptionMenu(),
                        parserReturning(-1), readerOf("a\n"), "> ") {
                    @Override protected void outsideLoop() {}
                    @Override protected void loopBlock() {}
                });
    }

    @Test
    void nullOptionMenuThrowsNPE() {
        assertThrows(NullPointerException.class, () ->
                new DefaultInteractiveMenu(errorLog, null,
                        parserReturning(-1), readerOf("a\n"), "> ") {
                    @Override protected void outsideLoop() {}
                    @Override protected void loopBlock() {}
                });
    }

    @Test
    void nullParserThrowsNPE() {
        assertThrows(NullPointerException.class, () ->
                new DefaultInteractiveMenu(errorLog, simpleOptionMenu(),
                        null, readerOf("a\n"), "> ") {
                    @Override protected void outsideLoop() {}
                    @Override protected void loopBlock() {}
                });
    }

    @Test
    void nullReaderThrowsNPE() {
        assertThrows(NullPointerException.class, () ->
                new DefaultInteractiveMenu(errorLog, simpleOptionMenu(),
                        parserReturning(-1), null, "> ") {
                    @Override protected void outsideLoop() {}
                    @Override protected void loopBlock() {}
                });
    }

    @Test
    void nullPromptThrowsNPE() {
        assertThrows(NullPointerException.class, () ->
                new DefaultInteractiveMenu(errorLog, simpleOptionMenu(),
                        parserReturning(-1), readerOf("a\n"), null) {
                    @Override protected void outsideLoop() {}
                    @Override protected void loopBlock() {}
                });
    }
}
