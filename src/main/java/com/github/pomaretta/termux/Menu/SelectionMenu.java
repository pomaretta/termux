/*

    Project     Termux UX
    Package     java.com.pomaretta.termux.Menu
    
    Version     1.0      
    Author      Carlos Pomares
    Date        2021-03-18

    DESCRIPTION
    
*/

package com.github.pomaretta.termux.Menu;

import java.util.ArrayList;

import com.github.pomaretta.termux.Util.Encapsulate;
import com.github.pomaretta.termux.Util.StringGenerator;

/**
 * @author Carlos Pomares
 */

public abstract class SelectionMenu extends DefaultMenu {

    /**
     * The length size of the showed menu, horizontal length.
     */
    private static int BOX_LENGTH = 35;
    /**
     * Max items per page.
     */
    private static int MAX_ITEM_IN_PAGE = 5;

    /**
     * Max page detected by the number of items.
     */
    private final int MAX_PAGE;
    /**
     * The array of items.
     */
    private final ArrayList<Object> ITEMS;
    /**
     * The header to be shown.
     */
    private final String HEADER;

    /**
     * The current selection of item in visual menu.
     */
    private int selectionIndex;
    /**
     * The current selected page.
     */
    private int pageIndex = 1;
    /**
     * The current minimum selection to prevent go to previous page without changing page.
     */
    private int currentMinSelection = 0;

    /**
     *
     * Selection menu that allows te user navigate by pages and allows to select an object.
     * This menu is for User Experience pack feature.
     *
     * @param e the escape characters.
     * @param items the items to be shown.
     * @param header the title of the menu.
     */
    public SelectionMenu(String e, ArrayList<Object> items, String header) {
        super(e);
        this.ITEMS = items;
        this.HEADER = header;

        // Establish maximum page, by dividing the items size by the maximum items in page and do the ceil of the result.
        this.MAX_PAGE = (int)Math.ceil((double)this.ITEMS.size() / MAX_ITEM_IN_PAGE);

    }

    /**
     *
     * Shows the menu.
     *
     */
    private void showPage(){

        /*
            EXAMPLE:

            PAGE 1
                - ITEM 0-4
            PAGE 2
                - ITEM 5-9
            ...
         */

        // Show the current page number.
        System.out.print("\n" + escapeCharacters + generateHeader(
                String.format("%s %d","Page",pageIndex)
        ));

        // Show the header of the selection menu.
        System.out.print(HEADER);

        /*
            Allows to show the items according to the current selection and the page index.
            Example:

                24 % 5 : 4,8 = CEIL(5)

                PAGE 1 = 0-4
                PAGE 2 = 5-9
                PAGE 3 = 10-14
                ...
        */
        for (int i = currentMinSelection; i < (currentMinSelection + MAX_ITEM_IN_PAGE); i++) {

            Object o;

            // Avoid to get IndexOfBounds exceptions, we must ignore it.
            try {
                o = ITEMS.get(i);
            } catch (IndexOutOfBoundsException exception){
                continue;
            }

            // Show the item.
            if(o != null){
                // Indicate if the item is the current selection.
                showItem(o, i == selectionIndex);
            }

        }

        // Generate the bottom of the menu, is the page index indicator.
        System.out.printf("\n%s\n",escapeCharacters + generateHeader(
                String.format("%s %d/%d"
                        ,"Page"
                        ,pageIndex
                        ,MAX_PAGE
                )
        ));

    }

    /**
     *
     * Shows the header with a title inside.
     *
     * @param s the title to show.
     * @return an String with the string encapsulated.
     */
    private String generateHeader(String s){
        int HEADER_SPACING = 2;
        return Encapsulate.inlineEncapsulate(s, BOX_LENGTH, HEADER_SPACING);
    }

    /**
     *
     * Returns an string with the - character and the length of the BOX_LENGTH variable.
     *
     * @return an string with the - character.
     */
    private String generateBottom(){
        return StringGenerator.generateStringByChar("-", BOX_LENGTH);
    }

    /**
     *
     * Allows to decrement the page index and assign the correct item index.
     *
     * @throws Exception if the minimum page is raised.
     */
    public void previousPage() throws Exception {
        if(this.currentMinSelection - MAX_ITEM_IN_PAGE < 0)
            throw new Exception("MIN PAGE");
        this.currentMinSelection -= MAX_ITEM_IN_PAGE;
        this.selectionIndex = currentMinSelection;
        this.pageIndex--;
    }

    /**
     *
     * Allows to increment the page index and assign the minimum item index in the current page.
     *
     * @throws Exception if the maximum page is raised.
     */
    public void nextPage() throws Exception {
        if(this.currentMinSelection + MAX_ITEM_IN_PAGE > ITEMS.size() - 1)
            throw new Exception("MAX PAGE");
        this.currentMinSelection += MAX_ITEM_IN_PAGE;
        this.selectionIndex = currentMinSelection;
        this.pageIndex++;
    }

    /**
     *
     * Allows to decrement the item index by 1.
     *
     * @throws Exception if the minimum item is raised.
     */
    public void previousItem() throws Exception {
        if(this.selectionIndex - 1 < this.currentMinSelection)
            throw new Exception("MIN ITEM");
        this.selectionIndex--;
    }

    /**
     *
     * Allows to increment the item selection and check
     * if the next item is null.
     *
     * @throws Exception if the maximum item is raised.
     */
    public void nextItem() throws Exception {
        Object o = null;

        // Detect if the next item is not in the items.
        try {
            o = this.ITEMS.get(this.selectionIndex + 1);
        } catch (IndexOutOfBoundsException ignored){}

        // Detect if the current selection is the maximum item.
        if(this.selectionIndex + 1 > (this.currentMinSelection + MAX_ITEM_IN_PAGE - 1))
            throw new Exception("MAX ITEM");

        // Increment the counter.
        if(o != null)
            this.selectionIndex++;
    }

    /**
     * @return returns the item that pointers the selectedIndex.
     */
    public Object select(){
        return this.ITEMS.get(selectionIndex);
    }

    /**
     *
     * Abstract method that the user has to customize that
     * is the way that the items will be showed in the menu.
     * It has the selected metric that allows the user to know
     * if the item is the selected one and show it in another way.
     *
     * @param o the object to be shown.
     * @param selected if the object is the selected one.
     */
    protected abstract void showItem(Object o,boolean selected);

    @Override
    protected void update() {
        showPage();
    }

    @Override
    public void show() {
        update();
    }

    /**
     * Allows to set the maximums items per page.
     * @param maxItemInPage the maximum items per page to be showed.
     */
    public static void setMaxItemInPage(int maxItemInPage) {
        MAX_ITEM_IN_PAGE = maxItemInPage;
    }

    /**
     * Allows to set the length of the menus (horizontal).
     * @param boxLength set the length of the menus (horizontal).
     */
    public static void setBoxLength(int boxLength) {
        BOX_LENGTH = boxLength;
    }

}
