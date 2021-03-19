package Components.Menu;

/*

    Project     Programming21
    Package     Application.Services.Components.Menu
    
    Version     1.0      
    Author      Carlos Pomares
    Date        2021-03-18

    DESCRIPTION
    
*/

import Components.Error.ErrorLog;

import java.io.BufferedReader;
import java.util.ArrayList;

/**
 * @author Carlos Pomares
 */

public class SequentialMenu implements Menu {

    protected String[] questions;
    protected BufferedReader reader;
    protected ArrayList<String> output;
    protected String espace;
    protected String[] validation;
    protected boolean validationActive;
    protected ErrorLog errorLog;
    protected int step;

    public SequentialMenu(String[] q, BufferedReader r, String es, ErrorLog er){
        this.questions = q;
        this.reader = r;
        this.espace = es;
        this.errorLog = er;
        this.output = new ArrayList<>();
    }

    public SequentialMenu(String[] q, String[] v,BufferedReader r, String es, ErrorLog er){
        this.questions = q;
        this.validation = v;
        this.reader = r;
        this.espace = es;
        this.errorLog = er;
        this.output = new ArrayList<>();
        this.validationActive = true;
    }

    private void loop(){
        while (output.size() < questions.length){
            try {
                if(validationActive){
                    output.add(Question.ask(questions[step],espace,validation[step],reader));
                } else {
                    output.add(Question.ask(questions[step],espace,reader));
                }
            } catch (ValidationException validationException){
                errorLog.add(validationException.getMessage());
                decrementStep();
            } catch (Exception ioException){
                errorLog.add(ioException.getMessage());
            } finally {
                incrementStep();
            }
        }
    }

    protected void incrementStep(){
        step++;
    }

    protected void decrementStep(){
        step--;
    }

    protected void resetStep(){
        step = 0;
    }

    private void update(){
        loop();
    }

    public ArrayList<String> getOutput() {
        return output;
    }

    @Override
    public void show() {
        update();
    }

}
