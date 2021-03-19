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

public interface Error {
    public void clear();
    public int size();
    public ArrayList<String> get();
    public void add(String s);
}
