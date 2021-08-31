package Console;

import Console.Parser.ExampleParser;
import com.github.pomaretta.termux.Console.DefaultConsole;
import com.github.pomaretta.termux.Menu.DefaultInteractiveMenu;
import com.github.pomaretta.termux.Menu.OptionMenu;
import com.github.pomaretta.termux.Menu.OptionsMenu;

public class ExampleConsole extends DefaultConsole {

    private void init(){

        String[] options = {
                "Amazing option!",
                "Go to submenu",
                "Exit"
        };

        OptionsMenu menu = new OptionMenu(options,"","Example Start Menu","%s",1,true);

        ExampleParser parser = new ExampleParser() {
            @Override
            protected int callBack(String command) throws Exception {
                switch (Integer.parseInt(command)){
                    case 1:
                        System.out.println("Amazing text to show up your amazing option!.");
                        break;
                    case 2:
                        submenuFeature();
                        break;
                    case 3:
                        return -1;
                }
                return 0;
            }
        };

        DefaultInteractiveMenu interactiveMenu = new DefaultInteractiveMenu(
                this.errorLog
                ,menu
                ,parser
                ,this.reader
                ,"> "
        ) {
            @Override
            protected void outsideLoop() {
                System.out.printf("%s\n%s","Amazing tip to show up one time!","Type \"help\" to show up some super tips!");
            }

            @Override
            protected void loopBlock() {
                this.optionMenu.show();
            }
        };

        interactiveMenu.show();

    }

    private void submenuFeature(){

        String[] options = {
                "Amazing submenu feature!",
                "Go back to menu!"
        };

        OptionsMenu menu = new OptionMenu(options,"","Example Submenu","%s",1,true);

        ExampleParser parser = new ExampleParser() {
            @Override
            protected int callBack(String command) throws Exception {
                switch (Integer.parseInt(command)){
                    case 1:
                        System.out.println("Amazing text to show up your amazing option!.");
                        break;
                    case 2:
                        return -1;
                }
                return 0;
            }
        };

        DefaultInteractiveMenu interactiveMenu = new DefaultInteractiveMenu(
                this.errorLog
                ,menu
                ,parser
                ,this.reader
                ,"> "
        ) {
            @Override
            protected void outsideLoop() {
                System.out.printf("%s\n%s","Amazing tip to show up one time!","Type \"help\" to show up some super tips!");
            }

            @Override
            protected void loopBlock() {
                this.optionMenu.show();
            }
        };

        interactiveMenu.show();

    }

    @Override
    protected void main() {
        init();
    }

}
