/*

    Project     Termux UX
    Package     java.com.pomaretta.termux.Error
    
    Version     1.0
    Author      Carlos Pomares
    Date        2021-03-18

*/

package com.pomaretta.termux.Error;

import com.pomaretta.termux.Menu.OptionMenu;

/**
 * @author Carlos Pomares
 */

public class ErrorLog extends DefaultError {

    /**
     *
     * Allows as an user to show the errors in the console.
     *
     * @param e the escape characters of the menu.
     * @param t the title of the menu.
     */
    public void show(String e,String t){
        OptionMenu optionMenu = new OptionMenu(get(),e,t,"%s");
        optionMenu.show();
    }

}
