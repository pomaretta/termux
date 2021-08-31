/*

    Project     Termux UX
    Package     java.com.pomaretta.termux.Menu

    Version     1.0      
    Author      Carlos Pomares
    Date        2021-03-18

    DESCRIPTION
    
*/

package com.github.pomaretta.termux.Menu;

import java.io.BufferedReader;
import java.util.ArrayList;

import com.github.pomaretta.termux.Error.ErrorLog;

/**
 * @author Carlos Pomares
 */

public class SequentialMenu implements Menu {

    /**
     * Question to be presented to the user.
     */
    protected String[] questions;

    /**
     * The validation expressions, must be ordered as the questions.
     */
    protected String[] validation;

    /**
     * The reader for get the input of the user.
     */
    protected BufferedReader reader;

    /**
     * Output of the questions.
     */
    protected ArrayList<String> output;

    /**
     * The escape characters.
     */
    protected String escape;

    /**
     * Allows to know if has to validate.
     */
    protected boolean validationActive;

    /**
     * ErrorLog to allow log validation exceptions or others.
     */
    protected ErrorLog errorLog;

    /**
     * Current step of the sequential menu.
     */
    protected int step;

    /**
     *
     * Allows to create a Object that will ask the user questions according to the
     * String array of the class in order.
     *
     * @param q the questions to be answered.
     * @param r the reader to get the user input.
     * @param es the escape characters to be shown with the menu.
     * @param er the ErrorLog to log the errors.
     */
    public SequentialMenu(String[] q, BufferedReader r, String es, ErrorLog er){
        this.questions = q;
        this.reader = r;
        this.escape = es;
        this.errorLog = er;
        this.output = new ArrayList<>();
    }

    /**
     *
     * Allows to create a Object that will ask the user questions according to the
     * String array of the class in order and will validate the input with an regular
     * expression and check if match true.
     *
     * @param q the questions to be answered.
     * @param r the reader to get the user input.
     * @param es the escape characters to be shown with the menu.
     * @param er the ErrorLog to log the errors.
     * @param v the validation array containing the regular expressions. Must be equal size as the question array.
     */
    public SequentialMenu(String[] q, BufferedReader r, String es, ErrorLog er,String[] v){
        this(q,r,es,er);
        this.validation = v;
        this.validationActive = true;
    }

    /**
     *
     * The main loop that will check the current step with the size of the questions.
     * This will obtain the question result and do some behaviour.
     *
     * If some exception occurs, according to the catch, if it is validation, will log the
     * exception and will decrement an step to repeat the current failed question. That's why
     * on the try-catch block, on the finally we increment the step.
     *
     */
    private void loop(){
        while (output.size() < questions.length){
            try {
                // Check if the question has to be validated.
                if(validationActive){
                    output.add(Question.ask(questions[step], escape,validation[step],reader));
                } else {
                    output.add(Question.ask(questions[step], escape,reader));
                }
            } catch (ValidationException validationException){
                errorLog.add(validationException.getMessage());
                decrementStep();
            } catch (Exception ioException){
                errorLog.add(ioException.getMessage());
            } finally {
                /*
                    Increment the step by one, this action
                    will be ignored if the validation
                    exception is called.
                */
                incrementStep();
            }
        }
    }

    /**
     * Allows to increment the step counter by 1.
     */
    protected void incrementStep(){
        step++;
    }

    /**
     * Allows to decrement the step counter by 1.
     */
    protected void decrementStep(){
        step--;
    }

    /**
     * Allows to reset the steps counter to 0.
     */
    protected void resetSteps(){
        step = 0;
    }

    /**
     * The update block will call the loop block that will ask a question by the according step.
     */
    private void update(){
        loop();
    }

    /**
     * Allows to get the output of the result of the answered questions.
     * @return an arraylist containing the answers.
     */
    public ArrayList<String> getOutput() {
        return output;
    }

    @Override
    public void show() {
        update();
    }

}
