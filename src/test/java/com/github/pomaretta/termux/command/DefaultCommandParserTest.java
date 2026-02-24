package com.github.pomaretta.termux.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultCommandParserTest {

    private DefaultCommandParser createParser() {
        return new DefaultCommandParser() {
            @Override
            protected int handleCommand(String command) throws Exception {
                switch (command) {
                    case "quit":
                        return -1;
                    case "help":
                        return 0;
                    default:
                        return handleDefault(command);
                }
            }

            @Override
            protected int handleDefault(String command) throws Exception {
                return 0;
            }
        };
    }

    @Test
    void parseCommandDelegatesToHandleCommand() throws Exception {
        DefaultCommandParser parser = createParser();
        assertEquals(-1, DefaultCommandParser.parseCommand("quit", parser));
    }

    @Test
    void parseCommandReturnsZeroForDefaultInput() throws Exception {
        DefaultCommandParser parser = createParser();
        assertEquals(0, DefaultCommandParser.parseCommand("anything", parser));
    }

    @Test
    void parseCommandReturnsZeroForKnownCommand() throws Exception {
        DefaultCommandParser parser = createParser();
        assertEquals(0, DefaultCommandParser.parseCommand("help", parser));
    }
}
