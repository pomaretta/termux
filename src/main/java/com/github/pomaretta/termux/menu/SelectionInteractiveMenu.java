package com.github.pomaretta.termux.menu;

import java.io.BufferedReader;
import java.util.Objects;

import com.github.pomaretta.termux.command.DefaultCommandParser;
import com.github.pomaretta.termux.error.ErrorLog;

/**
 * An interactive menu that displays a {@link SelectionMenu} alongside an option menu.
 *
 * @author Carlos Pomares
 */
public abstract class SelectionInteractiveMenu extends DefaultInteractiveMenu {

    protected SelectionMenu selectionMenu;

    /**
     * Creates a new selection interactive menu.
     *
     * @param errorLog the error log
     * @param optionMenu the options menu
     * @param parser the command parser
     * @param reader the input reader
     * @param prompt the prompt string
     * @param selectionMenu the paginated selection menu to display
     */
    public SelectionInteractiveMenu(ErrorLog errorLog, DefaultMenu optionMenu,
                                    DefaultCommandParser parser, BufferedReader reader,
                                    String prompt, SelectionMenu selectionMenu) {
        super(errorLog, optionMenu, parser, reader, prompt);
        Objects.requireNonNull(selectionMenu, "selectionMenu must not be null");
        this.selectionMenu = selectionMenu;
    }

    @Override
    protected void loopBlock() {
        selectionMenu.show();
        optionMenu.show();
    }
}
