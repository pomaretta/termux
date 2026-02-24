package com.github.pomaretta.termux.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.github.pomaretta.termux.error.ErrorLog;

/**
 * Abstract base class for terminal applications.
 *
 * <p>Provides a {@link BufferedReader} for user input and an {@link ErrorLog}
 * for error collection. Implements {@link AutoCloseable} to properly release
 * the input stream.</p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * class MyApp extends DefaultConsole {
 *     protected void run() {
 *         // create menus, interact with user...
 *     }
 * }
 *
 * try (MyApp app = new MyApp()) {
 *     app.start();
 * }
 * }</pre>
 *
 * @author Carlos Pomares
 */
public abstract class DefaultConsole implements Console {

    protected ErrorLog errorLog;
    protected BufferedReader reader;

    /**
     * Creates a new console with a standard input reader and empty error log.
     */
    public DefaultConsole() {
        this.errorLog = new ErrorLog();
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * The main application logic. Subclasses implement this to define
     * what happens when the console starts.
     */
    protected abstract void run();

    @Override
    public void start() {
        run();
    }

    /**
     * Closes the input reader. Call this when the application is done,
     * or use try-with-resources.
     *
     * @throws IOException if closing the reader fails
     */
    @Override
    public void close() throws IOException {
        if (reader != null) {
            reader.close();
        }
    }
}
