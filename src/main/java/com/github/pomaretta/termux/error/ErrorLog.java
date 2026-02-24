package com.github.pomaretta.termux.error;

import com.github.pomaretta.termux.menu.OptionMenu;

/**
 * Error collection that can display its contents as an {@link OptionMenu}.
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * ErrorLog log = new ErrorLog();
 * log.add("File not found");
 * log.add("Invalid input");
 * log.show("\t", "Errors");
 * }</pre>
 *
 * @author Carlos Pomares
 */
public class ErrorLog extends DefaultError {

    /**
     * Displays all stored errors as a numbered option menu.
     *
     * @param indent indentation prefix for the menu display
     * @param title the title shown above the error list
     */
    public void show(String indent, String title) {
        OptionMenu optionMenu = new OptionMenu(get(), indent, title, "%s");
        optionMenu.show();
    }
}
