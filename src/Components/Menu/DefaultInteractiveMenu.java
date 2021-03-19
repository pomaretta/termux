package Components.Menu;

/*

    Project     Programming21
    Package     Application.Services.Components.Menu
    
    Version     1.0      
    Author      Carlos Pomares
    Date        2021-03-18

    DESCRIPTION
    
*/


import Components.Command.CommandParser;
import Components.Error.ErrorLog;

import java.io.BufferedReader;

/**
 * @author Carlos Pomares
 */

public abstract class DefaultInteractiveMenu implements Menu {

    protected ErrorLog errorLog;
    protected DefaultMenu optionMenu;
    protected CommandParser parser;
    protected BufferedReader reader;
    protected String shell;
    protected boolean exit = false;

    public DefaultInteractiveMenu(ErrorLog e, DefaultMenu o, CommandParser p, BufferedReader r,String s) {
        this.errorLog = e;
        this.optionMenu = o;
        this.parser = p;
        this.reader = r;
        this.shell = s;
    }

    private void loop(){
        outsideLoop();
        while (!exit){

            loopBlock();

            try {

                System.out.print(shell);

                if(CommandParser.parseCommand(reader.readLine(),parser) == -1){
                    exit = true;
                }

            } catch (Exception e){
                errorLog.add(e.getMessage());
            }

        }
    }

    protected abstract void outsideLoop();

    protected abstract void loopBlock();

    protected void update(){
        loop();
    }

    @Override
    public void show() {
        update();
    }

}
