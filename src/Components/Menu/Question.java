package Components.Menu;

/*

    Project     Programming21
    Package     Application.Services.Components.Menu
    
    Version     1.0      
    Author      Carlos Pomares
    Date        2021-03-18

    DESCRIPTION
    
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Carlos Pomares
 */

public class Question {

    public static String shell = "> ";

    public static String ask(String message, String escape, BufferedReader reader)
            throws Exception {
        System.out.printf("\n" + escape + "%s",
                message
        );
        System.out.printf("\n" + escape + "%s",
                shell
        );
        return reader.readLine();
    }

    public static String ask(String message, String escape, String validation, BufferedReader reader)
            throws ValidationException, IOException {

        Pattern pattern = Pattern.compile(validation);
        Matcher matcher;
        String answer;

        System.out.printf("\n" + escape + "%s",
                message
        );
        System.out.printf("\n" + escape + "%s",
                shell
        );

        answer = reader.readLine();
        matcher = pattern.matcher(answer);

        if(!matcher.find()){
            throw new ValidationException("Validation failed, doesn't match with: " + validation);
        }

        return answer;
    }

}
