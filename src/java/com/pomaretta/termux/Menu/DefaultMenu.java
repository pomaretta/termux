package java.com.pomaretta.termux.Menu;

/*

    Project     Programming21
    Package     Application.Services.main.java.com.pomaretta.Components
    
    Version     1.0      
    Author      Carlos Pomares
    Date        2021-03-18

    DESCRIPTION
    
*/

/**
 * @author Carlos Pomares
 */

public abstract class DefaultMenu implements Menu {


    protected String escapeCharacters;

    public DefaultMenu(String e) {
        this.escapeCharacters = e;
    }

    protected abstract void update();
    
    @Override
    public void show() {
        update();
    }

}
