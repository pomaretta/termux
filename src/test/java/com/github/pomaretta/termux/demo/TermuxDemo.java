package com.github.pomaretta.termux.demo;

import com.github.pomaretta.termux.console.DefaultConsole;
import com.github.pomaretta.termux.menu.OptionMenu;
import com.github.pomaretta.termux.menu.Question;
import com.github.pomaretta.termux.menu.InlineMenu;
import com.github.pomaretta.termux.menu.SequentialMenu;
import com.github.pomaretta.termux.menu.ValidationException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Demo application showing Termux library features.
 * Demonstrates various menu types and user interactions.
 */
public class TermuxDemo extends DefaultConsole {

    @Override
    protected void run() {
        try {
            System.out.println("=== Termux CLI Library Demo ===\n");
            
            // Demo 1: Simple questions
            demoQuestions();
            
            // Demo 2: Option menu
            demoOptionMenu();
            
            // Demo 3: Inline menu
            demoInlineMenu();
            
            // Demo 4: Sequential questions
            demoSequentialQuestions();
            
            System.out.println("\nDemo completed! Thanks for trying Termux.");
            
        } catch (Exception e) {
            System.err.println("Demo error: " + e.getMessage());
        }
    }
    
    private void demoQuestions() throws IOException {
        System.out.println("--- Basic Questions Demo ---");
        
        // Simple question
        String name = Question.ask("What's your name?", "\t", reader);
        System.out.println("\tHello, " + name + "!\n");
        
        // Custom prompt
        String color = Question.ask("Favorite color?", "\t", ">> ", reader);
        System.out.println("\tNice choice: " + color + "\n");
        
        // Validation question
        try {
            String email = Question.askWithValidation("Email (must contain @)?", "\t", ".*@.*", reader);
            System.out.println("\tValid email: " + email + "\n");
        } catch (ValidationException e) {
            System.out.println("\tValidation failed, skipping...\n");
        }
    }
    
    private void demoOptionMenu() throws IOException {
        System.out.println("--- Option Menu Demo ---");
        String[] options = {
            "View Profile", 
            "Settings", 
            "Help", 
            "Exit"
        };
        
        OptionMenu menu = new OptionMenu(options, "\t", "Main Menu", "%s", 1, true);
        menu.show();
        
        System.out.println("\nSelect an option (1-" + options.length + "):");
        String choice = reader.readLine();
        System.out.println("\tYou selected: " + choice + "\n");
    }
    
    private void demoInlineMenu() throws IOException {
        System.out.println("--- Inline Menu Demo ---");
        String[] choices = {"Yes", "No", "Maybe", "Cancel"};
        
        InlineMenu menu = new InlineMenu(choices, "\t", 1);
        menu.show();
        
        System.out.println("\nYour choice:");
        String selection = reader.readLine();
        System.out.println("\tSelected: " + selection + "\n");
    }
    
    private void demoSequentialQuestions() throws IOException {
        System.out.println("--- Sequential Questions Demo ---");
        
        String[] questions = {
            "Username (letters only):",
            "Password (min 6 chars):",
            "Confirm password:"
        };
        
        String[] validations = {
            "[a-zA-Z]+",           // Letters only
            ".{6,}",               // At least 6 characters  
            ".+"                   // Any non-empty string
        };
        
        try {
            SequentialMenu sequential = new SequentialMenu(questions, reader, "\t", errorLog, validations);
            sequential.show();
            ArrayList<String> answers = sequential.getOutput();
            
            System.out.println("\tRegistration complete!");
            System.out.println("\tUsername: " + answers.get(0));
            System.out.println("\tPassword: [HIDDEN]");
            System.out.println("\tMatch: " + answers.get(1).equals(answers.get(2)) + "\n");
            
        } catch (ValidationException e) {
            System.out.println("\tSequential questions failed: " + e.getMessage() + "\n");
        }
    }
    
    public static void main(String[] args) {
        try (TermuxDemo demo = new TermuxDemo()) {
            demo.start();
        } catch (IOException e) {
            System.err.println("Failed to close demo: " + e.getMessage());
        }
    }
}