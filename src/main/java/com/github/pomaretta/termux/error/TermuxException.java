package com.github.pomaretta.termux.error;

/**
 * Base exception for all Termux library errors.
 *
 * <p>This is an unchecked exception so students don't need to write
 * try-catch blocks for every menu operation.</p>
 *
 * @author Carlos Pomares
 */
public class TermuxException extends RuntimeException {

    /**
     * Creates a new TermuxException with the given message.
     *
     * @param message description of what went wrong
     */
    public TermuxException(String message) {
        super(message);
    }
}
