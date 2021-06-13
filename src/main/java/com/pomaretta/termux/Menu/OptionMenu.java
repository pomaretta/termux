/*

    Project     Termux UX
    Package     java.com.pomaretta.termux.Menu
    
    Version     1.0      
    Author      Carlos Pomares
    Date        2021-03-18

    DESCRIPTION
    
*/

package com.pomaretta.termux.Menu;

import com.pomaretta.termux.Util.Encapsulate;
import com.pomaretta.termux.Util.StringGenerator;
import java.util.ArrayList;

/**
 * @author Carlos Pomares
 */

public class OptionMenu extends OptionsMenu {

    /**
     * The length of the horizontal title filled with characters.
     */
    private static int TITLE_LENGTH = 35;
    /**
     * The spacing for the title.
     */
    private static int SPACING = 2;

    /**
     * The format to show the options.
     */
    protected String format;
    /**
     * The title of the menu.
     */
    protected String title;

    /**
     * The number the start the count.
     */
    protected int number;
    /**
     * If has to show the number in the options.
     */
    protected boolean activateNumbers;

    /**
     *
     * Allows to create a option menu that can be showed in the console.
     *
     * @param o the options to show.
     * @param e the escape characters.
     * @param t the title of the menu.
     * @param f the format of the options.
     */
    public OptionMenu(String[] o, String e, String t, String f) {
        super(o, e);
        setTitleAndFormat(t,f);
    }

    /**
     *
     * Allows to create a menu with the position of the numbers.
     *
     * @param o the options.
     * @param e the escape characters.
     * @param t the title of the menu.
     * @param f the format.
     * @param n the number to start.
     * @param a if has to show the option number.
     */
    public OptionMenu(String[] o, String e, String t, String f, int n, boolean a) {
        this(o,e,t,f);
        this.number = n;
        this.activateNumbers = a;
    }

    /**
     *
     * Allows to create a option menu that can be showed in the console.
     * Support to arraylist.
     *
     * @param o the options to show.
     * @param e the escape characters.
     * @param t the title of the menu.
     * @param f the format of the options.
     */
    public OptionMenu(ArrayList<String> o, String e, String t, String f) {
        this(o.toArray(new String[0]),e,t,f);
    }

    /**
     *
     * Allows to create a menu with the position of the numbers.
     * Support to arraylist.
     *
     * @param o the options.
     * @param e the escape characters.
     * @param t the title of the menu.
     * @param f the format.
     * @param n the number to start.
     * @param a if has to show the option number.
     */
    public OptionMenu(ArrayList<String> o, String e, String t, String f, int n, boolean a) {
        this(o.toArray(new String[0]),e,t,f,n,a);
    }

    private void setTitle(String t){
        this.title = t;
    }

    private void setFormat(String f){
        this.format = f;
    }

    private void setTitleAndFormat(String t, String f){
        setTitle(t);
        setFormat(f);
    }

    private String generateTitle(){
        return Encapsulate.inlineEncapsulate(this.title, TITLE_LENGTH,SPACING);
    }

    private String generateBottom(){
        return StringGenerator.generateStringByChar("-", TITLE_LENGTH);
    }

    /**
     *
     * Allows to show the options of the menu.
     *
     */
    private void showOptions(){
        int n = number;
        for(String option : options){
            if(activateNumbers){
                System.out.printf("\n" + escapeCharacters + "%-5d " + format,n,option);
            } else {
                System.out.printf("\n" + escapeCharacters + format,option);
            }
            n++;
        }
    }

    public static void setTitleLength(int l){
        TITLE_LENGTH = l;
    }

    public static void setSpacing(int s) {
        SPACING = s;
    }

    @Override
    protected void update() {
        // SHOW TITLE
        System.out.printf("\n" + escapeCharacters + "%s",
                generateTitle()
        );
        showOptions();
        // SHOW END LINE
        System.out.printf("\n" + escapeCharacters + "%s\n",
                generateBottom()
        );
    }

}
