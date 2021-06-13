/*

    Project     Termux UX
    Package     java.com.pomaretta.termux.Error
    
    Version     1.0      
    Author      Carlos Pomares
    Date        2021-03-18

    DESCRIPTION
    
*/

package com.pomaretta.termux.Error;

import java.util.ArrayList;

/**
 * @author Carlos Pomares
 */

public abstract class DefaultError implements Error {

    private final ArrayList<String> errors;

    /**
     * Implements the Error Interface and creates a new ArrayList
     * that will contain the exception messages logged.
     */
    public DefaultError() {
        this.errors = new ArrayList<>();
    }

    @Override
    public ArrayList<String> get(){
        return this.errors;
    }

    @Override
    public void clear(){
        this.errors.clear();
    }

    @Override
    public int size() {
        return errors.size();
    }

    @Override
    public void add(String s){
        this.errors.add(s);
    }

}
