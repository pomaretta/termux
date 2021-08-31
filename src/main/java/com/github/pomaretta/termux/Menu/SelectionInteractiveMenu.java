/*

    Project     Termux UX
    Package     java.com.pomaretta.termux.Menu
    
    Version     1.0      
    Author      Carlos Pomares
    Date        2021-03-18

    DESCRIPTION
    
*/

package com.github.pomaretta.termux.Menu;

import java.io.BufferedReader;

import com.github.pomaretta.termux.Command.DefaultCommandParser;
import com.github.pomaretta.termux.Error.ErrorLog;

/**
 * @author Carlos Pomares
 */

public abstract class SelectionInteractiveMenu extends DefaultInteractiveMenu {

    /**
     * The selection menu.
     */
    protected SelectionMenu selectionMenu;

    /**
     *
     * Allows to create a loop within the selection menu that will show up
     * the selection menu within the option menu.
     *
     * @param e the errorlog.
     * @param o the option menu.
     * @param p the command parser.
     * @param r the reader for user input.
     * @param s the shell.
     * @param sm the selection menu.
     */
    public SelectionInteractiveMenu(ErrorLog e, DefaultMenu o, DefaultCommandParser p, BufferedReader r, String s, SelectionMenu sm) {
        super(e, o, p, r, s);
        this.selectionMenu = sm;
    }

    @Override
    protected void loopBlock() {
        selectionMenu.show();
        optionMenu.show();
    }

}
