/*

    Project     Termux UX
    Package     java.com.pomaretta.termux.Menu
    
    Version     1.0      
    Author      Carlos Pomares
    Date        2021-03-18

    DESCRIPTION
    
*/

package java.com.pomaretta.termux.Menu;

import java.com.pomaretta.termux.Command.DefaultCommandParser;
import java.com.pomaretta.termux.Error.ErrorLog;
import java.io.BufferedReader;

/**
 * @author Carlos Pomares
 */

public abstract class DefaultInteractiveMenu implements Menu {

    protected ErrorLog errorLog;
    protected DefaultMenu optionMenu;
    protected DefaultCommandParser parser;
    protected BufferedReader reader;
    protected String shell;
    protected boolean exit = false;

    /**
     *
     * Interactive Menu that allows to make a loop and show up menus and
     * allow the user to enter commands.
     *
     * @param e the errorlog to log errors.
     * @param o the menu to show options.
     * @param p the parser to parse incoming commands.
     * @param r the reader to read user input.
     * @param s the shell of the menu.
     */
    public DefaultInteractiveMenu(ErrorLog e, DefaultMenu o, DefaultCommandParser p, BufferedReader r, String s) {
        this.errorLog = e;
        this.optionMenu = o;
        this.parser = p;
        this.reader = r;
        this.shell = s;
    }

    /**
     *
     * The main loop that consists of a while that have the flag exit,
     * the first call will show the outsideLoop that only shows one time.
     * The loopBlock will be showed every iteration of the while.
     *
     */
    private void loop(){
        outsideLoop();
        while (!exit){

            loopBlock();

            try {

                System.out.print(shell);

                if(DefaultCommandParser.parseCommand(reader.readLine(),parser) == -1){
                    exit = true;
                }

            } catch (Exception e){
                errorLog.add(e.getMessage());
            }

        }
    }

    /**
     *
     * This method will be called once a time, at the first execution of the loop method.
     *
     */
    protected abstract void outsideLoop();

    /**
     *
     * This method will be called each iteration of the structure.
     *
     */
    protected abstract void loopBlock();

    protected void update(){
        loop();
    }

    @Override
    public void show() {
        update();
    }

}
