package Console.Parser;

import com.github.pomaretta.termux.Command.DefaultCommandParser;

public abstract class ExampleParser extends DefaultCommandParser {

    @Override
    protected int parseBlock(String command) throws Exception {
        switch (command){
            case "hello":
                System.out.println("World!");
                break;
            case "help":
                System.out.println("Help me!");
                break;
            default:
                return callBack(command);
        }
        return 0;
    }

}
