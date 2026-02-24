package com.github.pomaretta.termux.menu;

import com.github.pomaretta.termux.error.ItemBoundaryException;
import com.github.pomaretta.termux.error.PageBoundaryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SelectionMenuTest {

    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void captureOutput() {
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void restoreOutput() {
        System.setOut(originalOut);
    }

    private SelectionMenu createMenu(int itemCount) {
        return createMenu(itemCount, 5);
    }

    private SelectionMenu createMenu(int itemCount, int itemsPerPage) {
        ArrayList<Object> items = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            items.add("Item " + i);
        }
        return new SelectionMenu("", items, "\nHeader\n", itemsPerPage) {
            @Override
            protected void showItem(Object item, boolean selected) {
                System.out.printf("\n%s%s", selected ? ">" : " ", item);
            }
        };
    }

    @Test
    void showDisplaysFirstPage() {
        SelectionMenu menu = createMenu(10);
        menu.show();
        String output = outputStream.toString();
        assertTrue(output.contains("Page 1"));
        assertTrue(output.contains("Item 0"));
    }

    @Test
    void selectReturnsFirstItemByDefault() {
        SelectionMenu menu = createMenu(5);
        assertEquals("Item 0", menu.select());
    }

    @Test
    void nextItemMovesSelection() {
        SelectionMenu menu = createMenu(5);
        menu.nextItem();
        assertEquals("Item 1", menu.select());
    }

    @Test
    void previousItemMovesSelectionBack() {
        SelectionMenu menu = createMenu(5);
        menu.nextItem();
        menu.nextItem();
        menu.previousItem();
        assertEquals("Item 1", menu.select());
    }

    @Test
    void previousItemAtFirstThrowsItemBoundary() {
        SelectionMenu menu = createMenu(5);
        assertThrows(ItemBoundaryException.class, menu::previousItem);
    }

    @Test
    void nextItemAtLastOnPageThrowsItemBoundary() {
        SelectionMenu menu = createMenu(10);
        for (int i = 0; i < 4; i++) menu.nextItem();
        assertThrows(ItemBoundaryException.class, menu::nextItem);
    }

    @Test
    void nextPageAdvancesToNextPage() {
        SelectionMenu menu = createMenu(10);
        menu.nextPage();
        menu.show();
        String output = outputStream.toString();
        assertTrue(output.contains("Page 2"));
    }

    @Test
    void previousPageGoesBack() {
        SelectionMenu menu = createMenu(10);
        menu.nextPage();
        menu.previousPage();
        assertEquals("Item 0", menu.select());
    }

    @Test
    void previousPageAtFirstThrowsPageBoundary() {
        SelectionMenu menu = createMenu(10);
        assertThrows(PageBoundaryException.class, menu::previousPage);
    }

    @Test
    void nextPageAtLastThrowsPageBoundary() {
        SelectionMenu menu = createMenu(5);
        assertThrows(PageBoundaryException.class, menu::nextPage);
    }

    @Test
    void customItemsPerPageAffectsOnlyThisInstance() {
        SelectionMenu small = createMenu(10, 2);
        SelectionMenu large = createMenu(10, 10);

        small.nextPage();
        assertThrows(PageBoundaryException.class, large::nextPage);
    }

    @Test
    void nullItemsThrowsNullPointer() {
        assertThrows(NullPointerException.class,
            () -> new SelectionMenu("", null, "Header") {
                @Override
                protected void showItem(Object item, boolean selected) {}
            });
    }
}
