package com.github.pomaretta.termux.menu;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Abstract base class for menus that display a list of string options.
 *
 * @author Carlos Pomares
 */
public abstract class AbstractOptionsMenu extends DefaultMenu {

    protected String[] options;

    /**
     * Creates a new options menu.
     *
     * @param options the options to display (must not be null)
     * @param indent the indentation prefix (must not be null)
     * @throws NullPointerException if options or indent is null
     */
    public AbstractOptionsMenu(String[] options, String indent) {
        super(indent);
        Objects.requireNonNull(options, "options must not be null");
        this.options = options;
    }

    /**
     * Creates a new options menu from an ArrayList.
     *
     * @param options the options to display (must not be null)
     * @param indent the indentation prefix (must not be null)
     * @throws NullPointerException if options or indent is null
     */
    public AbstractOptionsMenu(ArrayList<String> options, String indent) {
        this(options.toArray(new String[0]), indent);
    }
}
