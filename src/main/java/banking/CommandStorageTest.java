package banking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


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
        Assertions.assertEquals(command, storage.getInvalidCommands().get(0));
    }

    @Test
    void store_multiple_invalid_commands(){
        command = "invalid1 12341 0.2";
        String otherCommand = "crreeeeaaate account for mee";
        storage.addInvalidCommand(command);
        storage.addInvalidCommand(otherCommand);
        Assertions.assertEquals(command, storage.getInvalidCommands().get(0));
        Assertions.assertEquals(otherCommand, storage.getInvalidCommands().get(1));
    }

}
