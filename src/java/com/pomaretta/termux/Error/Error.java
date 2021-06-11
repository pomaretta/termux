/*

    Project     Termux UX
    Package     java.com.pomaretta.termux.Error
    
    Version     1.0      
    Author      Carlos Pomares
    Date        2021-03-18

    DESCRIPTION
    
*/

package java.com.pomaretta.termux.Error;

import java.util.ArrayList;

/**
 * @author Carlos Pomares
 */

public interface Error {

    /**
     * Allows to clear the errors ArrayList.
     */
    void clear();

    /**
     * Returns the size of the current ArrayList of errors.
     * @return the size of the errors array.
     */
    int size();

    /**
     * Returns the ArrayList containing the errors logged.
     * @return an ArrayList with errors or empty ArrayList.
     */
    ArrayList<String> get();

    /**
     * Allows to add an Error logged to the errors Array.
     * @param s the exception message to be logged.
     */
    void add(String s);
}
