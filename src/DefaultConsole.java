/*

    Project     Programming21
    Package     Application.Services.Console    
    
    Version     1.0      
    Author      Carlos Pomares
    Date        2021-03-18

    DESCRIPTION
    
*/

import Components.Error.ErrorLog;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author Carlos Pomares
 */

public abstract class DefaultConsole implements Console {

    protected ErrorLog errorLog;
    protected BufferedReader reader;

    public DefaultConsole() {
        this.errorLog = new ErrorLog();
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    protected abstract void main();

    @Override
    public void start() {
        main();
    }
    
}
