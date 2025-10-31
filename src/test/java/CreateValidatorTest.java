import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class CreateValidatorTest {

    String[] command;
    CreateValidator validator;

    @BeforeEach
    void setUp(){
        validator = new CreateValidator();
    }

    @Test
    void missing_account_type_in_command(){
        command = "create".split(" ");
        boolean actual = validator.validate(command);
        assertFalse(actual);
    }

    @Test
    void missing_ID_in_command(){
        command = "create checking".split(" ");
        boolean actual = validator.validate(command);
        assertFalse(actual);
    }

    @Test
    void missing_APR_in_command(){
        command = "create checking 12345678".split(" ");
        boolean actual = validator.validate(command);
        assertFalse(actual);
    }
}
