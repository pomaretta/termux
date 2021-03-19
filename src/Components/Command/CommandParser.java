package Components.Command;

/*

    Project     Programming21
    Package     Application.Services.Components.Command
    
    Version     1.0      
    Author      Carlos Pomares
    Date        2021-03-18

    DESCRIPTION
    
*/

/**
 * @author Carlos Pomares
 */

public abstract class CommandParser {

    protected abstract int parseBlock(String command) throws Exception;

    public static int parseCommand(String command, CommandParser parser) throws Exception {
        return parser.parseBlock(command);
    }

}
