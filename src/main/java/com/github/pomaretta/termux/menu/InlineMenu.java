package com.github.pomaretta.termux.menu;

import java.util.ArrayList;

/**
 * A horizontal menu that displays numbered options in rows.
 *
 * <p>When the maximum items per row is reached, options wrap to the next line.</p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * String[] options = {"New", "Open", "Save", "Exit"};
 * InlineMenu menu = new InlineMenu(options, "\t", 1);
 * menu.show();
 * }</pre>
 *
 * @author Carlos Pomares
 */
public class InlineMenu extends AbstractOptionsMenu {

    private final int maxItemsPerRow;
    private final int number;

    /**
     * Creates an inline menu with default max items per row (5).
     *
     * @param options the options to display
     * @param indent indentation prefix
     * @param startNumber the number to start counting from
     */
    public InlineMenu(String[] options, String indent, int startNumber) {
        this(options, indent, startNumber, 5);
    }

    /**
     * Creates an inline menu with a custom max items per row.
     *
     * @param options the options to display
     * @param indent indentation prefix
     * @param startNumber the number to start counting from
     * @param maxItemsPerRow maximum number of options per line before wrapping
     */
    public InlineMenu(String[] options, String indent, int startNumber, int maxItemsPerRow) {
        super(options, indent);
        this.number = startNumber;
        this.maxItemsPerRow = maxItemsPerRow;
    }

    /**
     * Creates an inline menu from an ArrayList with default max items per row (5).
     *
     * @param options the options to display
     * @param indent indentation prefix
     * @param startNumber the number to start counting from
     */
    public InlineMenu(ArrayList<String> options, String indent, int startNumber) {
        this(options.toArray(new String[0]), indent, startNumber);
    }

    /**
     * Creates an inline menu from an ArrayList with a custom max items per row.
     *
     * @param options the options to display
     * @param indent indentation prefix
     * @param startNumber the number to start counting from
     * @param maxItemsPerRow maximum number of options per line before wrapping
     */
    public InlineMenu(ArrayList<String> options, String indent, int startNumber, int maxItemsPerRow) {
        this(options.toArray(new String[0]), indent, startNumber, maxItemsPerRow);
    }

    private String showOptions() {
        int n = this.number;
        int count = 1;
        StringBuilder text = new StringBuilder();
        for (String s : this.options) {
            if (count > maxItemsPerRow) {
                text.append("\n");
                count = 1;
            }
            if (count == 1) {
                text.append(indent);
            }
            text.append(String.format("%d %s ", n, s));
            count++;
            n++;
        }
        return text.toString();
    }

    @Override
    protected void update() {
        System.out.printf("\n%s\n", showOptions());
    }
}
