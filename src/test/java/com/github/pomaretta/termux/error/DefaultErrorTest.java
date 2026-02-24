package com.github.pomaretta.termux.error;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultErrorTest {

    private ErrorLog errorLog;

    @BeforeEach
    void setUp() {
        errorLog = new ErrorLog();
    }

    @Test
    void startsEmpty() {
        assertEquals(0, errorLog.size());
        assertTrue(errorLog.get().isEmpty());
    }

    @Test
    void addIncrementsSize() {
        errorLog.add("error 1");
        assertEquals(1, errorLog.size());
        assertEquals("error 1", errorLog.get().get(0));
    }

    @Test
    void addMultipleErrors() {
        errorLog.add("error 1");
        errorLog.add("error 2");
        assertEquals(2, errorLog.size());
    }

    @Test
    void clearRemovesAll() {
        errorLog.add("error 1");
        errorLog.add("error 2");
        errorLog.clear();
        assertEquals(0, errorLog.size());
    }
}
