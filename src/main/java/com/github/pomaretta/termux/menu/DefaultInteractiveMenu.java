package com.github.pomaretta.termux.menu;

import java.io.BufferedReader;
import java.util.Objects;

import com.github.pomaretta.termux.command.DefaultCommandParser;
import com.github.pomaretta.termux.error.ErrorLog;

/**
 * An interactive menu that runs an input loop, displaying menus and processing commands.
 *
 * <p>The lifecycle is:</p>
 * <ol>
 *   <li>{@link #outsideLoop()} - called once before the loop starts</li>
 *   <li>{@link #loopBlock()} - called each iteration to display the menu</li>
 *   <li>User input is read and routed through the {@link DefaultCommandParser}</li>
 *   <li>Loop exits when the parser returns {@code -1}</li>
 * </ol>
 *
 * @author Carlos Pomares
 */
public abstract class DefaultInteractiveMenu implements Menu {

    protected ErrorLog errorLog;
    protected DefaultMenu optionMenu;
    protected DefaultCommandParser parser;
    protected BufferedReader reader;
    protected String prompt;
    protected boolean exit = false;

    /**
     * Creates a new interactive menu.
     *
     * @param errorLog the error log for recording exceptions
     * @param optionMenu the menu to display options
     * @param parser the command parser for handling input
     * @param reader the reader for user input
     * @param prompt the prompt string shown before input (e.g. "> ")
     */
    public DefaultInteractiveMenu(ErrorLog errorLog, DefaultMenu optionMenu,
                                  DefaultCommandParser parser, BufferedReader reader, String prompt) {
        Objects.requireNonNull(errorLog, "errorLog must not be null");
        Objects.requireNonNull(optionMenu, "optionMenu must not be null");
        Objects.requireNonNull(parser, "parser must not be null");
        Objects.requireNonNull(reader, "reader must not be null");
        Objects.requireNonNull(prompt, "prompt must not be null");
        this.errorLog = errorLog;
        this.optionMenu = optionMenu;
        this.parser = parser;
        this.reader = reader;
        this.prompt = prompt;
    }

    private void loop() {
        outsideLoop();
        while (!exit) {
            loopBlock();
            try {
                System.out.print(prompt);
                String line = reader.readLine();
                if (line == null) {
                    exit = true;
                } else if (DefaultCommandParser.parseCommand(line, parser) == -1) {
                    exit = true;
                }
            } catch (Exception e) {
                errorLog.add(e.getMessage());
            }
        }
    }

    /**
     * Called once before the loop starts. Use for one-time setup or welcome messages.
     */
    protected abstract void outsideLoop();

    /**
     * Called each iteration of the loop before reading input. Typically displays the menu.
     */
    protected abstract void loopBlock();

    @Override
    public void show() {
        loop();
    }
}
