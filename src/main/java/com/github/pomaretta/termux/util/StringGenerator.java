package com.github.pomaretta.termux.util;

import java.util.Objects;

/**
 * Utility for generating repeated-character strings used in menu borders and separators.
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * String border = StringGenerator.generateStringByChar("-", 35);
 * // Returns "-----------------------------------"
 * }</pre>
 *
 * @author Carlos Pomares
 */
public class StringGenerator {

    /**
     * Generates a string by repeating the given character sequence.
     *
     * @param character the character or string to repeat (must not be null)
     * @param length how many times to repeat (must be >= 0)
     * @return a string of the repeated character
     * @throws NullPointerException if character is null
     * @throws IllegalArgumentException if length is negative
     */
    public static String generateStringByChar(String character, int length) {
        Objects.requireNonNull(character, "character must not be null");
        if (length < 0) {
            throw new IllegalArgumentException("length must be >= 0, got: " + length);
        }
        StringBuilder output = new StringBuilder(character.length() * length);
        for (int i = 0; i < length; i++) {
            output.append(character);
        }
        return output.toString();
    }
}
