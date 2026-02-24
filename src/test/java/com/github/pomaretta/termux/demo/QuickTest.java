package com.github.pomaretta.termux.demo;

import com.github.pomaretta.termux.console.DefaultConsole;
import com.github.pomaretta.termux.menu.OptionMenu;
import com.github.pomaretta.termux.menu.Question;

/**
 * Simple quick test for Termux library.
 */
public class QuickTest extends DefaultConsole {

    @Override
    protected void run() {
        try {
            System.out.println("=== Quick Termux Test ===\n");
            
            // Simple question
            String input = Question.ask("Enter anything to test input:", "", reader);
            System.out.println("You entered: '" + input + "'\n");
            
            // Basic menu
            String[] options = {"Option 1", "Option 2", "Option 3"};
            OptionMenu menu = new OptionMenu(options, "", "Test Menu", "%s", 1, true);
            menu.show();
            
            System.out.println("\nTest completed successfully!");
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        try (QuickTest test = new QuickTest()) {
            test.start();
        } catch (Exception e) {
            System.err.println("Failed: " + e.getMessage());
        }
    }
}