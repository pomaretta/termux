package Components.Menu;

/*

    Project     Programming21
    Package     Application.Services.Components.Menu
    
    Version     1.0      
    Author      Carlos Pomares
    Date        2021-03-18

    DESCRIPTION
    
*/

/**
 * @author Carlos Pomares
 */

public class StringGenerator {

    public static String generateStringByChar(String character,double lenght){
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < lenght; i++) {
            output.append(character);
        }
        return output.toString();
    }

}
