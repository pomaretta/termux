package com.github.pomaretta.termux.error;

import com.github.pomaretta.termux.menu.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionHierarchyTest {

    @Test
    void termuxExceptionExtendsRuntimeException() {
        TermuxException ex = new TermuxException("test");
        assertInstanceOf(RuntimeException.class, ex);
        assertEquals("test", ex.getMessage());
    }

    @Test
    void pageBoundaryExceptionExtendsTermuxException() {
        PageBoundaryException ex = new PageBoundaryException("min page reached");
        assertInstanceOf(TermuxException.class, ex);
        assertEquals("min page reached", ex.getMessage());
    }

    @Test
    void itemBoundaryExceptionExtendsTermuxException() {
        ItemBoundaryException ex = new ItemBoundaryException("max item reached");
        assertInstanceOf(TermuxException.class, ex);
        assertEquals("max item reached", ex.getMessage());
    }

    @Test
    void validationExceptionExtendsTermuxException() {
        ValidationException ex = new ValidationException("validation failed");
        assertInstanceOf(TermuxException.class, ex);
        assertEquals("validation failed", ex.getMessage());
    }
}
