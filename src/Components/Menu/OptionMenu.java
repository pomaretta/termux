package Components.Menu;

/*

    Project     Programming21
    Package     Application.Services.Components.Menu
    
    Version     1.0      
    Author      Carlos Pomares
    Date        2021-03-18

    DESCRIPTION
    
*/

import java.util.ArrayList;

/**
 * @author Carlos Pomares
 */

public class OptionMenu extends OptionsMenu {

    private static int TITLE_LENGHT = 35;
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
        super(o, e);
        setTitleAndFormat(t,f);
        this.number = n;
        this.activateNumbers = a;
    }

    public OptionMenu(ArrayList<String> o, String e, String t, String f) {
        super(o, e);
        setTitleAndFormat(t,f);
    }

    public OptionMenu(ArrayList<String> o, String e, String t, String f, int n, boolean a) {
        super(o, e);
        setTitleAndFormat(t,f);
        this.number = n;
        this.activateNumbers = a;
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
        return Encapsulate.inlineEncapsulate(this.title,TITLE_LENGHT,SPACING);
    }

    private String generateBottom(){
        return StringGenerator.generateStringByChar("-",TITLE_LENGHT);
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

    public static void setTitleLenght(int l){
        OptionMenu.TITLE_LENGHT = l;
    }

    public static void setSpacing(int s) {
        OptionMenu.SPACING = s;
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
