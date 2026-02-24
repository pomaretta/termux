package com.github.pomaretta.termux.error;

/**
 * Thrown when navigating past the first or last item on the current page.
 *
 * @author Carlos Pomares
 */
public class ItemBoundaryException extends TermuxException {

    /**
     * Creates a new ItemBoundaryException.
     *
     * @param message description such as "Already on first item" or "Already on last item"
     */
    public ItemBoundaryException(String message) {
        super(message);
    }
}
