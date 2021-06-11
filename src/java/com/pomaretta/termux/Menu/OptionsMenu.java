/*

    Project     Termux UX
    Package     java.com.pomaretta.termux.Menu
    
    Version     1.0      
    Author      Carlos Pomares
    Date        2021-03-18

    DESCRIPTION
    
*/

package java.com.pomaretta.termux.Menu;

import java.util.ArrayList;

/**
 * @author Carlos Pomares
 */

public abstract class OptionsMenu extends DefaultMenu {

    protected String[] options;

    /**
     *
     * Creates a new Option menu with an array of options and escape character.
     *
     * @param o the options, as an array.
     * @param e the escape characters.
     */
    public OptionsMenu(String[] o, String e) {
        super(e);
        this.options = o;
    }

    /**
     *
     * Overload of the constructor to accept ArrayLists.
     *
     * @param o the options in a ArrayList object.
     * @param e the escape characters.
     */
    public OptionsMenu(ArrayList<String> o, String e) {
        this(o.toArray(new String[0]),e);
    }

}
