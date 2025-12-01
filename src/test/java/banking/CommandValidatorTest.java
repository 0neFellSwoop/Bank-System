package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandValidatorTest {

    String command;
    CommandValidator commandValidator;
    Bank BANK;

    @BeforeEach
    void setUp(){
        BANK = new Bank();
        commandValidator = new CommandValidator(BANK);
    }

    @Test
    void missing_command(){
        boolean actual = commandValidator.validate(null);
        assertFalse(actual);
    }

    @Test
    void invalid_command_type(){
        command = "blah";
        boolean actual = commandValidator.validate(command);
        assertFalse(actual);
    }

    @Test
    void typo_in_create_command() {
        command = "craete checking 12345678 0.4";
        boolean actual = commandValidator.validate(command);
        assertFalse(actual);
    }

    @Test
    void typo_in_deposit_command(){
        command = "deepsoit 12345678 454";
        boolean actual = commandValidator.validate(command);
        assertFalse(actual);
    }


    @Test
    void command_type_is_case_insensitive(){
        command = "creaTE checking 12345678 0.4";
        boolean actual = commandValidator.validate(command);
        assertTrue(actual);
    }

}
