package com.github.pomaretta.termux.command;

/**
 * Abstract command parser that routes user input to handler methods.
 *
 * <p>Subclasses implement {@link #handleCommand(String)} to process recognized commands
 * (typically via a switch statement) and {@link #handleDefault(String)} as a fallback
 * for unrecognized input (typically numeric selections).</p>
 *
 * <p>Return values: {@code -1} signals the interactive menu to exit its loop,
 * {@code 0} signals to continue.</p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * DefaultCommandParser parser = new DefaultCommandParser() {
 *     protected int handleCommand(String command) throws Exception {
 *         switch (command) {
 *             case "help": System.out.println("Help!"); return 0;
 *             case "quit": return -1;
 *             default: return handleDefault(command);
 *         }
 *     }
 *     protected int handleDefault(String command) throws Exception {
 *         int choice = Integer.parseInt(command);
 *         // handle numeric selection...
 *         return 0;
 *     }
 * };
 * }</pre>
 *
 * @author Carlos Pomares
 */
public abstract class DefaultCommandParser {

    /**
     * Processes a recognized command string.
     *
     * <p>Implementations should use a switch/if-else to match known commands,
     * and delegate to {@link #handleDefault(String)} for unrecognized input.</p>
     *
     * @param command the user input to process
     * @return {@code -1} to exit the menu loop, {@code 0} to continue
     * @throws Exception if command processing fails
     */
    protected abstract int handleCommand(String command) throws Exception;

    /**
     * Routes a command string through the given parser.
     *
     * @param command the user input
     * @param parser the parser to handle the command
     * @return the result code from {@link #handleCommand(String)}
     * @throws Exception if command processing fails
     */
    public static int parseCommand(String command, DefaultCommandParser parser) throws Exception {
        return parser.handleCommand(command);
    }

    /**
     * Handles input that was not matched by {@link #handleCommand(String)}.
     *
     * <p>Typically used to handle numeric menu selections or other fallback behavior.</p>
     *
     * @param command the unrecognized input
     * @return {@code -1} to exit the menu loop, {@code 0} to continue
     * @throws Exception if processing fails
     */
    protected abstract int handleDefault(String command) throws Exception;
}
