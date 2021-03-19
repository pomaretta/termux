package Components.Error;

/*

    Project     Programming21
    Package     Application.Services.Components.Error
    
    Version     1.0      
    Author      Carlos Pomares
    Date        2021-03-18

    DESCRIPTION
    
*/

import Components.Menu.OptionMenu;

/**
 * @author Carlos Pomares
 */

public class ErrorLog extends DefaultError {

    OptionMenu optionMenu;

    public void show(String e,String t){
        optionMenu = new OptionMenu(get(),e,t,"%s");
        optionMenu.show();
    }

}
