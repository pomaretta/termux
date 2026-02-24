package com.github.pomaretta.termux.menu;

import com.github.pomaretta.termux.error.TermuxException;

/**
 * Thrown when user input fails regex validation in a {@link Question} or {@link SequentialMenu}.
 *
 * @author Carlos Pomares
 */
public class ValidationException extends TermuxException {

    /**
     * Creates a new ValidationException.
     *
     * @param message description of the validation failure
     */
    public ValidationException(String message) {
        super(message);
    }
}
