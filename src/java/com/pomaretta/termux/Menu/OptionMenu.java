/*

    Project     Termux UX
    Package     java.com.pomaretta.termux.Menu
    
    Version     1.0      
    Author      Carlos Pomares
    Date        2021-03-18

    DESCRIPTION
    
*/

package java.com.pomaretta.termux.Menu;

import java.com.pomaretta.termux.Util.Encapsulate;
import java.com.pomaretta.termux.Util.StringGenerator;
import java.util.ArrayList;

/**
 * @author Carlos Pomares
 */

public class OptionMenu extends OptionsMenu {

    private static int TITLE_LENGTH = 35;
    private static int SPACING = 2;

    protected String format;
    protected String title;

    protected int number;
    protected boolean activateNumbers;

    public OptionMenu(String[] o, String e, String t, String f) {
        super(o, e);
        setTitleAndFormat(t,f);
    }

    public OptionMenu(String[] o, String e, String t, String f, int n, boolean a) {
        this(o,e,t,f);
        this.number = n;
        this.activateNumbers = a;
    }

    public OptionMenu(ArrayList<String> o, String e, String t, String f) {
        this(o.toArray(new String[0]),e,t,f);
    }

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
