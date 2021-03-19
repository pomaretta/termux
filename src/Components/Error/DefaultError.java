package Components.Error;

/*

    Project     Programming21
    Package     Application.Services.Components.Error
    
    Version     1.0      
    Author      Carlos Pomares
    Date        2021-03-18

    DESCRIPTION
    
*/

import java.util.ArrayList;

/**
 * @author Carlos Pomares
 */

public abstract class DefaultError implements Error {

    private ArrayList<String> errors;

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
