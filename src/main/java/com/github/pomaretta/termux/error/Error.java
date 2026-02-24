package com.github.pomaretta.termux.error;

import java.util.ArrayList;

/**
 * Interface for collecting and retrieving error messages during menu operations.
 *
 * <p>Implementations store error messages as strings and provide
 * methods to add, retrieve, count, and clear them.</p>
 *
 * @author Carlos Pomares
 */
public interface Error {

    /**
     * Removes all stored error messages.
     */
    void clear();

    /**
     * Returns the number of stored error messages.
     *
     * @return the error count
     */
    int size();

    /**
     * Returns all stored error messages.
     *
     * @return a list of error messages (never null, may be empty)
     */
    ArrayList<String> get();

    /**
     * Adds an error message to the log.
     *
     * @param message the error message to store
     */
    void add(String message);
}
