package com.github.pomaretta.termux.menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility for asking a single question and reading validated user input.
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
 * String name = Question.ask("What is your name?", "\t", reader);
 * String email = Question.askWithValidation("Email?", "\t", ".+@.+", reader);
 * }</pre>
 *
 * @author Carlos Pomares
 */
public class Question {

    private static final String DEFAULT_PROMPT = "> ";

    /**
     * Asks a question and returns the user's answer (no validation).
     * Uses the default prompt "> ".
     *
     * @param message the question to display
     * @param indent indentation prefix
     * @param reader the reader for user input
     * @return the user's input
     * @throws IOException if reading input fails
     */
    public static String ask(String message, String indent, BufferedReader reader) throws IOException {
        return ask(message, indent, DEFAULT_PROMPT, reader);
    }

    /**
     * Asks a question with a custom prompt and returns the user's answer (no validation).
     *
     * @param message the question to display
     * @param indent indentation prefix
     * @param prompt the prompt string shown before the input cursor (e.g. "> ")
     * @param reader the reader for user input
     * @return the user's input
     * @throws IOException if reading input fails
     */
    public static String ask(String message, String indent, String prompt, BufferedReader reader) throws IOException {
        System.out.printf("\n%s%s", indent, message);
        System.out.printf("\n%s%s", indent, prompt);
        return reader.readLine();
    }

    /**
     * Asks a question with regex validation. Uses the default prompt "> ".
     *
     * @param message the question to display
     * @param indent indentation prefix
     * @param validation regex pattern the answer must match
     * @param reader the reader for user input
     * @return the validated user input
     * @throws ValidationException if the input does not match the regex
     * @throws IOException if reading input fails
     */
    public static String askWithValidation(String message, String indent, String validation, BufferedReader reader)
            throws ValidationException, IOException {
        return ask(message, indent, DEFAULT_PROMPT, validation, reader);
    }

    /**
     * Asks a question with a custom prompt and regex validation.
     *
     * @param message the question to display
     * @param indent indentation prefix
     * @param prompt the prompt string (e.g. "> ")
     * @param validation regex pattern the answer must match
     * @param reader the reader for user input
     * @return the validated user input
     * @throws ValidationException if the input does not match the regex
     * @throws IOException if reading input fails
     */
    public static String ask(String message, String indent, String prompt, String validation, BufferedReader reader)
            throws ValidationException, IOException {

        Pattern pattern = Pattern.compile(validation);

        System.out.printf("\n%s%s", indent, message);
        System.out.printf("\n%s%s", indent, prompt);

        String answer = reader.readLine();
        Matcher matcher = pattern.matcher(answer);

        if (!matcher.matches()) {
            throw new ValidationException("Validation failed, doesn't match with: " + validation);
        }

        return answer;
    }
}
