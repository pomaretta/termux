/*

    Project     Termux UX
    Package     java.com.pomaretta.termux.Menu
    
    Version     1.0      
    Author      Carlos Pomares
    Date        2021-03-18

    DESCRIPTION
    
*/

package com.pomaretta.termux.Menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Carlos Pomares
 */

public class Question {

    /**
     * Default shell.
     */
    protected static String shell = "> ";

    /**
     *
     * Allows to ask a question to the user, will show the question
     * and will wait for an answer that will be caught by the reader.
     *
     * @param message the message to show up (question).
     * @param escape the escape characters.
     * @param reader the reader for the user input.
     * @return the answer of the user, in String.
     * @throws Exception if something with the reader fails.
     */
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

    /**
     *
     * Allows to ask a question to the user, will show the question
     * and will wait for an answer that will be caught by the reader.
     * This will apply a middleware validation that will validate the answer
     * before return it, if the validation fails, will raise a ValidationException.
     *
     * @param message the message to show up (question).
     * @param escape the escape characters.
     * @param validation the validation expression.
     * @param reader the reader to get the user input.
     * @return the user input, in String object.
     * @throws ValidationException if the validation dont match.
     * @throws IOException if something with the reader fails.
     */
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

    /**
     * @param shell the shell to be shown in the questions.
     */
    public static void setShell(String shell) {
        Question.shell = shell;
    }

}
