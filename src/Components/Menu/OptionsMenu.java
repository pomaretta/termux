package Components.Menu;

/*

    Project     Programming21
    Package     Application.Services.Components.Menu
    
    Version     1.0      
    Author      Carlos Pomares
    Date        2021-03-18

    DESCRIPTION
    
*/

import java.util.ArrayList;

/**
 * @author Carlos Pomares
 */

public abstract class OptionsMenu extends DefaultMenu {

    protected String[] options;

    public OptionsMenu(String[] o, String e) {
        super(e);
        this.options = o;
    }

    public OptionsMenu(ArrayList<String> o, String e) {
        super(e);
        this.options = o.toArray(new String[0]);
    }

}
