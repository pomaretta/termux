package com.github.pomaretta.termux.menu;

import java.util.ArrayList;
import java.util.Objects;

import com.github.pomaretta.termux.error.ItemBoundaryException;
import com.github.pomaretta.termux.error.PageBoundaryException;
import com.github.pomaretta.termux.util.Encapsulate;
import com.github.pomaretta.termux.util.StringGenerator;

/**
 * A paginated menu for browsing and selecting items from a list.
 *
 * <p>Subclasses implement {@link #showItem(Object, boolean)} to control
 * how each item is rendered.</p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * ArrayList<Object> items = new ArrayList<>();
 * items.add("Apple");
 * items.add("Banana");
 *
 * SelectionMenu menu = new SelectionMenu("", items, "\nFruits:\n") {
 *     protected void showItem(Object item, boolean selected) {
 *         System.out.printf("\n  %s %s", selected ? ">" : " ", item);
 *     }
 * };
 * menu.show();
 * Object chosen = menu.select();
 * }</pre>
 *
 * @author Carlos Pomares
 */
public abstract class SelectionMenu extends DefaultMenu {

    private final int maxItemsPerPage;
    private final int boxLength;
    private final int maxPage;
    private final ArrayList<Object> items;
    private final String header;

    private int selectionIndex;
    private int pageIndex = 1;
    private int currentMinSelection = 0;

    /**
     * Creates a selection menu with default layout (box length 35, 5 items per page).
     *
     * @param indent indentation prefix
     * @param items the items to browse (must not be null)
     * @param header text displayed above the item list
     * @throws NullPointerException if items is null
     */
    public SelectionMenu(String indent, ArrayList<Object> items, String header) {
        this(indent, items, header, 5, 35);
    }

    /**
     * Creates a selection menu with custom pagination.
     *
     * @param indent indentation prefix
     * @param items the items to browse (must not be null)
     * @param header text displayed above the item list
     * @param maxItemsPerPage maximum items displayed per page
     * @throws NullPointerException if items is null
     */
    public SelectionMenu(String indent, ArrayList<Object> items, String header, int maxItemsPerPage) {
        this(indent, items, header, maxItemsPerPage, 35);
    }

    /**
     * Creates a selection menu with custom pagination and box width.
     *
     * @param indent indentation prefix
     * @param items the items to browse (must not be null)
     * @param header text displayed above the item list
     * @param maxItemsPerPage maximum items displayed per page
     * @param boxLength width of the header/footer bars in characters
     * @throws NullPointerException if items is null
     * @throws IllegalArgumentException if items is empty, maxItemsPerPage &lt;= 0,
     *         or boxLength &lt;= 0
     */
    public SelectionMenu(String indent, ArrayList<Object> items, String header,
                         int maxItemsPerPage, int boxLength) {
        super(indent);
        Objects.requireNonNull(items, "items must not be null");
        if (items.isEmpty()) {
            throw new IllegalArgumentException("items must not be empty");
        }
        if (maxItemsPerPage <= 0) {
            throw new IllegalArgumentException("maxItemsPerPage must be > 0, got: " + maxItemsPerPage);
        }
        if (boxLength <= 0) {
            throw new IllegalArgumentException("boxLength must be > 0, got: " + boxLength);
        }
        this.items = items;
        this.header = header;
        this.maxItemsPerPage = maxItemsPerPage;
        this.boxLength = boxLength;
        this.maxPage = (int) Math.ceil((double) this.items.size() / this.maxItemsPerPage);
    }

    private void showPage() {
        System.out.print("\n" + indent + generateHeader(
                String.format("%s %d", "Page", pageIndex)));
        System.out.print(header);

        int pageEnd = Math.min(currentMinSelection + maxItemsPerPage, items.size());
        for (int i = currentMinSelection; i < pageEnd; i++) {
            Object item = items.get(i);
            if (item != null) {
                showItem(item, i == selectionIndex);
            }
        }

        System.out.printf("\n%s\n", indent + generateHeader(
                String.format("%s %d/%d", "Page", pageIndex, maxPage)));
    }

    private String generateHeader(String text) {
        return Encapsulate.inlineEncapsulate(text, boxLength, 2);
    }

    /**
     * Moves to the previous page.
     *
     * @throws PageBoundaryException if already on the first page
     */
    public void previousPage() {
        if (currentMinSelection - maxItemsPerPage < 0) {
            throw new PageBoundaryException("Already on first page");
        }
        currentMinSelection -= maxItemsPerPage;
        selectionIndex = currentMinSelection;
        pageIndex--;
    }

    /**
     * Moves to the next page.
     *
     * @throws PageBoundaryException if already on the last page
     */
    public void nextPage() {
        if (currentMinSelection + maxItemsPerPage > items.size() - 1) {
            throw new PageBoundaryException("Already on last page");
        }
        currentMinSelection += maxItemsPerPage;
        selectionIndex = currentMinSelection;
        pageIndex++;
    }

    /**
     * Moves the selection to the previous item on the current page.
     *
     * @throws ItemBoundaryException if already on the first item of the page
     */
    public void previousItem() {
        if (selectionIndex - 1 < currentMinSelection) {
            throw new ItemBoundaryException("Already on first item");
        }
        selectionIndex--;
    }

    /**
     * Moves the selection to the next item on the current page.
     *
     * @throws ItemBoundaryException if already on the last item of the page
     */
    public void nextItem() {
        int nextIndex = selectionIndex + 1;
        if (nextIndex >= items.size() || nextIndex > currentMinSelection + maxItemsPerPage - 1) {
            throw new ItemBoundaryException("Already on last item");
        }
        selectionIndex = nextIndex;
    }

    /**
     * Returns the currently selected item.
     *
     * @return the selected item
     */
    public Object select() {
        return items.get(selectionIndex);
    }

    /**
     * Renders a single item. Subclasses implement this to customize display.
     *
     * @param item the item to render
     * @param selected true if this item is the current selection
     */
    protected abstract void showItem(Object item, boolean selected);

    @Override
    protected void update() {
        showPage();
    }

    @Override
    public void show() {
        update();
    }
}
