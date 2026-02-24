package com.github.pomaretta.termux.error;

import java.util.ArrayList;

/**
 * ArrayList-backed implementation of the {@link Error} interface.
 *
 * <p>Stores error messages in insertion order. This is the base class
 * for {@link ErrorLog} which adds display capabilities.</p>
 *
 * @author Carlos Pomares
 */
public abstract class DefaultError implements Error {

    private final ArrayList<String> errors;

    /**
     * Creates a new empty error collection.
     */
    public DefaultError() {
        this.errors = new ArrayList<>();
    }

    @Override
    public ArrayList<String> get() {
        return this.errors;
    }

    @Override
    public void clear() {
        this.errors.clear();
    }

    @Override
    public int size() {
        return errors.size();
    }

    @Override
    public void add(String message) {
        this.errors.add(message);
    }
}
