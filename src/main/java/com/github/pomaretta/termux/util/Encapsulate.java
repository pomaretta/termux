package com.github.pomaretta.termux.util;

/**
 * Utility for centering and bordering text in terminal output.
 *
 * <p>Used internally by menu classes to generate headers, footers, and title bars.</p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * // Center "Page 1" in a 35-character bar with 2-char spacing
 * String header = Encapsulate.inlineEncapsulate("Page 1", 35, 2);
 * // Returns "------------- Page 1 -------------"
 * }</pre>
 *
 * @author Carlos Pomares
 */
public class Encapsulate {

    /**
     * Prints the given text surrounded by a box of dashes.
     *
     * @param toEncapsulate the text to display inside the box
     * @param indent indentation prefix (e.g. "\t" for tab)
     */
    public static void encapsulateString(String toEncapsulate, String indent) {
        encapsulateString(toEncapsulate, indent, "-");
    }

    /**
     * Prints the given text surrounded by a box of the given border character.
     *
     * @param toEncapsulate the text to display inside the box
     * @param indent indentation prefix (e.g. "\t" for tab)
     * @param borderChar the character to use for top/bottom borders
     */
    public static void encapsulateString(String toEncapsulate, String indent, String borderChar) {
        int length = toEncapsulate.length();
        String border = StringGenerator.generateStringByChar(borderChar, length + 2);

        System.out.print("\n" + indent + border);
        System.out.print("\n" + indent + "|" + toEncapsulate + "|");
        System.out.print("\n" + indent + border);
    }

    /**
     * Centers text within a fixed-width bar of dashes, with spacing around the text.
     *
     * @param text the text to center
     * @param totalLength total length of the resulting string
     * @param spacing number of space characters around the text (split left/right)
     * @return the centered text bar
     */
    public static String inlineEncapsulate(String text, int totalLength, int spacing) {
        int leftSpacing = (int) Math.ceil((double) spacing / 2);
        int rightSpacing = (int) Math.floor((double) spacing / 2);
        int remaining = totalLength - text.length() - spacing;
        int leftDashes = (int) Math.ceil((double) remaining / 2);
        int rightDashes = (int) Math.floor((double) remaining / 2);

        return StringGenerator.generateStringByChar("-", leftDashes)
                + StringGenerator.generateStringByChar(" ", leftSpacing)
                + text
                + StringGenerator.generateStringByChar(" ", rightSpacing)
                + StringGenerator.generateStringByChar("-", rightDashes);
    }
}
