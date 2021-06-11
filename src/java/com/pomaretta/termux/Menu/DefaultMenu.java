/*

    Project     Termux UX
    Package     java.com.pomaretta.termux.Menu
    
    Version     1.0      
    Author      Carlos Pomares
    Date        2021-03-18

    DESCRIPTION
    
*/

package java.com.pomaretta.termux.Menu;

/**
 * @author Carlos Pomares
 */

public abstract class DefaultMenu implements Menu {

    protected String escapeCharacters;

    /**
     *
     * Default menu containing the escape characters.
     *
     * @param e the escape characters.
     */
    public DefaultMenu(String e) {
        this.escapeCharacters = e;
    }

    /**
     *
     * Method called by the show method.
     *
     */
    protected abstract void update();
    
    @Override
    public void show() {
        update();
    }

}
