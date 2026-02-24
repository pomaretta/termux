package com.github.pomaretta.termux.menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import com.github.pomaretta.termux.error.ErrorLog;

/**
 * A step-through questionnaire that asks questions in sequence and collects answers.
 *
 * <p>Optionally validates each answer against a regex pattern. On validation failure,
 * the question is re-asked until a valid answer is provided.</p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * String[] questions = {"What is your name?", "What is your email?"};
 * String[] validation = {".+", ".+@.+"};
 * SequentialMenu menu = new SequentialMenu(questions, reader, "\t", errorLog, validation);
 * menu.show();
 * ArrayList<String> answers = menu.getOutput();
 * }</pre>
 *
 * @author Carlos Pomares
 */
public class SequentialMenu implements Menu {

    private final String[] questions;
    private final String[] validation;
    private final BufferedReader reader;
    private final ArrayList<String> output;
    private final String indent;
    private final boolean validationActive;
    private final ErrorLog errorLog;

    /**
     * Creates a sequential menu without validation.
     *
     * @param questions the questions to ask in order
     * @param reader the reader for user input
     * @param indent indentation prefix
     * @param errorLog error log for recording failures
     */
    public SequentialMenu(String[] questions, BufferedReader reader, String indent, ErrorLog errorLog) {
        Objects.requireNonNull(questions, "questions must not be null");
        Objects.requireNonNull(reader, "reader must not be null");
        Objects.requireNonNull(indent, "indent must not be null");
        Objects.requireNonNull(errorLog, "errorLog must not be null");
        this.questions = questions;
        this.reader = reader;
        this.indent = indent;
        this.errorLog = errorLog;
        this.output = new ArrayList<>();
        this.validation = null;
        this.validationActive = false;
    }

    /**
     * Creates a sequential menu with regex validation.
     *
     * @param questions the questions to ask in order
     * @param reader the reader for user input
     * @param indent indentation prefix
     * @param errorLog error log for recording failures
     * @param validation regex patterns, one per question
     * @throws IllegalArgumentException if validation length differs from questions
     */
    public SequentialMenu(String[] questions, BufferedReader reader, String indent,
                          ErrorLog errorLog, String[] validation) {
        Objects.requireNonNull(questions, "questions must not be null");
        Objects.requireNonNull(reader, "reader must not be null");
        Objects.requireNonNull(indent, "indent must not be null");
        Objects.requireNonNull(errorLog, "errorLog must not be null");
        Objects.requireNonNull(validation, "validation must not be null");
        if (validation.length != questions.length) {
            throw new IllegalArgumentException(
                    "validation length (" + validation.length
                            + ") must match questions length (" + questions.length + ")");
        }
        this.questions = questions;
        this.reader = reader;
        this.indent = indent;
        this.errorLog = errorLog;
        this.output = new ArrayList<>();
        this.validation = validation;
        this.validationActive = true;
    }

    /**
     * Returns the collected answers.
     *
     * @return list of answers in the order questions were asked
     */
    public ArrayList<String> getOutput() {
        return output;
    }

    private void loop() {
        while (output.size() < questions.length) {
            int currentIndex = output.size();
            try {
                if (validationActive) {
                    output.add(Question.askWithValidation(questions[currentIndex], indent, validation[currentIndex], reader));
                } else {
                    output.add(Question.ask(questions[currentIndex], indent, reader));
                }
            } catch (ValidationException validationException) {
                errorLog.add(validationException.getMessage());
            } catch (IOException ioException) {
                errorLog.add(ioException.getMessage());
                output.add(""); // Skip question with empty answer due to IOException
            }
        }
    }

    @Override
    public void show() {
        loop();
    }
}
