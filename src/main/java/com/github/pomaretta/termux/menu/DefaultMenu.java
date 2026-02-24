package com.github.pomaretta.termux.menu;

import java.util.Objects;

/**
 * Abstract base class for all static (non-interactive) menus.
 *
 * <p>Provides an indentation prefix and the template method pattern:
 * {@link #show()} calls {@link #update()}, which subclasses implement
 * to render their specific content.</p>
 *
 * @author Carlos Pomares
 */
public abstract class DefaultMenu implements Menu {

    /**
     * Indentation prefix printed before each line of menu output.
     */
    protected String indent;

    /**
     * Creates a new menu with the given indentation prefix.
     *
     * @param indent the indentation prefix (must not be null)
     * @throws NullPointerException if indent is null
     */
    public DefaultMenu(String indent) {
        Objects.requireNonNull(indent, "indent must not be null");
        this.indent = indent;
    }

    /**
     * Renders the menu content. Called by {@link #show()}.
     */
    protected abstract void update();

    @Override
    public void show() {
        update();
    }
}
