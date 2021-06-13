/*

    Project     Termux UX
    Package     java.com.pomaretta.termux.Menu
    
    Version     1.0      
    Author      Carlos Pomares
    Date        2021-03-18

    DESCRIPTION
    
*/

package com.pomaretta.termux.Menu;

/**
 * @author Carlos Pomares
 */

public class ValidationException extends Exception {

    /**
     *
     * An exception to log when a validation dont match.
     *
     * @param message the message to be passed to the exception.
     */
    public ValidationException(String message) {
        super(message);
    }
}
