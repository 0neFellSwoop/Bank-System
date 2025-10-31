import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidatorTest {

    String command;
    Validator validator = new Validator();

    @Test
    void missing_command(){
        command = null;
        boolean actual = validator.validate(command);
        assertFalse(actual);
    }

    @Test
    void invalid_command_type(){
        command = "blah";
        boolean actual = validator.validate(command);
        assertFalse(actual);
    }

    @Test
    void typo_in_create_command() {
        command = "craete checking 12345678 0.4";
        boolean actual = validator.validate(command);
        assertFalse(actual);
    }

    @Test
    void command_type_is_case_insensitive(){
        command = "creaTE checking 12345678 0.4";
        boolean actual = validator.validate(command);
        assertTrue(actual);
    }

}
