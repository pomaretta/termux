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

public abstract class SelectionMenu extends DefaultMenu {

    private static int BOX_LENGHT = 35;
    private static int HEADER_SPACING = 2;
    private static int MAX_ITEM_IN_PAGE = 5;

    private int selectionIndex;
    private int pageIndex = 1;
    private int MAX_PAGE;
    private int currentMinSelection = 0;

    private ArrayList<Object> items;

    private String header;

    public SelectionMenu(String e, ArrayList<Object> items, String header) {
        super(e);
        this.items = items;
        this.header = header;
        this.MAX_PAGE = (int)Math.ceil((double)this.items.size() / MAX_ITEM_IN_PAGE);
    }

    private void showPage(){

        // PAGE 1
        // ITEM 0-4
        // PAGE 2
        // ITEM 5-9

        System.out.print("\n" + escapeCharacters + generateHeader(
                String.format("%s %d","Page",pageIndex)
        ));

        // SHOW HEADER
        System.out.print(header);

        // 24 % 5 : 4,8 = CEIL(5)
        // PAGE 0 = 0-4 5-9 10-14
        for (int i = currentMinSelection; i < (currentMinSelection + MAX_ITEM_IN_PAGE); i++) {

            Object o;

            try {
                o = items.get(i);
            } catch (IndexOutOfBoundsException exception){
                continue;
            }

            if(o != null){
                showItem(o, i == selectionIndex);
            }

        }


        System.out.printf("\n%s\n",escapeCharacters + generateHeader(
                String.format("%s %d/%d"
                        ,"Page"
                        ,pageIndex
                        ,MAX_PAGE
                )
        ));

    }

    private String generateHeader(String s){
        return Encapsulate.inlineEncapsulate(s,BOX_LENGHT,HEADER_SPACING);
    }

    private String generateBottom(){
        return StringGenerator.generateStringByChar("-", BOX_LENGHT);
    }

    public void previousPage() throws Exception {
        if(this.currentMinSelection - MAX_ITEM_IN_PAGE < 0)
            throw new Exception("MIN PAGE");
        this.currentMinSelection -= MAX_ITEM_IN_PAGE;
        this.pageIndex--;
    }

    public void nextPage() throws Exception {
        if(this.currentMinSelection + MAX_ITEM_IN_PAGE > items.size() - 1)
            throw new Exception("MAX PAGE");
        this.currentMinSelection += MAX_ITEM_IN_PAGE;
        this.pageIndex++;
    }

    public void previousItem() throws Exception {
        if(this.selectionIndex - 1 < this.currentMinSelection)
            throw new Exception("MIN ITEM");
        this.selectionIndex--;
    }

    public void nextItem() throws Exception {
        Object o = null;
        try {
            o = this.items.get(this.selectionIndex + 1);
        } catch (IndexOutOfBoundsException exception){
            //
        }
        if(this.selectionIndex + 1 > (this.currentMinSelection + MAX_ITEM_IN_PAGE - 1))
            throw new Exception("MAX ITEM");
        if(o != null)
            this.selectionIndex++;
    }

    public Object select(){
        return this.items.get(selectionIndex);
    }

    protected abstract void showItem(Object o,boolean selected);

    @Override
    protected void update() {
        showPage();
    }

    @Override
    public void show() {
        update();
    }

    public static void setMaxItemInPage(int maxItemInPage) {
        MAX_ITEM_IN_PAGE = maxItemInPage;
    }

}
