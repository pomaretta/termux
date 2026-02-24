package com.github.pomaretta.termux.menu;

import java.util.ArrayList;

import com.github.pomaretta.termux.util.Encapsulate;
import com.github.pomaretta.termux.util.StringGenerator;

/**
 * A vertical numbered menu that displays options with a title bar and footer.
 *
 * <p>Example output:</p>
 * <pre>
 * ------------- My Menu -------------
 *   1     Option A
 *   2     Option B
 *   3     Exit
 * -----------------------------------
 * </pre>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * String[] options = {"Start", "Settings", "Exit"};
 * OptionMenu menu = new OptionMenu(options, "\t", "Main Menu", "%s", 1, true);
 * menu.show();
 * }</pre>
 *
 * @author Carlos Pomares
 */
public class OptionMenu extends AbstractOptionsMenu {

    private final int titleLength;
    private final int spacing;
    private final String format;
    private final String title;
    private final int number;
    private final boolean activateNumbers;

    /**
     * Creates an option menu with default layout (title length 35, spacing 2, no numbering).
     *
     * @param options the options to display
     * @param indent indentation prefix
     * @param title the title shown in the header bar
     * @param format printf format string for each option (e.g. "%s")
     */
    public OptionMenu(String[] options, String indent, String title, String format) {
        this(options, indent, title, format, 0, false, 35, 2);
    }

    /**
     * Creates a numbered option menu with default layout.
     *
     * @param options the options to display
     * @param indent indentation prefix
     * @param title the title shown in the header bar
     * @param format printf format string for each option
     * @param startNumber the number to start counting from
     * @param showNumbers whether to display numbers next to options
     */
    public OptionMenu(String[] options, String indent, String title, String format,
                      int startNumber, boolean showNumbers) {
        this(options, indent, title, format, startNumber, showNumbers, 35, 2);
    }

    /**
     * Creates a numbered option menu with custom layout dimensions.
     *
     * @param options the options to display
     * @param indent indentation prefix
     * @param title the title shown in the header bar
     * @param format printf format string for each option
     * @param startNumber the number to start counting from
     * @param showNumbers whether to display numbers next to options
     * @param titleLength the width of the title/footer bars in characters
     * @param spacing spaces around the title text in the header bar
     */
    public OptionMenu(String[] options, String indent, String title, String format,
                      int startNumber, boolean showNumbers, int titleLength, int spacing) {
        super(options, indent);
        this.title = title;
        this.format = format;
        this.number = startNumber;
        this.activateNumbers = showNumbers;
        this.titleLength = titleLength;
        this.spacing = spacing;
    }

    /**
     * Creates an option menu from an ArrayList with default layout.
     *
     * @param options the options to display
     * @param indent indentation prefix
     * @param title the title shown in the header bar
     * @param format printf format string for each option
     */
    public OptionMenu(ArrayList<String> options, String indent, String title, String format) {
        this(options.toArray(new String[0]), indent, title, format);
    }

    /**
     * Creates a numbered option menu from an ArrayList with default layout.
     *
     * @param options the options to display
     * @param indent indentation prefix
     * @param title the title shown in the header bar
     * @param format printf format string for each option
     * @param startNumber the number to start counting from
     * @param showNumbers whether to display numbers next to options
     */
    public OptionMenu(ArrayList<String> options, String indent, String title, String format,
                      int startNumber, boolean showNumbers) {
        this(options.toArray(new String[0]), indent, title, format, startNumber, showNumbers);
    }

    private String generateTitle() {
        return Encapsulate.inlineEncapsulate(this.title, titleLength, spacing);
    }

    private String generateBottom() {
        return StringGenerator.generateStringByChar("-", titleLength);
    }

    private void showOptions() {
        int n = number;
        for (String option : options) {
            if (activateNumbers) {
                System.out.printf("\n" + indent + "%-5d " + format, n, option);
            } else {
                System.out.printf("\n" + indent + format, option);
            }
            n++;
        }
    }

    @Override
    protected void update() {
        System.out.printf("\n" + indent + "%s", generateTitle());
        showOptions();
        System.out.printf("\n" + indent + "%s\n", generateBottom());
    }
}
