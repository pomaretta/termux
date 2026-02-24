package com.github.pomaretta.termux.console;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultConsoleTest {

    @Test
    void startCallsRun() {
        boolean[] ran = {false};
        DefaultConsole console = new DefaultConsole() {
            @Override
            protected void run() {
                ran[0] = true;
            }
        };
        console.start();
        assertTrue(ran[0]);
    }

    @Test
    void implementsAutoCloseable() {
        DefaultConsole console = new DefaultConsole() {
            @Override
            protected void run() {}
        };
        assertInstanceOf(AutoCloseable.class, console);
    }

    @Test
    void closeDoesNotThrow() {
        DefaultConsole console = new DefaultConsole() {
            @Override
            protected void run() {}
        };
        assertDoesNotThrow(console::close);
    }
}
