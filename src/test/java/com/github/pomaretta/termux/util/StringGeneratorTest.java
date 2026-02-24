package com.github.pomaretta.termux.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringGeneratorTest {

    @Test
    void generatesDashesOfGivenLength() {
        assertEquals("---", StringGenerator.generateStringByChar("-", 3));
    }

    @Test
    void generatesZeroLengthReturnsEmpty() {
        assertEquals("", StringGenerator.generateStringByChar("-", 0));
    }

    @Test
    void generatesMultiCharacterString() {
        assertEquals("ababab", StringGenerator.generateStringByChar("ab", 3));
    }

    @Test
    void negativeLengthThrowsIllegalArgument() {
        assertThrows(IllegalArgumentException.class,
            () -> StringGenerator.generateStringByChar("-", -1));
    }

    @Test
    void nullCharacterThrowsNullPointer() {
        assertThrows(NullPointerException.class,
            () -> StringGenerator.generateStringByChar(null, 5));
    }
}
