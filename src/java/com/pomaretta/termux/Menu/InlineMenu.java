package java.com.pomaretta.termux.Menu;

/*

    Project     Programming21
    Package     Application.Services.main.java.com.pomaretta.Components.Menu
    
    Version     1.0      
    Author      Carlos Pomares
    Date        2021-03-18

    DESCRIPTION
    
*/

import java.util.ArrayList;

/**
 * @author Carlos Pomares
 */

public class InlineMenu extends OptionsMenu {

    private static int MAX_ITEMS_PER_ROW = 5;

    private int number;

    public InlineMenu(String[] o, String e, int n) {
        super(o, e);
        this.number = n;
    }

    public InlineMenu(ArrayList<String> o, String e, int n) {
        super(o, e);
        this.number = n;
    }

    private String showOptions(){
        int n = this.number;
        int count = 1;
        String text = "";
        for(String s : this.options){
            if(count > MAX_ITEMS_PER_ROW){
                text += "\n";
                count = 1;
            }
            if(count == 1){
                text += escapeCharacters;
            }
            text += n + " " + s + " ";
            count++;
            n++;
        }
        return text;
    }

    @Override
    protected void update() {
        System.out.printf("\n%s\n",showOptions());
    }

}
