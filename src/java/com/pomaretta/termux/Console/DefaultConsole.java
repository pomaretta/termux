/*

    Project     Termux UX
    Package     java.com.pomaretta.termux.Console
    
    Version     1.0      
    Author      Carlos Pomares
    Date        2021-03-18

    DESCRIPTION
    
*/

package java.com.pomaretta.termux.Console;

import java.com.pomaretta.termux.Error.ErrorLog;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author Carlos Pomares
 */

public abstract class DefaultConsole implements Console {

    protected ErrorLog errorLog;
    protected BufferedReader reader;

    /**
     * Creates a new console that initialises an ErrorLog object and a buffered reader
     * for user input features.
     */
    public DefaultConsole() {
        this.errorLog = new ErrorLog();
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Abstract method to be implemented by utility subclasses.
     * This will act as entry point to the Console Class.
     */
    protected abstract void main();

    @Override
    public void start() {
        main();
    }
    
}
