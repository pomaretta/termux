/*

    Project     Termux UX
    Package     java.com.pomaretta.termux.Util
    
    Version     1.0      
    Author      Carlos Pomares
    Date        2021-03-18

    DESCRIPTION
    
*/

package java.com.pomaretta.termux.Util;

/**
 * @author Carlos Pomares
 */

public class StringGenerator {

    /**
     *
     * Allows to generate an String with the character parameter and the
     * desired size.
     *
     * @param character the character to be filled the string.
     * @param length the length of the output String.
     * @return and String with number of characters specified on the length parameter.
     */
    public static String generateStringByChar(String character,double length){
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < length; i++) {
            output.append(character);
        }
        return output.toString();
    }

}
