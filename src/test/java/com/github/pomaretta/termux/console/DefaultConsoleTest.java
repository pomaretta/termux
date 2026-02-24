package com.github.pomaretta.termux.console;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class DefaultConsoleTest {

    private InputStream originalIn;

    @BeforeEach
    void setUp() {
        originalIn = System.in;
        System.setIn(new ByteArrayInputStream("".getBytes()));
    }

    @AfterEach
    void restoreInput() {
        System.setIn(originalIn);
    }

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

    @Test
    void closeIsIdempotent() {
        DefaultConsole console = new DefaultConsole() {
            @Override
            protected void run() {}
        };
        assertDoesNotThrow(() -> {
            console.close();
            console.close();
        });
    }

    @Test
    void startPropagatesRuntimeException() {
        DefaultConsole console = new DefaultConsole() {
            @Override
            protected void run() {
                throw new RuntimeException("test error");
            }
        };
        RuntimeException ex = assertThrows(RuntimeException.class, console::start);
        assertEquals("test error", ex.getMessage());
    }
}
