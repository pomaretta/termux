/*

    Project     Termux UX
    Package     java.com.pomaretta.termux.Menu
    
    Version     1.0      
    Author      Carlos Pomares
    Date        2021-03-18

    DESCRIPTION
    
*/

package java.com.pomaretta.termux.Menu;

import java.util.ArrayList;

/**
 * @author Carlos Pomares
 */

public class InlineMenu extends OptionsMenu {

    private static int MAX_ITEMS_PER_ROW = 5;

    /**
     * The option number to start by.
     */
    private final int number;

    /**
     *
     * A InlineMenu that shows up the options by an horizontal direction, and
     * when the max item is reached, will do a new line.
     *
     * @param o the options.
     * @param e the escape characters.
     * @param n the number to start by.
     */
    public InlineMenu(String[] o, String e, int n) {
        super(o, e);
        this.number = n;
    }

    /**
     *
     * A InlineMenu that shows up the options by an horizontal direction, and
     * when the max item is reached, will do a new line.
     * Support to ArrayList options.
     *
     * @param o the options.
     * @param e the escape characters.
     * @param n the number to start by.
     */
    public InlineMenu(ArrayList<String> o, String e, int n) {
        this(o.toArray(new String[0]),e,n);
    }

    /**
     *
     * Shows the options.
     *
     * @return the menu.
     */
    private String showOptions(){
        int n = this.number;
        int count = 1;
        StringBuilder text = new StringBuilder();
        for(String s : this.options){
            if(count > MAX_ITEMS_PER_ROW){
                text.append("\n");
                count = 1;
            }
            if(count == 1){
                text.append(escapeCharacters);
            }
            text.append(String.format("%d %s ",n,s));
            count++;
            n++;
        }
        return text.toString();
    }

    public static void setMaxItemsPerRow(int maxItemsPerRow) {
        MAX_ITEMS_PER_ROW = maxItemsPerRow;
    }

    @Override
    protected void update() {
        System.out.printf("\n%s\n",showOptions());
    }

}
