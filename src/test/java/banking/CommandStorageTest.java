package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandStorageTest {

    CommandStorage storage;
    String command;
    String otherCommand;

    @BeforeEach
    void setUp(){
        storage = new CommandStorage();
    }

    @Test
    void store_and_retrieve_an_invalid_command(){
        command = "invalidkbhihih";
        storage.addInvalidCommand(command);
        assertEquals(command, storage.getInvalidCommands().getFirst());
    }

    @Test
    void store_multiple_invalid_commands(){
        command = "invalid1 12341 0.2";
        otherCommand = "crreeeeaaate account for mee";
        storage.addInvalidCommand(command);
        storage.addInvalidCommand(otherCommand);
        assertEquals(command, storage.getInvalidCommands().getFirst());
        assertEquals(otherCommand, storage.getInvalidCommands().get(1));
    }

    @Test
    void store_and_retirieve_a_valid_command(){
        command = "pass 1";
        storage.addValidCommand(command);
        assertEquals(command, storage.getValidCommands().get(0));
    }

    @Test
    void store_multiple_valid_commands(){
        command = "pass 1";
        otherCommand = "cReate savings 12345678 5";
        storage.addValidCommand(command);
        storage.addValidCommand(otherCommand);
        assertEquals(command, storage.getValidCommands().get(0));
        assertEquals(otherCommand, storage.getValidCommands().get(1));
    }

}
