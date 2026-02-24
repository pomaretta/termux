package com.github.pomaretta.termux.error;

/**
 * Thrown when navigating past the first or last page of a paginated menu.
 *
 * @author Carlos Pomares
 */
public class PageBoundaryException extends TermuxException {

    /**
     * Creates a new PageBoundaryException.
     *
     * @param message description such as "Already on first page" or "Already on last page"
     */
    public PageBoundaryException(String message) {
        super(message);
    }
}
