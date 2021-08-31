/*

    Project     Termux UX
    Package     java.com.pomaretta.termux.Command
    
    Version     1.0      
    Author      Carlos Pomares
    Date        2021-03-18

    DESCRIPTION
    
*/

package com.github.pomaretta.termux.Command;

/**
 * @author Carlos Pomares
 */

public abstract class DefaultCommandParser {

    /**
     *
     * Allows to create custom behaviours to a given String command.
     * An example of use is with a switch statement and return int numbers or
     * execute methods inside.
     *
     * Where has to be implemented must be configured with custom behaviours.
     *
     * Tip: For default behaviour return 0.
     *
     * @param command command to be parsed, as String to apply logical operations.
     * @return a given number, with union with menus, return -1 is for exit and 0 is for default behaviour and do nothing.
     * @throws Exception by given methods inside it may return an exception and this will raise to up.
     */
    protected abstract int parseBlock(String command) throws Exception;

    /**
     *
     * For use in a menu, this will request for a command parse and will
     * redirect the input to the parseBlock that the user implements.
     *
     * @param command the command to be parsed.
     * @param parser the parser to process the command and call parseBlock.
     * @return by the return of the parseBlock, usually -1 for exit and 0 for nothing.
     * @throws Exception if the parseBlock raise an Exception.
     */
    public static int parseCommand(String command, DefaultCommandParser parser) throws Exception {
        return parser.parseBlock(command);
    }

    /**
     *
     * Allows to execute commands passing the parseBlock Middleware.
     *
     * @param command the input command.
     * @return an input default 0, for exit the menus return -1.
     * @throws Exception for something inside the callback.
     */
    protected abstract int callBack(String command) throws Exception;

}
