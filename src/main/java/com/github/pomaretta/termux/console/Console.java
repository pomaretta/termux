package com.github.pomaretta.termux.console;

/**
 * Entry point interface for a terminal application.
 *
 * <p>Implementations define the application logic in a subclass of
 * {@link DefaultConsole} and call {@link #start()} to begin.</p>
 *
 * @author Carlos Pomares
 */
public interface Console extends AutoCloseable {

    /**
     * Starts the console application.
     */
    void start();
}
