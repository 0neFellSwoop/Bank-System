package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandStorageTest {

    CommandStorage storage;
    String command;

    @BeforeEach
    void setUp(){
        storage = new CommandStorage();
    }

    @Test
    void store_and_retrieve_an_invalid_command(){
        command = "invalidkbhihih";
        storage.addInvalidCommand(command);
        assertEquals(command, storage.getInvalidCommands().get(0));
    }

    @Test
    void store_multiple_invalid_commands(){
        command = "invalid1 12341 0.2";
        String otherCommand = "crreeeeaaate account for mee";
        storage.addInvalidCommand(command);
        storage.addInvalidCommand(otherCommand);
        assertEquals(command, storage.getInvalidCommands().get(0));
        assertEquals(otherCommand, storage.getInvalidCommands().get(1));
    }

}
